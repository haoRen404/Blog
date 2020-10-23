package com.lcy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

}

//@SpringBootApplication
//class RegisteradminApplication extends SpringBootServletInitializer implements WebApplicationInitializer {
//	public static void main(String[] args) {
//		SpringApplication.run(RegisteradminApplication.class, args);
//	}
//
//	// 重写configure方法
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
//		return application.sources(RegisteradminApplication.class);
//	}
//
//}
