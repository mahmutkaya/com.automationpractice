package stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import pages.SignInPage;
import pojos.User;

import java.util.List;

public class AccountSteps {

    SignInPage signInPage = new SignInPage();

    @When("I save the new account {string}")
    public void i_save_the_new_account(String testCase) {
        List<User> users = AccountCommonSteps.users;
        signInPage.signUp(users.get(0));
    }

    @Then("the response on ui should be: {string}")
    public void the_response_on_ui_should_be(String expectedRes) {
        String expectedResult = AccountCommonSteps.expectedResult;

        if (expectedResult.equals("FAILS")) {
            List<String> actualResList;
            actualResList = signInPage.getCreateAccountErrorsRes();
            Assert.assertTrue("Expected: "+expectedRes + "\nActual: "+actualResList,
                    actualResList.contains(expectedRes)
            );
        }
        if (expectedResult.equals("IS SUCCESSFUL")) {
            String actualRes = signInPage.getCreateAccountSuccessRes();
            Assert.assertTrue("Expected: "+expectedRes + "\nActual: "+actualRes,
                    actualRes.contains(expectedRes)
            );
        }
    }
}
