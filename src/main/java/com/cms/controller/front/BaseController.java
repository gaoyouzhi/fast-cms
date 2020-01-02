/*
 * 
 * 
 * 
 */
package com.cms.controller.front;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.cms.util.SystemUtils;
import com.jfinal.core.Controller;
import com.jfinal.core.NotAction;

/**
 * Controller - 基类
 * 
 * 
 * 
 */
public class BaseController extends Controller{
	
	/**
	 * 获取主题
	 * 
	 * @return 主题
	 */
    @NotAction
	public String getTheme(){
		return SystemUtils.getConfig().getTheme();
	}
	
	/**
	 * 获取BigDecimal数据
	 * 
	 * @param name
	 * 			名称
	 * @return BigDecimal数据
	 */
    @NotAction
	public BigDecimal getParaToBigDecimal(String name){
		String value = getPara(name);
		if(StringUtils.isNotBlank(value)){
			return new BigDecimal(value);
		}
		return null;
	}

}