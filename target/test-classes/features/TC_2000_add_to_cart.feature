@smoke @api @addToCart_api
Feature: Add product to cart - api

  Scenario Outline: Add to cart <testCase> <expectedResult> <response>
    Given the cart endpoint is "?controller=cart&ajax=true"
    And a product with the following attributes:
      | id_product | qty | ipa |
      | <id_product> | <qty> | <ipa> |

    When I want to add the product '<testCase>'

    Then the request '<expectedResult>'
    And the api response is: '<response>'

    Examples:
      | testCase              | expectedResult | response                             |id_product | qty | ipa |
      | WITH NON-AVAILABLE ID | FAILS          | This product is no longer available. | 9          | 1   | 21  |
      | WITH AVAILABLE ID     | IS SUCCESS     |                                      | 3          | 1   | 13  |

