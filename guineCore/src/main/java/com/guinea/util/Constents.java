package com.guinea.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.joda.time.DateTime;

import com.google.common.collect.Maps;

/**
 * Created by shiky on 2015/12/22.
 */
public class Constents {
    public static final String CURRENT_USER = "user";
    public static final String SESSION_FORCE_LOGOUT_KEY = "session.force.logout";
    public static final String WEB_IOS = "web_ios";
    public static final String WEB_A = "web_android";
    public static final String WEB_PC = "web_pc";
    public static final String APP_IOS = "app_ios";
    public static final String APP_A = "app_android";
    public static final String PAGENUMBER_ = "pageNumber", PAGESIZE_ = "pageSize";

    public static final Short gen = 1, del = 0;

    public final static Integer pageNumber = 1, pageSize = 80, p2 = 60, p3 = 50;

    public static List<Integer> pageList = Arrays.asList(pageSize, p2, p3);

    public final static Long rootId = 0L;

    public static final String MONDAY = "星期一", TUESDAY = "星期二",
            WEDNESDAY = "星期三", THURSDAY = "星期四",
            FRIDAY = "星期五", SATURDAY = "星期六", SUNDAY = "星期日";

    public static final String DUANWU = "端午节",
            ZHONGQIU = "中秋节", GUOQING = "国庆节", LAODONG = "劳动节",
            YUANDAN = "元旦", CHUNJIE = "春节", QINGMING = "清明节";

    //保留小数
    public static final Integer BAOLIU=3;
    //百分数
    public static final  Double BAIFEN=0.01;

    public static String web_socket_conn ="127.0.0.1:8787";
    public static Integer netty_web_socket_port =8787;



     //最晚到账时间
    public static Integer HOUR = 2230;
    //限制到账金额:拉卡拉，瑞信银行，和付
    public static Double PAY_LIMIT_MONEY_LAKL = 1000.00,
    		PAY_LIMIT_MONEY_RUIXY=1000.00,PAY_LIMIT_MONEY_HEF=100000.00;

    public static Map<String, Map<Integer, Integer>> userPage = null;

    public static Map<String, DateTime> operatorPageMap = null;

    public static AtomicInteger operatorRoleCount = null;

    public static AtomicInteger flagLoadingPlanTask=null;


    public static void initLoading() {
        userPage = Maps.newConcurrentMap();
        operatorPageMap = Maps.newConcurrentMap();
        operatorRoleCount = new AtomicInteger(0);
        flagLoadingPlanTask=new AtomicInteger(0);
//		operatorRoleCount.addAndGet(-1);
//		operatorRoleCount.addAndGet(1);
//		if(operatorRoleCount.get()>0){
//		}
    }
}
