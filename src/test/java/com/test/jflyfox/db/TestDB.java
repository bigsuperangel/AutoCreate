package com.test.jflyfox.db;

import com.jflyfox.autocreate.db.Column;
import com.jflyfox.autocreate.db.Table;
import com.jflyfox.autocreate.db.TableFactory;
import com.jflyfox.autocreate.util.DbUtils;

public class TestDB {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		DbUtils.init();

		TableFactory tbFactory = TableFactory.getInstance();
		// System.out.println(tbFactory.getAllTables());
		Table t = tbFactory.getTable("report_app_advert");
		System.out.println(t.toString());
		String tmp;
		for (Column col : t.getColumns()) {
			tmp = "@Column(name = \"" + col.getColumnName() + "\")";
			System.out.println(tmp);
			System.out.println("private " + col.getJavaType().replace("java.lang.", "") + " " + col.getColumnName() +";");
			System.out.println();

		}
	}

}
