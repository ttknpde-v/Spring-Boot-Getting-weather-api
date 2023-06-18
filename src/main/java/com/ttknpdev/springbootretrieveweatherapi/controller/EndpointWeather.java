package com.ttknpdev.springbootretrieveweatherapi.controller;

import com.ttknpdev.springbootretrieveweatherapi.service.WeatherApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api-weather")
public class EndpointWeather {
    private WeatherApiService weatherApiService;
    @Autowired
    public EndpointWeather(WeatherApiService weatherApiService) {
        this.weatherApiService = weatherApiService;
    }
    @GetMapping(value = "/{city}&{country}")
    private ResponseEntity getWeatherTwiceRequest(@PathVariable String city , @PathVariable String country) throws Exception {
        weatherApiService.setCityAndCountry(city, country);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(weatherApiService.getWeather());
    }
    @GetMapping(value = "/{city}")
    private ResponseEntity getWeatherOnceRequest(@PathVariable String city) throws IOException {
        weatherApiService.setCityAndCountry(city, " ");
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(weatherApiService.getWeather());
    }

}
