package com.jflyfox.modules.@{crud.urlKey};

import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.jfinal.component.annotation.ControllerBind;
import com.jflyfox.jfinal.component.db.SQLUtils;
import com.jflyfox.modules.admin.article.TbArticle;
import com.jflyfox.modules.admin.site.TbSite;
import com.jflyfox.util.StrUtils;
import com.jfinal.plugin.activerecord.Page;

/**
 * @{crud.table.remarks}
 * 
 * @author flyfox 2014-4-24
 */
@ControllerBind(controllerKey = "/admin/@{crud.urlKey}")
public class @{strutils.toUpperCaseFirst(crud.urlKey)}Controller extends BaseProjectController {

	private static final String path = "/pages/admin/@{crud.urlKey}/@{crud.urlKey}_";

	public void index() {
		list();
	}
	
	public void list() {
		@{crud.table.className} model = getModelByAttr(@{crud.table.className}.class);

		SQLUtils sql = new SQLUtils(" from @{crud.table.tableName} t where 1=1 ");
		if (model.getAttrValues().length != 0) {
			sql.setAlias("t");
			// 查询条件
		}
		// 排序
		String orderBy = getBaseForm().getOrderBy();
		if (StrUtils.isEmpty(orderBy)) {
			sql.append(" order by t.@{crud.primaryKey} desc ");
		} else {
			sql.append(" order by ").append(orderBy);
		}

		Page<@{crud.table.className}> page = @{crud.table.className}.dao.paginate(getPaginator(), "select t.* ", //
				sql.toString().toString());

		// 下拉框
		setAttr("page", page);
		setAttr("attr", model);
		render(path + "list.html");
	}

	public void add() {
		render(path + "add.html");
	}

	public void view() {
		@{crud.table.className} model = @{crud.table.className}.dao.findById(getParaToInt());
		setAttr("model", model);
		render(path + "view.html");
	}

	public void delete() {
		Integer pid = getParaToInt();
		@{crud.table.className} model = new @{crud.table.className}();
		Integer userid= getSessionUser().getUserID();
		String now = getNow();
		model.put("update_id", userid);
		model.put("update_time", now);
		model.deleteById(pid);
		
		list();
	}

	public void edit() {
		@{crud.table.className} model = @{crud.table.className}.dao.findById(getParaToInt());
		setAttr("model", model);
		render(path + "edit.html");
	}

	public void save() {
		Integer pid = getParaToInt();
		@{crud.table.className} model = getModel(@{crud.table.className}.class);
		
		Integer userid= getSessionUser().getUserID();
		String now = getNow();
		model.put("update_id", userid);
		model.put("update_time", now);
		if (pid != null && pid > 0) { // 更新
			model.update();
		} else { // 新增
			model.remove("@{crud.primaryKey}");
			model.put("create_id", userid);
			model.put("create_time", now);
			model.save();
		}
		renderMessage("保存成功");
	}
}
