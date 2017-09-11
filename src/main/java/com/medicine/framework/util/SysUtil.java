package com.medicine.framework.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.medicine.framework.util.ffmpeg.FFmpegUtil;
import com.medicine.framework.util.swf.DOC2PDFUtil;

import net.coobird.thumbnailator.Thumbnails;
import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Relation;
import net.sf.mpxj.Resource;
import net.sf.mpxj.ResourceAssignment;
import net.sf.mpxj.Task;
import net.sf.mpxj.mpp.MPPReader;

public class SysUtil {

    /**
     * zip压缩 preAuthenticationChecks.check(user)
     *
     * @param fileNameList
     * @param zipPath
     * @throws IOException
     */
    public static void zip(List<String> fileNameList, String zipPath)
            throws IOException {
        long a = System.currentTimeMillis();

        File zipFile = new File(zipPath); // 定义压缩文件名称
        InputStream input = null; // 定义文件输入流
        ZipOutputStream zipOut = null; // 声明压缩流对象
        zipOut = new ZipOutputStream(new FileOutputStream(zipFile));

        int temp = 0;
        for (String fileName : fileNameList) {
            File file = new File(fileName);
            if (!file.exists()) {
                continue;
            }
            input = new FileInputStream(file); // 定义文件的输入流
            BufferedInputStream bi = new BufferedInputStream(input);
            zipOut.putNextEntry(new ZipEntry(file.getParentFile().getName()
                    + File.separator + file.getName())); // 设置ZipEntry对象
            while ((temp = bi.read()) != -1) { // 读取内容
                zipOut.write(temp); // 压缩输出
            }
            input.close(); // 关闭输入流
        }

        zipOut.close(); // 关闭输出流
        long b = System.currentTimeMillis();
        System.out.println("压缩文件用时" + (b - a));
    }

