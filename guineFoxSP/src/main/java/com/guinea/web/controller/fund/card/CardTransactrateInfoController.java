package com.guinea.web.controller.fund.card;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.guinea.cache.SysCache;
import com.guinea.datadb.mysql.entity.card.CardTransactrateInfo;
import com.guinea.datadb.mysql.entity.card.CardMerchant;
import com.guinea.profession.service.fund.card.CardTransactrateInfoServer;
import com.guinea.util.CommonToolsUtil;
import com.guinea.util.date.JodatimeUtil;
import com.guinea.web.BaseFormController;
import com.guinea.web.model.User;
import com.guinea.web.model.card.CardMsgTip;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.joda.time.DateTime;
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
import java.util.Map;

import static com.guinea.cache.SysCache.getCardMerchantMapData;

/**
 * @Title: 刷刷
 * @Describe: 刷刷流水
 * @Author: shikaiyuan
 * @Datetime: 2016/5/30
 */
@Controller
public class CardTransactrateInfoController extends BaseFormController {

    private static Logger logger = LoggerFactory.getLogger(CardTransactrateInfoController.class);

    @Autowired
    private CardTransactrateInfoServer cardTransactrateInfoServer;

    @RequestMapping(value = "/card_transactrate_info_list", method = RequestMethod.GET)
    public String print_list(ModelMap model) {
        showList(model, logger.hashCode());
        model.put("cardMerchantMap", SysCache.getCardMerchantMap());
        return "fund/card/card_transactrate_info_list";
    }

