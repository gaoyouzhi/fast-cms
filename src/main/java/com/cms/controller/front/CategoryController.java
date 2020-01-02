package com.cms.controller.front;

import org.apache.commons.lang.StringUtils;

import com.cms.entity.Category;
import com.cms.entity.Content;
import com.cms.routes.RouteMapping;

/**
 * Controller - 栏目
 * 
 * 
 * 
 */
@RouteMapping(url = "/category")
public class CategoryController extends BaseController {

	/**
	 * 栏目
	 */
	public void index() {
		Long categoryId = getParaToLong(0);
		Category category = new Category().dao().findById(categoryId);
		setAttr("currentCategory", category);
		Integer type = category.getType();
		if(Category.PAGE_TYPE == type){
		    render("/templates/"+getTheme()+"/"+category.getTemplate()+"_page.html");
		}else if(Category.LIST_TYPE == type){
		    Integer pageNumber = getParaToInt("pageNumber");
		    if(pageNumber==null){
		        pageNumber = 1;
		    }
		    Integer pageSize = 20;
		    if(category.getPageSize()!=null){
		        pageSize = category.getPageSize();
		    }
		    String title = getPara("title");
		    String description = getPara("description");
		    if(StringUtils.isBlank(title)){
		        title = "";
		    }
		    if(StringUtils.isBlank(description)){
		        description = "";
		    }
		    setAttr("title", title);
		    setAttr("description", description);
		    setAttr("page", new Content().dao().findPage(categoryId, title,description,pageNumber,pageSize));
		    render("/templates/"+getTheme()+"/"+category.getTemplate()+"_list.html");
		}
	}
}
