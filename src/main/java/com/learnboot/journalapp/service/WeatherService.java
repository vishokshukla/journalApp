package com.learnboot.journalapp.service;

import com.learnboot.journalapp.api.response.MiniWeatherResponse;
import com.learnboot.journalapp.cache.AppCache;
import com.learnboot.journalapp.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;

    public MiniWeatherResponse getWeather(String city) {
        MiniWeatherResponse weatherResponse = redisService.getValue("weather_of_" + city, MiniWeatherResponse.class);
        if (weatherResponse == null) {
            String finalAPI = appCache.appCache.get(AppCache.keys.WEATHER_API.name()).replace(Placeholders.API_KEY, apiKey).replace(Placeholders.CITY, city);
            ResponseEntity<MiniWeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, MiniWeatherResponse.class);
            MiniWeatherResponse body = response.getBody();
            if (body != null) {
                redisService.setValue("weather_of_" + city, body, 300l);
            }
            return body;
        } else
            return weatherResponse;
    }

}
