package com.guinea.profession.service.fund.card;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.guinea.cache.SysCache;
import com.guinea.datadb.mysql.entity.card.CardTransactrateInfo;
import com.guinea.datadb.mysql.entity.card.CardMerchant;
import com.guinea.datadb.mysql.persistence.card.CardTransactrateInfoMapper;
import com.guinea.util.Constents;
import com.guinea.util.date.JodatimeUtil;
import com.guinea.web.PageMsg;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @title:交易流水
 * @describle:
 * @createBy" shikaiyuan
 * @date: 2016/6/2.
 */
@Service("cardTransactrateInfoServer")
public class CardTransactrateInfoServer {

    @Autowired
    private CardTransactrateInfoMapper cardTransactrateInfoMapper;

    /***
     * 处理交易计算
     *
     * @param merchantId_s
     * @param transactrateMoney_s
     * @param transactrateTime_s
     * @return
     */
    public Map<Integer, Object> process_transactrate_matche(String merchantId_s, String transactrateMoney_s,
                                                            String transactrateTime_s) {
        Map<Integer, Object> resultMap = Maps.newHashMap();
        StringBuffer message_sbf = new StringBuffer();
        CardMerchant cardMerchant = SysCache.getCardMerchantMapData().get(merchantId_s);
        BigDecimal rate = new BigDecimal(cardMerchant.getRate().doubleValue())
                .multiply(new BigDecimal(Constents.BAIFEN)), transactrateMoney = new BigDecimal(transactrateMoney_s);
        Long operatorId = cardMerchant.getOperatorId();
        // 计算结算金额
        BigDecimal prage = BigDecimal.ONE.subtract(rate), cash_payment = transactrateMoney.multiply(prage);
        // 交易时间,交易时间+1
        DateTime transactrateTime = JodatimeUtil.getDateTimeForStrSS(transactrateTime_s),
                transactrateTimePay = transactrateTime.plusDays(1);
        // 1.计算交易时间是否过晚
        Map<Integer, Object> laterPayMap = later_transactrate(transactrateTime, transactrateTimePay);
        StringBuffer later_sbf = (StringBuffer) laterPayMap.get(1);
        message_sbf.append(later_sbf.toString());
        // 2.判断是否是节假日
        Map<Integer, Object> vacationPayMap = vacation_process((DateTime) laterPayMap.get(2));
        StringBuffer vacation_sbf = (StringBuffer) vacationPayMap.get(1);
        message_sbf.append(vacation_sbf.toString());
        DateTime tempPayVacation = (DateTime) vacationPayMap.get(2), payDate = tempPayVacation;
        // 3.判断是否是周末，是否金额大于XXX(比如:1000)，然后是否可以成功到账
        payDate = process_limit_tmoney_week(message_sbf, operatorId, cash_payment, tempPayVacation, payDate);
        // 四舍五入保留三位小数
        cash_payment = cash_payment.setScale(Constents.BAOLIU, BigDecimal.ROUND_HALF_UP);
        resultMap.put(1, cash_payment);
        resultMap.put(2, payDate);
        resultMap.put(3, message_sbf);
        return resultMap;
    }

    /***
     * 处理周末到账金额限制
     *
     * @param message_sbf
     * @param operatorId
     * @param cash_payment
     * @param tempPayVacation
     * @param payDate
     * @return
     */
    private DateTime process_limit_tmoney_week(StringBuffer message_sbf, Long operatorId, BigDecimal cash_payment,
                                               DateTime tempPayVacation, DateTime payDate) {
        StringBuffer week_sbf = new StringBuffer();
        if (operatorId == 1) {// 拉卡拉，瑞信银
            // 限定周末到账金额
            if (cash_payment.doubleValue() < Constents.PAY_LIMIT_MONEY_LAKL) {
                week_sbf.append("交易金额<");
                week_sbf.append(Constents.PAY_LIMIT_MONEY_LAKL);
                week_sbf.append("￥,");
                Map<Integer, Object> weekPayMap = process_week(week_sbf, tempPayVacation);
                payDate = (DateTime) weekPayMap.get(2);
                StringBuffer week_sub = (StringBuffer) weekPayMap.get(1);
                message_sbf.append(week_sub.toString());
            }
        } else if (operatorId == 3) {// 其他
            // 限定周末到账金额
            if (cash_payment.doubleValue() < Constents.PAY_LIMIT_MONEY_RUIXY) {
                week_sbf.append("交易金额<");
                week_sbf.append(Constents.PAY_LIMIT_MONEY_LAKL);
                week_sbf.append("￥,");
                Map<Integer, Object> weekPayMap = process_week(week_sbf, tempPayVacation);
                payDate = (DateTime) weekPayMap.get(2);
                StringBuffer week_sub = (StringBuffer) weekPayMap.get(1);
                message_sbf.append(week_sub.toString());
            }
        } else {
            Map<Integer, Object> weekPayMap = process_week(week_sbf, tempPayVacation);
            payDate = (DateTime) weekPayMap.get(2);
            StringBuffer week_sub = (StringBuffer) weekPayMap.get(1);
            message_sbf.append(week_sub.toString());
        }
        return payDate;
    }

