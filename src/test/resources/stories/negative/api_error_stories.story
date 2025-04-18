Meta:
@story API error negative tests

Scenario: Missing query parameter
Given a weather API client with base URL 'http://localhost:${wiremock.port}' and API key 'test-key'
Given WireMock is configured to return error with status 400, code 1003 and message 'Parameter 'q' not provided.'
When I make an invalid request without query parameter
Then the error response should have status 400, code 1003 and message 'Parameter 'q' not provided.'

Scenario: Invalid API key
Given a weather API client with base URL 'http://localhost:${wiremock.port}' and API key 'invalid-key'
Given WireMock is configured to return error with status 401, code 1002 and message 'API key is invalid or not provided.'
When I make an invalid request without query parameter
Then the error response should have status 401, code 1002 and message 'API key is invalid or not provided.'