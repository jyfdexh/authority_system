package com.hopu.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author JYF
 * @create 2020/12/19 15:12
 */
@Controller
public class UploadController {
//    //图片上传测试
//    @ResponseBody
//    @RequestMapping("upload")
//    public String upload2(MultipartFile file,HttpServletRequest request){
//
//        String result = userBiz.upload(file,request);
//
//        return result;
//
//    }
    //图片上传测试
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
                //in Linux: String filepath = "\\home\\zyy\\images" + dateStr+"\\"+uuid+"." + suffix;


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
}
