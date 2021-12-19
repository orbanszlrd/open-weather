package com.orbanszlrd.openweather.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orbanszlrd.openweather.model.CurrentWeather;
import com.orbanszlrd.openweather.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URL;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = WeatherController.class)
@AutoConfigureMockMvc(addFilters = false)
class WeatherControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WeatherService weatherService;

    private CurrentWeather currentWeather;

    @Test
    void index_should_redirect_to_weather_current() throws Exception {
        mockMvc.perform(get("/weather")).andDo(print())
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/weather/current"));
    }

    @Test
    void currentWeather_should_return_the_correct_data() throws Exception {
        currentWeather = objectMapper.readValue(new URL("file:src/test/resources/current_weather.json"), CurrentWeather.class);
        when(weatherService.current(currentWeather.getName())).thenReturn(currentWeather);

        mockMvc.perform(get("/weather/current/Lagos")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(currentWeather.getName())))
                .andExpect(content().string(containsString(String.valueOf(currentWeather.getCoord().getLat()))))
                .andExpect(content().string(containsString(String.valueOf(currentWeather.getCoord().getLon()))));
        ;
    }

    @Test
    void searchCurrentWeather_should_redirect_to_the_correct_url() throws Exception {
        currentWeather = objectMapper.readValue(new URL("file:src/test/resources/current_weather.json"), CurrentWeather.class);
        when(weatherService.current(currentWeather.getName())).thenReturn(currentWeather);

        String location = "Lagos";

        mockMvc.perform(get("/weather/current/?location=" + location)).andDo(print())
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/weather/current/" + location));
        ;
    }

    @Test
    void searchCurrentWeather_should_return_the_error_page() throws Exception {
        String location = "NonExistingLocation";

        mockMvc.perform(get("/weather/current/?location=" + location)).andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().string(containsString("Page Not Found")))
        ;
    }
}