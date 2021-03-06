package com.jflyfox.autocreate.db;

import java.sql.Types;
import java.util.HashMap;

/**
 * 
 * @author badqiu
 * @email badqiu(a)gmail.com
 */
public class DbDataTypesUtils {

	private final static HashMap<Integer, Object> TYPE_MAP = new HashMap<>();
	private final static HashMap<Integer, String> SWAGGER_TYPE_MAP = new HashMap<>();

	public static boolean isFloatNumber(String javaType) {
		if (javaType.endsWith("Float") || javaType.endsWith("Double") || javaType.endsWith("BigDecimal")
				|| javaType.endsWith("BigInteger")) {
			return true;
		}
		return javaType.endsWith("float") || javaType.endsWith("double") || javaType.endsWith("BigDecimal")
				|| javaType.endsWith("BigInteger");
	}

	public static boolean isIntegerNumber(String javaType) {
		if (javaType.endsWith("Long") || javaType.endsWith("Integer") || javaType.endsWith("Short")
				|| javaType.endsWith("Byte")) {
			return true;
		}
		return javaType.endsWith("long") || javaType.endsWith("int") || javaType.endsWith("short")
				|| javaType.endsWith("byte");
	}

	public static boolean isDate(String javaType) {
		return javaType.endsWith("Date") || javaType.endsWith("Timestamp") || javaType.endsWith("Time");
	}

	public static boolean isTimeStamp(String javaType) {
		return javaType.endsWith("Timestamp");
	}

	public static boolean isString(String javaType) {
		return javaType.endsWith("String");
	}

	public static String getSwaggerType(int sqlType) {
		return SWAGGER_TYPE_MAP.get(sqlType);
	}

	public static String getPreferredJavaType(int sqlType, int size, int decimalDigits) {
		if ((sqlType == Types.DECIMAL || sqlType == Types.NUMERIC) && decimalDigits == 0) {
			if (size == 1) {
				// https://sourceforge.net/tracker/?func=detail&atid=415993&aid=662953&group_id=36044
				return "java.lang.Boolean";
//			全部都改成int类型
//			} else if (size < 3) {
//				return "java.lang.Byte";
//			} else if (size < 5) {
//				return "java.lang.Short";
			} else if (size < 10) {
				return "java.lang.Integer";
			} else if (size < 19) {
				return "java.lang.Long";
			} else {
				return "java.math.BigDecimal";
			}
		}
		String result = (String) TYPE_MAP.get(sqlType);
		if (result == null) {
			result = "java.lang.Object";
		}
		return result;
	}

	static {
//		TYPE_MAP.put(Types.TINYINT, "java.lang.Byte");
		TYPE_MAP.put(Types.TINYINT, "java.lang.Integer");
//		TYPE_MAP.put(Types.SMALLINT, "java.lang.Short");
		TYPE_MAP.put(Types.SMALLINT, "java.lang.Integer");
		TYPE_MAP.put(Types.INTEGER, "java.lang.Integer");
		TYPE_MAP.put(Types.BIGINT, "java.lang.Long");
		TYPE_MAP.put(Types.REAL, "java.lang.Float");
		TYPE_MAP.put(Types.FLOAT, "java.lang.Double");
		TYPE_MAP.put(Types.DOUBLE, "java.lang.Double");
		TYPE_MAP.put(Types.DECIMAL, "java.math.BigDecimal");
		TYPE_MAP.put(Types.NUMERIC, "java.math.BigDecimal");
		TYPE_MAP.put(Types.BIT, "java.lang.Boolean");
		TYPE_MAP.put(Types.BOOLEAN, "java.lang.Boolean");
		TYPE_MAP.put(Types.CHAR, "java.lang.String");
		TYPE_MAP.put(Types.VARCHAR, "java.lang.String");
		TYPE_MAP.put(Types.LONGVARCHAR, "java.lang.String");
		TYPE_MAP.put(Types.BINARY, "byte[]");
		TYPE_MAP.put(Types.VARBINARY, "byte[]");
		TYPE_MAP.put(Types.LONGVARBINARY, "byte[]");
		TYPE_MAP.put(Types.DATE, "java.sql.Date");
		TYPE_MAP.put(Types.TIME, "java.sql.Time");
		TYPE_MAP.put(Types.TIMESTAMP, "java.sql.Timestamp");
		TYPE_MAP.put(Types.CLOB, "java.sql.Clob");
		TYPE_MAP.put(Types.BLOB, "java.sql.Blob");
		TYPE_MAP.put(Types.ARRAY, "java.sql.Array");
		TYPE_MAP.put(Types.REF, "java.sql.Ref");
		TYPE_MAP.put(Types.STRUCT, "java.lang.Object");
		TYPE_MAP.put(Types.JAVA_OBJECT, "java.lang.Object");

		SWAGGER_TYPE_MAP.put(Types.TINYINT, "integer");
//		TYPE_MAP.put(Types.SMALLINT, "java.lang.Short");
		SWAGGER_TYPE_MAP.put(Types.SMALLINT, "integer");
		SWAGGER_TYPE_MAP.put(Types.INTEGER, "integer");
		SWAGGER_TYPE_MAP.put(Types.BIGINT, "long");
		SWAGGER_TYPE_MAP.put(Types.REAL, "float");
		SWAGGER_TYPE_MAP.put(Types.FLOAT, "double");
		SWAGGER_TYPE_MAP.put(Types.DOUBLE, "double");
		SWAGGER_TYPE_MAP.put(Types.DECIMAL, "integer");
		SWAGGER_TYPE_MAP.put(Types.NUMERIC, "integer");
		SWAGGER_TYPE_MAP.put(Types.BIT, "boolean");
		SWAGGER_TYPE_MAP.put(Types.BOOLEAN, "boolean");
		SWAGGER_TYPE_MAP.put(Types.CHAR, "string");
		SWAGGER_TYPE_MAP.put(Types.VARCHAR, "string");
		SWAGGER_TYPE_MAP.put(Types.LONGVARCHAR, "string");
		SWAGGER_TYPE_MAP.put(Types.BINARY, "string");
		SWAGGER_TYPE_MAP.put(Types.VARBINARY, "string");
		SWAGGER_TYPE_MAP.put(Types.LONGVARBINARY, "string");
		SWAGGER_TYPE_MAP.put(Types.DATE, "string");
		SWAGGER_TYPE_MAP.put(Types.TIME, "string");
		SWAGGER_TYPE_MAP.put(Types.TIMESTAMP, "string");
		SWAGGER_TYPE_MAP.put(Types.CLOB, "string");
		SWAGGER_TYPE_MAP.put(Types.BLOB, "string");
		SWAGGER_TYPE_MAP.put(Types.ARRAY, "string");
		SWAGGER_TYPE_MAP.put(Types.REF, "string");
		SWAGGER_TYPE_MAP.put(Types.STRUCT, "string");
		SWAGGER_TYPE_MAP.put(Types.JAVA_OBJECT, "string");
	}

}
