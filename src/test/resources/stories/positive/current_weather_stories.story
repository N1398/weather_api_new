Meta:
@story Current weather positive tests

Scenario: Get current weather for Osaka
Given a weather API client with base URL 'http://localhost:${wiremock.port}' and API key 'test-key'
Given WireMock is configured to return current weather for 'Osaka' from file 'osaka_response.json'
When I request current weather for 'Osaka'
Then the response status code should be 200
And the weather data should match expected values:
| location.name       | Osaka           |
| location.country    | Japan           |
| current.temp_c      | 22.0            |
| current.condition.text | Partly cloudy |

Scenario: Get current weather for Toronto
Given a weather API client with base URL 'http://localhost:${wiremock.port}' and API key 'test-key'
Given WireMock is configured to return current weather for 'Toronto' from file 'toronto_response.json'
When I request current weather for 'Toronto'
Then the response status code should be 200
And the weather data should match expected values:
| location.name       | Toronto         |
| location.country    | Canada          |
| current.temp_c      | 18.5            |
| current.condition.text | Sunny         |