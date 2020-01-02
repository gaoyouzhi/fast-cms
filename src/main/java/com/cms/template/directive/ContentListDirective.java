/*
 * 
 * 
 * 
 */
package com.cms.template.directive;

import java.util.List;

import com.cms.TemplateVariable;
import com.cms.entity.Content;
import com.jfinal.template.Env;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;

/**
 * 模板指令 - 内容列表
 * 
 * 
 * 
 */
@TemplateVariable(name="content_list")
public class ContentListDirective extends BaseDirective {

	/** "栏目ID"参数名称 */
	private static final String CATEGORY_ID_PARAMETER_NAME = "categoryId";

	/** 变量名称 */
	private static final String VARIABLE_NAME = "contents";

	@Override
    public void exec(Env env, Scope scope, Writer writer) {
	    scope = new Scope(scope);
		Long categoryId = getParameter(CATEGORY_ID_PARAMETER_NAME, Long.class, scope);
		Integer start = getStart(scope);
		Integer count = getCount(scope);
		String orderBy = getOrderBy(scope);
		List<Content> contents = new Content().dao().findList(categoryId, orderBy,start,count);
		scope.setLocal(VARIABLE_NAME,contents);
        stat.exec(env, scope, writer);
	}
	
    public boolean hasEnd() {
        return true;
    }	
}