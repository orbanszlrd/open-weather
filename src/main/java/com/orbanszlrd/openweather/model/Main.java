package com.orbanszlrd.openweather.model;

import lombok.Data;

@Data
public class Main {
    private float temp;
    private float feels_like;
    private float temp_min;
    private float temp_max;
    private int pressure;
    private int humidity;
    private int sea_level;
    private int grnd_level;
}
