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
 * 模板指令 - 下级栏目列表
 * 
 * 
 * 
 */
@TemplateVariable(name="category_children_list")
public class CategoryChildrenListDirective extends BaseDirective {
	
	/** "分类ID"参数名称 */
	private static final String CATEGORY_ID_PARAMETER_NAME = "categoryId";
	
	/** "是否是菜单"参数名称 */
	private static final String IS_MENU_PARAMETER_NAME = "isMenu";

	/** "是否递归"参数名称 */
	private static final String RECURSIVE_PARAMETER_NAME = "recursive";

	/** 变量名称 */
	private static final String VARIABLE_NAME = "categorys";
	
	
    @Override
    public void exec(Env env, Scope scope, Writer writer) {
        // TODO Auto-generated method stub
        scope = new Scope(scope);
        Long categoryId = getParameter(CATEGORY_ID_PARAMETER_NAME, Long.class, scope);
        Boolean isMenu = getParameter(IS_MENU_PARAMETER_NAME, Boolean.class, scope);
        Boolean recursive = getParameter(RECURSIVE_PARAMETER_NAME, Boolean.class, scope);
        Integer start = getStart(scope);
        Integer count = getCount(scope);
        List<Category> categorys = new Category().dao().findChildren(categoryId,isMenu, recursive != null ? recursive : false, start,count);
        scope.setLocal(VARIABLE_NAME,categorys);
        stat.exec(env, scope, writer);
    }
    
    public boolean hasEnd() {
        return true;
    }
}