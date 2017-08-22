package com.medicine.framework.util.swf;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
import com.medicine.framework.util.ProcessUtil;

/**
 * 将文件转成swf格式
 */
public class DOC2PDFUtil {

    // 地址
    private static String host_Str = "127.0.0.1";

    // 端口号
    private static String port_Str = "8100";


    /**
     * 入口方法-通过此方法转换文件至swf格式
     *
     * @param filePath 上传文件所在文件夹的绝对路径
     * @param fileName 文件名称
     * @return 生成swf文件名
     */
    public static String beginConvert(String sourcePath, String swfPath,
                                      String sourceFileName, String pdf2swfPath) {
        final String DOC = ".doc";
        final String DOCX = ".docx";
        final String XLS = ".xls";
        final String XLSX = ".xlsx";
        final String TXT = ".txt";
        final String PDF = ".pdf";
        /* final String SWF = ".swf"; */

        String outFile = "";
        String fileExt = "";
        String sourceFileType = "";
        String fileNameOnly = "";

        if (null != sourceFileName && sourceFileName.lastIndexOf(".") > 0) {
            int index = sourceFileName.lastIndexOf(".");
            fileNameOnly = sourceFileName.substring(0, index);
            sourceFileType = sourceFileName.substring(index).toLowerCase();
            fileExt = sourceFileType;
        }

        String inputFile = sourcePath + File.separator + sourceFileName;

        String outputFile = "";

        // 如果是office文档，先转为pdf文件
        if (fileExt.equals(DOC) || fileExt.equals(DOCX) || fileExt.equals(XLS)
                || fileExt.equals(XLSX) || fileExt.equals(TXT)) {
            outputFile = sourcePath + File.separator + fileNameOnly + PDF;
            office2PDF(inputFile, outputFile);
            inputFile = outputFile;
            fileExt = PDF;
        }

        return outFile;
    }

    /**
     * 将pdf文件转换成swf文件
     *
     * @param sourceFile pdf文件绝对路径
     * @param outFile    swf文件绝对路径
     * @param toolFile   转换工具绝对路径
     */
    private static void convertPdf2Swf(String sourceFile, String outFile, String pdf2swfPath) {

        String command = pdf2swfPath + " \"" + sourceFile + "\" -o  \""
                + outFile + "\" -s flashversion=9 ";

        try {
            Process process = Runtime.getRuntime().exec(command);
            System.out.println("###--Msg: swf 转换成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * office文档转pdf文件
     *
     * @param sourceFile office文档绝对路径
     * @param destFile   pdf文件绝对路径
     * @return
     */
    private static int office2PDF(String sourceFile, String destFile) {
        try {
            File inputFile = new File(sourceFile);
            if (!inputFile.exists()) {
                return -1; // 找不到源文件
            }

            if (ProcessUtil.isAliveByProName("soffice")) {
                // 如果目标路径不存在, 则新建该路径
                File outputFile = new File(destFile);
                if (!outputFile.getParentFile().exists()) {
                    outputFile.getParentFile().mkdirs();
                }

                // 连接openoffice服务
                OpenOfficeConnection connection = new SocketOpenOfficeConnection(host_Str, Integer.parseInt(port_Str));

                connection.connect();

                // 转换
                DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);

                converter.convert(inputFile, outputFile);

                // 关闭连接和服务
                connection.disconnect();

                return 0;
            } else {
                return 2;//服务未启动
            }

        } catch (ConnectException e) {
            System.out.println("OpenOffice服务监听异常！");
            e.printStackTrace();
        }

        return 1;
    }

    public static void startOpenOfficeServer(String OpenOffice_HOME) throws IOException {
        File inputFile = new File(OpenOffice_HOME);

        if (!inputFile.exists()) {
            return; // 找不到源文件
        }

        System.out.println("检查OpenOffice服务是否启动!");

        if (!ProcessUtil.isAliveByProName("soffice")) {
            System.out.println("OpenOffice服务开始启动!");

            // 启动OpenOffice的服务
            String command = OpenOffice_HOME + " -headless -accept=\"socket,host=" + host_Str + ",port=" + port_Str + ";urp;\"";

            // 启动服务
            Runtime.getRuntime().exec(command);

            // OpenOffice服务启动
            System.out.println("OpenOffice服务启动: " + command);

            if (ProcessUtil.isAliveByProName("soffice")) {
                System.out.println("OpenOffice服务启动成功!");
            } else {
                System.out.println("OpenOffice服务启动失败!");
                startOpenOfficeServer(OpenOffice_HOME);
            }
        } else {
            System.out.println("OpenOffice服务已经启动!");
        }
    }

    private static String loadStream(InputStream in) throws IOException {
        int ptr = 0;
        in = new BufferedInputStream(in);
        StringBuffer buffer = new StringBuffer();

        while ((ptr = in.read()) != -1) {
            buffer.append((char) ptr);
        }

        return buffer.toString();
    }

    public static void makeDirs(String filePath) {
        File file = new File(filePath);
        if (!file.exists() && !file.isDirectory()) {
            System.out.println("//不存在");
            file.mkdir();
        } else {
            System.out.println("//目录存在");
        }
    }
}