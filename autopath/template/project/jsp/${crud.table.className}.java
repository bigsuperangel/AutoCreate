package com.jflyfox.modules.@{crud.urlKey};

import com.jflyfox.jfinal.base.BaseModel;
import com.jflyfox.jfinal.component.annotation.ModelBind;

@ModelBind(table = "@{crud.table.tableName}")
public class @{crud.table.className} extends BaseModel<@{crud.table.className}> {

	private static final long serialVersionUID = 1L;
	public static final @{crud.table.className} dao = new @{crud.table.className}();

    //columns START
	# for(column in crud.table.columns){ #
    private String @{strutils.toUpperCase(column.columnName)} = "@{strutils.toLowerCase(column.columnName)}";  // @{column.remarks}
    # } #
    
    # for(column in crud.table.columns){ #
    public @{crud.table.className} set@{strutils.toUpperCaseFirst(column.columnJavaName)}(@{column.javaType} value) {
        set(@{strutils.toUpperCase(column.columnName)}, value);
        return this;
    }

	public @{column.javaType} get@{strutils.toUpperCaseFirst(column.columnJavaName)}() {
		return get(@{strutils.toUpperCase(column.columnName)});
	}
	# } #
}