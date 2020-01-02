package com.cms.entity;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;

import com.alibaba.fastjson.annotation.JSONField;
import com.cms.entity.base.BaseCategory;
import com.cms.util.DBUtils;
import com.jfinal.core.JFinal;

/**
 * Entity - 分类
 * 
 * 
 * 
 */
@SuppressWarnings("serial")
public class Category extends BaseCategory<Category> {
	
	/** 树路径分隔符 */
	public static final String TREE_PATH_SEPARATOR = ",";
	
	//列表栏目
    public static int LIST_TYPE=1;
    //单页面
    public static int PAGE_TYPE=2;
    //链接
    public static int LINK_TYPE=3;
    //空栏目
    public static int EMPTY_TYPE=4;
    
    //文章
    public static int ARTICLE_MODEL=1;
    //代码
    public static int CODE_MODEL=2;
	
	
	/**
	 * 内容
	 */
	@JSONField(serialize=false)  
	private List<Content> contents;
	
	/**
	 * 下级分类
	 */
	@JSONField(serialize=false)  
	private List<Category> children;
	
	/**
	 * 上级分类
	 */
	@JSONField(serialize=false)  
	private Category parent;
	
	/**
	 * 获取内容
	 * 
	 * @return 内容
	 */
	public List<Content> getContents(){
	    if(contents == null){
	        contents = new Content().dao().find("select * from cms_content where categoryId = ?",getId());
	    }
		return contents;
	}
	
	/**
	 * 获取下级分类
	 * 
	 * @return 下级分类
	 */
	public List<Category> getChildren() {
	    if(children == null){
	        children = find("select * from cms_category where parentId=? order by sort desc",getId());
	    }
		return children;
	}
	
	/**
	 * 获取上级分类
	 * @return	上级分类
	 */
	public Category getParent(){
	    if(parent == null){
	        parent = findById(getParentId());
	    }
		return parent;
	}
	
	
	/**
	 * 查找顶级分类
	 * 
	 * @param contentModelId
     *            内容模型ID
	 * @param isMenu
	 *            是否是菜单
	 * @param start
     *            起始位置
	 * @param count
	 *            数量
	 * @return 顶级分类
	 */
	public List<Category> findRoots(Boolean isMenu,Integer start,Integer count){
	    String filterSql = "";
		if(isMenu!=null){
		    filterSql+= " and isMenu="+BooleanUtils.toInteger(isMenu);
		}
		String countSql=DBUtils.getCountSql(start, count);
		String orderBySql = DBUtils.getOrderBySql("sort desc");
		return find("select * from cms_category where parentId is null"+filterSql+orderBySql+countSql);
	}
	
	
	/**
	 * 查找上级分类
	 * 
	 * @param categoryId
	 *            分类Id
	 * @param isMenu
	 *            是否是菜单
	 * @param recursive
	 *            是否递归
	 * @param start
     *            起始位置
	 * @param count
	 *            数量
	 * @return 上级分类
	 */
	public List<Category> findParents(Long categoryId,Boolean isMenu,Boolean recursive,Integer start, Integer count){
		Category category = findById(categoryId);
		if(categoryId == null || category.getParentId() == null){
			return Collections.emptyList();
		}
		String filterSql = "";
		if(isMenu!=null){
		    filterSql+= " and isMenu="+BooleanUtils.toInteger(isMenu);
		}
		if(recursive){
			String countSql=DBUtils.getCountSql(start, count);
			String orderBySql = DBUtils.getOrderBySql("grade asc");
			return find("select * from cms_category where id in ("+StringUtils.join(category.getParentIds(), ",")+") "+filterSql+orderBySql+countSql);
		}else{
			return find("select * from cms_category where id = ? "+filterSql,findById(categoryId).getParentId());
		}
	}
	
	
	/**
	 * 查找下级分类
	 * 
	 * @param categoryId
	 *            分类Id
	 * @param isMenu
	 *            是否是菜单
	 * @param recursive
	 *            是否递归
	 * @param start
     *            起始位置
	 * @param count
	 *            数量
	 * @return 下级文章分类
	 */
	public List<Category> findChildren(Long categoryId,Boolean isMenu,Boolean recursive,Integer start,Integer count){
	    String filterSql = "";
		if(isMenu!=null){
		    filterSql+= " and isMenu="+BooleanUtils.toInteger(isMenu);
		}
		if(recursive){
			String countSql=DBUtils.getCountSql(start, count);
			String orderBySql = DBUtils.getOrderBySql("grade asc,sort desc");
			List<Category> categorys;
			if(categoryId!=null){
			    categorys = find("select * from cms_category where 1=1 and treePath like ? "+filterSql+orderBySql+countSql,"%,"+categoryId+",%");
			}else{
			    categorys = find("select * from cms_category where 1=1 "+filterSql+orderBySql+countSql);
			}
			sort(categorys);
			return categorys;
		}else{
		    String orderBySql = DBUtils.getOrderBySql("sort desc");
			return find("select * from cms_category where parentId = ? "+filterSql+orderBySql,categoryId);
		}
	}
	
