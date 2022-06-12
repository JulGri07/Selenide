import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;

public class SeleniumTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private By first_name = By.xpath(".//*[@name='firstname']");
    private By last_name = By.xpath(".//*[@name='lastname']");
    private By reg_email = By.xpath(".//*[@name='reg_email__']");
    private By reg_email_confirm = By.xpath(".//*[@name='reg_email_confirmation__']");
    private By password = By.xpath(".//*[@id='password_step_input']");
    private By birth_day = By.xpath(".//*[@id='day']");
    private By birth_month = By.xpath(".//*[@id='month']");
    private By birth_year = By.xpath(".//*[@id='year']");
    private By sex = By.xpath(".//input[@name='sex']");

    private By reg_button = By.xpath(".//*[@data-testid='open-registration-form-button']");
    private By submit_button = By.xpath(".//button[@name='websubmit']");

    private By reg_form = By.cssSelector(".registration_redesign");

    @DataProvider(name = "inputData")
    public FBClassUser[][] inputUser() {
        return new FBClassUser[][] {{new FBClassUser(
                "User",
                "NewComer",
                "set.email@gmail.com",
                "QwertY123",
                "27",
                "6",
                "1984",
                0)}};
    }

    @BeforeTest
    public void startup(){
        System.setProperty("webdriver.chrome.driver", "C:\\Documents and Settings\\User\\IdeaProjects\\SeleniumWdTestNg\\src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void openRegForm() {
        driver.get("https://www.facebook.com/");
        Assert.assertTrue(driver.findElement(reg_button).isEnabled());
        driver.findElement(reg_button).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(submit_button));
        Assert.assertTrue(driver.findElement(reg_form).isDisplayed());
    }

    @Test(dependsOnMethods = "openRegForm")
    public void checkRegFormElements()  {
        Assert.assertTrue(driver.findElement(first_name).isDisplayed());
        Assert.assertTrue(driver.findElement(last_name).isDisplayed());
        Assert.assertTrue(driver.findElement(reg_email).isDisplayed());
        Assert.assertFalse(driver.findElement(reg_email_confirm).isDisplayed());
        Assert.assertTrue(driver.findElement(password).isDisplayed());
        Assert.assertTrue(driver.findElement(birth_day).isDisplayed());
        Assert.assertTrue(driver.findElement(birth_month).isDisplayed());
        Assert.assertTrue(driver.findElement(birth_year).isDisplayed());
        Assert.assertEquals(driver.findElements(sex).size(),3);
    }

    @Test(dependsOnMethods = "checkRegFormElements", dataProvider = "inputData")
    public void fillRegistrationForm(FBClassUser user) {
        driver.findElement(first_name).sendKeys(user.first_name);
        driver.findElement(last_name).sendKeys(user.last_name);
        driver.findElement(reg_email).sendKeys(user.reg_email);
        driver.findElement(password).sendKeys(user.password);
        new Select(driver.findElement(birth_day)).selectByValue(user.birth_day);
        new Select(driver.findElement(birth_month)).selectByValue(user.birth_month);
        new Select(driver.findElement(birth_year)).selectByValue(user.birth_year);
        driver.findElements(sex).get(user.sex).click();
    }

    @Test(dependsOnMethods = "fillRegistrationForm", dataProvider = "inputData")
    public void checkEmailConfirmation(FBClassUser user) {
        Assert.assertTrue(driver.findElement(reg_email_confirm).isDisplayed());
        driver.findElement(reg_email_confirm).sendKeys(user.reg_email);
        Assert.assertEquals(driver.findElement(reg_email_confirm).getAttribute("value"),user.reg_email);
    }

    @Test(dependsOnMethods = "fillRegistrationForm", dataProvider = "inputData")
    public void checkRegForm(FBClassUser user){
        Assert.assertEquals(driver.findElement(first_name).getAttribute("value"),user.first_name);
        Assert.assertEquals(driver.findElement(last_name).getAttribute("value"),user.last_name);
        Assert.assertEquals(driver.findElement(reg_email).getAttribute("value"),user.reg_email);
        Assert.assertEquals(driver.findElement(password).getAttribute("value"),user.password);
        Assert.assertEquals(driver.findElement(birth_day).getAttribute("value"),user.birth_day);
        Assert.assertEquals(driver.findElement(birth_month).getAttribute("value"),user.birth_month);
        Assert.assertEquals(driver.findElement(birth_year).getAttribute("value"),user.birth_year);
        Assert.assertTrue(driver.findElements(sex).get(user.sex).isSelected());
    }

    @Test(dependsOnMethods = "fillRegistrationForm")
    public void checkRegButton(){
        Assert.assertTrue(driver.findElement(submit_button).isEnabled());
    }

    @AfterTest
    public void closeDown() {
        driver.quit();
    }

}
