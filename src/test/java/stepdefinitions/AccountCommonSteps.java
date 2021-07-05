package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import pojos.Address;
import pojos.User;

import java.util.List;


public class AccountCommonSteps {

    protected static String expectedResult;
    protected static List<User> users;

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
        AccountCommonSteps.expectedResult = expectedResult;

        if (expectedResult.equals("FAILS")) {
            //Todo: connect to db and verify that account does not exist
        }
        if (expectedResult.equals("IS SUCCESSFUL")) {
            //Todo: connect to db and verify that account is exist
        }
    }
}
