Feature: Validating Place API's
  @AddPlace @Regression
  Scenario Outline: Verify if Place is being Successfully added using AddPlaceAPI
    Given Add Place Payload with "<name>" "<address>" "<language>"
    When user calls "AddPlaceAPI" with "Post" http request
    Then the API call got success with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And verify "name" in response from using "getPlaceAPI" equal to "<name>" by place_id

    Examples:
        |name   | language | address           |
        |AAhouse| English  | World cross center|
        #|BBHouse| Spanish  | Sea cross center  |
  @DeletePlace @Regression
  Scenario: Verify if Delete place functionality is working
    Given DeletePlace Payload
    When user calls "deletePlaceAPI" with "Post" http request
    Then the API call got success with status code 200
    And "status" in response body is "OK"