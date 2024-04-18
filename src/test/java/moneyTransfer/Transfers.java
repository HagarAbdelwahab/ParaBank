package moneyTransfer;

import constants.PackagesPaths;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import services.requests.AccountService;
import services.requests.AuthenticationService;
import services.requests.OverviewService;
import services.requests.TransferService;
import services.responseExtractors.Accounts;
import services.responseExtractors.Overview;
import utils.JSONExtractor;

import org.testng.asserts.SoftAssert;


import java.util.Map;

import static io.qameta.allure.SeverityLevel.CRITICAL;


public class Transfers {
    SoftAssert softAssert = new SoftAssert();
    Map<String, String> cookie;


    @Severity(CRITICAL)
    @TmsLink("")
    @Description("When the user transfers amount from one account to another, the transferred amount should be deducted from the account balance.")
    @Test(description = "Transfer the money from different user accounts")
    void checkTransferIsSuccessful(){
        //get customer id
        Response customerInfo = OverviewService.getCustomerInfo(cookie);
        String customerID = Overview.getCustomerId(customerInfo);

        //get main account info
        Response accountsResponse = AccountService.getAccounts(customerID, cookie);
        int mainAccountID = JSONExtractor.getIntFromJson(accountsResponse,"[0].id");
        Double mainAccountBalance = Accounts.getAccountBalance(accountsResponse, mainAccountID);

        //open a new account from main account
        Response newAccountResponse =AccountService.openAccount(customerID,mainAccountID, cookie);
        int newAccountID = Accounts.getNewAccountID(newAccountResponse);
        Response accountResponseAfterNewAccount = AccountService.getAccounts(customerID, cookie);
        Double newAccountBalance =Accounts.getAccountBalance(accountResponseAfterNewAccount, newAccountID);
        Double mainAccountBalanceAfterOpeningNewAccount = Accounts.getAccountBalance(accountResponseAfterNewAccount, mainAccountID);
        Double originalNewAccountBalance = mainAccountBalance - mainAccountBalanceAfterOpeningNewAccount ;

        //do the transaction from main account to the new account
        Double transferredAmount = 100.5;
        Response transferResponse = TransferService.transferMoney(mainAccountID, newAccountID,transferredAmount, cookie);

        //assert that the transfer has been done successfully
        softAssert.assertEquals(transferResponse.getBody().prettyPrint(),
                "Successfully transferred $"+transferredAmount+" from account #"+mainAccountID+" to account #"+ newAccountID);

        //get the balance in the accounts after the transfer
        Response accountsResponseAfterTransfer = AccountService.getAccounts(customerID, cookie);
        Double mainAccountBalanceAfterTransfer = Accounts.getAccountBalance(accountsResponseAfterTransfer, mainAccountID);
        Double newAccountBalanceAfterTransfer = Accounts.getAccountBalance(accountsResponseAfterTransfer, newAccountID);

        //assert the balance in the account after the transfer
        softAssert.assertEquals(mainAccountBalanceAfterTransfer,mainAccountBalance-transferredAmount-originalNewAccountBalance);
        softAssert.assertEquals(newAccountBalanceAfterTransfer,newAccountBalance+transferredAmount);

        softAssert.assertAll();

    }

    @BeforeClass
    void setup(){
        //register a new user
        String username = RandomStringUtils.random(11, true, false);
        cookie = AuthenticationService.register(PackagesPaths.TEST_DATA_PATH+"customerInfo.json",username);
    }


}
