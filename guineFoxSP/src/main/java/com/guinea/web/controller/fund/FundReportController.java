package com.guinea.web.controller.fund;

import com.guinea.web.BaseFormController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * shiky on 2015/9/22.
 */
@Controller
public class FundReportController extends BaseFormController {
    private static Logger logger = LoggerFactory.getLogger(FundReportController.class);

    @RequestMapping(value = "/reportList_pie", method = RequestMethod.GET)
    public String report_pie(HttpServletRequest request, ModelMap model) {
        showList(model, logger.hashCode());
        return "fund/reportList_pie";
    }

    @RequestMapping(value = "/reportList_line", method = RequestMethod.GET)
    public String report_line(ModelMap model) {
        showList(model, logger.hashCode());

        /***
         * SELECT fct.fName,fxkc.* from fund_cardtrade fct LEFT JOIN
         * fund_xykcard fxkc on fct.id = fxkc.fcid
         */
        return "fund/reportList_line";
    }
}
