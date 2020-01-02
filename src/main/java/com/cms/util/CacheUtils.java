package com.cms.util;

import com.jfinal.kit.PropKit;
import com.jfinal.plugin.ehcache.CacheKit;

import net.sf.ehcache.Ehcache;
/**
 * Utils - 缓存
 * 
 * 
 * 
 */
public class CacheUtils {

	/**
	 * 获取缓存存储路径
	 * 
	 * @return 缓存存储路径
	 */
	public static String getDiskStorePath() {
		return CacheKit.getCacheManager().getConfiguration().getDiskStoreConfiguration().getPath();
	}

	/**
	 * 获取缓存数
	 * 
	 * @return 缓存数
	 */
	public static int getCacheSize() {
		int cacheSize = 0;
		String[] cacheNames = CacheKit.getCacheManager().getCacheNames();
		if (cacheNames != null) {
			for (String cacheName : cacheNames) {
				Ehcache cache = CacheKit.getCacheManager().getEhcache(cacheName);
				if (cache != null) {
					cacheSize += cache.getSize();
				}
			}
		}
		return cacheSize;
	}

	/**
	 * 清除缓存
	 */
	public static void clear() {
	    CacheKit.getCacheManager().clearAll();
		PropKit.clear();
	}
}
