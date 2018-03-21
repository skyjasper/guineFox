package com.guinea.web.controller.uploadify;

import com.guinea.web.BaseFormController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author: shiky
 * @describle:文件下载
 * @dateTime: 2016/2/2
 */
@Controller
public class FileDownloadController extends BaseFormController {

    private static Logger logger = LoggerFactory.getLogger(FileDownloadController.class);

    @RequestMapping(value = "/uploadify/file_download", method = RequestMethod.GET)
    public String report_pie(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("file download");
        //获取网站部署路径(通过ServletContext对象)，用于确定下载文件位置，从而实现下载
        String path = "D:\\download\\";
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        //2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)
        response.setHeader("Content-Disposition", "attachment;fileName=" + "a.pdf");
        ServletOutputStream out;
        //通过文件路径获得File对象(假如此路径中有一个download.pdf文件)
        File file = new File(path+ "bird_128px_1173141_easyicon.net.png");
        try {
            FileInputStream inputStream = new FileInputStream(file);
            //3.通过response获取ServletOutputStream对象(out)
            out = response.getOutputStream();
            int b = 0;
            byte[] buffer = new byte[512];
            while (b != -1) {
                b = inputStream.read(buffer);
                //4.写到输出流(out)中
                out.write(buffer, 0, b);
            }
            inputStream.close();
            out.close();
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "uploadify/file_download";
    }
}
