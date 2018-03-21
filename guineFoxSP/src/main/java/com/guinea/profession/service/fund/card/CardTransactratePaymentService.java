package com.guinea.profession.service.fund.card;

import com.google.common.collect.Maps;
import com.guinea.datadb.mysql.entity.card.CardTransactrateInfo;
import com.guinea.datadb.mysql.persistence.card.CardTransactrateInfoMapper;
import com.guinea.datadb.mysql.persistence.card.CardTransactratePaymentMapper;
import com.guinea.util.Constents;
import com.guinea.util.date.JodatimeUtil;
import com.guinea.web.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @title: 刷刷结算
 * @describle:
 * @createBy" shikaiyuan
 * @date: 2016/6/22.
 */
@Service("cardTransactratePaymentService")
public class CardTransactratePaymentService {

    @Autowired
    private CardTransactratePaymentMapper cardTransactratePaymentMapper;

    @Autowired
    private CardTransactrateInfoMapper cardTransactrateInfoMapper;

    /***
     * 交易结算
     *
     */
    public void transactratePayment(){
        Map<String,String> paramMap = Maps.newHashMap();
        // t-1 日的结算(t-2日超过规定时间也算t-1日的结算)
        DateTime nowTime = new DateTime();
        StringBuffer startSbf = new StringBuffer(nowTime.toString(JodatimeUtil.simfmtDD));
        startSbf.append( Constents.HOUR);
        startSbf.append("00");
        DateTime tempDateTime = JodatimeUtil.getDateTimeForStr(startSbf.toString(),
                JodatimeUtil.simfmtSS);
        // t-2 日的规定时间之后交易，算作t-1日交易
        DateTime startPaymentTime = tempDateTime.minusDays(2);
        DateTime endtPaymentTime = tempDateTime.minusDays(1);
        paramMap.put("sttime",startPaymentTime.toString(JodatimeUtil.fmtSS));
        paramMap.put("ettime",endtPaymentTime.toString(JodatimeUtil.fmtSS));
        paramMap.put("user_id","2");
        List<CardTransactrateInfo> cardTransactrateInfos = cardTransactrateInfoMapper.selectByPaymentTime(paramMap);
        if(null!=cardTransactrateInfos&&cardTransactrateInfos.size()>0){
//            System.out.println("cardTransactrateInfos"+cardTransactrateInfos.toString());
        }
    }

    public static void main(String[] args) {
//        transactratePayment();
    }
}
