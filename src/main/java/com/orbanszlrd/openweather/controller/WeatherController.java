package com.orbanszlrd.openweather.controller;

import com.orbanszlrd.openweather.model.CurrentWeather;
import com.orbanszlrd.openweather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping("")
    public String index() {
        return "redirect:/weather/current";
    }

    @GetMapping({"/current", "/current/{location}"})
    public String currentWeather(@PathVariable(required = false) String location, Model model) {
        CurrentWeather data = weatherService.current(location);

        if (data != null) {
            model.addAttribute("data", data);
            model.addAttribute("location", location);

            return "current-weather";
        } else {
            return "error";
        }
    }

    @GetMapping({"/current/"})
    public String searchCurrentWeather(@RequestParam(required = false) String location, Model model) {
        CurrentWeather data = weatherService.current(location);

        if (data != null) {
            model.addAttribute("data", data);
            model.addAttribute("location", location);

            return "redirect:/weather/current/" + location;
        } else {
            return "error";
        }
    }
}
