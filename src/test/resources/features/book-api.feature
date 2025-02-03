Feature: Book API Integration Test
  Scenario: Fetch Books with JWT Token
    Given I have a valid JWT token
    When I make a GET request to "/api/v1/books"
    Then the response status should be 200
    And the response should contain at least one book