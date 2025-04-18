package com.example.weather.stub;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

public class WireMockConfig {
    private static WireMockServer wireMockServer;

    public static void start() {
        if (wireMockServer == null || !wireMockServer.isRunning()) {
            wireMockServer = new WireMockServer(WireMockConfiguration.options().dynamicPort());
            wireMockServer.start();
        }
    }

    public static void stop() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    public static int getPort() {
        return wireMockServer.port();
    }
}