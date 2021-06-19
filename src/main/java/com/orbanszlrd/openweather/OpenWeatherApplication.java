package com.orbanszlrd.openweather;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class OpenWeatherApplication {
	private static final Logger logger = LoggerFactory.getLogger(OpenWeatherApplication.class);

	private static final String appid = System.getenv("appid");
	private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
	private static String lang = "en";
	private static String unit = "metric";
	private static String q = System.getenv("q") !=  null ? System.getenv("q") : "Budapest";

	public static void main(String[] args) {
		SpringApplication.run(OpenWeatherApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) {
		return args -> {
			Object data = restTemplate.getForObject(BASE_URL +
					"weather?q=" + q + "&units=" + unit + "&lang="+ lang + "&appid=" + appid, Object.class);

			if (data != null) {
				logger.info(data.toString());
			}
		};
	}
}
