package com.test.jflyfox.config;

import com.jfinal.kit.Config;
import com.jflyfox.autocreate.beetl.TemplateUtils;
import com.jflyfox.autocreate.db.Table;
import com.jflyfox.autocreate.template.CRUD;
import com.jflyfox.autocreate.template.model.FormType;
import com.jflyfox.autocreate.template.model.InputType;
import com.jflyfox.autocreate.template.model.ModelAttr;
import com.jflyfox.autocreate.util.AutoCreate;

import java.util.HashMap;
import java.util.Map;

public class TableConfig {

	public final static Map<String, CRUD> crudMap = new HashMap<String, CRUD>();

	static {
		init();
	}

	public static void main(String[] args) throws Exception {
		AutoCreate.PATH_PAGE_TEMPLATE = Config.getStr("template.path.jsp");
		AutoCreate.crudMap = TableConfig.crudMap;
		AutoCreate.createCode();
	}

	public static void init() {
		// 用户管理
		Table table = new Table();
		table.setTableName("tb_user");
		CRUD user = new CRUD();
		user.setTable(table);
		user.setPrimaryKey("userid");
		user.setUrlKey("user");
		user.setName("用户");
		user.setAttr(new ModelAttr().setKey("username").setName("登陆名").addSearch()
				.setFormTypeValid("required='required'"));
		user.setAttr(new ModelAttr().setKey("realname").setName("真实姓名").addSearch());
		String type = TemplateUtils.getJson("1", "男", "2", "女");
		user.setAttr(new ModelAttr().setKey("type").setName("性别") //
				.setInputType(InputType.RADIO).setFormTypeData(type).addSearch());
		String statue = TemplateUtils.getJson("1", "管理员", "2", "普通用户");
		user.setAttr(new ModelAttr().setKey("statue").setName("角色") //
				.setInputType(InputType.CHECKBOX).setFormTypeData(statue).addSearch());
		String statue2 = TemplateUtils.getJson("1", "管理员2", "2", "普通用户2");
		user.setAttr(new ModelAttr().setKey("statue2").setName("角色2") //
				.setFormType(FormType.SELECT).setFormTypeData(statue2).addSearch());
		user.setAttr(new ModelAttr().setKey("email").setName("Email").setInputType(InputType.EMAIL).addSearch());
		user.setAttr(new ModelAttr().setKey("birthday").setName("生日").removeList().setInputType(InputType.DATE));
		user.setAttr(new ModelAttr().setKey("remark").setName("说明").setFormType(FormType.TEXTAREA));
		add(user);
	}

	protected static void add(CRUD contact) {
		crudMap.put(contact.getUrlKey(), contact);
	}

}
