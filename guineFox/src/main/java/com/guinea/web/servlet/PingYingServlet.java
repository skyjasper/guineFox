package com.guinea.web.servlet;

import java.io.IOException;
//import javax.servlet.annotation.WebServlet;
//import java.io.PrintWriter;
//import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

///**
// * Created by shiky on 2015/12/9.
// */
//@WebServlet(name="pingYingServlet",urlPatterns="/interface/changeToPinYin",loadOnStartup = -3)
public class PingYingServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7339441236095612288L;

	@Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("000000000000000000------");
//        resp.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = resp.getWriter();
//        out.println("进入Servlet的时间：" + new Date() + ".");
//        out.flush();
//        //在子线程中执行业务调用，并由其负责输出响应，主线程退出
////        AsyncContext ctx = req.startAsync();
////        new Thread(new Executor(ctx)).start();
//
//        out.println("结束Servlet的时间：" + new Date() + ".");
//        out.flush();
    }

//    @Override
//    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("post=======");
//        super.doPost(req, resp);
//    }
}
