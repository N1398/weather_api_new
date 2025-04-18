package com.example.weather.steps;

import com.example.weather.stub.WireMockConfig;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.AfterStories;

import java.util.Map;
import java.util.Objects;

public class CommonSteps {
    @BeforeStories
    public void setUp() {
        WireMockConfig.start();
    }

    @AfterStories
    public void tearDown() {
        WireMockConfig.stop();
    };
    public static void compareValues(String field, String expected, String actual, Map<String, String> differences) {
        if (!Objects.equals(expected, actual)) {
            differences.put(field, String.format("expected '%s' but was '%s'", expected, actual));
        }
    }
}