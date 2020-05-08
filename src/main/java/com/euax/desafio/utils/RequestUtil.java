package com.euax.desafio.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.HandlerMapping;

public class RequestUtil {
	
	public static Integer getIntegerURLParamFromRequest(HttpServletRequest request, String paramName, Integer defaultValue) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String> ) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		
		System.out.println(request.getMethod()); 
		
		Integer integerParam = defaultValue; 
		
		if( map == null || map.size() == 0)
			return integerParam;
			
		try {

			integerParam = Integer.parseInt(map.get(paramName));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return integerParam;
	}

}
