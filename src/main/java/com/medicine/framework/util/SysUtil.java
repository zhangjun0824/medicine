package com.medicine.framework.util;

import com.medicine.framework.util.ffmpeg.FFmpegUtil;
import com.medicine.framework.util.swf.DOC2PDFUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SysUtil {


    public static String encodePath(String srcPath) {
        if (File.separator.equals("/")) {
            srcPath = srcPath.replace("\\", "/");
        } else {
            srcPath = srcPath.replace("/", "\\");
        }
        return srcPath;
    }

    public static String encodePassword(String password, String salt) {
        Md5PasswordEncoder md5 = new Md5PasswordEncoder();
        md5.setEncodeHashAsBase64(false);
        return md5.encodePassword(password, salt);
    }

    public static String uploadFile(String fileTypeStr, String num,MultipartFile file) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String url = "";
        String filePath = "";
      
        try {
            if (!file.isEmpty()) {
                GlobalProperties.init();
                String attachmentPath = GlobalProperties.getGlobalProperty("attachmentPath");

                Date createDate = new Date();

                url = formatter.format(createDate) + num;

                if (null != fileTypeStr && !fileTypeStr.equals("")) {
                    filePath = SysUtil.encodePath(attachmentPath + "/" + fileTypeStr + "/" + url + "." + checkFormat(file.getOriginalFilename()));
                }
                // 转存文件
                File newFile = new File(filePath);

                if (!newFile.exists()) {
                    newFile.mkdirs();
                }

                file.transferTo(newFile); // 上传文件实质性操作

                // todo 将文件转换为flash格式的再存储一份
                // 表达式对象
                Pattern p = Pattern.compile("^(wmv|avi|mp4|3gp|flv)$");
                // 创建 Matcher 对象
                Matcher m = p.matcher(checkFormat(file.getOriginalFilename()));
                // 是否完全匹配
                if (m.matches()) {
                    String ffmpegPath = GlobalProperties.getGlobalProperty("ffmpegPath");

                    // 输出文件路径
                    String path = SysUtil.encodePath(
                            attachmentPath + "/" + fileTypeStr + "/lit");

                    mkdirPath(path);

                    // 视频截图
                    FFmpegUtil.videoScreenshot(ffmpegPath, filePath,path + "/" + url + ".jpg");
                }

                // 表达式对象
                p = Pattern.compile("^(doc|docx|xls|xlsx|pdf|txt)$");
                // 创建 Matcher 对象
                m = p.matcher(checkFormat(file.getOriginalFilename()));

                if (m.matches()) {

                    String fileName = url + "." + checkFormat(file.getOriginalFilename());

                    // 输出文件路径
                    String path = SysUtil.encodePath(attachmentPath + "/" + fileTypeStr);

                    DOC2PDFUtil.beginConvert(path, null, fileName, null);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return url;
    }

    public static void mkdirPath(String path) {
        File newFile = new File(new String(path));
        if (!newFile.exists()) {
            newFile.mkdirs();
        }
    }

    /**
     * 上传到指定的path中
     *
     * @param file
     * @param path
     * @param request
     */
    @Deprecated
    public static void uploadFile(MultipartFile file, String path) {
        try {
            if (!file.isEmpty()) {
                path = SysUtil.encodePath(path);
                // 转存文件
                File newFile = new File(path);
                if (!newFile.exists()) {
                    newFile.mkdirs();
                }
                file.transferTo(newFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 上传多个文件到指定的path中
     *
     * @param path
     * @param request
     */
    public static void uploadFiles(MultipartFile[] multipartFile, String path) {
        try {
            if (multipartFile != null && multipartFile.length > 0) {
                for (int i = 0; i < multipartFile.length; i++) {
                    MultipartFile file = multipartFile[i];
                    if (!file.isEmpty()) {
                        String filePath = SysUtil.encodePath(
                                path + "/" + file.getOriginalFilename());
                        // 转存文件
                        File newFile = new File(filePath);
                        if (!newFile.exists()) {
                            newFile.mkdirs();
                        }
                        file.transferTo(newFile);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param filename
     * @return String
     * @Title: checkFormat
     * @Descriptiong:用于获取文件后缀
     * @author: lhb
     * @Date:2015-5-4 下午2:43:42
     */
    public static String checkFormat(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1).toLowerCase();
            }
        }
        return filename.toLowerCase();
    }


    /**
     * 根据原图地址把图片转换成缩略图
     *
     * @param filePath
     * @return
     */
    public static String getLitUrl(String filePath, String baseUrl)
            throws Exception {

        String fileName = "";
        String litUrl = "";

        Pattern v = Pattern.compile("^(wmv|avi|mp4|3gp|flv)$");

        File file = new File(filePath);

        Matcher mv = v.matcher(SysUtil.checkFormat(file.getName()));

        fileName = file.getName();

        fileName = fileName.substring(0, fileName.indexOf("."));

        if (mv.matches()) {

            filePath = SysUtil.encodePath(file.getParentFile() + "/lit/" + fileName + ".jpg");
            litUrl = filePath;
        } else {
            litUrl = SysUtil.encodePath(file.getParentFile() + "/lit/" + fileName + ".jpg");

            String parentPath = litUrl.substring(0, litUrl.lastIndexOf(File.separator));
            File parentFile = new File(parentPath);
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }

            if (file.exists()) {
                Thumbnails.of(filePath).size(Integer.parseInt(GlobalProperties.getGlobalProperty("lit.img.width")), Integer.parseInt(GlobalProperties.getGlobalProperty("lit.img.height"))).toFile(litUrl);
            }
        }

        return litUrl;
    }

    /**
     * 清空某一个目录下的所有文件及文件夹
     *
     * @param path
     */
    public static void deleteFile(String path) {
        // 判断上级目录是否存在
        String parentPath = path.substring(0, path.lastIndexOf(File.separator));
        File parentFile = new File(parentPath);
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        File file = new File(path);
        if (file.isFile()) {// 表示该文件不是文件夹
            file.delete();
        } else {
            // 首先得到当前的路径
            String[] childFilePaths = file.list();
            if (null != childFilePaths && childFilePaths.length > 0) {
                for (String childFilePath : childFilePaths) {
                    deleteFile(SysUtil.encodePath(
                            file.getAbsolutePath() + "\\" + childFilePath));
                }
            }

            file.delete();
        }
    }

    /**
     * 获取目录下的所有文件
     *
     * @param zipPath
     * @return
     */
    public static Map getFiles(String zipPath) {
        Map<String, String> map = new HashMap<String, String>();
        File file = new File(zipPath);
        if (file.isDirectory()) {
            File[] dirFile = file.listFiles();
            for (File f : dirFile) {
                if (f.getName().endsWith(".db")) {
                    map.put("dbFile", zipPath + "/" + f.getName());
                    map.put("dbName", f.getName());
                }
                if (f.getName().endsWith(".zip")) {
                    map.put("zipFile", zipPath + "/" + f.getName());
                    map.put("zipName", f.getName());
                }
            }
        }
        return map;
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            // 判断上级目录是否存在
            newPath = encodePath(newPath);
            String parentPath = newPath.substring(0,
                    newPath.lastIndexOf(File.separator));
            File parentFile = new File(parentPath);
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { // 文件存在时
                InputStream inStream = new FileInputStream(encodePath(oldPath)); // 读入原文件
                FileOutputStream fs = new FileOutputStream(encodePath(newPath));
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; // 字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                fs.flush();
                fs.close();
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }
    /**
     * 计算日期相差天数
     * @param smdate 
     * @param bdate
     * @return
     */
	public static int daysBetween(String smdate,String bdate){  
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
		 Calendar cal = Calendar.getInstance();    
		 long time1 = 0;
		 long time2 = 0;
		 
		 try{
		      cal.setTime(sdf.parse(smdate));   
		      time1 = cal.getTimeInMillis();    
		      cal.setTime(sdf.parse(bdate)); 
		      time2 = cal.getTimeInMillis();  
		 }catch(Exception e){
		     e.printStackTrace();
		 }
		 long between_days=(time2-time1)/(1000*3600*24);  
		     
		return Integer.parseInt(String.valueOf(between_days));     
	}
	/**
	 * 比较日期大小
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compare_date(String date1, String date2) {
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    try {
	        Date dt1 = df.parse(date1);
	        Date dt2 = df.parse(date2);
	        if (dt1.getTime() > dt2.getTime()) {
	            return 1;
	        } else if (dt1.getTime() < dt2.getTime()) {
	            return -1;
	        } else {
	            return 0;
	        }
	    } catch (Exception exception) {
	        exception.printStackTrace();
	    }
	    return 0;
	}
}
