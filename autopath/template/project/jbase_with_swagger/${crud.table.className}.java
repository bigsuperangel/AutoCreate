package com.jflyfox.modules.@

{crud.urlKey};

/**
 * <p>Description:@{crud.table.remarks}</p>
 * @author linyu
 * @create @{date()}
 */
@ModelBind(table = "@{crud.table.tableName}", remarks="@{crud.table.remarks}")
public class @{crud.table.className} extends BaseProjectModel<@{crud.table.className}> {

	private static final long serialVersionUID = 1L;
	public static final @{crud.table.className} dao = new @{crud.table.className}();

    //columns START
	# for(column in crud.table.columns){ #
	/** @{column.remarks} **/
	private String @{strutils.toUpperCase(column.columnName)} = "@{strutils.toLowerCase(column.columnName)}";
	# } #

	// method START
    # for(column in crud.table.columns){ #
	/** @{column.remarks} **/
    public @{crud.table.className} set@{strutils.toUpperCaseFirst(column.columnJavaName)}(@{column.javaType} value) {
        set(@{strutils.toUpperCase(column.columnName)}, value);
        return this;
    }

	/** @{column.remarks} **/
	public @{column.javaType} get@{strutils.toUpperCaseFirst(column.columnJavaName)}() {
		return get(@{strutils.toUpperCase(column.columnName)});
	}
	# } #
	
	@Override
	public String toString() {
		String log = ""; 
	# for(column in crud.table.columns){ #
		log += "[@{strutils.toLowerCaseFirst(column.columnJavaName)}:" + get@{strutils.toUpperCaseFirst(column.columnJavaName)}() + "]";
	# } #
		return log;
	}
}