    @RequestMapping(value = "/fund/card/card_transactrate_info_list", method = {RequestMethod.POST})
    public void find_by_page(HttpServletRequest request, HttpServletResponse response) {
        try {
            showDataGrid(request, response, objectMapper.writeValueAsString(
                    cardTransactrateInfoServer.findByPage(processParam(request.getParameterMap(), "user_id", logger.hashCode()))));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/card_transactrate_info_add", method = RequestMethod.GET)
    public String show_add(ModelMap model) {
        processShowSelect(model);
        model.put("cardMerchant", initMerchantRate());
        return "/fund/card/card_transactrate_info_add";
    }

    /***
     * 添加刷刷
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/fund/card/card_transactrate_info_add", method = {RequestMethod.POST})
    public void process_add(HttpServletRequest request, HttpServletResponse response) {
        String merchantId_str = StringUtils.trim(request.getParameter("merchantId")),
                transactrateMoney_str = StringUtils.trim(request.getParameter("transactrateMoney")),
                cashPayment_str = StringUtils.trim(request.getParameter("cashPayment")),
                transactrateTime_str = StringUtils.trim(request.getParameter("transactrateTime")),
                cashTime_str = StringUtils.trim(request.getParameter("cashTime")),
                ps = StringUtils.trim(request.getParameter("ps"));
        if (null != merchantId_str && StringUtils.isNumeric(merchantId_str) && StringUtils.isNotBlank(cashTime_str)
                && StringUtils.isNotBlank(cashPayment_str)) {
            Subject subject = SecurityUtils.getSubject();
            User user = (User) subject.getPrincipal();
            if (null != user) {
                Long merchantId = Long.parseLong(merchantId_str);
                DateTime transactrateTime = JodatimeUtil.getDateTimeForStr(transactrateTime_str, JodatimeUtil.fmtSS);
                CardTransactrateInfo transactrateInfo = new CardTransactrateInfo();
                transactrateInfo.setUserId(user.getId());
                transactrateInfo.setTransactrateTime(transactrateTime.toDate());
                transactrateInfo.setTransactrateMoney(new BigDecimal(transactrateMoney_str));
//                transactrateInfo.setCashTime(Long.parseLong(cashTime.toString(JodatimeUtil.simfmtSS)));
//                transactrateInfo.setCashPayment(cashPayment);
                transactrateInfo.setMerchantId(merchantId);
                transactrateInfo.setPs(ps);
                cardTransactrateInfoServer.insert(transactrateInfo);
                succMesg(request, response);
            } else {
                errorMsg(request, response, "没有登录!");
            }
        } else {
            errorMsg(request, response);
        }
    }

    /***
     * 级联
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/fund/card/card_transactrate_info_cascade", method = {RequestMethod.POST})
    public void process_cascade(Long merchant_id, HttpServletRequest request, HttpServletResponse response) {
        if (null != merchant_id) {
            try {
                CardMerchant cardMerchant = SysCache.getCardMerchantMapData().get(merchant_id + "");
                succMesgParam(request, response, objectMapper.writeValueAsString(cardMerchant));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            errorMsg(request, response);
        }
    }

    /***
     * 处理显示消息
     *
     * @param str_transactrateTimePay
     * @param transactrateTimePay
     * @param cash_payment
     * @return
     */
    private CardMsgTip process_viewmessage(StringBuffer message_sbf, String str_transactrateTimePay, DateTime transactrateTimePay, BigDecimal cash_payment) {
        StringBuffer result_sbf = new StringBuffer("<font color='#a52a2a'>"), final_sbf = new StringBuffer();
        final_sbf.append("正常最晚到账时间：");
        final_sbf.append(str_transactrateTimePay);
        final_sbf.append("(");
        final_sbf.append(JodatimeUtil.getWeekDay(transactrateTimePay));
        final_sbf.append("),(请以实际到账时间为准，以上作为参考)。");

        result_sbf.append(message_sbf.toString());
        result_sbf.append(final_sbf.toString());
        result_sbf.append("</font>");
        System.out.println(result_sbf);

        CardMsgTip cardMsgTip = new CardMsgTip();
        cardMsgTip.setMessage(result_sbf.toString());
        cardMsgTip.setCashPayment(cash_payment.toString());
        cardMsgTip.setTransactrateTimePay(str_transactrateTimePay);
        return cardMsgTip;
    }

    /***
     * 处理显示消息
     *
     * @param str_transactrateTimePay
     * @param transactrateTimePay
     * @param cash_payment
     * @return
     */
    private CardMsgTip process_viewmessage_bath(StringBuffer message_sbf, String str_transactrateTimePay,
                                                DateTime transactrateTimePay, BigDecimal cash_payment,
                                                String totalTransactrateMoney, int count) {
        StringBuffer result_sbf = new StringBuffer("<font color='#a52a2a'>"), final_sbf = new StringBuffer();
        final_sbf.append("正常最晚到账时间：");
        final_sbf.append(str_transactrateTimePay);
        final_sbf.append("(");
        final_sbf.append(JodatimeUtil.getWeekDay(transactrateTimePay));
        final_sbf.append("),(请以实际到账时间为准，以上作为参考)。");

        result_sbf.append(message_sbf.toString());
        result_sbf.append(final_sbf.toString());
        result_sbf.append("</font>");
        System.out.println(result_sbf);

        CardMsgTip cardMsgTip = new CardMsgTip();
        cardMsgTip.setMessage(result_sbf.toString());
        cardMsgTip.setCashPayment(cash_payment.toString());
        cardMsgTip.setTransactrateTimePay(str_transactrateTimePay);
        cardMsgTip.setTotalTransactrateMoney(totalTransactrateMoney);
        cardMsgTip.setCount(count);
        return cardMsgTip;
    }


    /***
     * 计算
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/fund/card/card_transactrate_info_match", method = {RequestMethod.POST})
    public void process_match(HttpServletRequest request, HttpServletResponse response) {
        String transactrateMoney_s = StringUtils.trim(request.getParameter("transactrateMoney")),
                transactrateTime_s = StringUtils.trim(request.getParameter("transactrateTime")),
                merchantId_s = StringUtils.trim(request.getParameter("merchantId"));
        if (StringUtils.isNotBlank(transactrateMoney_s) && StringUtils.isNotBlank(transactrateTime_s)
                && StringUtils.isNotBlank(merchantId_s) && StringUtils.isNumeric(merchantId_s)) {
            try {
                //处理到账逻辑
                Map<Integer, Object> resultMap =
                        cardTransactrateInfoServer.process_transactrate_matche
                                (merchantId_s, transactrateMoney_s, transactrateTime_s);
                BigDecimal cash_payment = (BigDecimal) resultMap.get(1);
                DateTime transactrateTimePay = (DateTime) resultMap.get(2);
                //支付时间
                String str_transactrateTimePay =
                        JodatimeUtil.getDateTimeToStr(transactrateTimePay, JodatimeUtil.fmtDD) + " 18:30:00";
                succMesg(request, response, objectMapper.writeValueAsString(process_viewmessage(
                        (StringBuffer) resultMap.get(3), str_transactrateTimePay, transactrateTimePay, cash_payment)));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            errorMsg(request, response, "参数不全!");
        }
    }

    /***
     * 批量计算
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/fund/card/card_transactrate_info_bath_match", method = {RequestMethod.POST})
    public void process_bath_match(HttpServletRequest request, HttpServletResponse response) {
        String transactrateTime_s = StringUtils.trim(request.getParameter("transactrateTime")),
                transactrate_info_s = StringUtils.trim(request.getParameter("transactrate_info")),
                merchantId_s = StringUtils.trim(request.getParameter("merchantId"));
        Double transactrateMoney = null;
        Integer tcount = null;
        boolean flag = false;
        if (StringUtils.isNotBlank(transactrate_info_s) && StringUtils.indexOf(transactrate_info_s, ";") > 0
                && StringUtils.isNotBlank(transactrateTime_s) && StringUtils.indexOf(transactrateTime_s, "-") > 0
                && StringUtils.isNotBlank(merchantId_s)) {
            String[] transactrateMoneys = StringUtils.split(transactrate_info_s, ";");
            if (ArrayUtils.isNotEmpty(transactrateMoneys)) {
                transactrateMoney = 0.0;
                tcount = 0;
                for (String transactrateMoneyTemp : transactrateMoneys) {
                    if (CommonToolsUtil.isMoney(transactrateMoneyTemp)) {
                        Double temp = Double.parseDouble(StringUtils.trim(transactrateMoneyTemp));
                        transactrateMoney = transactrateMoney + temp;
                        tcount++;
                    } else {
                        flag = true;
                        break;
                    }
                }
            }
        }else{
            logger.debug("参数不全");
        }
        if (flag) {
            errorMsg(request, response, "金额只能是数字");
            return;
        }
        if (null != transactrateMoney && null != tcount && !flag) {
            try {
                String totalTransactrateMoney = transactrateMoney + "";
                //处理到账逻辑
                Map<Integer, Object> resultMap = cardTransactrateInfoServer.process_transactrate_matche(merchantId_s,
                        totalTransactrateMoney, transactrateTime_s);
                BigDecimal cash_payment = (BigDecimal) resultMap.get(1);
                DateTime transactrateTimePay = (DateTime) resultMap.get(2);
                //支付时间
                String str_transactrateTimePay =
                        JodatimeUtil.getDateTimeToStr(transactrateTimePay, JodatimeUtil.fmtDD) + " 18:30:00";
                succMesg(request, response, objectMapper.writeValueAsString(process_viewmessage_bath(
                        (StringBuffer) resultMap.get(3), str_transactrateTimePay, transactrateTimePay,
                        cash_payment, totalTransactrateMoney, tcount)));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            errorMsg(request, response);
        }
    }


    /***
     * 批量添加
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/card_transactrate_info_bath_add", method = RequestMethod.GET)
    public String show_bath_add(ModelMap model) {
        processShowSelect(model);
        model.put("cardMerchant", initMerchantRate());
        return "/fund/card/card_transactrate_info_bath_add";
    }

    private CardMerchant initMerchantRate() {
        CardMerchant cardMerchant = new CardMerchant();
        cardMerchant.setCode(916330152231L);
        cardMerchant.setRate(0.38F);
        return cardMerchant;
    }

    /***
     * 批量添加
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/fund/card/card_transactrate_info_bath_add", method = {RequestMethod.POST})
    public void process_bath_add(HttpServletRequest request, HttpServletResponse response) {
        String merchantId_str = StringUtils.trim(request.getParameter("merchantId")),
                transactrate_info_s = StringUtils.trim(request.getParameter("transactrate_info")),
                transactrate_count_s = StringUtils.trim(request.getParameter("transactrate_count")),
                cashPayment_str = StringUtils.trim(request.getParameter("cashPayment")),
                transactrateTime_str = StringUtils.trim(request.getParameter("transactrateTime")),
                cashTime_str = StringUtils.trim(request.getParameter("cashTime")),
                ps = StringUtils.trim(request.getParameter("ps"));

        if (null != merchantId_str && StringUtils.isNumeric(merchantId_str) && StringUtils.isNotBlank(cashTime_str)
                && StringUtils.isNotBlank(cashPayment_str) && StringUtils.isNotBlank(transactrate_count_s) &&
                StringUtils.isNumeric(transactrate_count_s) && StringUtils.isNotBlank(transactrateTime_str) &&
                StringUtils.indexOf(transactrateTime_str, "-") > 0) {
            Subject subject = SecurityUtils.getSubject();
            User user = (User) subject.getPrincipal();
            if (null != user) {
                Long merchantId = Long.parseLong(merchantId_str);
                DateTime transactrateTime = JodatimeUtil.getDateTimeForStr(transactrateTime_str, JodatimeUtil.fmtSS);
                String[] transactrateMoneys = StringUtils.split(transactrate_info_s, ";");
                if (ArrayUtils.isNotEmpty(transactrateMoneys)) {
                    boolean falg = false;
                    for (String transactrateMoneyTemp : transactrateMoneys) {
                        if (!CommonToolsUtil.isMoney(StringUtils.trim(transactrateMoneyTemp))) {
                            falg = true;
                            break;
                        }
                    }
                    if (falg) {
                        errorMsg(request, response, "交易金额只能是数字!");
                        return;
                    } else {
                        for (String transactrateMoneyTemp : transactrateMoneys) {
                            CardTransactrateInfo transactrateInfo = new CardTransactrateInfo();
                            transactrateInfo.setUserId(user.getId());
                            transactrateInfo.setTransactrateTime(transactrateTime.toDate());
                            transactrateInfo.setTransactrateMoney(new BigDecimal(StringUtils.trim(transactrateMoneyTemp)));
                            transactrateInfo.setMerchantId(merchantId);
                            transactrateInfo.setPs(ps);
                            cardTransactrateInfoServer.insert(transactrateInfo);
                        }
                        succMesg(request, response);
                    }
                }
            } else {
                errorMsg(request, response, "没有登录!");
            }
        } else {
            errorMsg(request, response);
        }
    }

    /**
     * 删除
     * @param id
     * @param request
     * @param response
     */
    @RequestMapping(value = "/fund/card/card_transactrate_info_delete", method = { RequestMethod.POST })
    public void processDelete(Long id, HttpServletRequest request, HttpServletResponse response) {
        if (null != id) {
            cardTransactrateInfoServer.deleteByPrimaryKey(id);
            succMesg(request, response, "删除成功!");
        } else {
            errorMsg(request, response, "参数不全!");
        }
    }

    private void processShowSelect(ModelMap model) {
        model.put("cardMerchantMap", SysCache.getCardMerchantMap());
        model.put("cardMerchantMapData", getCardMerchantMapData());
    }
}
