/*
 * 
 * 
 * 
 */
package com.cms.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.cms.CommonAttribute;
import com.cms.Config;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.ehcache.CacheKit;

/**
 * Utils - 系统
 * 
 * 
 * 
 */
public final class SystemUtils {

	/**
	 * 不可实例化
	 */
	private SystemUtils() {
	}

	/**
	 * 获取系统设置
	 * 
	 * @return 系统设置
	 */
	@SuppressWarnings("unchecked")
	public static Config getConfig() {
		String cacheKey = "config";
		Config cacheConfig = CacheKit.get(Config.CACHE_NAME, cacheKey);
		if (cacheConfig == null) {
			Config config = new Config();
			try {
				File configXmlFile = new File(PathKit.getRootClassPath()+CommonAttribute.CONFIG_XML_PATH);
				Document document = new SAXReader().read(configXmlFile);
				List<org.dom4j.Element> elements = document.selectNodes("/cms/config");
				for (org.dom4j.Element element : elements) {
					try {
						String name = element.attributeValue("name");
						String value = element.attributeValue("value");
						BeanUtils.setProperty(config, name, value);
					} catch (IllegalAccessException e) {
						throw new RuntimeException(e.getMessage(), e);
					} catch (InvocationTargetException e) {
						throw new RuntimeException(e.getMessage(), e);
					}
				}
			} catch (DocumentException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
			CacheKit.put(Config.CACHE_NAME, cacheKey, config);
			cacheConfig = CacheKit.get(Config.CACHE_NAME, cacheKey);
		}
		return cacheConfig;
	}

	/**
	 * 设置系统设置
	 * 
	 * @param config
	 *            系统设置
	 */
	@SuppressWarnings("unchecked")
	public static void setConfig(Config config) {
		try {
			File configXmlFile = new File(PathKit.getRootClassPath()+CommonAttribute.CONFIG_XML_PATH);
			Document document = new SAXReader().read(configXmlFile);
			List<org.dom4j.Element> elements = document.selectNodes("/cms/config");
			for (org.dom4j.Element element : elements) {
				try {
					String name = element.attributeValue("name");
					String value = BeanUtils.getProperty(config, name);
					Attribute attribute = element.attribute("value");
					attribute.setValue(value);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (InvocationTargetException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (NoSuchMethodException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}

			XMLWriter xmlWriter = null;
			try {
				OutputFormat outputFormat = OutputFormat.createPrettyPrint();
				outputFormat.setEncoding("UTF-8");
				outputFormat.setIndent(true);
				outputFormat.setIndent("	");
				outputFormat.setNewlines(true);
				xmlWriter = new XMLWriter(new FileOutputStream(configXmlFile), outputFormat);
				xmlWriter.write(document);
				xmlWriter.flush();
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			} finally {
				try {
					if (xmlWriter != null) {
						xmlWriter.close();
					}
				} catch (IOException e) {
				}
			}
			String cacheKey = "config";
			CacheKit.put(Config.CACHE_NAME, cacheKey, config);
		} catch (DocumentException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}


	/**
	 * 获取所有主题
	 * 
	 * @return 所有主题
	 */
	public static List<String> getThemes(){
		File[] files = new File(PathKit.getWebRootPath()+File.separator+"templates").listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.exists() && file.isDirectory();
			}
		});
		List<String> themes = new ArrayList<String>();
		for (File file : files) {
			themes.add(file.getName());
		}
		return themes;
	}
}