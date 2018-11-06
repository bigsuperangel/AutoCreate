package com.jflyfox.modules.@{crud.urlKey};

import com.jfinal.plugin.activerecord.Page;
import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.component.annotation.ControllerBind;
import com.jflyfox.util.SQLUtils;
import com.jflyfox.modules.admin.attachimage.TbAttachImage;
import com.jflyfox.util.StrUtils;
import com.swagger.annotation.Api;
import com.swagger.annotation.ApiOperation;
import com.swagger.annotation.Param;
import com.swagger.annotation.Params;

/**
 * <p>Description:@{crud.table.remarks}</p>
 * @author linyu
 * @create @{date()}
 */
@Api(tag = "@{crud.urlKey}", description = "@{crud.table.remarks}")
@ControllerBind(controllerKey = "/admin/@{crud.urlKey}")
public class @{strutils.toUpperCaseFirst(crud.urlKey)}Controller extends BaseProjectController {

	private static final String path = "/pages/admin/@{crud.urlKey}/@{crud.urlKey}_";
	private static final String BIZ_CODE = "@{strutils.toUpperCase(crud.urlKey)}_CODE";

	@Inject
	@{strutils.toUpperCaseFirst(crud.urlKey)}Service service;

	@ApiOperation(url = "/admin/@{crud.urlKey}", tag = "@{crud.urlKey}", httpMethod = "get", description = "列表")
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
				sql.toString().toString());

		// 下拉框
		setAttr("page", page);
		setAttr("attr", model);
		setAttr("form", getBaseForm());

		render(path + "list.html");
	}

	@ApiOperation(url = "/admin/@{crud.urlKey}/add", tag = "@{crud.urlKey}", httpMethod = "get", description = "新增")
	public void add() {
		render(path + "add.html");
	}

	@ApiOperation(url = "/admin/@{crud.urlKey}/view", tag = "@{crud.urlKey}", httpMethod = "get", description = "查看")
	@Params({
			@Param(name = "@{crud.primaryKey}", description = "ID", required = true, dataType = "integer"),
	})
	public void view() {
		@{crud.table.className} model = @{crud.table.className}.dao.findById(getParaToInt());
		setAttr("model", model);
		render(path + "view.html");
	}

	@ApiOperation(url = "/admin/@{crud.urlKey}/delete", tag = "@{crud.urlKey}", httpMethod = "post", description = "删除")
	@Params({
			@Param(name = "@{crud.primaryKey}", description = "ID", required = true, dataType = "integer"),
	})
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

	@ApiOperation(url = "/admin/@{crud.urlKey}/edit", tag = "@{crud.urlKey}", httpMethod = "get", description = "修改")
	@Params({
			@Param(name = "@{crud.primaryKey}", description = "ID", required = true, dataType = "integer"),
	})
	public void edit() {
		@{crud.table.className} model = @{crud.table.className}.dao.findById(getParaToInt());
		setAttr("model", model);
		render(path + "edit.html");
	}

	@ApiOperation(url = "/admin/@{crud.urlKey}/save", tag = "@{crud.urlKey}", httpMethod = "post", description = "保存")
	@Params({
			# for(column in crud.table.columns){ #
			@Param(name = "@{strutils.toLowerCase(column.columnName)}", description = "@{column.remarks}", dataType = "@{column.swaggerType}"),
			# } #
	})
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

	public void toUpload(){
		Long bizId = getParaToLong();
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
