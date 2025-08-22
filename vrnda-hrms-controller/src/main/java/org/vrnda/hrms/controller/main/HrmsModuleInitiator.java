package org.vrnda.hrms.controller.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = { "org.vrnda.hrms.*" })
@EntityScan(basePackages = { "org.vrnda.hrms.*" })
@EnableJpaRepositories(basePackages = { "org.vrnda.hrms.*" })
@EnableTransactionManagement
@CrossOrigin(origins = "*")
public class HrmsModuleInitiator {

	public static void main(String[] args) {

		SpringApplication.run(HrmsModuleInitiator.class);

	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*");
			}
		};
	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate;
	}

}
