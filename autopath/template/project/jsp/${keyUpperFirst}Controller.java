package com.jflyfox.modules.@{crud.urlKey};

import com.jflyfox.jfinal.base.BaseController;
import com.jflyfox.jfinal.component.db.SQLUtils;
import com.jfinal.plugin.activerecord.Page;

/**
 * @{crud.table.remarks}
 * 
 * @author flyfox 2014-4-24
 */
public class @{strutils.toUpperCaseFirst(crud.urlKey)}Controller extends BaseController {

	private static final String path = "/pages/@{crud.urlKey}/@{crud.urlKey}_";

	public void list() {
		@{crud.table.className} model = getModelByAttr(@{crud.table.className}.class);

		SQLUtils sql = new SQLUtils(" from @{crud.table.tableName} t where 1=1 ");
		if (model.getAttrValues().length != 0) {
			sql.setAlias("t");
			// 查询条件
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
		@{crud.table.className}.dao.deleteById(getParaToInt());
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
		if (pid != null && pid > 0) { // 更新
			model.update();
		} else { // 新增
			model.remove("@{crud.primaryKey}");
			model.put("create_id", getSessionUser().getUserID());
			model.put("create_time", getNow());
			model.save();
		}
		renderMessage("保存成功");
	}
}
