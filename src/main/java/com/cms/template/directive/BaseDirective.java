/*
 * 
 * 
 * 
 */
package com.cms.template.directive;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.lang.ArrayUtils;

import com.jfinal.template.Directive;
import com.jfinal.template.expr.ast.Assign;
import com.jfinal.template.expr.ast.Expr;
import com.jfinal.template.expr.ast.ExprList;
import com.jfinal.template.stat.Scope;

/**
 * 模板指令 - 基类
 * 
 * 
 * 
 */
public abstract class BaseDirective extends Directive {
    
    private static final ConvertUtilsBean CONVERT_UTILS = new ConvertUtilsBean();
    
    /** "起始数量"参数名称 */
    private static final String START_PARAMETER_NAME = "start";

	/** "数量"参数名称 */
	private static final String COUNT_PARAMETER_NAME = "count";

	/** "排序"参数名称 */
	private static final String ORDER_BY_PARAMETER_NAME = "orderBy";
	
	protected Map<String,Expr> params = new HashMap<>();
	
	/**
	 * 设置参数
	 */
    @Override
    public void setExprList(ExprList exprList) {
        // TODO Auto-generated method stub
        super.setExprList(exprList);
        Expr[] exprArray = exprList.getExprArray();
        if(ArrayUtils.isNotEmpty(exprArray)){
            for(Expr expr : exprArray){
                Assign assign = (Assign)expr;
                params.put(assign.getId(), expr);
            }
        }
    }
    
    /**
     * 获取参数
     * 
     * @param name
     *            名称
     * @param type
     *            类型
     * @return 参数，若不存在则返回null
     */
    @SuppressWarnings("unchecked")
    protected <T> T getParameter(String name,Class<T> type,Scope scope){
        Expr expr = params.get(name);
        if(expr!=null){
            Object value = expr.eval(scope);
            return (T)CONVERT_UTILS.convert(value, type);
        }
        return null;
    }

    /**
     * 获取起始数量
     * 
     * @param params
     *            参数
     * @return 起始数量
     */
    protected Integer getStart(Scope scope){
        Expr expr = params.get(START_PARAMETER_NAME);
        if(expr!=null){
            return (Integer)expr.eval(scope);
        }
        return null;
    }
	
	/**
	 * 获取数量
	 * 
	 * @param params
	 *            参数
	 * @return 数量
	 */
	protected Integer getCount(Scope scope){
	    Expr expr = params.get(COUNT_PARAMETER_NAME);
	    if(expr!=null){
	        return (Integer)expr.eval(scope);
	    }
	    return null;
	}
	
	/**
	 * 获取排序
	 * 
	 * @param params
	 *            参数
	 * @return 数量
	 */
	protected String getOrderBy(Scope scope){
	    Expr expr = params.get(ORDER_BY_PARAMETER_NAME);
	    if(expr!=null){
	        return (String)expr.eval(scope);
	    }
	    return null;
	}
	
}