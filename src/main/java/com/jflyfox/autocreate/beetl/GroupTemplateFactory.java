package com.jflyfox.autocreate.beetl;

import java.io.IOException;

import com.jfinal.kit.StrUtils;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.FileResourceLoader;
import org.beetl.core.resource.StringTemplateResourceLoader;

import com.jflyfox.autocreate.template.CRUD;

/**
 * 字符串模板
 * 
 * 2015年4月22日 下午2:04:52 flyfox 330627517@qq.com
 */
public class GroupTemplateFactory {
	private static GroupTemplate stringTemplate = null;
	private static GroupTemplate groupTemplate;

	public static GroupTemplate getClasspath() {
		try {
			if (groupTemplate == null) {
				FileResourceLoader resourceLoader = new FileResourceLoader();
				Configuration cfg = Configuration.defaultConfiguration();
				groupTemplate = new GroupTemplate(resourceLoader, cfg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return groupTemplate;
	}

	public static void closeClasspath() {
		if (groupTemplate != null)
			groupTemplate.close();
	}

	public static GroupTemplate getString() throws IOException {
		try {
			if (stringTemplate == null) {
				StringTemplateResourceLoader stringLoader = new StringTemplateResourceLoader();
				Configuration cfg = Configuration.defaultConfiguration();
				stringTemplate = new GroupTemplate(stringLoader, cfg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringTemplate;
	}

	public static void closeString() {
		if (stringTemplate != null)
			stringTemplate.close();
	}

	public static String getFileName(CRUD crud, String name) throws Exception {
		GroupTemplate stringTemplate = getString();
		Template t = stringTemplate.getTemplate(name);
		t.binding("crud", crud);
		t.binding("key", crud.getUrlKey());
		t.binding("keyUpper", crud.getUrlKey().toUpperCase());
		t.binding("keyLower", crud.getUrlKey().toLowerCase());
		t.binding("keyLowerFirst", StrUtils.toLowerCaseFirst(crud.getUrlKey()));
		t.binding("keyUpperFirst", StrUtils.toUpperCaseFirst(crud.getUrlKey()));
		String fileName = t.render();
		return fileName;
	}
}
