package com.guinea.web.controller.trace;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.guinea.datadb.mysql.entity.TracePoint;
import com.guinea.profession.service.trace.TracePointService;
import com.guinea.web.BaseFormController;
import com.guinea.web.model.User;

/**
 * Created by shiky on 2015/12/17.
 */
@Controller
public class TracePointController extends BaseFormController {

	private static Logger logger = LoggerFactory.getLogger(TracePointController.class);

	@Autowired
	private TracePointService tracePointService;

	@RequestMapping(value = "/trace/tracePointList", method = RequestMethod.GET)
	public String printWelcome(HttpServletRequest request, ModelMap model) {
		showList(model, logger.hashCode());
		return "trace/tracePointList";
	}

	@RequestMapping(value = "/trace/tracePointList", method = { RequestMethod.POST })
	public void findBpage(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("位置点 -- page view    ===== post");
		showDataGrid(request, response, JSON.toJSONString(
				tracePointService.findByPage(processParam(request.getParameterMap(), logger.hashCode()))));
	}

	@RequestMapping(value = "/trace/tracePointAdd", method = RequestMethod.GET)
	public String showAdd(HttpServletRequest request, ModelMap model) {
		return "trace/tracePointAdd";
	}

	@RequestMapping(value = "/trace/tracePointAdd", method = { RequestMethod.POST })
	public void processAdd(HttpServletRequest request, HttpServletResponse response) {
		String pname = StringUtils.trim(request.getParameter("pname")),
				scope = StringUtils.trim(request.getParameter("scope")),
				address = StringUtils.trim(request.getParameter("address")),
				lat = StringUtils.trim(request.getParameter("lat")),
				lgt = StringUtils.trim(request.getParameter("lgt"));
		if (StringUtils.isNotBlank(scope) && StringUtils.isNumericSpace(scope)) {
			if (StringUtils.isNotBlank(lat) && StringUtils.isNotBlank(lgt)) {
				TracePoint tracePoint = new TracePoint();
				tracePoint.setPname(pname);
				tracePoint.setAddress(address);
				tracePoint.setScope(Float.parseFloat(scope));
				tracePoint.setLat(new BigDecimal(lat));
				tracePoint.setLgt(new BigDecimal(lgt));
				Subject subject = SecurityUtils.getSubject();
				User user = (User) subject.getPrincipal();
				tracePoint.setUid(user.getId());
				tracePointService.insert(tracePoint);
				succMesg(request, response, "添加成功!");
			} else {
				errorMsg(request, response, "地址坐标获取失败，地址或不合法!");
			}
		} else {
			errorMsg(request, response, "范围输入不合法!");
		}
	}

	@RequestMapping(value = "/trace/tracePointEdit", method = RequestMethod.GET)
	public String showEdit(HttpServletRequest request, Long id, ModelMap model) {
		TracePoint tracePoint = new TracePoint();
		if (null != id) {
			tracePoint = tracePointService.selectByPrimaryKey(id);
		}
		model.put("tracePoint", tracePoint);
		return "trace/tracePointEdit";
	}

	@RequestMapping(value = "/trace/tracePointEdit", method = { RequestMethod.POST })
	public void processEdit(HttpServletRequest request, HttpServletResponse response) {
		String pname = StringUtils.trim(request.getParameter("pname")),
				scope = StringUtils.trim(request.getParameter("scope")),
				address = StringUtils.trim(request.getParameter("address")),
				lat = StringUtils.trim(request.getParameter("lat")),
				lgt = StringUtils.trim(request.getParameter("lgt")), id = StringUtils.trim(request.getParameter("id"));
		if (StringUtils.isNotBlank(scope) && StringUtils.isNumericSpace(scope)) {
			if (StringUtils.isNotBlank(lat) && StringUtils.isNotBlank(lgt)) {
				TracePoint tracePoint = new TracePoint();
				tracePoint.setId(Long.parseLong(id));
				tracePoint.setPname(pname);
				tracePoint.setAddress(address);
				tracePoint.setScope(Float.parseFloat(scope));
				tracePoint.setLat(new BigDecimal(lat));
				tracePoint.setLgt(new BigDecimal(lgt));
				tracePointService.updateByPrimaryKey(tracePoint);
				succMesg(request, response, "更新成功!");
			} else {
				errorMsg(request, response, "地址坐标获取失败，地址或不合法!");
			}
		} else {
			errorMsg(request, response, "范围输入不合法!");
		}
	}

	/**
	 * 删除
	 *
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/trace/tracePointDelete", method = { RequestMethod.POST })
	public void processDelete(Long id, HttpServletRequest request, HttpServletResponse response) {
		if (null != id) {
			tracePointService.deleteByPrimaryKey(id);
			succMesg(request, response, "删除成功!");
		} else {
			errorMsg(request, response, "参数不全!");
		}
	}
}
