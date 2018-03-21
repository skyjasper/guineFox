package com.guinea.web.servlet;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * @author: shiky
 * @describle:
 * @dateTime: 2016/2/1
 */
public class UploadifyServlet extends HttpServlet {

    private static final long serialVersionUID = 3371896840232072160L;

    private static Logger logger = LoggerFactory.getLogger(UploadifyServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            //获取上传的文件集合
            Collection<Part> parts = request.getParts();
            if (null != parts && parts.size() > 0) {
                //上传单个文件
                if (parts.size() == 1) {
                    //Servlet3.0将multipart/form-data的POST请求封装成Part，通过Part对上传的文件进行操作。
                    //Part part = parts[0];//从上传的文件集合中获取Part对象
                    Part part = request.getPart("mefile");//通过表单file控件(<input type="file" name="file">)的名字直接获取Part对象
                    //Servlet3没有提供直接获取文件名的方法,需要从请求头中解析出来
                    //获取请求头，请求头的格式：form-data; name="file"; filename="snmp4j--api.zip"
                    String header = part.getHeader("content-disposition");
                    //获取文件名
                    String fileName = getFileName(header);
                    //把文件写到指定路径
                    part.write(fileName);
                } else {
                    //上传多个文件
                    for (Part part : parts) {//循环处理上传的文件
                        //获取请求头，请求头的格式：form-data; name="file"; filename="snmp4j--api.zip"
                        String header = part.getHeader("content-disposition");
                        //获取文件名
                        String fileName = getFileName(header);
                        if(StringUtils.isNotBlank(fileName)){
                            //把文件写到指定路径
                            part.write(fileName);
                            logger.debug("文件:"+fileName+"上传成功");
                        }
                    }
                }
                PrintWriter out = response.getWriter();
                out.println("succ");
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据请求头解析出文件名
     * 请求头的格式：火狐和google浏览器下：form-data; name="file"; filename="snmp4j--api.zip"
     * IE浏览器下：form-data; name="file"; filename="E:\snmp4j--api.zip"
     *
     * @param header 请求头
     * @return 文件名
     */
    public String getFileName(String header) {
        /**
         * String[] tempArr1 = header.split(";");代码执行完之后，在不同的浏览器下，tempArr1数组里面的内容稍有区别
         * 火狐或者google浏览器下：tempArr1={form-data,name="file",filename="snmp4j--api.zip"}
         * IE浏览器下：tempArr1={form-data,name="file",filename="E:\snmp4j--api.zip"}
         */
        String[] tempArr1 =null;
        if(StringUtils.isNotBlank(header)){
            tempArr1 = header.split(";");
        }
        /**
         *火狐或者google浏览器下：tempArr2={filename,"snmp4j--api.zip"}
         *IE浏览器下：tempArr2={filename,"E:\snmp4j--api.zip"}
         */
        String fileName=null;
        if(ArrayUtils.isNotEmpty(tempArr1)&&tempArr1.length>2){
            String[] tempArr2 = tempArr1[2].split("=");
            if(ArrayUtils.isNotEmpty(tempArr2)){
                fileName = tempArr2[1].substring(tempArr2[1].lastIndexOf("\\") + 1).replaceAll("\"", "");
            }
        }
        //获取文件名，兼容各种浏览器的写法
        return fileName;
    }
}