    /**
     * 解压文件
     *
     * @param zipPath
     * @param unzipPath
     * @throws IOException
     */
    public static void unzip(String zipPath, String unzipPath)
            throws IOException {
        long a = System.currentTimeMillis();
        File zipFile = new File(zipPath); // 定义压缩文件名称
        ZipInputStream zipInput = new ZipInputStream(
                new FileInputStream(zipFile));
        ZipEntry zipEntry = null;
        while ((zipEntry = zipInput.getNextEntry()) != null) {
            String upEntryPath = encodePath(
                    unzipPath + "/" + zipEntry.getName());

            // 判断上级目录是否存在
            String parentPath = upEntryPath.substring(0,
                    upEntryPath.lastIndexOf(File.separator));
            File parentFile = new File(parentPath);
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }

            File upEntryFile = new File(upEntryPath);
            if (upEntryFile.isDirectory()) {
                continue;
            } else {
                BufferedOutputStream bos = new BufferedOutputStream(
                        new FileOutputStream(upEntryFile));
                int b = 0;
                while ((b = zipInput.read()) != -1) {
                    bos.write(b);
                }
                bos.flush();
                bos.close();
            }

        }
        zipInput.close();
        long b = System.currentTimeMillis();
        System.out.println("解压文件用时" + (b - a));
    }

    public static String encodePath(String srcPath) {
        if (File.separator.equals("/")) {
            srcPath = srcPath.replace("\\", "/");
        } else {
            srcPath = srcPath.replace("/", "\\");
        }
        return srcPath;
    }

    /**
     * 将字符串转成unicode
     *
     * @param str 待转字符串
     * @return unicode字符串
     */
    public static String convert(String str) {
        str = (str == null ? "" : str);
        String tmp;
        StringBuffer sb = new StringBuffer(1000);
        char c;
        int i, j;
        sb.setLength(0);
        for (i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            // sb.append("\\u");
            j = (c >>> 8); // 取出高8位
            tmp = Integer.toHexString(j);
            if (tmp.length() == 1)
                sb.append("0");
            sb.append(tmp);
            j = (c & 0xFF); // 取出低8位
            tmp = Integer.toHexString(j);
            if (tmp.length() == 1)
                sb.append("0");
            sb.append(tmp);

        }
        return (new String(sb));
    }

    /**
     * 将unicode 字符串
     *
     * @param str 待转字符串
     * @return 普通字符串
     */
    public static String revert(String str) {
        str = (str == null ? "" : str);
        if (str.indexOf("\\u") == -1)// 如果不是unicode码则原样返回
            return str;

        StringBuffer sb = new StringBuffer(1000);

        for (int i = 0; i < str.length() - 6; ) {
            String strTemp = str.substring(i, i + 6);
            String value = strTemp.substring(2);
            int c = 0;
            for (int j = 0; j < value.length(); j++) {
                char tempChar = value.charAt(j);
                int t = 0;
                switch (tempChar) {
                    case 'a':
                        t = 10;
                        break;
                    case 'b':
                        t = 11;
                        break;
                    case 'c':
                        t = 12;
                        break;
                    case 'd':
                        t = 13;
                        break;
                    case 'e':
                        t = 14;
                        break;
                    case 'f':
                        t = 15;
                        break;
                    default:
                        t = tempChar - 48;
                        break;
                }

                c += t * ((int) Math.pow(16, (value.length() - j - 1)));
            }
            sb.append((char) c);
            i = i + 6;
        }
        return sb.toString();
    }

    public String changeCharset(String str, String newCharset)
            throws UnsupportedEncodingException {
        if (str != null) {
            // 用默认字符编码解码字符串。
            byte[] bs = str.getBytes();
            // 用新的字符编码生成字符串
            return new String(bs, newCharset);
        }
        return null;
    }

    public static String encodePassword(String password, String salt) {
        Md5PasswordEncoder md5 = new Md5PasswordEncoder();
        md5.setEncodeHashAsBase64(false);
        return md5.encodePassword(password, salt);
    }

    /**
     * 信息摘要算法
     *
     * @param algorithm 算法类型
     * @param data      要加密的字符串
     * @return 返回加密后的摘要信息
     */
    public static String encryptEncode(String algorithm, String data) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            return byteArrayToHexStr(md.digest(data.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String byteArrayToHexStr(byte byteArray[]) {
        StringBuffer buffer = new StringBuffer(byteArray.length * 2);
        int i;
        for (i = 0; i < byteArray.length; i++) {
            if (((int) byteArray[i] & 0xff) < 0x10)// 小于十前面补零
                buffer.append("0");
            buffer.append(Long.toString((int) byteArray[i] & 0xff, 16));
        }
        return buffer.toString();
    }


    public static String uploadFileByInputStream(String fileTypeStr, String num,MultipartFile file) {
    	
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String url = "";
        String filePath = "";
        FileOutputStream os = null;
        InputStream in = null;

        if(!file.isEmpty()){  
            try {  
            	
                GlobalProperties.init();
                String attachmentPath = GlobalProperties.getGlobalProperty("attachmentPath");

                Date createDate = new Date();

                url = formatter.format(createDate) + num;

                if (null != fileTypeStr && !fileTypeStr.equals("")) 
                {
                    filePath = SysUtil.encodePath(attachmentPath + "/" + fileTypeStr + "/" + url + "." + checkFormat(file.getOriginalFilename()));
                }
                
                // 转存文件
                File newFile = new File(filePath);

                if (!newFile.getParentFile().exists()) 
                {
                    newFile.getParentFile().mkdirs();
                }

                
                //定义输出流 将文件保存在D盘    file.getOriginalFilename()为获得文件的名字   
                os = new FileOutputStream(filePath);  
                in = file.getInputStream();  
                
                byte[] readBytes=new byte[1024];
                
                while(in.read(readBytes) != -1)
                { 
                	os.write(readBytes);
                }
                
				os.flush();

                // todo 将文件转换为flash格式的再存储一份
                // 表达式对象
                Pattern p = Pattern.compile("^(wmv|avi|mp4|3gp|flv)$");
                // 创建 Matcher 对象
                Matcher m = p.matcher(checkFormat(file.getOriginalFilename()));
                // 是否完全匹配
                if (m.matches()) {
                    String ffmpegPath = GlobalProperties.getGlobalProperty("ffmpegPath");

                    // 输出文件路径
                    String path = SysUtil.encodePath(attachmentPath + "/" + fileTypeStr + "/lit");

                    mkdirPath(path);

                    // 视频截图
                    FFmpegUtil.videoScreenshot(ffmpegPath, filePath,
                            path + "/" + url + ".jpg");
                }

                // 表达式对象
                p = Pattern.compile("^(doc|docx|xls|xlsx|pdf|txt)$");
                // 创建 Matcher 对象
                m = p.matcher(checkFormat(file.getOriginalFilename()));

                if (m.matches()) {
                    // pdf2swf安装路径
                    // String pdf2swfPath =
                    // GlobalProperties.getGlobalProperty("pdf2swfPath");

                    // openOffice安装路径
                    //String openOfficePath = GlobalProperties.getGlobalProperty("openOfficePath");

                    String fileName = url + "." + checkFormat(file.getOriginalFilename());

                    // 输出文件路径
                    String path = SysUtil.encodePath(attachmentPath + "/" + fileTypeStr);

                    DOC2PDFUtil.beginConvert(path, null, fileName, null);
                }
            } catch (FileNotFoundException e) {  
                e.printStackTrace();  
            } catch (IOException e) {  
                e.printStackTrace();  
            } catch (Exception e) {
				e.printStackTrace();
			}finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        // ignore
                    }
                }
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        // ignore
                    }
                }
            }
        }  
   
        return url;
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
                    // pdf2swf安装路径
                    // String pdf2swfPath =
                    // GlobalProperties.getGlobalProperty("pdf2swfPath");

                    // openOffice安装路径
                    //String openOfficePath = GlobalProperties.getGlobalProperty("openOfficePath");

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
    public static void uploadFile(MultipartFile file, String path,
                                  HttpServletRequest request) {
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
     * 上传到指定的path中
     *
     * @param file
     * @param path
     * @param request
     */
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
     * @param file
     * @param path
     * @param request
     */
    public static void uploadFiles(MultipartFile[] multipartFile, String path,
                                   HttpServletRequest request) {
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
     * 拆分入库
     *
     * @param list
     * @param count
     * @return
     */
    public static <T> List<List<T>> splitList(List<T> list, int count) {
        List<List<T>> col = new ArrayList<List<T>>();
        if (list != null && list.size() > count) {
            col = new ArrayList<List<T>>();
            int listSize = list.size();
            int n = listSize / count + 1;
            int m = listSize / n;
            for (int i = 0; i < n; i++) {
                List<T> subList = new ArrayList<T>();
                if (i == n - 1) {
                    subList = list.subList(i * m, listSize);
                } else {
                    subList = list.subList(i * m, (i + 1) * m);
                }
                col.add(subList);
            }
        } else if (list != null && list.size() > 0) {
            col.add(list);
        }
        return col;
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
     * 找到某一个目录下的所有b3d文件
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static List<String> getDirectoryB3d(String path) throws Exception {
        List<String> fileNameList = new ArrayList<String>();
        File file = new File(path);
        if (file.isDirectory()) {
            File[] dirFile = file.listFiles();
            for (File f : dirFile) {
                if (f.isDirectory()) {
                    File[] dirFiles = f.listFiles();
                    for (File fi : dirFiles) {
                        if (fi.isDirectory()) {
                            File[] fileList = fi.listFiles();
                            for (File temp : fileList) {
                                if (temp.isFile()
                                        && temp.getName().endsWith(".b3d")) {
                                    fileNameList.add(temp.getAbsolutePath());
                                }
                            }
                        } else {
                            if (fi.isFile() && fi.getName().endsWith(".b3d")) {
                                fileNameList.add(fi.getAbsolutePath());
                            }
                        }

                    }
                    // }
                } else {
                    if (f.isFile() && f.getName().endsWith(".b3d")) {
                        fileNameList.add(f.getAbsolutePath());
                    }
                }
            }
        }
        return fileNameList;
    }

    /**
     * 找到db文件
     *
     * @param path
     * @return
     */
    public static String getDBFile(String path) {
        String dbPath = "";
        File file = new File(path);
        if (file.isDirectory()) {
            File[] dirFile = file.listFiles();
            for (File f : dirFile) {
                if (f.isDirectory())
                    getDBFile(f.getAbsolutePath());
                else {
                    if (f.getName().endsWith(".db"))
                        dbPath = f.getAbsolutePath();
                }
            }
        }
        return dbPath;
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
     * 日期格式字符串转换日期
     *
     * @param dateStr      日期格式字符串(不带时分秒)
     * @param parsePattern 日期解析格式 默认yyyy/MM/dd
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String dateStr, String parsePattern)
            throws ParseException {
        if (StringUtils.isEmpty(parsePattern)) {
            parsePattern = "yyyy/MM/dd";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(parsePattern);
        return sdf.parse(dateStr);
    }

    /**
     * 日期格式字符串转换日期
     *
     * @param dateStr      日期格式字符串(带时分秒)
     * @param parsePattern 日期解析格式 默认yyyy/MM/dd hh:mm:ss
     * @return
     * @throws ParseException
     */
    public static Date parseDateTime(String dateStr, String parsePattern)
            throws ParseException {
        if (StringUtils.isEmpty(parsePattern)) {
            parsePattern = "yyyy/MM/dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(parsePattern);
        return sdf.parse(dateStr);
    }

    /**
     * 日期转换为字符串
     *
     * @param date         日期
     * @param parsePattern parsePattern 日期解析格式 默认yyyy/MM/dd
     * @return
     */
    public static String formatDate(Date date, String parsePattern) {
        if (StringUtils.isEmpty(parsePattern)) {
            parsePattern = "yyyy/MM/dd";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(parsePattern);

        return sdf.format(date);

    }

    /**
     * 日期转换为字符串
     *
     * @param date         日期
     * @param parsePattern 日期解析格式 默认yyyy/MM/dd hh:mm:ss
     * @return
     */
    public static String formatDateTime(Date date, String parsePattern) {
        if (StringUtils.isEmpty(parsePattern)) {
            parsePattern = "yyyy/MM/dd  HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(parsePattern);
        return sdf.format(date);

    }
    /**
     * 获取project文件中任务列表
     * @param mppfile
     * @return
     */
    public static List psrseProjectFile(File mppfile){
        List list = new ArrayList();
        MPPReader reader = new MPPReader();
        ProjectFile projectFile;
        try{
            projectFile = reader.read(mppfile);
            List<Task> taskList = projectFile.getAllTasks();
            for(Task task : taskList){
                Map<String,String> map = new HashMap();
                map.put("id",task.getID().toString());
                map.put("taskName",task.getName());
                map.put("startDate",SysUtil.formatDateTime(task.getStart(),""));
                map.put("endDate",SysUtil.formatDateTime(task.getFinish(),""));
                map.put("beforeTask",getBeforeTaskId(task));//获取前置任务的Id
                map.put("resource",getResources(task));//获得资源
                list.add(map);
            }
        }catch(MPXJException e ){
            e.printStackTrace();
        }
        return list;
    }
    /**
     * 获取前置任务的Id
     * @param task
     * @return
     */
    private static String  getBeforeTaskId(Task task){
        StringBuffer beforeTaskId = new StringBuffer();
        if(task!=null){
            List list = task.getPredecessors();
            if(list != null ){
                if(list.size()>0){
                    for(int i=0; i<list.size(); i++){
                        Relation relation = (Relation)list.get(i);
                        beforeTaskId.append(relation.getTargetTask().getID());
                    }
                }
            }
        }
        return beforeTaskId.toString();
    }

   /**
    * 获得资源
    * @param task
    * @return
    */
    private static String getResources(Task task){
        if(task == null){
            return "";
        }
        StringBuffer sb = new StringBuffer();
        List<ResourceAssignment> assignments = task.getResourceAssignments();
        for(ResourceAssignment ra : assignments){
            Resource resource = ra.getResource();
            if(resource != null){
                sb = sb.append(resource.getName());
            }
        }
        return sb.toString();
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
	 * @param DATE1
	 * @param DATE2
	 * @return
	 */
	public static int compare_date(String DATE1, String DATE2) {
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    try {
	        Date dt1 = df.parse(DATE1);
	        Date dt2 = df.parse(DATE2);
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
