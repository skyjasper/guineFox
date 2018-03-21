package com.guinea.profession.service.trace;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guinea.datadb.mysql.entity.TracePoint;
import com.guinea.datadb.mysql.persistence.TracePointMapper;
import com.guinea.util.Constents;
import com.guinea.web.PageMsg;

/**
 * Created by shiky on 2015/12/17.
 */
@Service("tracePointServiceService")
public class TracePointService {

	@Autowired
	private TracePointMapper tracePointMapper;

	/***
	 * 分页查询
	 * @param conditions
	 * @return
	 */
	public PageMsg<TracePoint> findByPage(Map<String, String> conditions) {
		PageInfo<TracePoint> page = PageHelper
				.startPage(Integer.valueOf(conditions.get(Constents.PAGENUMBER_)),
						Integer.valueOf(conditions.get(Constents.PAGESIZE_)))
				.doSelectPageInfo(() -> tracePointMapper.findByPage(conditions));
		PageMsg<TracePoint> pageMsg = new PageMsg<>();
		pageMsg.setTotal(page.getTotal());
		pageMsg.setRows(page.getList());
		return pageMsg;
	}

	public int insert(TracePoint record) {
		return tracePointMapper.insert(record);
	}

	public TracePoint selectByPrimaryKey(Long id) {
		return tracePointMapper.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKey(TracePoint record) {
		return tracePointMapper.updateByPrimaryKey(record);
	}

	public int deleteByPrimaryKey(Long id) {
		return tracePointMapper.deleteByPrimaryKey(id);
	}
}
