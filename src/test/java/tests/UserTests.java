package tests;

import config.Config;
import io.restassured.response.Response;
import model.UserRequest;
import model.UserResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.Constants;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static utils.Constants.*;

public class UserTests extends Config {

    SoftAssert softAssert;

    @BeforeMethod(alwaysRun = true)
    public void setup(){
        softAssert = new SoftAssert();
    }
    @Test
    public void getUsersTest() {
        Map<String, String> map = new HashMap<>();
        map.put("page", "1");
        map.put("limit", "5");
        Response response = given()
                .queryParams(map)
                .when().get("model");

        Assert.assertEquals(response.getStatusCode(), 200, "Expected 200 but got: " + response.getStatusCode());
        String actualFirstName = response.jsonPath().get("data[0].firstName");
        System.out.println(actualFirstName);

        Assert.assertEquals(actualFirstName, "Carolina");
    }

    @Test
    public void getUsersUsingJsonPathTest() {
        Map<String, String> map = new HashMap<>();
        map.put("page", "1");
        map.put("limit", "5");
        Response response = given()
                .queryParams(map)
                .when().get(Constants.GET_ALL_USERS);

        softAssert.assertEquals(response.getStatusCode(), 200, "Expected 200 but got: "+response.getStatusCode());
        String actualFirstName = response.jsonPath().getString("data[0].firstName");
        boolean result = actualFirstName.equals("Carolina");

        softAssert.assertTrue(result, "Expected first name is not correct.");
        softAssert.assertAll(); //ako se ova linija ne upiše, nama će sve asertacije uvek da prolaze bez obzira da li je nešto palo ili nije
    }

    @Test
    public void getUserByIdTest() {
        UserRequest user = UserRequest.createUser();

        UserResponse userResponse = given()
                .body(user)
                .when().post(CREATE_USER)
                .getBody().as(UserResponse.class);

        String userId = userResponse.getId();

        Response response = given()
                .pathParam("id",userId)
                .when().get(GET_USER_BY_ID);

        softAssert.assertEquals(response.getStatusCode(), 200, "Expected 200 but got: " + response.getStatusCode());
        String actualFirstName = response.jsonPath().get("firstName");
        System.out.println(actualFirstName);

        softAssert.assertEquals(actualFirstName, "Carolina");
        softAssert.assertAll();
    }

    @Test
    public void deleteUserByIdTest() {

        UserRequest user = UserRequest.createUser();

        UserResponse userResponse = given()
                .body(user)
                .when().post(CREATE_USER)
                .getBody().as(UserResponse.class);

        String userId = userResponse.getId();

        Response response = given()
                .pathParam("id", userId)
                .when().delete(DELETE_USER_BY_ID);


        softAssert.assertEquals(response.getStatusCode(), 200, "Expected 200 but got: " + response.getStatusCode());
        String id = response.jsonPath().get("id");
        System.out.println(id);

        softAssert.assertEquals(id, userId);

        given()
                .pathParam("id", userId)
                .when().delete(DELETE_USER_BY_ID);
        softAssert.assertEquals(response.getStatusCode(), 404, "Expected 404 but got: " + response.getStatusCode());
        softAssert.assertAll();
    }

    @Test
    public void createUserTest() {
        UserRequest user = UserRequest.createUser();

        UserResponse userResponse = given()
                .body(user)
                .when().post(CREATE_USER)
                .getBody().as(UserResponse.class);

        String userId = userResponse.getId();

        softAssert.assertEquals(userResponse.getTitle(),user.getTitle());
        softAssert.assertEquals(userResponse.getFirstName(),user.getFirstName());
        softAssert.assertEquals(userResponse.getLastName(),user.getLastName());
        softAssert.assertEquals(userResponse.getPicture(),user.getPicture());
        softAssert.assertEquals(userResponse.getGender(),user.getGender());
        softAssert.assertEquals(userResponse.getEmail(),user.getEmail());
        softAssert.assertEquals(userResponse.getDateOfBirth(),user.getDateOfBirth()+".000Z");
        softAssert.assertEquals(userResponse.getPhone(),user.getPhone());
        softAssert.assertEquals(userResponse.getLocation(),user.getLocation());
        softAssert.assertAll();
    }

    @Test
    public void updateUserUsingJavaObjectTest() {
        UserRequest user = UserRequest.createUser();

        UserResponse userResponse = given()
                .body(user)
                .when().post(CREATE_USER)
                .getBody().as(UserResponse.class);

        String userId = userResponse.getId();

        String updatedFirstName = "updatedFirstName";
        String updatedCity = "updatedCity";

        UserRequest updatedUser = user
                .withFirstName(updatedFirstName)
                .withLocation(user.getLocation().withCity(updatedCity));

                UserResponse updatedUserResponse = given()
                .body(updatedUser)
                .pathParam("id",userId)
                .when().put(UPDATE_USER)
                .getBody().as(UserResponse.class);

        softAssert.assertEquals(updatedUserResponse.getFirstName(),updatedFirstName);
        softAssert.assertEquals(updatedUserResponse.getLocation().getCity(),updatedCity);
        softAssert.assertAll();
    }

}