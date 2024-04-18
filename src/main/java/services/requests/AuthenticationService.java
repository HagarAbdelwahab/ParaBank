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

    public static String login(String username, String password) {
        Response re = given()
                .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .header("accept-language", "en-US,en;q=0.9,ar;q=0.8")
                .header("cache-control", "max-age=0")
                .header("content-type", "application/x-www-form-urlencoded")
                .formParam("username", username)
                .formParam("password", password)
                .when()
                .post("https://parabank.parasoft.com/parabank/login.htm")
                .then().log().all().extract().response();

        return re.getCookie("Cookie");
    }


    @Step("register a new user")
    public static Map<String, String> register(String filePath, String username) {

        // Make a GET request to retrieve initial cookies
        Response initialResponse = RestAssured.get(Endpoints.BASE_URL+Endpoints.REGISTER);
        Map<String, String> cookies = initialResponse.getCookies();

        Response response = given().baseUri(Endpoints.BASE_URL)
                .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .header("upgrade-insecure-requests", "1")
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
                .log().all()
                .when()
                .post(Endpoints.REGISTER)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();
        Allure.addAttachment("Response: ", response.getBody().prettyPrint());
        return cookies;
    }

}
