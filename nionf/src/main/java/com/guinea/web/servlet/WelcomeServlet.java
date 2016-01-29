package com.guinea.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: shiky
 * @describle:
 * @dateTime: 2016/1/28
 */
@WebServlet(name = "welcome", urlPatterns = "/welcome")
public class WelcomeServlet extends BaseServlet{
    private static final long serialVersionUID = 6635924731934979932L;
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getContextPath();
        request.setAttribute("path", path);
        forward(request, response, path, "welcome");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
