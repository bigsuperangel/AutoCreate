package com.jflyfox.autocreate.util;

import com.jfinal.kit.Config;
import com.jfinal.kit.FileUtils;
import com.jfinal.kit.StrUtils;
import com.jflyfox.autocreate.beetl.GroupTemplateFactory;
import com.jflyfox.autocreate.beetl.TemplateUtils;
import com.jflyfox.autocreate.template.CRUD;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;

import java.io.File;
import java.io.FileFilter;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class AutoCreate {

	private final static GroupTemplate groupTemplate = GroupTemplateFactory.getClasspath();
	// 需设置
	public static String PATH_OUTPUT = System.getProperty("user.dir") + "/" + Config.getStr("template.output.path");
	public static String PATH_PAGE_TEMPLATE = Config.getStr("template.path.jsp");
	public static Map<String, CRUD> crudMap;

	public static void createCode() throws Exception {
		System.out.println("System.getProperty(\"user.dir\") = " + System.getProperty("user.dir"));
		createCode(AutoCreate.PATH_PAGE_TEMPLATE);
	}

	public static void createCode(String templatePath) throws Exception {
		if (crudMap == null) {
			System.err.println("###crudMap为null，请参考其他配置数据信息。");
			return;
		}

		System.out.println("####生成模板开始...");
		init();
		String webPath,javaPath, html;
		System.out.print("####创建文件：");

		if (crudMap != null) {
			for (CRUD crud : crudMap.values()) {
				System.out.print("\t" + crud.getUrlKey() + ".....\n");
				webPath = PATH_OUTPUT + "web/" + crud.getUrlKey();
				javaPath = PATH_OUTPUT + "java/" + crud.getUrlKey();
				if (!new File(webPath).exists()) {
					new File(webPath).mkdirs();
				}
				if (!new File(javaPath).exists()) {
					new File(javaPath).mkdirs();
				}

				if (groupTemplate != null)
					groupTemplate.close();
				List<String> pageList = FileUtils.findFileNames(System.getProperty("user.dir") + templatePath,
						new FileFilter() {
							public boolean accept(File pathname) {
								// 有后缀就处理
								return pathname.getName().indexOf(".java") > 0;
							}
						});

				List<String> htmlList = FileUtils.findFileNames(System.getProperty("user.dir") + templatePath,
						new FileFilter() {
							public boolean accept(File pathname) {
								// 有后缀就处理
								return pathname.getName().indexOf(".html") > 0;
							}
						});

				for (String name : pageList) {
					System.out.println(name);
					html = TemplateUtils.getStr(templatePath + name, "crud", crud);

					// 文件名处理
					String fileName = GroupTemplateFactory.getFileName(crud, name);
					FileUtils.write(Paths.get(javaPath,fileName).toString(), html.getBytes("UTF-8"));
				}

				for (String name : htmlList) {
					html = TemplateUtils.getStr(templatePath + name, "crud", crud);

					// 文件名处理
					String fileName = GroupTemplateFactory.getFileName(crud, name);
					FileUtils.write(Paths.get(webPath,fileName).toString(), html.getBytes("UTF-8"));
				}
			}
			System.out.println();
		}

		System.out.println("####生成模板完成。");
	}

	public static void init() {
		// 模板配置和函数加载，区分别的模板~生成没冲突
		groupTemplate.registerFunctionPackage("flyfox", TemplateUtils.class);
		groupTemplate.registerFunctionPackage("strutils", StrUtils.class);
		Configuration cfg = groupTemplate.getConf();
		
		cfg.setStatementStart(Config.getStr("beetl.statementStart"));
		cfg.setStatementEnd(Config.getStr("beetl.statementEnd"));
		cfg.setPlaceholderStart(Config.getStr("beetl.placeholderStart"));
		cfg.setPlaceholderEnd(Config.getStr("beetl.placeholderEnd"));
	}

}
