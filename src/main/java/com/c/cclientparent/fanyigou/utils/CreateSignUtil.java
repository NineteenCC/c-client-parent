package com.c.cclientparent.fanyigou.utils;

import com.c.cclientparent.fanyigou.constants.FanYiGouBaseURL;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 拼装参数校验
 * 
 * @author Administrator
 * 
 */
public class CreateSignUtil {

	private CreateSignUtil() {
	}

	/**
	 * 除去数组中的空值和签名参数
	 * 
	 * @param sArray
	 *            签名参数组
	 * @return 去掉空值与签名参数后的新签名参数组
	 */
	public static Map<String, String> paraFilter(Map<String, String> sArray) {

		Map<String, String> result = new ConcurrentHashMap<>();

		if (sArray == null || sArray.size() == 0) {
			return result;
		}

		for (String key : sArray.keySet()) {
			String value = sArray.get(key);
			if (value == null || "".equals(value)
					|| "token".equalsIgnoreCase(key)) {
				continue;
			}
			result.put(key, value);
		}

		return result;
	}

	/**
	 * 把数组所有元素，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要参与字符拼接的参数组
	 * @param sorts
	 *            是否需要排序 true 或者 false
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, String> params) {

		List<String> keys = new ArrayList<>(params.keySet());

		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);
			// 拼接时，不包括最后一个&字符
			if (i == keys.size() - 1) {
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}
		System.out.println("params：" + prestr);
		return prestr;
	}
	/**
	 * 生成签名
	 * @throws UnsupportedEncodingException 
	 */
	public static String createSign(Map<String,String> params) throws UnsupportedEncodingException{
		String sign = null;
		params.put("privatekey", FanYiGouBaseURL.SECRET);
		params.put("appid", FanYiGouBaseURL.APPID);
		// 过滤空值、token参数
		params = paraFilter(params);
		sign = MD5.md5(CreateSignUtil.createLinkString(params)).toUpperCase();
		System.out.println("sign:" + sign);
		return sign;
	}
}
