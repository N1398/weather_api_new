package com.example.weather.steps;

import com.example.weather.api.WeatherApiEndpoints;
import io.restassured.response.Response;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.When;
import org.jbehave.core.annotations.Then;
import com.example.weather.api.WeatherApiClient;
import com.example.weather.dto.ErrorResponse;
import com.example.weather.stub.WeatherApiStub;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class NegativeTestSteps {
    private WeatherApiClient apiClient;
    private Response response;
    private ErrorResponse errorResponse;

    @Given("a weather API client with base URL $baseUrl and invalid API key $apiKey")
    public void setupApiClientWithInvalidKey(String baseUrl, String apiKey) {
        apiClient = new WeatherApiClient(baseUrl, apiKey);
    }

    @Given("WireMock is configured to return error with status $statusCode, code $errorCode and message $errorMessage")
    public void setupErrorStub(int statusCode, int errorCode, String errorMessage) {
        new WeatherApiStub().stubErrorResponse(statusCode, errorCode, errorMessage);
    }

    @When("I make an invalid request without query parameter")
    public void makeInvalidRequestWithoutQueryParam() {
        response = given()
                .spec(apiClient.getSpec())
                .when()
                .get(WeatherApiEndpoints.CURRENT_WEATHER);

        errorResponse = response.as(ErrorResponse.class);
    }

    @Then("the error response should have status $statusCode, code $errorCode and message $errorMessage")
    public void verifyErrorResponse(int statusCode, int errorCode, String errorMessage) {
        assertThat(response.getStatusCode()).isEqualTo(statusCode);
        assertThat(errorResponse.getError().getCode()).isEqualTo(errorCode);
        assertThat(errorResponse.getError().getMessage()).isEqualTo(errorMessage);
    }
}