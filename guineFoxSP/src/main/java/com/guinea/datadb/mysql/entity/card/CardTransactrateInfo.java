package com.guinea.datadb.mysql.entity.card;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 交易明细
 */
public class CardTransactrateInfo implements Serializable{

    private static final long serialVersionUID = 6558919805280970712L;

    private Long id;


    private Long merchantId;

    /***
     * 商户名称
     */
    private String merchantName;


    private Long userId;
    /****
     * 交易金额
     */
    private BigDecimal transactrateMoney;

    /**
     *交易时间
     */
    private Date transactrateTime;

    /**
     *说明
     */
    private String ps;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getTransactrateMoney() {
        return transactrateMoney;
    }

    public void setTransactrateMoney(BigDecimal transactrateMoney) {
        this.transactrateMoney = transactrateMoney;
    }

    public Date getTransactrateTime() {
        return transactrateTime;
    }

    public void setTransactrateTime(Date transactrateTime) {
        this.transactrateTime = transactrateTime;
    }

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps == null ? null : ps.trim();
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}