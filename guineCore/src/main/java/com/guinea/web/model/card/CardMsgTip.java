package com.guinea.web.model.card;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * @title: 刷刷交易消息
 * @describle:
 * @createBy" shikaiyuan
 * @date: 2016/6/2.
 */
public class CardMsgTip implements Serializable{

    private static final long serialVersionUID = -3874851073801560687L;
    /***
     * 提示消息
     */
    private String message;
    /***
     *   支付时间
     */
    private String transactrateTimePay;

    /***
     * 到账金额
     */
    private String cashPayment;

    /***
     * 总交易金额
     */
    private String totalTransactrateMoney;

    /***
     * 交易比数
     */
    private Integer count;

    public String getMessage() {
        return message;
    }

    public String getTransactrateTimePay() {
        return transactrateTimePay;
    }


    public void setMessage(String message) {
        this.message = message;
    }

    public void setTransactrateTimePay(String transactrateTimePay) {
        this.transactrateTimePay = transactrateTimePay;
    }


    public Integer getCount() {
        return count;
    }

    public String getTotalTransactrateMoney() {
        return totalTransactrateMoney;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setTotalTransactrateMoney(String totalTransactrateMoney) {
        this.totalTransactrateMoney = totalTransactrateMoney;
    }

    public String getCashPayment() {
        return cashPayment;
    }

    public void setCashPayment(String cashPayment) {
        this.cashPayment = cashPayment;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
