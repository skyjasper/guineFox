package com.guinea.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.guinea.dic.Contents;

/***
 * 
 * @author: shiky
 * @describe:
 * 2016年1月27日
 */
public class BaseServlet extends HttpServlet{
	
	private static final long serialVersionUID = -5175085035432531758L;

	/***
    *
    * @param request
    * @param path
    * @return
    */
   protected String ctrlpath(HttpServletRequest request, String path) {
       StringBuffer result = new StringBuffer(request.getContextPath());
       result.append(Contents.CONTENTPATH);
       result.append(path);
       result.append(Contents.FTL);
       return result.toString();
   }

   /***
    *
    * @param cpath
    * @param path
    * @return
    */
   protected String ctrlpath(String cpath, String path) {
       StringBuffer result = new StringBuffer(cpath);
       result.append(Contents.CONTENTPATH);
       result.append(path);
       result.append(Contents.FTL);
       return result.toString();
   }

   protected void forward(HttpServletRequest request, HttpServletResponse response,String cpath) throws ServletException, IOException {
       request.getRequestDispatcher(ctrlpath(request, cpath)).forward(request, response);
   }

   protected void forward(HttpServletRequest request, HttpServletResponse response,String path,String cpath) throws ServletException, IOException {
       request.getRequestDispatcher(ctrlpath(path, cpath)).forward(request, response);
   }
}
