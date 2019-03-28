package com.iwhalecloud.retail.web.utils;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class UserAgentUtil {
	
	/**
	 * 判断是否是手机端
	 * @param request
	 * @return
	 */
	public static boolean isMobile(HttpServletRequest request) {
		List<String> mobileAgents = Arrays.asList("ipad", "iphone os",
				"rv:1.2.3.4", "ucweb", "android", "windows ce",
				"windows mobile");
		String ua = request.getHeader("User-Agent").toLowerCase();
		for (String sua : mobileAgents) {
			if (ua.indexOf(sua) > -1) {
				return true;// 手机端
			}
		}
		return false;// PC端
	}

	/**
	 * 判断释放是微信浏览器
	 * @param request
	 * @return
	 */
	public static boolean isWechat(HttpServletRequest request) {
		String ua = request.getHeader("User-Agent").toLowerCase();
		if (ua.indexOf("micromessenger") > -1) {
			return true;// 微信
		}
		return false;// 非微信手机浏览器

	}

}
