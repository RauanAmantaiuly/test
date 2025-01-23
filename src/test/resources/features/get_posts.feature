Feature: Testing JSONPlaceholder API

  @smoke
  Scenario: Verify that a post with ID 1 exists
    Given the JSONPlaceholder API is up and running
    When I request a post with ID 1
    Then I receive a response with status code 200
    And the title of the post is "sunt aut facere repellat provident occaecati excepturi optio reprehenderit"