	/**
	 * 查找分类树
	 * 
	 * @return 分类树
	 */
	public List<Category> findTree(){
		return findChildren(null,null,true,null,null);
	}
	
	
	/**
	 * 获取所有上级分类ID
	 * 
	 * @return 所有上级分类ID
	 */
	public Long[] getParentIds() {
		String[] treePaths = StringUtils.split(getTreePath(), TREE_PATH_SEPARATOR);
		Long[] result = new Long[treePaths.length];
		for (int i = 0; i < treePaths.length; i++) {
			result[i] = Long.valueOf(treePaths[i]);
		}
		return result;
	}
	
	/**
     * 排序分类
     * 
     * @param categorys
     *            分类
     */
    private void sort(List<Category> categorys) {
        if(categorys == null || categorys.size()==0) {
            return;
        }
        final Map<Long, Integer> sortMap = new HashMap<Long, Integer>();
        for (Category category : categorys) {
            sortMap.put(category.getId(), category.getSort());
        }
        Collections.sort(categorys, new Comparator<Category>() {
            @Override
            public int compare(Category category1, Category category2) {
                Long[] ids1 = (Long[]) ArrayUtils.add(category1.getParentIds(), category1.getId());
                Long[] ids2 = (Long[]) ArrayUtils.add(category2.getParentIds(), category2.getId());
                Iterator<Long> iterator1 = Arrays.asList(ids1).iterator();
                Iterator<Long> iterator2 = Arrays.asList(ids2).iterator();
                CompareToBuilder compareToBuilder = new CompareToBuilder();
                while (iterator1.hasNext() && iterator2.hasNext()) {
                    Long id1 = iterator1.next();
                    Long id2 = iterator2.next();
                    Integer sort1 = sortMap.get(id1);
                    Integer sort2 = sortMap.get(id2);
                    compareToBuilder.append(sort2,sort1).append(id1, id2);
                    if (!iterator1.hasNext() || !iterator2.hasNext()) {
                        compareToBuilder.append(category1.getGrade(), category2.getGrade());
                    }
                }
                return compareToBuilder.toComparison();
            }
        });
    }
	
	
	/**
	 * 设置值
	 * 
	 */
	public void setValue() {
		if (getParentId() != null) {
			setTreePath(getParent().getTreePath() + getParent().getId() + Category.TREE_PATH_SEPARATOR);
		} else {
			setTreePath(Category.TREE_PATH_SEPARATOR);
		}
		setGrade(getParentIds().length);
	}
	
	/**
	 * 获取路径
	 * 
	 * @return 路径
	 */
	public String getPath() {
	    if(getType() == LINK_TYPE && StringUtils.isNotBlank(getUrl())){
            return getUrl();
        }
	    return JFinal.me().getContextPath()+"/category/"+getId();
	}
}
