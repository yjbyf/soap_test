package com.greglturnquist.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

//@Component
public class SimpleCORSFilter implements Filter {

	Logger logger = Logger.getLogger(SimpleCORSFilter.class);

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		logger.error("filter--->SimpleCORSFilter");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods","GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
		response.setHeader("Access-Control-Max-Age", "3600");
		//response.setHeader("Access-Control-Allow-Headers", "x-requested-with, content-Type, accept, authorization");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with, content-Type, accept, authorization");
		//return;
		chain.doFilter(req, res);


	}

	public void init(FilterConfig filterConfig) {
	}

	public void destroy() {
	}


}
