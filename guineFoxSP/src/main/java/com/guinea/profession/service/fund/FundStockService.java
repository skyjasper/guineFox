package com.guinea.profession.service.fund;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.guinea.datadb.mysql.entity.FundStock;
import com.guinea.datadb.mysql.persistence.FundStockMapper;
import com.guinea.dic.StockPaceEnum;
import com.guinea.dic.StockTypeEnum;
import com.guinea.util.Constents;
import com.guinea.web.PageMsg;

/**
 * Created by shiky on 2015/9/17.
 */
@Service("fundStockService")
public class FundStockService{

	@Autowired
	private FundStockMapper fundStockMapper;

	/****
	 * 分页查询
	 * 
	 * @param conditions
	 * @return
	 */
	public PageMsg<FundStock> findByPage(Map<String, String> conditions) {
		PageInfo<FundStock> page = PageHelper
				.startPage(Integer.valueOf(conditions.get(Constents.PAGENUMBER_)),
						Integer.valueOf(conditions.get(Constents.PAGESIZE_)))
				.doSelectPageInfo(() -> fundStockMapper.findByPage(conditions));
		PageMsg<FundStock> pageMsg = new PageMsg<>();
		pageMsg.setTotal(page.getTotal());
		pageMsg.setRows(processView(page.getList()));
		return pageMsg;
	}

	private List<FundStock> processView(List<FundStock> fundStocks) {
		fundStocks.forEach(ev -> {
			String stypeName = StockTypeEnum.getName(ev.getStype());
			if (!Strings.isNullOrEmpty(stypeName))
				ev.setStypeName(stypeName);
			String spaceName = StockPaceEnum.getName(ev.getSpace());
			if (!Strings.isNullOrEmpty(spaceName))
				ev.setSpaceName(spaceName);
		});
		return fundStocks;
	}

	/***
	 * 保存
	 * 
	 * @param fundStock
	 */
	public void save(FundStock fundStock) {
		fundStockMapper.insert(fundStock);
	}

	/***
	 * 查询通过code
	 * 
	 * @param sCode
	 * @return
	 */
	public FundStock selectByCode(String sCode) {
		FundStock fundStock = null;
		if (!Strings.isNullOrEmpty(sCode)) {
			List<FundStock> fundStocks = fundStockMapper.selectByCode(sCode);
			if (null != fundStocks && fundStocks.size() > 0) {
				fundStock = fundStocks.get(0);
			}
		}
		return fundStock;
	}

	/**
	 * 查询通过id
	 * 
	 * @param id
	 * @return
	 */
	public FundStock selectById(Long id) {
		FundStock fundStock = null;
		if (null != id) {
			fundStock = fundStockMapper.selectByPrimaryKey(id);
		}
		return fundStock;
	}

	/***
	 * 更新
	 * 
	 * @param fundStock
	 */
	public void updateByPrimaryKey(FundStock fundStock) {
		fundStockMapper.updateByPrimaryKey(fundStock);
	}

	/***
	 * 删除
	 * 
	 * @param id
	 */
	public void deleteByPrimaryKey(Long id) {
		fundStockMapper.deleteByPrimaryKey(id);
	}

}
