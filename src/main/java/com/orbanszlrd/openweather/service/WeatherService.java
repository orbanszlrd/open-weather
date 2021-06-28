package com.orbanszlrd.openweather.service;

import com.orbanszlrd.openweather.OpenWeatherApplication;
import com.orbanszlrd.openweather.model.CurrentWeather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class WeatherService {
    @Autowired
    RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(OpenWeatherApplication.class);

    private static final String appid = System.getenv("appid");
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private static String lang = "en";
    private static String unit = "metric";
    private static String q = System.getenv("q") !=  null ? System.getenv("q") : "Budapest";

    @Cacheable("weather.current")
    public CurrentWeather current(String location) {
        System.out.println("Called at " + Timestamp.from(Instant.now()));

        if (location != null) {
            q = location;
        }

        CurrentWeather data = null;

        try {
            data = restTemplate.getForObject(BASE_URL +
                    "weather?q=" + q + "&units=" + unit + "&lang="+ lang + "&appid=" + appid, CurrentWeather.class);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        if (data != null) {
            logger.info(data.toString());
        } else {
            logger.warn("No data");
        }

        return data;
    }

    @Scheduled(fixedDelay = 60 * 60 * 1000)
    @CacheEvict(cacheNames = {"weather.current"}, allEntries = true)
    public void clearCache() {
        logger.info("Clear cache" + Timestamp.from(Instant.now()));
    }
}
