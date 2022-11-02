package com.wese.weseaddons;

import com.wese.weseaddons.bereaudechange.helper.OffshoreThread;
import com.wese.weseaddons.bereaudechange.session.FxInit;
import com.wese.weseaddons.bereaudechange.session.FxSession;

import com.wese.weseaddons.helper.Constants;
import com.wese.weseaddons.helper.FileHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class WeseaddonsApplication extends SpringBootServletInitializer{



	public static void main(String[] args) {
		SpringApplication.run(WeseaddonsApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(WeseaddonsApplication.class);
	}

	@Bean
	public Docket productApi(){
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage(Constants.basePackage)).build();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/*").allowedOrigins("*");
			}
		};
	}


	@Bean
	CommandLineRunner init(){

		return args ->{		
			Runnable runnable = ()->{
				FxSession.getInstance();	
			};
			OffshoreThread.run(runnable);
		};
	}

}
