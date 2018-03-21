package com.guinea.profession.service.fund.card;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.guinea.datadb.mysql.entity.FundStock;
import com.guinea.datadb.mysql.entity.card.CardOperator;
import com.guinea.datadb.mysql.persistence.card.CardOperatorMapper;
import com.guinea.dic.StockPaceEnum;
import com.guinea.dic.StockTypeEnum;
import com.guinea.util.Constents;
import com.guinea.web.PageMsg;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Title: 运营商管理
 * @Describe:
 * @Author: shikaiyuan
 * @Datetime: 2016/5/23
 */
@Service("cardOperatorService")
public class CardOperatorService {

    @Autowired
    private CardOperatorMapper cardOperatorMapper;


    public int deleteByPrimaryKey(Long id) {
        return cardOperatorMapper.deleteByPrimaryKey(id);
    }

    public int save(CardOperator record) {
        int i = 0;
        if (null != record) {
            i = cardOperatorMapper.insert(record);
        }
        return i;
    }

    public CardOperator selectByPrimaryKey(Long id) {
        return cardOperatorMapper.selectByPrimaryKey(id);
    }

    public List<CardOperator> selectAll(){
        return cardOperatorMapper.selectAll();
    }

    /***
     * 查询通过名称
     * @param name
     * @return
     */
    public CardOperator selectByName(String name) {
        CardOperator cardOperator = null;
        if (StringUtils.isNotBlank(name)) {
            cardOperator = cardOperatorMapper.selectByName(name);
        }
        return cardOperator;
    }

    public int updateByPrimaryKey(CardOperator record){
        int i=0;
        if(null!=record){
            i =cardOperatorMapper.updateByPrimaryKey(record);
        }
        return i;
    }


    /****
     * 分页查询
     *
     * @param conditions
     * @return
     */
    public PageMsg<CardOperator> findByPage(Map<String, String> conditions) {
        PageInfo<CardOperator> page = PageHelper
                .startPage(Integer.valueOf(conditions.get(Constents.PAGENUMBER_)),
                        Integer.valueOf(conditions.get(Constents.PAGESIZE_)))
                .doSelectPageInfo(() -> cardOperatorMapper.findByPage(conditions));
        PageMsg<CardOperator> pageMsg = new PageMsg<>();
        pageMsg.setTotal(page.getTotal());
        pageMsg.setRows(page.getList());
        return pageMsg;
    }
}
