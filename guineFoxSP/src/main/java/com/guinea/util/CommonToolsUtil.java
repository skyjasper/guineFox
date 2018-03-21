package com.guinea.util;

import com.google.common.base.CharMatcher;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shiky on 2015/12/2.
 */
public class CommonToolsUtil {
    /***
     * 特殊字符过滤
     *
     * @param str
     * @return
     */
    public static String filterChar(String str) {
        String result = "";
        if (StringUtils.isNotBlank(str)) {
            result = StringUtils.trim(str);
            result = StringUtils.replace(result, "'", "");
            result = StringUtils.replace(result, "@", "");
            result = StringUtils.replace(result, "<", "");
            result = StringUtils.replace(result, ">", "");
            result = StringUtils.replace(result, "*", "");
            result = trimString(result);
        }
        return result;
    }

    public static String processFileName(HttpServletRequest request, HttpServletResponse response, String fileName) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/vnd.ms-excel");
        String client = request.getHeader("user-agent"), temp = "";
        try {
            if (StringUtils.contains(client, "MSIE")) {
                temp = URLEncoder.encode(fileName, "utf-8");
            } else {
                temp = new String(fileName.getBytes("utf-8"), "iso-8859-1");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return temp;
    }

    /***
     * 去掉不可识别空格
     *
     * @param strcontent
     * @return
     */
    public static String trimString(String strcontent) {
        String temp = "", strleft = "";
        if (StringUtils.isNotBlank(strcontent)) {
            strleft = CharMatcher.INVISIBLE.trimLeadingFrom(strcontent);// left
            temp = CharMatcher.INVISIBLE.trimTrailingFrom(strleft);// right
        }
        return temp;
    }

    // 检查文件夹是否存在,不存在择创建
    public static File checkFileDir(String cache) {
        File saveDirFile = new File(cache);
        if (saveDirFile.canWrite()) {
            if (!saveDirFile.exists() || !saveDirFile.isDirectory()) {
                saveDirFile.mkdirs();
            }
        } else {
            saveDirFile.setWritable(true);
            saveDirFile = new File(cache);
        }
        return saveDirFile;
    }

    public static void ncheckFDir(String cache) {
        try {
            Path directoryPath = Paths.get(cache);
            if (Files.isWritable(directoryPath)) {
                if (!Files.isDirectory(directoryPath)) {
                    Files.createDirectories(directoryPath);
                }
            } else {
                new File(cache).setWritable(true);
                Files.createDirectories(directoryPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File ncheckFileDir(String cache) {
        File saveDirFile = null;
        try {
            Path directoryPath = Paths.get(cache);
            if (Files.isWritable(directoryPath)) {
                if (Files.isDirectory(directoryPath)) {
                    saveDirFile = new File(cache);
                } else {
                    Files.createDirectories(directoryPath);
                    saveDirFile = new File(cache);
                }
            } else {
                saveDirFile = new File(cache);
                saveDirFile.setWritable(true);
                Files.createDirectories(directoryPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return saveDirFile;
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /***
     * 判断是否为数字可(以带小数点，带负号)
     * @param str
     * @return
     */
    public static boolean isMoney(String str){
        Boolean strResult = str.matches("-?[0-9]+.*[0-9]*");
        return strResult;
    }
}
