package com.ht.card.Util;

import java.text.DecimalFormat;

public class StringUtil {

	
	/**
	 * 格式化数字为三位一逗并保留指定位数小数
	 * @param value Number类型或toString返回数字类型字符串的对象
	 * @param scale 小数位保留的有效位数
	 * @return
	 */
	public static String number(Object value, int scale){
		if(value == null) return "";
		// value形参类型不能直接是Number，否则EL表达式${}会将null强制转换为0的Number对象
		if(!(value instanceof Number)){
			value = Double.valueOf(value.toString());
		}
		String format = "#,##0";
		for(int i = 0; i < scale; i++){
			if(i == 0) format += ".";
			format += "0";
		}
		return new DecimalFormat(format).format(value);
	}
	
	/**
	 * 判断字符串是否为空或为null
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || "".equals(str);
	}
	
	/**
	 * 判断字符串是否都是空白字符
	 * @param str
	 * @return
	 */
	public static boolean isSpace(String str) {
		if (str == null) {
			return true;
		} else {
			boolean r = true;

			for (int i = 0; i < str.length(); ++i) {
				if (!Character.isSpaceChar(str.charAt(i))) {
					r = false;
					break;
				}
			}

			return r;
		}
	}

	//判断数组是否存在空值
	public static boolean strsEmplty(String[] strs){
		for(String str:strs){
			if(isEmpty(str)){
				return true;
			}else{
				continue;
			}
		}
		return false;
	}
}
