package com.guinea.util;

import org.apache.commons.lang3.StringUtils;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by shiky on 2015/9/11.
 */
public class WebUtil {

	private final static String IPV6LOCAL = "0:0:0:0:0:0:0:1";

	public static String getClientIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		ip = request.getHeader("VIA");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_FORWARDED");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_VIA");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (StringUtils.equals(ip, IPV6LOCAL)) {
			try {
				ip = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		return ip;
	}

	/****
	 * 检查浏览器版本
	 *
	 * @param request
	 * @return
	 */
	public static String checkBrowser(HttpServletRequest request) {
		String flag = "0", browser = request.getHeader("user-agent");
		System.out.println("browser:" + browser);
		if (StringUtils.isNotBlank(browser)) {
			if (StringUtils.containsIgnoreCase(browser, "MSIE 9.0")
					|| StringUtils.containsIgnoreCase(browser, "MSIE 8.0")
					|| StringUtils.containsIgnoreCase(browser, "MSIE 7.0")
					|| StringUtils.containsIgnoreCase(browser, "MSIE 6.0")) {
				flag = "1";
			}
		} else {
			flag = "1";
		}
		return flag;
	}

	/***
	 * 获取客户端信息
	 * @param request
	 * @return
     */
	public static UserAgent obtainClientInfo(HttpServletRequest request){
		String agentStr = request.getHeader("user-agent");
		UserAgent agent = UserAgent.parseUserAgentString(agentStr);
		return agent;
	}

	public static void obtainInfo(HttpServletRequest request) {
		String agentStr = request.getHeader("user-agent");
		System.out.println(agentStr);
		UserAgent agent = UserAgent.parseUserAgentString(agentStr);
		// 浏览器
		Browser browser = agent.getBrowser();
		System.out.println("类型：" + browser.getBrowserType() + "\n名称：" + browser.getName() + "\n厂商："
				+ browser.getManufacturer() + "\n产品系列：" + browser.getGroup() + "\n引擎：" + browser.getRenderingEngine());

		// 浏览器版本
		Version version = agent.getBrowserVersion();
		if(null!=version){
			System.out.println("========================");
			System.out.println("主版本：" + version.getMajorVersion() + "\n小版本：" + version.getMinorVersion() + "\n完整版本："
					+ version.getVersion());
		}

		// 操作系统
		System.out.println("========================");
		OperatingSystem os = agent.getOperatingSystem();
		System.out.println("名称：" + os.getName() + "\n设备类型：" + os.getDeviceType() + "\n产品系列：" + os.getGroup() + "\n生成厂商："
				+ os.getManufacturer());
	}

}
