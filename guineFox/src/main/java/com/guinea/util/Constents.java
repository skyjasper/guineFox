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

	public static final Short gen =1,del =0;

	public final static Integer pageNumber = 1, pageSize = 50, p2 = 100, p3 = 200;

	public static List<Integer> pageList = Arrays.asList(pageSize, p2, p3);

	public final static Long rootId = 0L;
	
	public static Map<String,Map<Integer,Integer>> userPage = Maps.newConcurrentMap();
	
	public static Map<String,DateTime> operatorPageMap = Maps.newConcurrentMap();

	public static AtomicInteger operatorRoleCount = new AtomicInteger(0);
	
	static {
//		operatorRoleCount.addAndGet(-1);
//		operatorRoleCount.addAndGet(1);
//		if(operatorRoleCount.get()>0){
//		}
	}
}
