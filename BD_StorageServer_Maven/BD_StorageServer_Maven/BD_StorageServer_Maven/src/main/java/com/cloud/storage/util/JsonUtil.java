package com.cloud.storage.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * json操作类
 */
public class JsonUtil {
	public static boolean isBadJson(String json) {
		return !isGoodJson(json);
	}

	public static boolean isGoodJson(String json) {
		if (StringUtils.isBlank(json)) {
			return false;
		}
		try {
			JSONObject.fromObject(json);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		System.out.println(isGoodJson("ww"));
		System.out.println(isBadJson("ww"));
	}

	/**
	 * 功能描述:传入一个 javabean对象生成一个json格式的字符串
	 * 
	 * @param bean
	 * @return String
	 */
	public static String beanToJson(Object bean) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		PropertyDescriptor[] props = null;
		try {
			props = Introspector.getBeanInfo(bean.getClass(), Object.class).getPropertyDescriptors();
		} catch (IntrospectionException e) {
		}
		if (props != null) {
			for (int i = 0; i < props.length; i++) {
				try {
					String name = objectToJson(props[i].getName());
					String value = objectToJson(props[i].getReadMethod().invoke(bean));
					json.append(name);
					json.append(":");
					json.append(value);
					json.append(",");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}
		return json.toString();
	}

	/**
	 * 功能描述:传入任意一个 Object对象生成一个json格式的字符串
	 * 
	 * @param object
	 *            任意对象
	 * @return String
	 */
	@SuppressWarnings("rawtypes")
	public static String objectToJson(Object object) {
		StringBuilder json = new StringBuilder();
		if (object == null) {
			json.append("\"\"");// 输出双引号
		} else if (object instanceof String || object instanceof Integer || object instanceof Long
				|| object instanceof Boolean || object instanceof Date || object instanceof java.sql.Date) {
			json.append("\"").append(object.toString()).append("\"");
		} else if (object instanceof List) {
			json.append(listToJson((List) object));
		} else {
			json.append(beanToJson(object));
		}
		return json.toString();
	}

	/**
	 * 功能描述:通过传入一个列表对象,调用指定方法将列表中的数据生成一个JSON规格指定字符串
	 * 
	 * @param list
	 *            列表对象
	 * @return String
	 */
	public static String listToJson(List<?> list) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (list != null && list.size() > 0) {
			for (Object obj : list) {
				json.append(objectToJson(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	public static JSONArray getJsonParamterArray(JSONObject jo, String field) {
		JSONArray re_str = new JSONArray();
		if (jo != null && jo.containsKey(field)) {
			re_str = jo.getJSONArray(field);
		}
		return re_str;
	}

	public static JSONObject getJsonParamterObject(JSONObject jo, String field) {
		JSONObject re_str = new JSONObject();
		if (jo != null && jo.containsKey(field)) {
			re_str = jo.getJSONObject(field);
		}
		return re_str;
	}

	public static String getJsonParamterString(JSONObject jo, String field) {
		String re_str = "";
		if (jo != null && jo.containsKey(field)) {
			re_str = jo.getString(field);
		}
		return re_str;
	}

	public static int getJsonParamterInteger(JSONObject jo, String field) {
		int re_str = 0;
		if (jo != null && jo.containsKey(field)) {
			re_str = jo.getInt(field);
		}
		return re_str;
	}

	public static long getJsonParamterLong(JSONObject jo, String field) {
		long re_str = 0l;
		if (jo != null && jo.containsKey(field)) {
			re_str = jo.getLong(field);
		}
		return re_str;
	}

	public static double getJsonParamterDouble(JSONObject jo, String field) {
		double re_str = 0d;
		if (jo != null && jo.containsKey(field)) {
			re_str = jo.getDouble(field);
		}
		return re_str;
	}

	public static JSONArray addEntryToJsonArray(JSONArray paramJSONArray, String paramString1, String paramString2) {
		JSONObject localJSONObject = new JSONObject();
		localJSONObject.put(paramString1, paramString2);
		paramJSONArray.add(localJSONObject);
		return paramJSONArray;
	}

	public static JSONArray addEntryToJsonArray(JSONArray paramJSONArray, int paramInt, String paramString1,
			String paramString2) {
		JSONObject localJSONObject = new JSONObject();
		localJSONObject.put(paramString1, paramString2);
		paramJSONArray.add(paramInt, localJSONObject);
		return paramJSONArray;
	}
}
