package com.wese.weseaddons.interceptor;


import com.wese.weseaddons.crb.error.NotSubscribedToCrbException;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WeseInterceptorConfig implements WebMvcConfigurer {


	@Override
	public void addInterceptors(InterceptorRegistry interceptorRegistry){

		interceptorRegistry.addInterceptor(new BereauDeChangeInterceptor()).addPathPatterns("/bereaudechange/*");
		//interceptorRegistry.addInterceptor(new CrbInterceptor()).addPathPatterns("/crb/*");
		//interceptorRegistry.addInterceptor(new NotSubscribedToCrbException()).addPathPatterns("/crb/*");
		interceptorRegistry.addInterceptor(new FineractInterceptor()).addPathPatterns("/**");

	}

}