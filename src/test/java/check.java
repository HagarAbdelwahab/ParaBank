
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import services.AccountService;
import services.LoginService;
import services.TransferService;
import utils.JSONExtractor;

import org.testng.asserts.SoftAssert;


import java.util.Map;


public class check {
    SoftAssert softAssert = new SoftAssert();



    @Description("When the user transfers amount from one account to another, the transferred amount should be deducted from the account balance.")
    @Test(description = "")
    void checkTransfer(){
        //register a new user
        String username = RandomStringUtils.random(11, true, false);
        System.out.println("username is : "+username);
        Map<String, String> co = LoginService.register(username,username,"register","register","register",
                1414,1223,13141,username,username,username);

        //login
        LoginService.login(username,username);

        //get customer id
        String customerID = LoginService.getCustomerId(co);

        //get main account
        Response accountsResponse = AccountService.getAccounts(customerID,co);
        String mainAccountID = JSONExtractor.getStringFromJson(accountsResponse,"[0].id");
        Double mainAccountBalance = JSONExtractor.getDoubleFromJson(accountsResponse,"[0].balance");
        Double transferredAmount = 1000000.7;

        //open account from main account
        String newAccount =AccountService.openAccount(customerID,mainAccountID,co);

        //do the transaction from one account to another
        Response transferResponse = TransferService.transferMoney(mainAccountID,newAccount,transferredAmount,co);

        //assert on the response
        softAssert.assertEquals(transferResponse.getBody().prettyPrint(),
                "Successfully transferred $"+transferredAmount+" from account #"+mainAccountID+" to account #"+newAccount);
        softAssert.assertAll();

        //TODO: assert in the accounts overview

        //TODO: read from test data files
        //TODO: put the end points in the end points class
        //TODO: remove unneeded headers
        //TODO: report
        //TODO: readme file

    }



    @BeforeClass
    void setup(){
        //login

    }


}
