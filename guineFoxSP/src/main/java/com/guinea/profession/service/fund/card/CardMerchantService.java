package com.guinea.profession.service.fund.card;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guinea.datadb.mysql.entity.FundStock;
import com.guinea.datadb.mysql.entity.card.CardMerchant;
import com.guinea.datadb.mysql.persistence.card.CardMerchantMapper;
import com.guinea.util.Constents;
import com.guinea.web.PageMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Title: 商户管理
 * @Describe:
 * @Author: shikaiyuan
 * @Datetime: 2016/5/24
 */
@Service("cardMerchantService")
public class CardMerchantService {

    @Autowired
    private CardMerchantMapper cardMerchantMapper;

    public int deleteByPrimaryKey(Long id) {
        return cardMerchantMapper.deleteByPrimaryKey(id);
    }

    public int deleteByCode(Long code){
       return cardMerchantMapper.deleteByCode(code);
    }

    public int insert(CardMerchant record) {
        return cardMerchantMapper.insert(record);
    }

    public CardMerchant selectByPrimaryKey(Long id) {
        return cardMerchantMapper.selectByPrimaryKey(id);
    }

    public List<CardMerchant> selectAll() {
        return cardMerchantMapper.selectAll();
    }


    public int updateByPrimaryKey(CardMerchant record) {
        return cardMerchantMapper.updateByPrimaryKey(record);
    }

    /***
     * 查询通过code
     *
     * @param code
     * @return
     */
    public CardMerchant selectByCode(Long code) {
        return cardMerchantMapper.selectByCode(code);
    }

    /***
     * page分页查询
     *
     * @param conditions
     * @return
     */
    public PageMsg<CardMerchant> findByPage(Map<String, String> conditions) {
        PageInfo<CardMerchant> page = PageHelper
                .startPage(Integer.valueOf(conditions.get(Constents.PAGENUMBER_)),
                        Integer.valueOf(conditions.get(Constents.PAGESIZE_)))
                .doSelectPageInfo(() -> cardMerchantMapper.findByPage(conditions));
        PageMsg<CardMerchant> pageMsg = new PageMsg<>();
        pageMsg.setTotal(page.getTotal());
        pageMsg.setRows(page.getList());
        return pageMsg;
    }
}
