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
import com.cms.entity.Ad;
import com.cms.entity.AdPosition;
import com.cms.routes.RouteMapping;


/**
 * Controller - 广告
 * 
 * 
 * 
 */
@RouteMapping(url = "/admin/ad")

public class AdController extends BaseController {


	/**
	 * 添加
	 */
	public void add() {
		setAttr("adPositions", new AdPosition().dao().findAll());
		render(getView("ad/add"));
	}

	/**
	 * 保存
	 */
	public void save() {
		Ad ad = getModel(Ad.class,"",true); 
		ad.setCreateDate(new Date());
		ad.setModifyDate(new Date());
		ad.save();
		redirect(getListQuery("/admin/ad/list"));
	}

	/**
	 * 编辑
	 */
	public void edit() {
		Long id = getParaToLong("id");
		setAttr("ad", new Ad().dao().findById(id));
		setAttr("adPositions", new AdPosition().dao().findAll());
		render(getView("ad/edit"));
	}

	/**
	 * 更新
	 */
	public void update() {
		Ad ad = getModel(Ad.class,"",true); 
		ad.setModifyDate(new Date());
		ad.update();
		redirect(getListQuery("/admin/ad/list"));
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
            Ad ad = new Ad().dao().findById(id);
            ad.setSort(sort);
            ad.update();
        }
        renderJson(Feedback.success(new HashMap<>()));
    }

	/**
	 * 列表
	 */
	public void list() {
	    String name = getPara("name");
		Integer pageNumber = getParaToInt("pageNumber");
		if(pageNumber==null){
			pageNumber = 1;
		}
		setAttr("page", new Ad().dao().findPage(name,pageNumber,PAGE_SIZE));
		setAttr("name", name);
		render(getView("ad/list"));
	}

	/**
	 * 删除
	 */
	public void delete() {
		Long ids[] = getParaValuesToLong("ids");
		if(ArrayUtils.isNotEmpty(ids)){
			for(Long id:ids){
				new Ad().dao().deleteById(id);
			}
		}
		renderJson(Feedback.success(new HashMap<>()));
	}

}