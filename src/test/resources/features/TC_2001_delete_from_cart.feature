@smoke @api @deleteFromCart_api
Feature: Delete product from cart - api

  Background:
    Given the cart endpoint is "?controller=cart&ajax=true"

    And a product with the following attributes:
      | id_product | qty | ipa |
      | 3          | 1   | 13  |

    When the product is in cart

  Scenario: Delete from cart <expectedResult> <response>
    When I want to delete the product by id 3
    Then the product is removed from the cart
