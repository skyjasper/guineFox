package com.guinea.web.controller.fund.card;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.guinea.cache.SysCache;
import com.guinea.datadb.mysql.entity.card.CardMerchant;
import com.guinea.datadb.mysql.entity.card.CardOperator;
import com.guinea.profession.service.fund.card.CardMerchantService;
import com.guinea.web.BaseFormController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * @Title: 刷刷商户
 * @Describe:
 * @Author: shikaiyuan
 * @Datetime: 2016/5/24
 */
@Controller
public class CardMerchantController extends BaseFormController {

    private static Logger logger = LoggerFactory.getLogger(CardMerchantController.class);

    @Autowired
    private CardMerchantService cardMerchantService;

    @RequestMapping(value = "/card_merchant_list", method = RequestMethod.GET)
    public String print_list(ModelMap model) {
        showList(model, logger.hashCode());
        processShowSelect(model);
        return "fund/card/card_merchant_list";
    }

    @RequestMapping(value = "/fund/card/card_merchant_list", method = {RequestMethod.POST})
    public void find_by_page(HttpServletRequest request, HttpServletResponse response) {
        try {
            showDataGrid(request, response, objectMapper.writeValueAsString(
                    cardMerchantService.findByPage(processParam(request.getParameterMap(), logger.hashCode()))));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/card_merchant_add", method = RequestMethod.GET)
    public String show_add(ModelMap model) {
        processShowSelect(model);
        return "fund/card/card_merchant_add";
    }

    @RequestMapping(value = "/fund/card/card_merchant_add", method = {RequestMethod.POST})
    public void process_add(HttpServletRequest request, HttpServletResponse response) {
        String operatorId = StringUtils.trim(request.getParameter("operatorId")), code_s = StringUtils.trim(request.getParameter("code")),
                name = StringUtils.trim(request.getParameter("name")), rate = StringUtils.trim(request.getParameter("rate"));
        if (StringUtils.isNumeric(code_s) && StringUtils.indexOf(rate, ".") > 0) {
            Long code = Long.valueOf(code_s);
            CardMerchant cardMerchant = new CardMerchant();
            cardMerchant.setCode(code);
            cardMerchant.setName(name);
            cardMerchant.setOperatorId(Long.valueOf(operatorId));
            cardMerchant.setRate(Float.valueOf(rate));
            CardMerchant temp = cardMerchantService.selectByCode(code);
            if (null != temp) {
                errorMsg(request, response, "代码已经存在!");
            } else {
                cardMerchantService.insert(cardMerchant);
                succMesg(request, response);
                //同步缓存
                SysCache.putCardMerchantMap(cardMerchant);
            }
        } else {
            errorMsg(request, response, "代码，费率只能是数字");
        }
    }

    /****
     * 编辑商户
     *
     * @param code
     * @param model
     * @return
     */
    @RequestMapping(value = "/card_merchant_edit", method = RequestMethod.GET)
    public String show_edit(String code, ModelMap model) {
        if (StringUtils.isNotBlank(code) && StringUtils.isNumericSpace(code)) {
            processShowSelect(model);
            CardMerchant cardMerchant = cardMerchantService.selectByCode(Long.valueOf(code));
            if (null != cardMerchant) {
                model.put("cardMerchant", cardMerchant);
            }
        }
        return "fund/card/card_merchant_edit";
    }

    @RequestMapping(value = "/fund/card/card_merchant_edit", method = {RequestMethod.POST})
    public void process_edit(HttpServletRequest request, HttpServletResponse response) {
        String operatorId = StringUtils.trim(request.getParameter("operatorId")), code_s = StringUtils.trim(request.getParameter("code")),
                name = StringUtils.trim(request.getParameter("name")), rate = StringUtils.trim(request.getParameter("rate")),
                id = StringUtils.trim(request.getParameter("id"));
        if (StringUtils.isNumeric(code_s) && StringUtils.indexOf(rate, ".") > 0) {
            Long code = Long.valueOf(code_s);
            CardMerchant cardMerchant = new CardMerchant();
            cardMerchant.setId(Long.valueOf(id));
            cardMerchant.setCode(code);
            cardMerchant.setName(name);
            cardMerchant.setOperatorId(Long.valueOf(operatorId));
            cardMerchant.setRate(Float.valueOf(rate));
            CardMerchant temp = cardMerchantService.selectByCode(code);
            if (null != temp) {
                if (cardMerchant.getId() != temp.getId()) {
                    errorMsg(request, response, "代码已经存在!");
                } else {
                    cardMerchantService.updateByPrimaryKey(cardMerchant);
                    succMesg(request, response);
                    //同步缓存
                    SysCache.putCardMerchantMap(cardMerchant);
                }
            } else {
                cardMerchantService.updateByPrimaryKey(cardMerchant);
                succMesg(request, response);
                //同步缓存
                SysCache.putCardMerchantMap(cardMerchant);
            }
        } else {
            errorMsg(request, response, "代码，费率只能是数字");
        }
    }

    /**
     * 删除
     *
     * @param code
     * @param request
     * @param response
     */
    @RequestMapping(value = "/fund/card/card_merchant_delete", method = {RequestMethod.POST})
    public void processDelete(String code, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isNotBlank(code) && StringUtils.isNumeric(code)) {
            cardMerchantService.deleteByCode(Long.valueOf(code));
            //同步缓存
            SysCache.removeCardMerchantMapById(code);
            succMesg(request, response, "删除成功!");
        } else {
            errorMsg(request, response, "参数不全!");
        }
    }

    private void processShowSelect(ModelMap model) {
        model.put("cardOperatorMap", SysCache.getCardOperatorMap());
    }
}
