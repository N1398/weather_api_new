package com.example.weather.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class WeatherApiClient {
    private final RequestSpecification spec;

    public WeatherApiClient(String baseUrl, String apiKey) {
        this.spec = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .addQueryParam(WeatherApiEndpoints.API_KEY_PARAM, apiKey)
                .build();
    }

    public RequestSpecification getSpec() {
        return spec;
    }
}