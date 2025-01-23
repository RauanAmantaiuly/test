Feature: Testing add pet

  Background:


  Scenario:
    Given
    When I created a pet
    Then I receive a response with status code 200
    And I retrieve created pet by id = {id}
    And I check created pet by names
    Then I got equal names