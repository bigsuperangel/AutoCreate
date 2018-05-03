package conf.auto.project.beetl;

@ModelBind(table = "@{crud.table.tableName}")
public class @{crud.table.className} extends BaseModel<@{crud.table.className}> {

	private static final long serialVersionUID = 1L;
	public static final @{crud.table.className} dao = new @{crud.table.className}();

}