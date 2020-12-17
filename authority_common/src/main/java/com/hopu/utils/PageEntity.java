package com.hopu.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

/**
 * @author lenovo
 */
@Data
public class PageEntity {
	private int code = 0;
	private String msg;
	private int count;
	private Object data;

	public PageEntity(){};

	public PageEntity(long count, Object data){
		this.count = (int)count;
		this.data = data;
	}

	public PageEntity(IPage<?> page){
		this.count = (int) page.getTotal();
		this.data = page.getRecords();
	}
}
