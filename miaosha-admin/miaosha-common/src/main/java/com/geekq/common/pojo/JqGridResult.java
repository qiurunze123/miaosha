package com.geekq.common.pojo;

import java.util.List;

/**
 * 
 * @Description: 用来返回jqGrid的数据格式
 */
public class JqGridResult {
	
	private int page;			// 当前页数
	private int total;			// 总页数	
	private long records;		// 总记录数
	private List<?> rows;		// 每行显示的内容
	private Object userdata;	// 用户自定义数据
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public long getRecords() {
		return records;
	}
	public void setRecords(long records) {
		this.records = records;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	public Object getUserdata() {
		return userdata;
	}
	public void setUserdata(Object userdata) {
		this.userdata = userdata;
	}

}
