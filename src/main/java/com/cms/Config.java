package com.cms;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Config implements Serializable {

	private static final long serialVersionUID = 1012463140749529805L;

	/** 缓存名称 */
	public static final String CACHE_NAME = "config";

	/** 网站名称 */
	private String siteName;
	
	/** 网站网址 */
	private String siteUrl;
	
	/** logo */
	private String logo;
	
	/** 标题 */
	private String title;
	
	/** 关键词 */
	private String keywords;
	
	/** 描述 */
	private String description;
	
	/** 主题 */
	private String theme;
	
	private String address;//地址
	private String certtext;//备案
	private String email;//邮箱
	private String zipCode;//邮编
	private String qq;//QQ
	private String weixin;//微信
	private String phone;//电话
	private String statisticsCode;//统计代码
	
	/**
	 * 前端展现数据
	 * 
	 * @return
	 */
	public Map<String,Object> toMap(){
	    Map<String, Object> configMap = new HashMap<>();
        configMap.put("siteName", siteName);
        configMap.put("siteUrl", siteUrl);
        configMap.put("logo", logo);
        configMap.put("title", title);
        configMap.put("keywords", keywords);
        configMap.put("description", description);
        configMap.put("theme", theme);
        configMap.put("address", address);
        configMap.put("certtext", certtext);
        configMap.put("email", email);
        configMap.put("zipCode", zipCode);
        configMap.put("qq", qq);
        configMap.put("weixin", weixin);
        configMap.put("phone", phone);
        configMap.put("statisticsCode", statisticsCode);
        return configMap;
	}

	
    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public String getCerttext() {
        return certtext;
    }


    public void setCerttext(String certtext) {
        this.certtext = certtext;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getZipCode() {
        return zipCode;
    }


    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }


    public String getQq() {
        return qq;
    }


    public void setQq(String qq) {
        this.qq = qq;
    }


    public String getPhone() {
        return phone;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getStatisticsCode() {
        return statisticsCode;
    }


    public void setStatisticsCode(String statisticsCode) {
        this.statisticsCode = statisticsCode;
    }


    public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSiteUrl() {
		return siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}


	public String getWeixin() {
		return weixin;
	}


	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
}
