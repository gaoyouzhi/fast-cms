/*
 * 
 * 
 * 
 */
package com.cms.template.directive;

import java.util.List;

import com.cms.TemplateVariable;
import com.cms.entity.Category;
import com.jfinal.template.Env;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;

/**
 * 模板指令 - 顶级文章分类列表
 * 
 * 
 * 
 */
@TemplateVariable(name="category_root_list")
public class CategoryRootListDirective extends BaseDirective {

	/** "是否是菜单"参数名称 */
	private static final String IS_MENU_PARAMETER_NAME = "isMenu";
	
	/** 变量名称 */
	private static final String VARIABLE_NAME = "categorys";

	@Override
    public void exec(Env env, Scope scope, Writer writer) {
	    scope = new Scope(scope);
		Boolean isMenu = getParameter(IS_MENU_PARAMETER_NAME, Boolean.class, scope);
		Integer start = getStart(scope);
		Integer count = getCount(scope);
		List<Category> categorys = new Category().dao().findRoots(isMenu,start,count);
		scope.setLocal(VARIABLE_NAME,categorys);
        stat.exec(env, scope, writer);
	}
	
    public boolean hasEnd() {
        return true;
    }	
}