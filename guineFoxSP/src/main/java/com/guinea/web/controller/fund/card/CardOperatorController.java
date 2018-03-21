package com.guinea.web.controller.fund.card;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.guinea.cache.SysCache;
import com.guinea.datadb.mysql.entity.card.CardOperator;
import com.guinea.profession.service.fund.card.CardOperatorService;
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

/**
 * @Title: 运营商配置
 * @Describe:
 * @Author: shikaiyuan
 * @Datetime: 2016/5/23
 */
@Controller
public class CardOperatorController extends BaseFormController {

    private static Logger logger = LoggerFactory.getLogger(CardOperatorController.class);

    @Autowired
    private CardOperatorService cardOperatorService;

    @RequestMapping(value = "/card_operator_list", method = RequestMethod.GET)
    public String print_list(HttpServletRequest request, ModelMap model) {
        showList(model, logger.hashCode());
        return "fund/card/card_operator_list";
    }

    @RequestMapping(value = "/fund/card/card_operator_list", method = {RequestMethod.POST})
    public void find_by_page(HttpServletRequest request, HttpServletResponse response) {
        try {
            showDataGrid(request, response, objectMapper.writeValueAsString(
                    cardOperatorService.findByPage(processParam(request.getParameterMap(), logger.hashCode()))));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/card_operator_add", method = RequestMethod.GET)
    public String show_add(HttpServletRequest request, ModelMap model) {
        return "fund/card/card_operator_add";
    }

    @RequestMapping(value = "/fund/card/card_operator_add", method = {RequestMethod.POST})
    public void process_add(CardOperator cardOperator, HttpServletRequest request, HttpServletResponse response) {
        if (null != cardOperator) {
            String name = cardOperator.getName();
            if (StringUtils.isNotBlank(name)) {
                CardOperator temp = cardOperatorService.selectByName(name);
                if (null != temp) {
                    errorMsg(request, response, "名称已经存在!");
                } else {
                    cardOperatorService.save(cardOperator);
                    succMesg(request, response);
                    //同步缓存
                    SysCache.putCardOperator(cardOperator);
                }
            }
        } else {
            errorMsg(request, response);
        }
    }

    @RequestMapping(value = "/card_operator_edit", method = RequestMethod.GET)
    public String show_edit(Long id, ModelMap model) {
        if (null != id) {
            CardOperator cardOperator = cardOperatorService.selectByPrimaryKey(id);
            model.put("cardOperator", cardOperator);
        }
        return "fund/card/card_operator_edit";
    }

    @RequestMapping(value = "/fund/card/card_operator_edit", method = {RequestMethod.POST})
    public void process_edit(CardOperator cardOperator, HttpServletRequest request, HttpServletResponse response) {
        if (null != cardOperator) {
            String name = cardOperator.getName();
            if (StringUtils.isNotBlank(name)) {
                CardOperator temp = cardOperatorService.selectByName(name);
                if (null != temp) {
                    if (cardOperator.getId() != temp.getId()) {
                        errorMsg(request, response, "名称已经存在!");
                    } else {
                        succMesg(request, response);
                    }
                } else {
                    cardOperatorService.updateByPrimaryKey(cardOperator);
                    succMesg(request, response);
                    //同步缓存
                    SysCache.putCardOperator(cardOperator);
                }
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
    @RequestMapping(value = "/fund/card/card_operator_delete", method = {RequestMethod.POST})
    public void processDelete(Long id, HttpServletRequest request, HttpServletResponse response) {
        if (null != id) {
            cardOperatorService.deleteByPrimaryKey(id);
            //同步缓存
            SysCache.removeCardOperatordeleteById(id);
            succMesg(request, response, "删除成功!");
        } else {
            errorMsg(request, response, "参数不全!");
        }
    }
}
