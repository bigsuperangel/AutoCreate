package com.jflyfox.client;

import com.jfinal.kit.Config;
import com.jfinal.kit.StrUtils;
import com.jflyfox.autocreate.template.CRUD;
import com.jflyfox.autocreate.util.AutoCreate;
import com.jflyfox.autocreate.util.DbUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.sql.SQLException;
import java.util.Map;


public class AutoCreateClient {

	public static void main(String[] args) throws Exception {
		FileUtils.deleteDirectory(new File(AutoCreate.PATH_OUTPUT));
		run();
	}

	protected static void run() throws Exception {
		DbUtils.init();

		String selected = Config.getStr("template.selected");
		String tables = Config.getStr("template.tables");
		Map<String, CRUD> crudMap = null;
		if (StrUtils.isEmpty(tables) || "all".equalsIgnoreCase(tables)) {
			crudMap = DbUtils.getCRUDMap();
		} else {
			String[] tableArray = tables.split(",");
			crudMap = DbUtils.getCRUDMap(tableArray);
		}

		AutoCreate.PATH_PAGE_TEMPLATE = Config.getStr(selected);
		AutoCreate.crudMap = crudMap;
		AutoCreate.createCode();
		//打开文件夹

		Runtime.getRuntime().exec("cmd.exe /c start "+AutoCreate.PATH_OUTPUT);
	}

}
