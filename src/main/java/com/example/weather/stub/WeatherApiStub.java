package com.example.weather.stub;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.example.weather.api.WeatherApiEndpoints;

public class WeatherApiStub {
    private final WireMock wireMock;

    public WeatherApiStub() {
        this.wireMock = new WireMock("localhost", WireMockConfig.getPort());
    }

    public void stubCurrentWeather(String city, String responseFile) {
        wireMock.register(WireMock.get(WireMock.urlPathEqualTo(WeatherApiEndpoints.CURRENT_WEATHER))
                .withQueryParam("q", WireMock.equalTo(city))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile(responseFile)));
    }

    public void stubErrorResponse(int statusCode, int errorCode, String errorMessage) {
        String errorJson = String.format("{\"error\":{\"code\":%d,\"message\":\"%s\"}}",
                errorCode, errorMessage);

        wireMock.register(WireMock.get(WireMock.urlPathEqualTo(WeatherApiEndpoints.CURRENT_WEATHER))
                .willReturn(WireMock.aResponse()
                        .withStatus(statusCode)
                        .withHeader("Content-Type", "application/json")
                        .withBody(errorJson)));
    }
}