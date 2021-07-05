package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import pages.SignInPage;

import java.util.List;
import java.util.Map;

public class LoginSteps {

    SignInPage signInPage = new SignInPage();

    String expectedResult;
    String email,password;

    @Given("I am on the sign in page")
    public void i_am_on_the_sign_in_page() {
        signInPage.goToSignInPage();
    }
    @Given("credentials are:")
    public void credentials_are(List<Map<String, String>> credentialsDt) {
        email = credentialsDt.get(0).get("email");
        password = credentialsDt.get(0).get("password");
    }

    @When("I want to login {string}")
    public void i_want_to_login(String string) {
        signInPage.login(email, password);
    }

    @Then("the login {string}")
    public void the_login(String expectedResult) {
        this.expectedResult = expectedResult;
        boolean isLoggedIn = signInPage.isLoggedIn();

        if (expectedResult.equals("FAILS")) {
            Assert.assertFalse(isLoggedIn);
        }
        if (expectedResult.equals("IS SUCCESSFUL")) {
            Assert.assertTrue(isLoggedIn);
        }

    }
    @Then("the response is {string}")
    public void the_response_is(String expectedResponse) {
        if (expectedResult.equals("FAILS")) {
            List<String> actualErrors = signInPage.getSignInErrorsRes();
            Assert.assertTrue("expected: "+expectedResponse + "\nactual: "+actualErrors,
                    actualErrors.contains(expectedResponse)
            );
        }
        if (expectedResult.equals("IS SUCCESSFUL")) {
            String actualMsg = signInPage.getSignInSuccessRes();
            Assert.assertTrue("Expected: "+expectedResponse + "\nActual: "+actualMsg,
                    actualMsg.contains(expectedResponse)
            );
        }

    }
}
