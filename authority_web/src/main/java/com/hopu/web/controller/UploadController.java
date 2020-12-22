package com.hopu.web.controller;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Controller
public class UploadController {
    //Windows图片上传测试
    @ResponseBody
    @RequestMapping("/upload")
    public Map upload(MultipartFile file, HttpServletRequest request){

        String suffix="";
        String dateStr="";
        //保存上传
                String originalName = file.getOriginalFilename();
                suffix=originalName.substring(originalName.lastIndexOf(".")+1);
                Date date = new Date();
                String uuid = UUID.randomUUID()+"";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                dateStr = simpleDateFormat.format(date);
                String filepath = "D:\\mynginx\\nginx-1.18.0\\html\\" + dateStr+"\\"+uuid+"." + suffix;
                //in Linux: String filepath = "/home/zyy/images" + dateStr+"/"+uuid+"." + suffix;


                File files=new File(filepath);
                //打印查看上传路径
                System.out.println( "filepath = " + filepath );
                if(!files.getParentFile().exists()){
                    files.getParentFile().mkdirs();
                }
                try {
                    file.transferTo(files);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Map<String,Object> map2=new HashMap<>();
                Map<String,Object> map=new HashMap<>();
                map.put("code",0);
                map.put("msg","");
                map.put("data",map2);
        System.out.println( "dateStr = " + dateStr );
        System.out.println( "suffix = " + suffix );
                map2.put("src",dateStr+"/"+uuid+"." + suffix);
                return map;
    }




//    ------------------------------in Linux图片上传-------------------------

    @ResponseBody
    @RequestMapping("/upload22")
    public Map upload2(MultipartFile file, HttpServletRequest request) throws IOException {
        //1、连接ftp服务器
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect("192.168.179.128",21);
        //2、登录ftp服务器
        ftpClient.login("ftpuser","jyf123,.");

        //解决了ftp上传图片大小为0的问题
        //ftp有两种模式：主动 （默认） 和 被动
        //解决方式：
        // ①关闭防火墙
        // ②设置为被动模式  ftpClient.enterLocalPassiveMode();
        //        主动模式  ftpClient.enterLocalActiveMode();
        ftpClient.enterLocalActiveMode();
        //3、读取本地文件
        FileInputStream inputStream = (FileInputStream) file.getInputStream();

        //4、上传文件
        //1）指定上传目录                   提前把nginx的主页位置映射到了/usr/docker/nginx/html
        ftpClient.changeWorkingDirectory("/usr/docker/nginx/html");
        //2）指定文件类型
        ftpClient.setFileType( FTP.BINARY_FILE_TYPE);
        //第一个参数：文件在远程服务器的名称
        //第二个参数：文件流
        String newFileName = UUID.randomUUID() + file.getOriginalFilename();
        ftpClient.storeFile(newFileName,inputStream);
        System.out.println("ok");
        //5、退出登录
        ftpClient.logout();
        Map<String,Object> map2=new HashMap<>();
        Map<String,Object> map=new HashMap<>();
        map.put("code",0);
        map.put("msg","");
        map.put("data",map2);
        map2.put("src",newFileName);
        return map;
    }





}
