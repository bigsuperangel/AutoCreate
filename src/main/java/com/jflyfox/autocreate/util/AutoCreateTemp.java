package com.jflyfox.autocreate.util;

import com.jfinal.kit.Config;
import com.jfinal.kit.FileUtils;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.StrUtils;
import com.jfinal.plugin.activerecord.DbKit;
import com.jflyfox.autocreate.beetl.GroupTemplateFactory;
import com.jflyfox.autocreate.beetl.TemplateUtils;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;

import java.io.File;
import java.io.FileFilter;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AutoCreateTemp {

    private final static GroupTemplate groupTemplate = GroupTemplateFactory.getClasspath();
    // 需设置
    public static String PATH_OUTPUT = System.getProperty("user.dir") + "/" + Config.getStr("template.output.path");
    public static String PATH_PAGE_TEMPLATE = "/autopath/template/project/questionnaire/";



    private com.jfinal.plugin.activerecord.Config getConfig() {
        return DbKit.getConfig();
    }

    public List<TbQuestion> getAllTables() throws SQLException {
        DbUtils.init();
        com.jfinal.plugin.activerecord.Config config = getConfig();
        Connection conn = config.getConnection();
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("select * from tb_question");
        List<TbQuestion> tables = new ArrayList<TbQuestion>();
        while (rs.next()) {
            int id = rs.getInt("id");
            int ques_type = rs.getInt("ques_type");
            int sort = rs.getInt("sort");
            int is_require = rs.getInt("is_require");
            int relate_id = rs.getInt("relate_id");
            int qid = rs.getInt("qid");
            String title = rs.getString("title");



            TbQuestion table = new TbQuestion();
            table.setIs_require(is_require);
            table.setQid(qid);
            table.setQues_type(ques_type);
            table.setRelate_id(relate_id);
            table.setSort(sort);
            table.setTitle(title);
            tables.add(table);
        }
        conn.close();// 关闭数据库连接
        return tables;
    }

    public static void main(String[] args) throws Exception {
//        org.apache.commons.io.FileUtils.deleteDirectory(new File(AutoCreate.PATH_OUTPUT));
        createCode();
        Runtime.getRuntime().exec("cmd.exe /c start "+AutoCreate.PATH_OUTPUT);

    }

    public static void createCode() throws Exception {
        System.out.println(System.getProperty("user.dir"));
        createCode(AutoCreateTemp.PATH_PAGE_TEMPLATE);
    }

    public static void createCode(String templatePath) throws Exception {
        List<TbQuestion> tables = new AutoCreateTemp().getAllTables();

        System.out.println("####生成模板开始...");
        init();
        String webPath = PATH_OUTPUT;
        System.out.print("####创建文件：");


        if (groupTemplate != null)
            groupTemplate.close();


        List<String> htmlList = FileUtils.findFileNames(System.getProperty("user.dir") + templatePath,
                new FileFilter() {
                    public boolean accept(File pathname) {
                        // 有后缀就处理
                        return pathname.getName().indexOf(".html") > 0;
                    }
                });


        for (String name : htmlList) {
            System.out.println("JsonKit.toJson(tables) = " + JsonKit.toJson(tables));
            String html = TemplateUtils.getStr(templatePath + name, "items", tables);
            System.out.println("html = " + html);
            // 文件名处理
            String fileName = "ques.html";
            FileUtils.write(Paths.get(webPath, fileName).toString(), html.getBytes("UTF-8"));
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
