package com.guinea.web;

import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shiky on 2015/12/8.
 */
public class PageMsg<T> implements Serializable {

	private static final long serialVersionUID = -4358987991454771542L;

	public PageMsg() {
	}

	private Long total;
	private List<T> rows;

	public List<T> getRows() {
		return rows;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}


}
