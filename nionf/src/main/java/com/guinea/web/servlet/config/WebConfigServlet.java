package com.guinea.web.servlet.config;

import freemarker.ext.servlet.FreemarkerServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * @Author: shiky on 2016/1/19.
 * @DocSee:
 * @Describe:
 */
@WebServlet(
        urlPatterns = {"*.ftl"}, // 需要定义Freemarker解析的页面后缀类型
        asyncSupported = false,
        loadOnStartup = 0,
        name = "webConfigServlet",
        displayName = "WebConfigServlet",
        initParams = {
                @WebInitParam(name = "TemplatePath", value = "/"),
                @WebInitParam(name = "NoCache", value = "true"),//定义是否缓存
                @WebInitParam(name = "ContentType", value = "text/html; charset=UTF-8"),// 定义内容类型
                @WebInitParam(name = "template_update_delay", value = "0"), // 开发环境中可设置为0
                @WebInitParam(name = "default_encoding", value = "UTF-8"),
                @WebInitParam(name = "number_format", value = "0.##########")
        }
)
public class WebConfigServlet extends FreemarkerServlet {
    private static final long serialVersionUID = -7275520288283603164L;
}
