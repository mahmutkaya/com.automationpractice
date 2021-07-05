package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pojos.Address;
import pojos.User;
import utilities.Driver;
import utilities.Helpers;
import utilities.SeleniumUtils;

import java.util.ArrayList;
import java.util.List;

public class SignInPage {

    public SignInPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    MyAccountPage myAccountPage = new MyAccountPage();

    @FindBy(xpath = "//a[contains(text(), 'Sign in')]")
    WebElement signInLink;

    @FindBy(xpath = "//a[contains(text(), 'Sign out')]")
    WebElement signOutLink;

    //------- sign up -------\\
    @FindBy(id = "email_create")
    WebElement emailCreateInput;

    @FindBy(id = "SubmitCreate")
    WebElement submitCreateBtn;

    @FindAll(
            @FindBy(xpath = "//div[@id='create_account_error']//li")
    )
    List<WebElement> submitEmailErrorList;

    @FindAll(
            @FindBy(xpath = "//div[@class='alert alert-danger']//li")
    )
    List<WebElement> createAccountErrorList;


    @FindBy(xpath = "//h1[contains(text(), 'Create an account')]")
    WebElement createAccountHeader;

    @FindBy(id = "submitAccount")
    WebElement registerBtn;

    //personal info
    @FindBy(id = "customer_firstname")
    WebElement firstNameInput_PI;

    @FindBy(id = "customer_lastname")
    WebElement lastNameInput_PI;

    @FindBy(id = "passwd")
    WebElement passwordInput_PI;

    //address info
    @FindBy(id = "firstname")
    WebElement firstNameInput_AI;

    @FindBy(id = "lastname")
    WebElement lastNameInput_AI;

    @FindBy(id = "address1")
    WebElement address1Input_AI;

    @FindBy(id = "city")
    WebElement cityInput_AI;

    @FindBy(id = "id_state")
    WebElement stateSelect_AI;

    @FindBy(id = "postcode")
    WebElement postCodeInput_AI;

    @FindBy(id = "id_country")
    WebElement countrySelect_AI;

    @FindBy(id = "phone_mobile")
    WebElement mobilePhoneInput_AI;

    @FindBy(id = "alias")
    WebElement aliasInput_AI;

    //------- sign in -------\\
    @FindBy(id = "email")
    WebElement emailInput;

    @FindBy(id = "passwd")
    WebElement passwordInput;

    @FindBy(id = "SubmitLogin")
    WebElement submitLoginBtn;

    public void goToSignInPage() {
        SeleniumUtils.waitForClickablility(signInLink, 5);
        signInLink.click();
    }

    public void login(String email, String password) {
        SeleniumUtils.waitForVisibility(emailInput, 5);

        Helpers.sendKeysIfNotNull(emailInput, email);
        Helpers.sendKeysIfNotNull(passwordInput, password);

        submitLoginBtn.click();
    }

    public void signUp(User user) {
        //check the current page and call needed method
        if (isSignInPage()) {
            submitEmail(user);
        }
        if (isAccountCreationPage()) {
            registerAccount(user);
        }
    }

    public boolean isLoggedIn() {
        return SeleniumUtils.isElementVisible(signOutLink);
    }

    void submitEmail(User user) {
        //generate email ** we would not need this if we could delete account
        String email = "";
        if (user.getEmail() != null) {
            email = user.getEmail();

            //to test signup with existing email don't generate a new one
            if (!user.getEmail().contains("exist")) {
                int iend = email.lastIndexOf('_') + 1;
                email = email.substring(0, iend)
                        + Helpers.generateDate()
                        + email.substring(iend);
            }
        }

        SeleniumUtils.waitForVisibility(emailCreateInput, 5);
        Helpers.sendKeysIfNotNull(emailCreateInput, email);
        submitCreateBtn.click();

        SeleniumUtils.waitForPageToLoad(3);
    }

    void registerAccount(User user) {
        //enter personal infos
        Helpers.sendKeysIfNotNull(firstNameInput_PI, user.getFirstName());
        Helpers.sendKeysIfNotNull(lastNameInput_PI, user.getLastName());
        Helpers.sendKeysIfNotNull(passwordInput_PI, user.getPassword());

        //enter address infos
        Address address = user.getAddresses().get(0);

        Helpers.sendKeysIfNotNull(firstNameInput_AI, address.getFirstName());
        Helpers.sendKeysIfNotNull(lastNameInput_AI, address.getLastName());
        Helpers.sendKeysIfNotNull(address1Input_AI, address.getAddress());
        Helpers.sendKeysIfNotNull(cityInput_AI, address.getCity());

        SeleniumUtils.selectByText(stateSelect_AI, address.getState());

        Helpers.sendKeysIfNotNull(postCodeInput_AI, address.getZip());

        SeleniumUtils.selectByText(countrySelect_AI, address.getCountry());

        Helpers.sendKeysIfNotNull(mobilePhoneInput_AI, address.getMobilePhone());
        Helpers.sendKeysIfNotNull(aliasInput_AI, address.getAddressAlias());

        registerBtn.click();

        SeleniumUtils.waitForPageToLoad(3);
    }

    public List<String> getCreateAccountErrorsRes() {
        List<String> errorLs = new ArrayList<>();
        if (isSignInPage()) {
            SeleniumUtils.waitForVisibility(submitEmailErrorList.get(0), 5);
            errorLs = SeleniumUtils.getElementsText(submitEmailErrorList);
        }
        if (isAccountCreationPage()) {
            SeleniumUtils.waitForVisibility(createAccountErrorList.get(0), 5);
            errorLs = SeleniumUtils.getElementsText(createAccountErrorList);
        }
        return errorLs;
    }

    public String getCreateAccountSuccessRes() {
        String res = "";
        if (isAccountCreationPage()) {
            SeleniumUtils.waitForVisibility(createAccountHeader, 5);
            res = createAccountHeader.getText();
        }
        if (isMyAccountPage()) {
            res = myAccountPage.getWelcomeTxt();
        }
        return res;
    }

    public List<String> getSignInErrorsRes() {
        List<String> errorLs;
        //sign in and create account page are using same same alert locator
        SeleniumUtils.waitForVisibility(createAccountErrorList.get(0), 5);
        errorLs = SeleniumUtils.getElementsText(createAccountErrorList);

        return errorLs;
    }

    public String getSignInSuccessRes() {
        return myAccountPage.getWelcomeTxt();
    }

    boolean isMyAccountPage() {
        return SeleniumUtils.getCurrentPageUrl().contains("controller=my-account");
    }

    boolean isSignInPage() {
        return SeleniumUtils.isElementVisible(emailCreateInput);
    }

    boolean isAccountCreationPage() {
        return SeleniumUtils.isElementVisible(createAccountHeader);
    }

}
