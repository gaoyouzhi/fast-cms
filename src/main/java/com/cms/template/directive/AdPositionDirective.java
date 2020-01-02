/*
 * 
 * 
 * 
 */
package com.cms.template.directive;

import com.cms.TemplateVariable;
import com.cms.entity.AdPosition;
import com.jfinal.template.Env;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;

/**
 * 模板指令 - 广告位
 * 
 * 
 * 
 */
@TemplateVariable(name="ad_position")
public class AdPositionDirective extends BaseDirective {

	/** "ID"参数名称 */
	private static final String ID_PARAMETER_NAME = "id";
	
	/** 变量名称 */
	private static final String VARIABLE_NAME = "adPosition";
	
    @Override
    public void exec(Env env, Scope scope, Writer writer) {
        // TODO Auto-generated method stub
        scope = new Scope(scope);
        Long id = getParameter(ID_PARAMETER_NAME, Long.class, scope);
        AdPosition adPosition = new AdPosition().dao().findById(id);
        scope.setLocal(VARIABLE_NAME,adPosition);
        stat.exec(env, scope, writer);
    }
    
    public boolean hasEnd() {
        return true;
    }
}