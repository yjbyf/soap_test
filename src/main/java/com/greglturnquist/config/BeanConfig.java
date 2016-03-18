package com.greglturnquist.config;


import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.greglturnquist.filter.SimpleCORSFilter;

@Configuration
public class BeanConfig {
	// @Autowired
	// private Filter securityFilter;
	// 定义filter的优先级
	// 跨域提交控制
	@Bean
	public FilterRegistrationBean corsFilterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		SimpleCORSFilter simpleCORSFilter = new SimpleCORSFilter();
		registrationBean.setFilter(simpleCORSFilter);
		registrationBean.setOrder(1);
		return registrationBean;
	}



}
