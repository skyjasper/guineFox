package com.guinea.web.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * @Author: shiky on 2016/1/19.
 * @DocSee:
 * @Describe:
 */
@WebServlet(name = "gt", urlPatterns = "/gt")
public class GtServlet extends BaseServlet {
    private static final long serialVersionUID = -7409872544417323479L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getContextPath();
        String strValue = "this is String";
        Date myDate = new Date();
        Object[] objectArray = new Object[]{1, "str", 1.2};

        Set<Object> setData = Sets.newHashSet();
        setData.add("dataValue1");
        setData.add("dataValue2");
        setData.add("dataValue3");

        List<String> strList = Lists.newArrayList();
        strList.add("字符串1");
        strList.add("字符串2");
        strList.add("字符串3");

        Map<String, Object> map = Maps.newHashMap();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");

        request.setAttribute("strValue", strValue);
        request.setAttribute("objectArray", objectArray);
        request.setAttribute("strList", strList);
        request.setAttribute("map", map);
        request.setAttribute("myDate", myDate);
        request.setAttribute("setData", setData);
        request.setAttribute("path", path);
        forward(request, response, path, "gt");

        /****
         * 页面跳转
         * response.sendRedirect("/a.jsp");//浏览器地址栏变化
         * RequestDispatcher dispatcher = request.getRequestDispatcher("/a.jsp");
         dispatcher .forward(request, response);浏览器地址栏不变化
         String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
         System.out.println("basePath"+basePath);
         System.out.println("request.getScheme()"+ request.getScheme());
         System.out.println("request.getServerName()"+request.getServerName());
         */
//        request.getRequestDispatcher(path + "/WEB-INF/gpage/gt.ftl").forward(request, response);
        // response.getWriter().write("Hello Servlet4.0");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
