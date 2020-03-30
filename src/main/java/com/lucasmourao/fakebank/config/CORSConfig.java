package com.lucasmourao.fakebank.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class CORSConfig {
	
	public void addCorsMapping(CorsRegistry registry) {
		registry.addMapping("/**").allowedMethods("PUT","GET","POST","DELETE","OPTIONS");
	}

}
