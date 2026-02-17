package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuilder;
import resources.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class StepDefination extends Utils {
    //ResponseSpecification respSpec;
    RequestSpecification res;
    Response response;
    static String place_id;

    @Given("Add Place Payload with {string} {string} {string}")
    public void add_place_payload_with(String name, String address, String language) throws IOException {
        AddPlace place = TestDataBuilder.addPlacePayload(name,address,language);
        res = given().spec(requestSpecification())
                .body(place);
    }
    @When("user calls {string} with {string} http request")
    public void user_calls_with_http_request(String resource, String method) {
        APIResources resourceAPI = APIResources.valueOf(resource);
        System.out.println(resourceAPI.getResource());
        if(method.equalsIgnoreCase("POST"))
            response = res.when().post(resourceAPI.getResource())
                .then().spec(responseSpecification()).extract().response();
        else if (method.equalsIgnoreCase("GET")) {
            response = res.when().get(resourceAPI.getResource());
        }
    }
    @Then("the API call got success with status code {int}")
    public void the_api_call_got_success_with_status_code(Integer statusCode) {
//        System.out.println(response.getStatusCode());
        Integer expectedStatusCode = response.getStatusCode();
        assertEquals(expectedStatusCode,statusCode);
    }
    @Then("{string} in response body is {string}")
    public void in_response_body_is(String key, String expectedValue) {
//        String resp = response.asString();
//        JsonPath js = new JsonPath(resp);
        assertEquals(extractDataFromResponseByKey(key,response),expectedValue);
    }
    @Then("verify {string} in response from using {string} equal to {string} by place_id")
    public void verify_in_response_from_using_equal_to_by_place_id(String name, String resource, String expectedName) throws IOException {
        place_id = extractDataFromResponseByKey("place_id",response);
        res = given().spec(requestSpecification()).queryParam("place_id",place_id);
        user_calls_with_http_request(resource,"GET");
        String actualName = extractDataFromResponseByKey(name,response);
        assertEquals(expectedName,actualName);
    }

    @Given("DeletePlace Payload")
    public void delete_place_payload() throws IOException {
        res = given().spec(requestSpecification()).body(TestDataBuilder.deletePlacePayload(place_id));
    }
}
