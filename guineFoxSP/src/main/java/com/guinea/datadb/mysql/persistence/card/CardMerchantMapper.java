package com.guinea.datadb.mysql.persistence.card;

import com.guinea.datadb.mysql.entity.card.CardMerchant;

import java.util.List;
import java.util.Map;

/***
 * 商户配置
 */
public interface CardMerchantMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CardMerchant record);

    CardMerchant selectByPrimaryKey(Long id);

    CardMerchant selectByCode(Long code);

    List<CardMerchant> selectAll();

    int updateByPrimaryKey(CardMerchant record);

    /***
     * 删除通过代码
     * @param code
     * @return
     */
    int deleteByCode(Long code);

    /***
     * page分页查询
     * @param conditions
     * @return
     */
    List<CardMerchant> findByPage(Map<String,String> conditions);
}