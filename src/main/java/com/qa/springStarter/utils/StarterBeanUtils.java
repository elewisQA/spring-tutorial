package com.qa.springStarter.utils;

import org.springframework.beans.BeanUtils;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StarterBeanUtils {

	public static void mergeObject(Object source, Object target) {
		BeanUtils.copyProperties(source, target);
	}
	
}
