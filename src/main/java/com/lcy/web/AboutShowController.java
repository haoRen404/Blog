package com.lcy.web;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class AboutShowController {
    // 从配置文件中获取简历路径，写死文件路径
    @Value("${jianli}")
    private String path;
    // 从配置文件中获取下载后的简历名称，写死文件名称
    @Value("${jlName}")
    private String jlName;


    @GetMapping("/about")
    public String about() {
        return "about";
    }

    // 文件下载
    @GetMapping("/domload")
    public void domload(HttpServletResponse response, HttpServletRequest request) throws IOException, IOException {
        response.reset();//清除response中的缓存

        // 通过setHeader（请求头）实现以附件(attachment)的形式下载。对中文进行编码.filename=新名字，后面是设置中文
        response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(jlName, "UTF-8"));

        // 获取response输出流，输出文件
        ServletOutputStream out = response.getOutputStream();
        // 读取文件，以输入流的形式读取文件
        FileInputStream in = new FileInputStream(path);
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        in.close();
    }




}
