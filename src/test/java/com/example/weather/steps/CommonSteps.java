package com.example.weather.steps;

import com.example.weather.stub.WireMockConfig;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.AfterStories;

public class CommonSteps {
    @BeforeStories
    public void setUp() {
        WireMockConfig.start();
    }

    @AfterStories
    public void tearDown() {
        WireMockConfig.stop();
    }
}