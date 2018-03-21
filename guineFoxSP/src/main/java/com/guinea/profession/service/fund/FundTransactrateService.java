package com.guinea.profession.service.fund;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.guinea.datadb.mysql.entity.FundTransactrate;
import com.guinea.datadb.mysql.persistence.FundTransactrateMapper;
import com.guinea.dic.TransactrateTypeEnum;
import com.guinea.util.Constents;
import com.guinea.web.PageMsg;

/**
 * Created by shiky on 2015/9/22.
 */
@Service("fundTransactrateService")
public class FundTransactrateService {

	@Autowired
	private FundTransactrateMapper fundTransactrateMapper;

	/****
	 * 分页查询
	 * 
	 * @param conditions
	 * @return
	 */
	public PageMsg<FundTransactrate> findByPage(Map<String, String> conditions) {
		PageInfo<FundTransactrate> page = PageHelper
				.startPage(Integer.valueOf(conditions.get(Constents.PAGENUMBER_)),
						Integer.valueOf(conditions.get(Constents.PAGESIZE_)))
				.doSelectPageInfo(() -> fundTransactrateMapper.findByPage(conditions));
		PageMsg<FundTransactrate> pageMsg = new PageMsg<>();
		pageMsg.setTotal(page.getTotal());
		pageMsg.setRows(processView(page.getList()));
		return pageMsg;
	}

	private List<FundTransactrate> processView(List<FundTransactrate> fundTransactrates) {
		fundTransactrates.forEach(ev -> {
			String tTypeName = TransactrateTypeEnum.getName(ev.getTtype());
			if (!Strings.isNullOrEmpty(tTypeName))
				ev.setTtypeName(tTypeName);
		});
		return fundTransactrates;
	}

	/***
	 * 保存
	 * 
	 * @param fundTransactrate
	 */
	public void save(FundTransactrate fundTransactrate) {
		fundTransactrateMapper.insert(fundTransactrate);
	}

}
