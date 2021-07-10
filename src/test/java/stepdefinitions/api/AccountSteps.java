package stepdefinitions.api;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import pojos.Address;
import pojos.User;
import utilities.ApiUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountSteps {

    private Response response;
    private String endpoint;

    public static String expectedResult;
    public static List<User> users;

    @Given("(I want to create/update )an account with the following attributes:")
    public void i_want_to_create_an_account_with_the_following_attributes(List<User> users) {
        this.users = users;
    }

    @Given("with the following address information:")
    public void with_the_following_address_information(List<Address> addresses) {
        users.get(0).setAddresses(addresses);
    }
    @Then("the save {string}")
    public void the_save(String expectedResult) {
        this.expectedResult = expectedResult;

        if (expectedResult.equals("FAILS")) {
            //Todo: connect to db and verify that account does not exist
        }
        if (expectedResult.equals("IS SUCCESSFUL")) {
            //Todo: connect to db and verify that account is exist
        }
    }

    @Given("the account creation endpoint is {string}")
    public void the_account_creation_endpoint_is(String endpoint) {
        this.endpoint = endpoint;
    }

    @When("I save the new account by api {string}")
    public void i_save_the_new_account_by_api(String testCase) {
        response = ApiUtils.post(endpoint, getFormParams());
    }

    @Then("the response on api should be: {string}")
    public void the_response_on_api_should_be(String response) {
        String expectedResult = this.expectedResult;

        boolean hasError = this.response.body().jsonPath().getBoolean("hasError");
        List<String> errors = this.response.body().jsonPath().getList("errors");

        if (expectedResult.equals("FAILS")) {
            Assert.assertTrue("Expected: " + true + "\nActual: " + hasError,
                    hasError
            );

            Assert.assertTrue("Expected: " + response + "\nActual: " + errors,
                    errors.contains(response)
            );
        }
        if (expectedResult.equals("IS SUCCESSFUL")) {
            Assert.assertFalse("Expected: " + false + "\nActual: " + hasError,
                    hasError
            );

            Assert.assertTrue("Expected: []" + "\nActual: " + errors,
                    errors.isEmpty()
            );
        }
    }

    private Map<String, Object> getFormParams() {
        //get user data
        User user = users.get(0);

        //add form-data
        Map<String, Object> formParams = new HashMap<>();
        formParams.put("SubmitCreate", 1);
        formParams.put("ajax", true);
        formParams.put("email_create", user.getEmail());

        return formParams;
    }
}
