package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import static org.hamcrest.Matchers.*;
import utilities.ApiUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartSteps_API {

    private Response response;
    private String endpoint;
    private String expectedResult;

    private Map<String,Object> cartFormParams = new HashMap<>();

    @Given("the cart endpoint is {string}")
    public void the_cart_endpoint_is(String endpoint) {
        this.endpoint = endpoint;
    }

    @Given("a product with the following attributes:")
    public void a_product_with_the_following_attributes(List<Map<String,String>> productDt) {
        String id_product = productDt.get(0).get("id_product");
        String qty = productDt.get(0).get("qty");
        String ipa = productDt.get(0).get("ipa");

        cartFormParams.put("id_product", Integer.parseInt(id_product));
        cartFormParams.put("qty",qty);
        cartFormParams.put("ipa",ipa);
    }

    @When("I want to add the product {string}")
    public void i_want_to_add_the_product(String testCase) {
        addToCart();
    }

    @Then("the request {string}")
    public void the_request(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    @Then("the api response is: {string}")
    public void the_api_response_is(String expectedResponse) {
        boolean hasError = response.body().jsonPath().getBoolean("hasError");
        List<String> errors = this.response.body().jsonPath().getList("errors");

        if (expectedResult.equals("FAILS")) {
            Assert.assertTrue("Expected: " + true + "\nActual: " + hasError,
                    hasError
            );
            Assert.assertTrue("Expected: " + expectedResponse + "\nActual: " + errors,
                    errors.contains(expectedResponse)
            );
        }
        if (expectedResult.equals("IS SUCCESSFUL")) {
            Assert.assertFalse("Expected: " + false + "\nActual: " + hasError,
                    hasError
            );

            Assert.assertNull("Expected: null" + "\nActual: " + errors,
                    errors
            );
        }
    }

    @When("the product is in cart")
    public void the_product_is_in_cart() {
        //make sure that cart has the product
        addToCart();
        response.then()
                .assertThat()
                .body(
                        "products.id", hasItem(cartFormParams.get("id_product"))
                );
    }

    @When("I want to delete the product by id {int}")
    public void i_want_to_delete_the_product_by_id(int id) {
        deleteFromCart(id);
    }

    @Then("the product is removed from the cart")
    public void the_product_is_removed_from_the_cart() {
        response.then()
                .assertThat()
                .body(
                        "products.id", not(hasItem(cartFormParams.get("id_product")))
                );
    }

    void addToCart(){
        //to add a product to cart we need to add "add" attribute in form-data
        cartFormParams.put("add", true);
        response = ApiUtils.post(endpoint, cartFormParams);
    }
    void deleteFromCart(int id){
        //to delete a product from cart we need to remove "add" and
        //add "delete" attribute in form-data
        cartFormParams.remove("add");
        cartFormParams.put("delete", true);
        //add product id to delete
        cartFormParams.put("id_product", id);

        response = ApiUtils.post(endpoint, cartFormParams);
    }
}