    /***
     * 1.计算交易时间是否过晚
     */
    private Map<Integer, Object> later_transactrate(DateTime transactrateTime, DateTime transactrateTimePay) {
        Map<Integer, Object> resultMap = Maps.newHashMap();
        StringBuffer later_sbf = new StringBuffer();
        DateTime tempPay = transactrateTimePay;
        // 小时,小时后的分钟(22:30)
        String hour = transactrateTime.getHourOfDay() + "", minOfHour = transactrateTime.getMinuteOfHour() + "";
        int transactrateHourMin = Integer.valueOf(hour + minOfHour);
        if (transactrateHourMin >= Constents.HOUR) {
            tempPay = transactrateTime.plusDays(2);
            later_sbf.append("交易时间为：");
            later_sbf.append(hour);
            later_sbf.append(":");
            later_sbf.append(minOfHour);
            later_sbf.append(",晚于设置最晚交易时间:");
            later_sbf.append("22:30");
            later_sbf.append(",所以t+2日到账.");
        }
        resultMap.put(1, later_sbf);
        resultMap.put(2, tempPay);
        return resultMap;
    }

    /***
     * 2.判断是否是节假日
     */
    private Map<Integer, Object> vacation_process(DateTime transactrateTimePay) {
        Map<Integer, Object> resultMap = Maps.newHashMap();
        StringBuffer vacation_sbf = new StringBuffer();
        DateTime tempPay = transactrateTimePay;
        // 假期日历
        List<Integer> vacationList = SysCache.vacationList;
        if (null != vacationList && vacationList.size() > 0) {
            for (Integer date : vacationList) {
                int transactrateDateNO_ = Integer.valueOf(JodatimeUtil.getDateToStr(tempPay));
                if (date == transactrateDateNO_) {
                    vacation_sbf.append(tempPay.toString("MM-dd"));
                    vacation_sbf.append(",");
                    tempPay = tempPay.plusDays(1);
                }
            }
            if (vacation_sbf.length() > 0) {
                vacation_sbf.append("是节假日,不能到账.");
            }
        }
        resultMap.put(1, vacation_sbf);
        resultMap.put(2, tempPay);
        return resultMap;
    }

    /***
     * 3.判断是否是周末，是否金额大于XXX(比如:1000)，然后是否可以成功到账
     *
     * @param transactrateTimePay
     */
    private Map<Integer, Object> process_week(StringBuffer week_sbf, DateTime transactrateTimePay) {
        Map<Integer, Object> resultMap = Maps.newHashMap();
        DateTime tempPay = transactrateTimePay;
        StringBuffer temp_week_sbf = week_sbf;
        switch (transactrateTimePay.getDayOfWeek()) {
            case DateTimeConstants.SUNDAY:
                temp_week_sbf.append(JodatimeUtil.getDateTimeToStr(transactrateTimePay, JodatimeUtil.fmtDD));
                temp_week_sbf.append("(");
                temp_week_sbf.append(Constents.SUNDAY);
                temp_week_sbf.append("),");
                temp_week_sbf.append("不能到账.");
                tempPay = transactrateTimePay.plusDays(1);
                break;
            case DateTimeConstants.SATURDAY:
                temp_week_sbf.append(JodatimeUtil.getDateTimeToStr(transactrateTimePay, JodatimeUtil.fmtDD));
                temp_week_sbf.append("(");
                temp_week_sbf.append(Constents.SATURDAY);
                temp_week_sbf.append("),");
                temp_week_sbf.append("不能到账.");
                tempPay = transactrateTimePay.plusDays(2);
                break;
        }
        resultMap.put(1, temp_week_sbf);
        resultMap.put(2, tempPay);
        return resultMap;
    }

    public int deleteByPrimaryKey(Long id) {
        return cardTransactrateInfoMapper.deleteByPrimaryKey(id);
    }


    public int insert(CardTransactrateInfo record) {
        return cardTransactrateInfoMapper.insert(record);
    }


    public CardTransactrateInfo selectByPrimaryKey(Long id) {
        return cardTransactrateInfoMapper.selectByPrimaryKey(id);
    }

    public List<CardTransactrateInfo> selectAll() {
        return cardTransactrateInfoMapper.selectAll();
    }

    public int updateByPrimaryKey(CardTransactrateInfo record) {
        return cardTransactrateInfoMapper.updateByPrimaryKey(record);
    }

    /****
     * 分页查询
     *
     * @param conditions
     * @return
     */
    public PageMsg<CardTransactrateInfo> findByPage(Map<String, String> conditions) {
        PageInfo<CardTransactrateInfo> page = PageHelper
                .startPage(Integer.valueOf(conditions.get(Constents.PAGENUMBER_)),
                        Integer.valueOf(conditions.get(Constents.PAGESIZE_)))
                .doSelectPageInfo(() -> cardTransactrateInfoMapper.findByPage(conditions));
        PageMsg<CardTransactrateInfo> pageMsg = new PageMsg<>();
        pageMsg.setTotal(page.getTotal());
        pageMsg.setRows(processView(page.getList()));
        return pageMsg;
    }

    private List<CardTransactrateInfo> processView(List<CardTransactrateInfo> cardTransactrateInfos) {
        cardTransactrateInfos.forEach(ev -> {
            ev.setMerchantName(SysCache.getCardMerchantMap().get(ev.getMerchantId()+""));
        });
        return cardTransactrateInfos;
    }


}
