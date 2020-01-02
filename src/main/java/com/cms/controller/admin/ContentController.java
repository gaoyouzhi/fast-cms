/*
 * 
 * 
 * 
 */
package com.cms.controller.admin;

import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.ArrayUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cms.Feedback;
import com.cms.entity.Category;
import com.cms.entity.Content;
import com.cms.routes.RouteMapping;


/**
 * Controller - 内容
 * 
 * 
 * 
 */
@RouteMapping(url = "/admin/content")

public class ContentController extends BaseController {

	/**
	 * 添加
	 */
	public void add() {
		Long categoryId = getParaToLong("categoryId");
		Category category = new Category().dao().findById(categoryId);
		setAttr("category", category);
		render(getView("content/add"));
	}
	
	/**
	 * 保存
	 */
	public void save() {
		Content content = getModel(Content.class,"",true);  
		content.setHits(0L);
		content.setCreateDate(new Date());
		content.setModifyDate(new Date());
		content.save();
		redirect(getListQuery("/admin/content/data?categoryId="+content.getCategoryId()));
	}

	/**
	 * 编辑
	 */
	public void edit() {
		Long id = getParaToLong("id");
		Content content = new Content().dao().findById(id);
		setAttr("content", content);
		render(getView("content/edit"));
	}

	/**
	 * 更新
	 */
	public void update() {
		Content content = getModel(Content.class,"",true);  
		content.setModifyDate(new Date());
		content.update();
		redirect(getListQuery("/admin/content/data?categoryId="+content.getCategoryId()));
	}
	
   /**
     * 修改排序
     */
    public void updateSort(){
        String sortArray = getPara("sortArray");
        JSONArray jsonArray = JSONArray.parseArray(sortArray);
        for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Long id = jsonObject.getLong("id");
            Integer sort = jsonObject.getInteger("sort");
            Content content = new Content().dao().findById(id);
            content.setSort(sort);
            content.update();
        }
        renderJson(Feedback.success(new HashMap<>()));
    }
	
	/**
	 * 列表
	 */
	public void list(){
	    setAttr("categoryTree", new Category().dao().findTree());
	    render(getView("content/list"));
	}

	/**
	 * 数据
	 */
	public void data() {
	    String title = getPara("title");
		Long categoryId = getParaToLong("categoryId");
		Integer pageNumber = getParaToInt("pageNumber");
		if(pageNumber==null){
			pageNumber = 1;
		}
		setAttr("categoryId",categoryId);
		setAttr("page", new Content().dao().findPage(categoryId,title,pageNumber,PAGE_SIZE));
		setAttr("title", title);
		render(getView("content/data"));
	}

	/**
	 * 删除
	 */
	public void delete() {
		Long ids[] = getParaValuesToLong("ids");
		if(ArrayUtils.isNotEmpty(ids)){
			for(Long id:ids){
				new Content().dao().deleteById(id);
			}
		}
		renderJson(Feedback.success(new HashMap<>()));
	}

}