package com.fc.modules.@{crud.urlKey};

import com.fc.component.annotation.ControllerBind;
import com.fc.component.base.BaseProjectController;
import com.fc.modules.admin.attachimage.TbAttachImage;
import com.fc.util.SQLUtils;
import com.fj.hiwetoptools.jfinal.jqgrid.JqGridModelUtils;
import com.jfinal.aop.Inject;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;

/**
 * <p>Description:@{crud.table.remarks}</p>
 * @author linyu
 * @create @{date()}
 */
@ControllerBind(controllerKey = "/learn/@{crud.urlKey}")
public class @{strutils.toUpperCaseFirst(crud.urlKey)}Controller extends BaseProjectController {

	private static final String path = "/pages/learn/@{crud.urlKey}/@{crud.urlKey}_";
	private static final String BIZ_CODE = "@{strutils.toUpperCase(crud.urlKey)}_CODE";

	@Inject
	@{strutils.toUpperCaseFirst(crud.urlKey)}Service service;

	public void index() {
		list();
	}

	public void getListData() {
		String keyword = this.getPara("keyword");
		Kv kv = this.getKv();
		SQLUtils sql = new SQLUtils(" from @{crud.table.tableName} t where 1=1 ");
		sql.setAlias("t");
		if (StrKit.notBlank(keyword)) {

		}

		int pageNo = this.getParaToInt("page", 1);
		int rows=this.getParaToInt("rows", 10);

		Page<@{crud.table.className}> page = @{crud.table.className}.dao.paginate(pageNo,rows, "select t.* ",sql.toString());
		this.renderJson(JqGridModelUtils.toJqGridView(page));
		return;
	}
	
	public void list() {
		@{crud.table.className} model = getModelByAttr(@{crud.table.className}.class);

		SQLUtils sql = new SQLUtils(" select t.* from @{crud.table.tableName} t where 1=1 ");
		if (model.getAttrValues().length != 0) {
			sql.setAlias("t");
			// 查询条件
		}

		//排序前先重组sql,必须放在orderby前面调用
		sql.recombine();
		// 排序
		String orderBy = getBaseForm().getOrderBy();
		if (StrKit.isBlank(orderBy)) {
		sql.append(" order by t.create_time desc ");
		} else {
		sql.append(" order by t.").append(orderBy);
		}

		Page<@{crud.table.className}> page = @{crud.table.className}.dao.paginate(getPaginator(), "select t.* ", //
				sql.toString());

		// 下拉框
		setAttr("page", page);
		setAttr("attr", model);
		setAttr("form", getBaseForm());

		render(path + "list.html");
	}

	public void add() {
		render(path + "add.html");
	}

	public void view() {
		@{crud.table.className} model = @{crud.table.className}.dao.findById(getPara());
		setAttr("model", model);
		render(path + "view.html");
	}

	public void delete() {
		String pid = getPara();
		@{crud.table.className} model = new @{crud.table.className}();
		String userid= getSessionUser().getUserID();
		String now = getNow();
		model.put("update_id", userid);
		model.put("update_time", now);
		model.deleteById(pid);
		
		list();
	}

	public void edit() {
		@{crud.table.className} model = @{crud.table.className}.dao.findById(getPara());
		setAttr("model", model);
		render(path + "edit.html");
	}

	public void save() {
		String pid = getPara();
		@{crud.table.className} model = getModel(@{crud.table.className}.class);

		String userid= getSessionUser().getUserID();
		String now = getNow();
		model.put("update_id", userid);
		model.put("update_time", now);
		if (StrKit.notBlank(pid)) { // 更新
			model.update();
		} else { // 新增
			model.remove("@{crud.primaryKey}");
			model.put("create_id", userid);
			model.put("create_time", now);
			model.save();
		}
		renderMessage("保存成功");
	}

	public void toUpload(){
		String bizId = getPara();
		TbAttachImage ai = TbAttachImage.dao.findFirst(" select * from tb_attach_image t where t.biz_id = ? and t.biz_code = ?",bizId,BIZ_CODE);
		if(null == ai){
			ai = new TbAttachImage();
			ai.setBizCode(BIZ_CODE);
			ai.setBizId(bizId);
		}
		setAttr("attach",ai);
		setAttr("actionUrl",UPLOAD_ACTION);
		render(UPLOAD_FILE);
	}
}
