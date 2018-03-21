package com.guinea.web.controller.fund.card;

import com.guinea.cache.SysCache;
import com.guinea.web.BaseFormController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Title: 刷刷
 * @Describe:
 * @Author: shikaiyuan
 * @datetime: 2015/9/22.
 */
@Controller
public class CardTransactrateController extends BaseFormController {

    private static Logger logger = LoggerFactory.getLogger(CardTransactrateController.class);

//    @RequestMapping(value = "/card_transactrate_list", method = RequestMethod.GET)
//    public String print_list(HttpServletRequest request, ModelMap model) {
//        showList(model, logger.hashCode());
//        return "fund/card/card_transactrate_list";
//    }
//
//
//
//    @RequestMapping(value = "/fund/card/card_transactrate_list", method = {RequestMethod.POST})
//    public void find_by_page(HttpServletRequest request, HttpServletResponse response) {
////        try {
////            showDataGrid(request, response, objectMapper.writeValueAsString(
////                    fundTransactrateService.findByPage(processParam(request.getParameterMap(), logger.hashCode()))));
////        } catch (JsonProcessingException e) {
////            e.printStackTrace();
////        }
//    }
//
//    @RequestMapping(value = "/card_transactrate_add", method = RequestMethod.GET)
//    public String show_add(HttpServletRequest request, ModelMap model) {
//        processShowSelect(model);
//        return "/fund/card/card_transactrate_add";
//    }
//
//    @RequestMapping(value = "/fund/card/card_transactrate_add", method = { RequestMethod.POST })
//    public void process_add( HttpServletRequest request, HttpServletResponse response) {
////        if (null != fundStock) {
////            String sCode = fundStock.getScode();
////            FundStock temp = fundStockService.selectByCode(sCode);
////            if (null != temp) {
////                errorMsg(request, response, "代码已存在!");
////            } else {
////                fundStockService.save(fundStock);
////                succMesg(request, response);
////            }
////        } else {
////            errorMsg(request, response);
////        }
//    }
//
//
//    @RequestMapping(value = "/card_transactrate_bath_add", method = RequestMethod.GET)
//    public String show_bath_add(HttpServletRequest request, ModelMap model) {
////        processShowSelect(model);
//        return "/fund/card/card_transactrate_bath_add";
//    }
//
//    @RequestMapping(value = "/fund/card/card_transactrate_add", method = { RequestMethod.POST })
//    public void process_bath_add( HttpServletRequest request, HttpServletResponse response) {
//
//    }
//
//    private void processShowSelect(ModelMap model) {
//        model.put("cardMerchantMap", SysCache.getCardMerchantMap());
//        model.put("cardMerchantMapData", SysCache.getCardMerchantMapData());
//    }
}
