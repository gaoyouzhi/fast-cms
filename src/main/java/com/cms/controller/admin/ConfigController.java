package com.cms.controller.admin;

import com.cms.Config;
import com.cms.routes.RouteMapping;
import com.cms.util.SystemUtils;
import com.cms.util.TemplateVariableUtils;

/**
 * Controller - 系统设置
 * 
 * 
 * 
 */
@RouteMapping(url = "/admin/config")
public class ConfigController extends BaseController {

    /**
     * 编辑
     */
    public void edit(){
        setAttr("config", SystemUtils.getConfig());
        setAttr("themes", SystemUtils.getThemes());
        render(getView("config/edit"));
    }
    
    /**
     * 更新
     */
    public void update(){
        Config config = getBean(Config.class, "",true);
        SystemUtils.setConfig(config);
        TemplateVariableUtils.putBaseVariable();
        redirect("/admin/config/edit");
    }
}
