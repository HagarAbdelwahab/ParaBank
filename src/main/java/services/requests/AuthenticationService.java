package services.requests;

import constants.Endpoints;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.JSONTestDataReader;

import java.util.Map;


import static io.restassured.RestAssured.given;


public class AuthenticationService {


    @Step("Register a new user")
    public static Map<String, String> register(String filePath, String username) {

        // Make a GET request to retrieve initial cookies
        Response initialResponse = RestAssured.get(Endpoints.BASE_URL+Endpoints.REGISTER);
        Map<String, String> cookies = initialResponse.getCookies();

        Response response = given().baseUri(Endpoints.BASE_URL)
                .cookies(cookies)
                .formParam("customer.firstName", JSONTestDataReader.getValueFromJsonFile(filePath, "customer-info", "firstName"))
                .formParam("customer.lastName", JSONTestDataReader.getValueFromJsonFile(filePath, "customer-info", "lastName"))
                .formParam("customer.address.street", JSONTestDataReader.getValueFromJsonFile(filePath, "customer-info", "street"))
                .formParam("customer.address.city", JSONTestDataReader.getValueFromJsonFile(filePath, "customer-info", "city"))
                .formParam("customer.address.state", JSONTestDataReader.getValueFromJsonFile(filePath, "customer-info", "state"))
                .formParam("customer.address.zipCode", JSONTestDataReader.getValueFromJsonFile(filePath, "customer-info", "zipCode"))
                .formParam("customer.phoneNumber", JSONTestDataReader.getValueFromJsonFile(filePath, "customer-info", "phoneNumber"))
                .formParam("customer.ssn", JSONTestDataReader.getValueFromJsonFile(filePath, "customer-info", "ssn"))
                .formParam("customer.username", username)
                .formParam("customer.password", JSONTestDataReader.getValueFromJsonFile(filePath, "customer-info", "password"))
                .formParam("repeatedPassword", JSONTestDataReader.getValueFromJsonFile(filePath, "customer-info", "confirmedPass"))
                .when()
                .post(Endpoints.REGISTER)
                .then()
                .statusCode(200)
                .extract()
                .response();
        Allure.addAttachment("Response: ", response.getBody().prettyPrint());
        return cookies;
    }

}
