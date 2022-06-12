import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@RunWith(value = Parameterized.class)
public class SeleniumTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private FBClassUser user;

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

    public SeleniumTest(FBClassUser user) {
        this.user = user;
    }

    @Parameterized.Parameters
    public static FBClassUser[][] inputUser() {
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

    @Before
    public void startUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Documents and Settings\\User\\IdeaProjects\\SeleniumWdTestNg\\src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.facebook.com/");
    }

    private void openRegForm() {
        driver.findElement(reg_button).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(submit_button));
    }

    private void fillRegForm() {
        driver.findElement(first_name).sendKeys(user.first_name);
        driver.findElement(last_name).sendKeys(user.last_name);
        driver.findElement(reg_email).sendKeys(user.reg_email);
        driver.findElement(password).sendKeys(user.password);
        new Select(driver.findElement(birth_day)).selectByValue(user.birth_day);
        new Select(driver.findElement(birth_month)).selectByValue(user.birth_month);
        new Select(driver.findElement(birth_year)).selectByValue(user.birth_year);
        driver.findElements(sex).get(user.sex).click();
    }

    private void fillRegConfirmEmail() {
        driver.findElement(reg_email_confirm).sendKeys(user.reg_email);
    }

    @Test
    public void testStartPage() {
        Assert.assertTrue(driver.findElement(reg_button).isEnabled());
        openRegForm();
        Assert.assertTrue(driver.findElement(reg_form).isDisplayed());
    }

    @Test
    public void testRegFormPage() {
        openRegForm();
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

     @Test
     public void testRegFormConfirmEmailFill() {
         openRegForm();
         fillRegForm();
         Assert.assertTrue(driver.findElement(reg_email_confirm).isDisplayed());
         fillRegConfirmEmail();
         Assert.assertEquals(driver.findElement(reg_email_confirm).getAttribute("value"),user.reg_email);
     }

     @Test
     public void testRegFormFill() throws InterruptedException {
         openRegForm();
         fillRegForm();
         fillRegConfirmEmail();

         Thread.sleep(3000);

         Assert.assertEquals(driver.findElement(first_name).getAttribute("value"),user.first_name);
         Assert.assertEquals(driver.findElement(last_name).getAttribute("value"),user.last_name);
         Assert.assertEquals(driver.findElement(reg_email).getAttribute("value"),user.reg_email);
         Assert.assertEquals(driver.findElement(password).getAttribute("value"),user.password);
         Assert.assertEquals(driver.findElement(birth_day).getAttribute("value"),user.birth_day);
         Assert.assertEquals(driver.findElement(birth_month).getAttribute("value"),user.birth_month);
         Assert.assertEquals(driver.findElement(birth_year).getAttribute("value"),user.birth_year);
         Assert.assertTrue(driver.findElements(sex).get(user.sex).isSelected());
    }

     @Test
     public void testSubmitButton() {
        openRegForm();
        fillRegForm();
        Assert.assertTrue(driver.findElement(submit_button).isEnabled());
    }

    @After
    public void closeDown() {
        driver.quit();
    }

}
