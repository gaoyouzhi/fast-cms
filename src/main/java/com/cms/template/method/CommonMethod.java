package com.cms.template.method;

import org.apache.commons.lang.StringUtils;

import com.jfinal.kit.StrKit;

/**
 * 模板方法 - 公共方法
 * 
 * 
 * 
 */
public class CommonMethod {

    public static String abbreviate(String str,Integer width,String ellipsis){
        if (StrKit.isBlank(str) || width == null) {
            return str;
        }
        if(str.length()<=width){
            return str;
        }
        return StringUtils.substring(str, 0, width)+ellipsis;
    }
}
