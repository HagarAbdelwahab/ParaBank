package services.responseExtractors;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Overview {

    @Step("Get the customer id")
    public static String getCustomerId(Response customerResponse) {

        String bodyString = customerResponse.body().asString();

        String regex = "services_proxy/bank/customers/\\\"\\s\\+\\s(\\d+)\\s\\+\\s\\\"/accounts";

        // Create a Pattern object
        Pattern pattern = Pattern.compile(regex);

        // Create a Matcher object
        Matcher matcher = pattern.matcher(bodyString);

        // Find and extract the customer ID if the pattern matches
        String customerId = null;
        if (matcher.find()) {
            customerId = matcher.group(1);
            System.out.println("Customer ID: " + customerId);
        } else {
            System.out.println("Customer ID not found.");
        }
        Allure.addAttachment("Customer id is:  ", String.valueOf(customerId));
        return customerId;

    }
}
