package com.orbanszlrd.openweather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentWeather {
    private Coord coord;
    private List<Weather> weather;
    private String base;
    private Main main;
    private int visibility;
    private Wind wind;
    private Clouds clouds;
    private int dt;
    private Sys sys;
    private int timezone;
    private int id;
    private String name;
    private int cod;

    @Getter
    private static class Clouds {
        private String all;
    }

    @Getter
    public static class Coord {
        private float lon;
        private float lat;
    }

    @Getter
    private static class Main {
        private float temp;
        private float feels_like;
        private float temp_min;
        private float temp_max;
        private int pressure;
        private int humidity;
        private int sea_level;
        private int grnd_level;
    }

    @Getter
    private static class Sys {
        private int type;
        private int id;
        private  String country;
        private int sunrise;
        private int sunset;
    }

    @Getter
    private static class Weather {
        private int id;
        private String main;
        private String description;
        private String icon;
    }

    @Getter
    private static class Wind {
        private float speed;
        private int deg;
        private float gust;
    }
}
