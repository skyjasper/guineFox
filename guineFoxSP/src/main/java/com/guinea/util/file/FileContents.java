package com.guinea.util.file;

/**
 * Created by shiky on 2015/12/7.
 */
public class FileContents {
    public static final String BASEPATH = System.getProperty("user.dir");
    public final static String SEP = System.getProperty("file.separator");//
    public final static String EXTSTR = ".";
    public final static String DIR = "uploadify";
    public final static String TEMP_DIR = BASEPATH + SEP + DIR + SEP + "uploadTemp" + SEP;// 默认临时文件存放路径(未保存进数据库的文件)
    public final static String TEMP_DOWNLOAD = TEMP_DIR + "downloadTemp" + SEP;// 默认下载文件临时目录
    public final static String TEMP_MUL = BASEPATH + SEP + DIR + SEP + "removeTemp" + SEP;// 默认临时区域(转存文件)
    public final static String CATCHPATH = BASEPATH + SEP + DIR + SEP + "cache" + SEP;// 默认缓存目录
    public final static String DBMAP = BASEPATH + SEP + "DBMAP" + SEP;// dbmap
    public final static Integer CACHESIZE = 8 * 1024 * 1024;// 默认 jvm 中缓存区大小
    public final static Integer ONELY_SIZE = 200 * 1024 * 1024;// 默认单个文件大小限制
    public final static Integer TOTAL_SIZE = 200 * 1024 * 1024;// 默认总文件大小限制

    /***
     * 文件后缀限制
     */
    public final static String[] EXTS = {".xls", ".xlsx"};
    public final static String EXCEL3 = ".xls", EXCEL7 = ".xlsx";
}
