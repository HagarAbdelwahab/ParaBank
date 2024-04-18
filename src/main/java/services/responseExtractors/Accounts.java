package services.responseExtractors;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.JSONExtractor;


public class Accounts {

    @Step("Get the new account id after opening a new account")
    public static int getNewAccountID(Response newAccountOpeningResponse) {
        int newAccountID = JSONExtractor.getIntFromJson(newAccountOpeningResponse, "id");
        Allure.addAttachment("New account id is: ", String.valueOf(newAccountID));
        return newAccountID;

    }

    @Step("Get the account balance")
    public static Double getAccountBalance(Response accountsResponse, int accountID) {
        String jsonArrayStr = accountsResponse.body().asString();
        JSONArray jsonArray = new JSONArray(jsonArrayStr);
        Double balance = 0.0;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int id = jsonObject.getInt("id");
            if (id == accountID) {
                balance = jsonObject.getDouble("balance");
            }
        }
        Allure.addAttachment("account "+accountID+" has balance:  ", String.valueOf(balance));
        return balance;

    }


}
