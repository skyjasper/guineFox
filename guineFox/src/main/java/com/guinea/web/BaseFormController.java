package com.guinea.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.joda.time.DateTime;
import org.springframework.ui.ModelMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.guinea.util.CommonToolsUtil;
import com.guinea.util.Constents;
import com.guinea.web.model.User;

/**
 * Created by shiky on 2015/12/2.
 */
public class BaseFormController {

	protected final static String PAGE = "page", ROWS = "rows", CHARSET = "UTF-8", MTA = "mta", MSG = "msg";

	protected Map<String, String> processParamInfo(Map<String, String[]> parmMap, Integer pageSize) {
		Map<String, String> conditions = Maps.newConcurrentMap();
		if (MapUtils.isNotEmpty(parmMap)) {
			for (Map.Entry<String, String[]> entry : parmMap.entrySet()) {
				String key = StringUtils.trim(entry.getKey()), value = CommonToolsUtil.filterChar(entry.getValue()[0]);
				switch (key) {
				case PAGE:
					Integer pageNumber_i = Constents.pageNumber;
					if (StringUtils.isNumericSpace(value)) {
						try {
							pageNumber_i = Integer.valueOf(value);
						} catch (Exception e) {
							pageNumber_i = Constents.pageNumber;
						} finally {
							conditions.put(Constents.PAGENUMBER_, pageNumber_i + "");
						}
					}
					break;
				case ROWS:
					conditions.put(Constents.PAGESIZE_, pageSize + "");
					break;
				default:
					if (!Strings.isNullOrEmpty(value)) {
						conditions.put(key, value);
					}
					break;
				}
			}
		}
		return conditions;
	}

	protected Map<String, String> processParam(Map<String, String[]> parmMap, Integer hashCode) {
		Map<String, String> conditions = Maps.newConcurrentMap();
		if (MapUtils.isNotEmpty(parmMap)) {
			for (Map.Entry<String, String[]> entry : parmMap.entrySet()) {
				String key = StringUtils.trim(entry.getKey()), value = CommonToolsUtil.filterChar(entry.getValue()[0]);
				switch (key) {
				case PAGE:
					Integer pageNumber_i = Constents.pageNumber;
					if (StringUtils.isNumericSpace(value)) {
						try {
							pageNumber_i = Integer.valueOf(value);
						} catch (Exception e) {
							pageNumber_i = Constents.pageNumber;
						} finally {
							conditions.put(Constents.PAGENUMBER_, pageNumber_i + "");
						}
					}
					break;
				case ROWS:
					Integer pageSize_i = Constents.pageSize;
					if (StringUtils.isNumericSpace(value)) {
						try {
							pageSize_i = Integer.valueOf(value);
						} catch (Exception e) {
							pageSize_i = Constents.pageSize;
						} finally {
							User user = (User) SecurityUtils.getSubject().getPrincipal();
							if (!pageSize_i.equals(Constents.pageSize)) {
								processPageMap(user.getLoginname(), pageSize_i, hashCode);
								Constents.operatorPageMap.put(user.getLoginname(),new DateTime());
							} else {
								Map<String, Map<Integer, Integer>> userPageMap = Constents.userPage;
								if (null != userPageMap && userPageMap.size() > 0) {
									Map<Integer, Integer> pageMap = Constents.userPage.get(user.getLoginname());
									if (null != pageMap && pageMap.size() > 0) {
										pageMap.remove(hashCode);
									}
								}
							}
							conditions.put(Constents.PAGESIZE_, pageSize_i + "");
						}
					}
					break;
				default:
					if (!Strings.isNullOrEmpty(value)) {
						conditions.put(key, value);
					}
					break;
				}
			}
		}
		return conditions;
	}

	protected void showList(ModelMap model, Integer hashCode) {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		model.put("pageSize", obtainPageSize(Constents.userPage.get(user.getLoginname()), hashCode));
		model.put("pageList", JSON.toJSONString(Constents.pageList));
	}

	private Integer obtainPageSize(Map<Integer, Integer> pageMap, Integer hashCode) {
		Integer pageSize = Constents.pageSize;
		if (null != pageMap && pageMap.size() > 0) {
			pageSize = pageMap.get(hashCode);
		}
		if (null == pageSize) {
			pageSize = Constents.pageSize;
		}
		return pageSize;
	}

	private void processPageMap(String loginName, Integer pageSize_i, Integer hashCode) {
		Map<String, Map<Integer, Integer>> userPageMap = Constents.userPage;
		if (null != userPageMap && userPageMap.size() > 0) {
			Map<Integer, Integer> pageMap = Constents.userPage.get(loginName);
			if (null != pageMap && pageMap.size() > 0) {
				pageMap.put(hashCode, pageSize_i);
			} else {
				pageMap = Maps.newHashMap();
				pageMap.put(hashCode, pageSize_i);
			}
		} else {
			Map<Integer, Integer> pageMap = Maps.newHashMap();
			pageMap.put(hashCode, pageSize_i);
			userPageMap.put(loginName, pageMap);
		}

	}

	protected void succMesg(HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		json.put(MTA, "1");
		json.put(MSG, "操作成功!");
		messagePrint(request, response, json);
	}

	protected void succMesg(HttpServletRequest request, HttpServletResponse response, String msg) {
		JSONObject json = new JSONObject();
		json.put(MTA, "1");
		json.put(MSG, msg);
		messagePrint(request, response, json);
	}

	protected void succMesgParam(HttpServletRequest request, HttpServletResponse response, JSONObject json) {
		json.put(MTA, "1");
		messagePrint(request, response, json);
	}

	protected void succMesgParam(HttpServletRequest request, HttpServletResponse response, Object jsonObj) {
		JSONObject json = new JSONObject();
		json.put(MTA, "1");
		json.put(MSG, jsonObj);
		messagePrint(request, response, json);
	}

	protected void limitMsg(HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		json.put(MTA, "-3");
		json.put(MSG, "没有权限");
		messagePrint(request, response, json);
	}

	protected void errorMsg(HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		json.put(MTA, "-1");
		json.put(MSG, "操作失败");
		messagePrint(request, response, json);
	}

	protected void errorMsg(HttpServletRequest request, HttpServletResponse response, String msg) {
		JSONObject json = new JSONObject();
		json.put(MTA, "-1");
		json.put(MSG, msg);
		messagePrint(request, response, json);
	}

	private void messagePrint(HttpServletRequest request, HttpServletResponse response, JSONObject jsonObject) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			request.setCharacterEncoding(CHARSET);
			response.setCharacterEncoding(CHARSET);
			response.setContentType("text/html;charset=utf-8");
			out = response.getWriter();
			out.print(jsonObject);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != out) {
				out.close();
			}
		}
	}

	protected void showDataGrid(HttpServletRequest request, HttpServletResponse response, String result) {
		PrintWriter out = null;
		try {
			request.setCharacterEncoding(CHARSET);
			response.setCharacterEncoding(CHARSET);
			response.setContentType("text/html;charset=utf-8");
			out = response.getWriter();
			out.print(result);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != out) {
				out.close();
			}
		}
	}
}
