package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;
import utilities.SeleniumUtils;

public class MyAccountPage {

    public MyAccountPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//p[@class='info-account']")
    WebElement welcomeTxt;

    public String getWelcomeTxt() {
        SeleniumUtils.waitForVisibility(welcomeTxt, 5);
        return welcomeTxt.getText();
    }
}
