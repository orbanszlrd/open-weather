package com.orbanszlrd.openweather.model;

import lombok.Data;

@Data
public class Wind {
    private float speed;
    private int deg;
    private float gust;
}
