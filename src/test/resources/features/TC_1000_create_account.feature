@smoke @api @createAccount_api
Feature: Create Account - api

  Scenario Outline: Create account <testCase> <expectedResult> <response>
    Given the account creation endpoint is "?controller=authentication&back=my-account"

    And I want to create an account with the following attributes:
      | email   | firstName   | lastName   | password   |
      | <email> | <firstName> | <lastName> | <password> |

    And with the following address information:
      | firstName | lastName | address            | city    | state | zip   | country       | mobilePhone | addressAlias |
      | John      | Doe      | Stresemannlaan 001 | Haarlem | Texas | 20300 | United States | 0686433030  | summerhouse  |

    When I save the new account by api '<testCase>'
    Then the save '<expectedResult>'
    And the response on api should be: '<response>'

    Examples:
      | testCase             | expectedResult | email               | firstName | lastName | password | response                                                         |
#      email registration test cases
      | WITHOUT EMAIL        | FAILS          |                     | John      | Doe      | 12345    | Invalid email address.                                           |
      | WITH INVALID EMAIL   | FAILS          | qa_email            | John      | Doe      | 12345    | Invalid email address.                                           |
      | WITH EXISTING EMAIL  | FAILS          | exist@gmail.com     | John      | Doe      | 12345    | An account using this email address has already been registered. |
      | WITH NEW/VALID EMAIL | IS SUCCESSFUL  | qa_email_@gmail.com |           |          |          |                                                                  |

#      personal information test cases
#      | WITHOUT FIRST NAME                      | FAILS          | qa_email_@gmail.com |           | Doe      | 12345    | firstname is required.                                           |
#      | WITHOUT LAST NAME                       | FAILS          | qa_email_@gmail.com | John      |          | 12345    | lastname is required.                                            |
#      | WITHOUT PASSWORD                        | FAILS          | qa_email_@gmail.com | John      | Doe      |          | passwd is required.                                              |
#      | WITH INVALID PASSWORD (four characters) | FAILS          | qa_email_@gmail.com | John      | Doe      | 1234     | passwd is invalid.                                               |
#      | WITH ALL REQUIRED FIELDS                | IS SUCCESSFUL  | qa_email_@gmail.com | John      | Doe      | 12345    | Welcome to your account.                                         |


