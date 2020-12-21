//package com.hopu.utils;
//
//import java.io.IOException;
//import java.net.SocketException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Properties;
//import java.util.Vector;
//
//import org.apache.commons.net.ftp.FTPClient;
//import org.apache.commons.net.ftp.FTPReply;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.jcraft.jsch.Channel;
//import com.jcraft.jsch.ChannelSftp;
//import com.jcraft.jsch.ChannelSftp.LsEntry;
//import com.jcraft.jsch.JSch;
//import com.jcraft.jsch.Session;
//
//public class FtpsFileList {
//    /**
//     * 初始化ftp服务器
//     *
//     * @throws IOException
//     * @throws SocketException
//     */
//    public void initFtpClient() throws SocketException, IOException {
//        ftpClient = new FTPClient();
//        ftpClient.setControlEncoding("utf-8");
//        LOGGER.info("connecting...ftp服务器:" + this.hostname + ":" + this.port);
//        ftpClient.connect(hostname, port); // 连接ftp服务器
//        ftpClient.login(username, password); // 登录ftp服务器
//        int replyCode = ftpClient.getReplyCode(); // 是否成功登录服务器
//        if (!FTPReply.isPositiveCompletion(replyCode)) {
//            LOGGER.info("connect failed...ftp服务器:" + this.hostname + ":" + this.port);
//        }
//        LOGGER.info("connect successfu...ftp服务器:" + this.hostname + ":" + this.port);
//
//    }
//
//    /**
//     * 上传文件
//     *
//     * @param pathname
//     *            ftp服务保存地址
//     * @param fileName
//     *            上传到ftp的文件名
//     * @param inputStream
//     *            输入文件流
//     * @return
//     * @throws IOException
//     */
//    public boolean uploadFile(String pathname, String fileName, InputStream inputStream) throws IOException {
//        boolean flag = false;
//        initFtpClient();
//        try {
//            LOGGER.info("开始上传文件");
//            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
////          CreateDirecroty(pathname);
//
//            boolean changeWorkingDirectory = ftpClient.changeWorkingDirectory(pathname);
//            if(changeWorkingDirectory) {
//                LOGGER.info("进入文件"+pathname+"夹成功.");
//            }else {
//                LOGGER.info("进入文件"+pathname+"夹失败.开始创建文件夹");
//                boolean makeDirectory = ftpClient.makeDirectory(pathname);
//                if(makeDirectory) {
//                    LOGGER.info("创建文件夹"+pathname+"成功");
//                    boolean changeWorkingDirectory2 = ftpClient.changeWorkingDirectory(pathname);
//                    if(changeWorkingDirectory2) {
//                        LOGGER.info("进入文件"+pathname+"夹成功.");
//                    }
//                }else {
//                    LOGGER.info("创建文件夹"+pathname+"失败");
//                }
//            }
//            ftpClient.storeFile(fileName, inputStream);
//            inputStream.close();
//            ftpClient.logout();
//            flag = true;
//            if (flag) {
//
//                LOGGER.info("上传文件成功");
//            }
//        } finally {
//            if (ftpClient.isConnected()) {
//                try {
//                    ftpClient.disconnect();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (null != inputStream) {
//                try {
//                    inputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return true;
//    }　
//}