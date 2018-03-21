package com.guinea.datadb.mysql.entity.card;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * 刷刷运营商
 */
public class CardOperator implements Serializable{
    private static final long serialVersionUID = -7589097321131555927L;
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fund_card_operator.id
     *
     * @mbggenerated Fri May 20 10:46:42 CST 2016
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fund_card_operator.name
     *
     * @mbggenerated Fri May 20 10:46:42 CST 2016
     */
    private String name;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fund_card_operator.id
     *
     * @return the value of fund_card_operator.id
     *
     * @mbggenerated Fri May 20 10:46:42 CST 2016
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fund_card_operator.id
     *
     * @param id the value for fund_card_operator.id
     *
     * @mbggenerated Fri May 20 10:46:42 CST 2016
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fund_card_operator.name
     *
     * @return the value of fund_card_operator.name
     *
     * @mbggenerated Fri May 20 10:46:42 CST 2016
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fund_card_operator.name
     *
     * @param name the value for fund_card_operator.name
     *
     * @mbggenerated Fri May 20 10:46:42 CST 2016
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}