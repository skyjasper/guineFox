package com.guinea.web.controller.fund;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.guinea.datadb.mysql.entity.FundStock;
import com.guinea.dic.StockPaceEnum;
import com.guinea.dic.StockTypeEnum;
import com.guinea.profession.service.fund.FundStockService;
import com.guinea.web.BaseFormController;

/**
 * shiky on 2015/9/17.
 */
@Controller
public class FundStockController extends BaseFormController {

	private static Logger logger = LoggerFactory.getLogger(FundStockController.class);

	@Autowired
	private FundStockService fundStockService;

	@RequestMapping(value = "/fundStockList", method = RequestMethod.GET)
	public String printWelcome(HttpServletRequest request, ModelMap model) {
		showList(model, logger.hashCode());
		processShowSelect(model);
		return "fund/fundStockList";
	}

	@RequestMapping(value = "/fund/fundStockList", method = { RequestMethod.POST })
	public void findBpage(HttpServletRequest request, HttpServletResponse response) {
		showDataGrid(request, response, JSON
				.toJSONString(fundStockService.findByPage(processParam(request.getParameterMap(), logger.hashCode()))));
	}

	@RequestMapping(value = "/fundStockAdd", method = RequestMethod.GET)
	public String showAdd(HttpServletRequest request, ModelMap model) {
		processShowSelect(model);
		return "fund/fundStockAdd";
	}

	@RequestMapping(value = "/fund/fundStockAdd", method = { RequestMethod.POST })
	public void processAdd(FundStock fundStock, HttpServletRequest request, HttpServletResponse response) {
		if (null != fundStock) {
			String sCode = fundStock.getScode();
			FundStock temp = fundStockService.selectByCode(sCode);
			if (null != temp) {
				errorMsg(request, response, "代码已存在!");
			} else {
				fundStockService.save(fundStock);
				succMesg(request, response);
			}
		} else {
			errorMsg(request, response);
		}
	}

	/****
	 * edit
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/fundStockEdit", method = RequestMethod.GET)
	public String showEdit(HttpServletRequest request, Long id, ModelMap model) {
		if (null != id) {
			FundStock fundStock = fundStockService.selectById(id);
			processShowSelect(model);
			model.put("fundStock", fundStock);
		}
		return "fund/fundStockEdit";
	}

	private void processShowSelect(ModelMap model) {
		Map<String, String> stockTypeMap = Maps.newHashMap(), stockPaceMap = Maps.newHashMap();
		for (StockTypeEnum c : StockTypeEnum.values()) {
			stockTypeMap.put(c.getCode() + "", c.getName());
		}
		for (StockPaceEnum c : StockPaceEnum.values()) {
			stockPaceMap.put(c.getCode() + "", c.getName());
		}
		model.put("stockTypeMap", stockTypeMap);
		model.put("stockPaceMap", stockPaceMap);
	}

	@RequestMapping(value = "/fund/fundStockEdit", method = { RequestMethod.POST })
	public void processEdit(FundStock fundStock, HttpServletRequest request, HttpServletResponse response) {
		if (null != fundStock) {
			String sCode = StringUtils.trim(fundStock.getScode());
			Long id = fundStock.getId();
			if (null != id && !Strings.isNullOrEmpty(sCode)) {
				FundStock temp = fundStockService.selectByCode(sCode);
				if (null != temp && !temp.getId().equals(id)) {
					errorMsg(request, response, "代码已存在!");
				} else {
					fundStockService.updateByPrimaryKey(fundStock);
					succMesg(request, response, "更新成功!");
				}
			} else {
				errorMsg(request, response, "参数不全!");
			}
		} else {
			errorMsg(request, response);
		}
	}

	/**
	 * 删除
	 *
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/fund/fundStockDelete", method = { RequestMethod.POST })
	public void processDelete(Long id, HttpServletRequest request, HttpServletResponse response) {
		if (null != id) {
			fundStockService.deleteByPrimaryKey(id);
			succMesg(request, response, "删除成功!");
		} else {
			errorMsg(request, response, "参数不全!");
		}
	}

}