package stepdefinitions.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import pojos.Product;
import utilities.ApiUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;

public class CartSteps {

    private Response response;
    private String endpoint;

    private String expectedResult;

    private List<Product> product;

    Map<String,Object> cartFormParams = new HashMap<>();

    @Given("the cart endpoint is {string}")
    public void the_cart_endpoint_is(String endpoint) {
        this.endpoint = endpoint;
    }

    @Given("a product with the following attributes:")
    public void a_product_with_the_following_attributes(List<Product> productDt) {
        this.product = productDt;
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
        Product p = product.get(0);
        //make sure that cart has the product
        addToCart();
        response.then()
                .assertThat()
                .body(
                        "products.id", hasItem(p.getId_product())
                );
    }

    @When("I want to delete the product by id {int}")
    public void i_want_to_delete_the_product_by_id(int id) {
        deleteFromCart(id);
    }

    @Then("the product is removed from the cart")
    public void the_product_is_removed_from_the_cart() {
        Product p = product.get(0);
        response.then()
                .assertThat()
                .body(
                        "products.id", not(hasItem(p.getId_product()))
                );
    }

    void addToCart(){
        mapProduct();
        //to add a product to cart we need to add "add" attribute in form-data
        cartFormParams.put("add", true);
        response = ApiUtils.post(endpoint, cartFormParams);
    }
    void deleteFromCart(int id){
        mapProduct();
        //to delete a product from the cart we need to add "delete" attribute in form-data
        cartFormParams.put("delete", true);
        //update product id to delete in case we want to delete a different one
        cartFormParams.put("id_product", id);
        response = ApiUtils.post(endpoint, cartFormParams);
    }
    void mapProduct(){
        Product p = product.get(0);
        ObjectMapper mapper = new ObjectMapper();
        cartFormParams = mapper.convertValue(p, HashMap.class);
    }
}
