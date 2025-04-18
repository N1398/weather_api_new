package com.example.weather.steps;

import com.example.weather.api.WeatherApiEndpoints;
import io.restassured.response.Response;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.example.weather.api.WeatherApiClient;
import com.example.weather.dto.CurrentWeatherResponse;
import com.example.weather.stub.WeatherApiStub;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.weather.steps.CommonSteps.compareValues;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class PositiveTestSteps {
    private static final Logger logger = LogManager.getLogger(PositiveTestSteps.class);
    private WeatherApiClient apiClient;
    private Response response;
    private CurrentWeatherResponse weatherResponse;

    @Given("a weather API client with base URL $baseUrl and API key $apiKey")
    public void setupApiClient(String baseUrl, String apiKey) {
        apiClient = new WeatherApiClient(baseUrl, apiKey);
    }

    @Given("WireMock is configured to return current weather for $city from file $responseFile")
    public void setupWireMockStub(String city, String responseFile) {
        new WeatherApiStub().stubCurrentWeather(city, responseFile);
    }

    @When("I request current weather for $city")
    public void requestCurrentWeather(String city) {
        response = given()
                .spec(apiClient.getSpec())
                .queryParam("q", city)
                .when()
                .get(WeatherApiEndpoints.CURRENT_WEATHER);

        weatherResponse = response.as(CurrentWeatherResponse.class);
    }

    @Then("the response status code should be $statusCode")
    public void verifyStatusCode(int statusCode) {
        assertThat(response.getStatusCode()).isEqualTo(statusCode);
    }

    @Then("the weather data should match expected values:")
    public void verifyWeatherData(Map<String, String> expectedValues) {
        Map<String, String> differences = new HashMap<>();

        // Проверка location
        compareValues("location.name", expectedValues.get("location.name"),
                weatherResponse.getLocation().getName(), differences);
        compareValues("location.country", expectedValues.get("location.country"),
                weatherResponse.getLocation().getCountry(), differences);

        // Проверка current
        compareValues("current.temp_c", expectedValues.get("current.temp_c"),
                String.valueOf(weatherResponse.getCurrent().getTempC()), differences);
        compareValues("current.condition.text", expectedValues.get("current.condition.text"),
                weatherResponse.getCurrent().getCondition().getText(), differences);

        // Логирование различий
        if (!differences.isEmpty()) {
            logger.warn("Found differences for {}:", weatherResponse.getLocation().getName());
            differences.forEach((field, diff) ->
                    logger.warn("Field {}: {}", field, diff));
        }

        assertThat(differences).isEmpty();
    }

}