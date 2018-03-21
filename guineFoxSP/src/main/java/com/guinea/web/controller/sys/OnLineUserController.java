package com.guinea.web.controller.sys;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.guinea.util.Constents;
import com.guinea.util.date.JodatimeUtil;
import com.guinea.web.BaseFormController;
import com.guinea.web.PageMsg;
import com.guinea.web.model.OnLineUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by shiky on 2015/12/22.
 */
@Controller
public class OnLineUserController extends BaseFormController {

	private static Logger logger = LoggerFactory.getLogger(OnLineUserController.class);

	@Autowired
	private SessionDAO sessionDAO;

	@RequestMapping(value = "sys/onLineUserList", method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		try {
			List<Integer> pageList = Arrays.asList(200, 300);
			model.put("pageSize", 200);
			model.put("pageList", objectMapper.writeValueAsString(pageList));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "sys/onLineUserList";
	}

	@RequestMapping(value = "sys/onLineUserList", method = { RequestMethod.POST })
	public void findBpage(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("======查看在线用户====");
		String formart = "yyyy-MM-dd HH:mm:ss";
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		List<OnLineUser> onLineUsers = Lists.newArrayList();
		if (null != sessions && sessions.size() > 0) {
			for (Session ses : sessions) {
				if (null != ses) {
					OnLineUser onLineUser = new OnLineUser();
					onLineUser.setId(ses.getId() + "");
					Object userObj = null;
					try {
						userObj = ses.getAttribute("username");
					} catch (Exception e) {
						userObj = null;
						logger.warn(e.getMessage());
					}
					String username = "";
					if (null != userObj) {
						username = userObj.toString();
					}
					onLineUser.setUsername(username);
					Date lastAccessTime = ses.getLastAccessTime(), startTimestamp = ses.getStartTimestamp();
					if (null != lastAccessTime) {
						onLineUser.setLastAccessTime(JodatimeUtil.getDateToStr(lastAccessTime, formart));
					}
					if (null != startTimestamp) {
						onLineUser.setStartTimestamp(JodatimeUtil.getDateToStr(startTimestamp, formart));
					}
					onLineUser.setHostIP(ses.getHost());
					onLineUsers.add(onLineUser);
				}
			}
		}
		try {
			PageHelper.startPage(Constents.pageNumber, 200);
			PageInfo<OnLineUser> page = new PageInfo<>(onLineUsers);
			PageMsg<OnLineUser> pageMsg = new PageMsg<>();
			pageMsg.setTotal(page.getTotal());
			pageMsg.setRows(onLineUsers);
			showDataGrid(request, response, objectMapper.writeValueAsString(pageMsg));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/forceLogout", method = { RequestMethod.POST })
	public void forceLogout(HttpServletRequest request, HttpServletResponse response) {
		try {
			String sessionId = request.getParameter("sessionId");
			if (StringUtils.isNotBlank(sessionId)) {
				Session session = sessionDAO.readSession(sessionId);
				if (session != null) {
					session.setAttribute(Constents.SESSION_FORCE_LOGOUT_KEY, Boolean.TRUE);
					succMesg(request, response);
				} else {
					errorMsg(request, response);
				}
			} else {
				errorMsg(request, response);
			}
		} catch (Exception e) {
			errorMsg(request, response);
		}
	}
}
