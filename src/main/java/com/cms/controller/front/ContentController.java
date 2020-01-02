/*
 * 
 * 
 * 
 */
package com.cms.controller.front;

import com.cms.entity.Content;
import com.cms.routes.RouteMapping;

/**
 * Controller - 内容
 * 
 * 
 * 
 */
@RouteMapping(url = "/content")
public class ContentController extends BaseController {

	/**
	 * 内容
	 */
	public void index() {
		Long contentId = getParaToLong(0);
		Content content = new Content().dao().findById(contentId);
		setAttr("currentContent", content);
		render("/templates/"+getTheme()+"/"+content.getCategory().getTemplate()+"_detail.html");
	}
	
	/**
	 * demo
	 */
	public void demo() {
		Long contentId = getParaToLong(0);
		Content content = new Content().dao().findById(contentId);
		setAttr("currentContent", content);
		render("/templates/"+getTheme()+"/"+content.getCategory().getTemplate()+"_demo.html");
	}
	
   /**
     * 搜索
     */
    public void search() {
        String keyword = getPara("keyword");
        Integer pageNumber = getParaToInt("pageNumber");
        if(pageNumber==null){
            pageNumber = 1;
        }
        int pageSize = 20 ; 
        setAttr("page", new Content().dao().search(keyword,pageNumber,pageSize));
        setAttr("keyword", keyword);
        render("/templates/"+getTheme()+"/search.html");
    }

	/**
	 * 点击数
	 */
	public void hits() {
		Long id = getParaToLong(0);
		if (id == null) {
			renderJson(0L);
			return;
		}
		Content content = new Content().dao().findById(id);
		Long hits = content.getHits();
		hits = hits+1;
		content.setHits(hits);
		content.update();
		renderJavascript("document.write("+hits+")");
	}

}