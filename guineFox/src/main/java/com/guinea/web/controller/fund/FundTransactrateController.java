package com.guinea.web.controller.fund;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import com.google.common.collect.Maps;
import com.guinea.datadb.mysql.entity.FundTransactrate;
import com.guinea.dic.TransactrateTypeEnum;
import com.guinea.profession.service.fund.FundTransactrateService;
import com.guinea.util.date.JdotimeUtil;
import com.guinea.web.BaseFormController;
import com.guinea.web.model.User;

/**
 * shiky on 2015/9/22.
 */
@Controller
public class FundTransactrateController extends BaseFormController {

	private static Logger logger = LoggerFactory.getLogger(FundTransactrateController.class);

	@Autowired
	private FundTransactrateService fundTransactrateService;

	@RequestMapping(value = "/fundTransactrateList", method = RequestMethod.GET)
	public String printWelcome(HttpServletRequest request, ModelMap model) {
		showList(model, logger.hashCode());
		processShowSelect(model);
		return "fund/fundTransactrateList";
	}

	@RequestMapping(value = "/fund/fundTransactrateList", method = { RequestMethod.POST })
	public void findBpage(HttpServletRequest request, HttpServletResponse response) {
		showDataGrid(request, response, JSON.toJSONString(
				fundTransactrateService.findByPage(processParam(request.getParameterMap(), logger.hashCode()))));
	}

	@RequestMapping(value = "/fundTransactrateAdd", method = RequestMethod.GET)
	public String showAdd(HttpServletRequest request, ModelMap model) {
		processShowSelect(model);
		return "fund/fundTransactrateAdd";
	}

	@RequestMapping(value = "/fund/fundTransactrateAdd", method = { RequestMethod.POST })
	public void processAdd(FundTransactrate fundTransactrate, HttpServletRequest request,
			HttpServletResponse response) {
		if (null != fundTransactrate) {
			Short scounts = fundTransactrate.getScounts();
			String ttime_str = StringUtils.trim(request.getParameter("ttime_str"));
			if (null != scounts && StringUtils.isNotBlank(ttime_str)) {
				try {
					if (StringUtils.indexOf(ttime_str, ":") > 0 && ttime_str.length() == 19 && StringUtils
							.isNumeric(StringUtils.replace(StringUtils.substring(ttime_str, 0, 10), "-", ""))) {
						Subject subject = SecurityUtils.getSubject();
						User user = (User) subject.getPrincipal();
						fundTransactrate.setUid(user.getId());
						Integer scountg = Math.multiplyExact(scounts, 100);
						Date ttime = JdotimeUtil.getDateTimeForStr(ttime_str, "yyyy-MM-dd HH:mm:ss").toDate();
						BigDecimal mValue = fundTransactrate.getTratio().multiply(new BigDecimal(scountg));
						fundTransactrate.setScountg(scountg);
						fundTransactrate.setMvalue(mValue);
						fundTransactrate.setTtime(ttime);
						fundTransactrateService.save(fundTransactrate);
						succMesg(request, response);
						logger.debug("fundtransactrate is create ");
					} else {
						errorMsg(request, response, "日期格式错误!");
					}
				} catch (IllegalArgumentException e) {
					errorMsg(request, response, "日期格式错误!");
					e.printStackTrace();
				} catch (Exception e) {
					errorMsg(request, response);
					e.printStackTrace();
				}
			} else {
				errorMsg(request, response, "参数不完整!");
			}
		} else {
			errorMsg(request, response);
		}
	}

	private void processShowSelect(ModelMap model) {
		Map<String, String> transactrateTypeMap = Maps.newHashMap();
		for (TransactrateTypeEnum c : TransactrateTypeEnum.values()) {
			transactrateTypeMap.put(c.getCode() + "", c.getName());
		}
		model.put("transactrateTypeMap", transactrateTypeMap);
	}

	@SuppressWarnings("resource")
	@RequestMapping(value = "/fundTransactrateExport_10")
	public void export(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		String fileName = "132";
		response.setHeader("Content-disposition", "attachment;fileName=" + fileName + ".xlsx");
		OutputStream os = response.getOutputStream();
		Workbook wb = new XSSFWorkbook();
		try {
			CreationHelper createHelper = wb.getCreationHelper();
			Sheet sheet1 = wb.createSheet("new sheet");
			Row row = sheet1.createRow((short) 0);
			row.setHeightInPoints(30);

			Cell cell = row.createCell(0);
			cell.setCellValue(1);
			// Or do it on one line.
			row.createCell(1).setCellValue(1.2);
			row.createCell(2).setCellValue(createHelper.createRichTextString("ddd"));
			row.createCell(3).setCellValue(true);

			CellStyle cellStyle = wb.createCellStyle();
			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
			Cell celldate = row.createCell(4);
			celldate.setCellValue(new Date());
			celldate.setCellStyle(cellStyle);

			wb.write(os);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != os) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
