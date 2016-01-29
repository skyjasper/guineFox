package com.guinea.util;

import javax.servlet.http.HttpServletRequest;

import com.guinea.dic.Contents;

public class ControlUtil {
	/***
	 *
	 * @param request
	 * @param path
	 * @return
	 */
	public static String ctrlpath(HttpServletRequest request, String path) {
		StringBuffer result = new StringBuffer(request.getContextPath());
		result.append(Contents.CONTENTPATH);
		result.append(path);
		return result.toString();
	}
}
