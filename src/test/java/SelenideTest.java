import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

@RunWith(value = Parameterized.class)
public class SelenideTest {

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

    public SelenideTest(FBClassUser user) {
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
        Configuration.browserSize = "1920x1080";
        open("https://www.facebook.com/");
    }

    private void openRegForm() {
        $(reg_button).click();
    }

    private void fillRegForm() {
        $(first_name).setValue(user.first_name);
        $(last_name).setValue(user.last_name);
        $(reg_email).setValue(user.reg_email);
        $(password).setValue(user.password);
        $(birth_day).selectOptionByValue(user.birth_day);
        $(birth_month).selectOptionByValue(user.birth_month);
        $(birth_year).selectOptionByValue(user.birth_year);
        $$(sex).get(user.sex).click();
    }

    private void fillRegConfirmEmail() {
        $(reg_email_confirm).setValue(user.reg_email);
    }

    @Test
    public void testStartPage() {
        $(reg_button).should(Condition.enabled);
        openRegForm();
        $(".registration_redesign").should(Condition.appear);
    }

    @Test
    public void testRegFormPage() {
        openRegForm();
        $(first_name).shouldBe(Condition.visible);
        $(last_name).shouldBe(Condition.visible);
        $(reg_email).shouldBe(Condition.visible);
        $(reg_email_confirm).shouldBe(Condition.hidden);
        $(password).shouldBe(Condition.visible);
        $(birth_day).shouldBe(Condition.visible);
        $(birth_month).shouldBe(Condition.visible);
        $(birth_year).shouldBe(Condition.visible);
        $$(sex).shouldHave(CollectionCondition.size(3));
    }

     @Test
     public void testRegFormConfirmEmailFill() {
         openRegForm();
         fillRegForm();
         $(reg_email_confirm).shouldBe(Condition.visible);
         fillRegConfirmEmail();
         $(reg_email_confirm).shouldHave(Condition.exactValue(user.reg_email));
     }

     @Test
     public void testRegFormFill() throws InterruptedException {
         openRegForm();
         fillRegForm();
         fillRegConfirmEmail();

         Thread.sleep(3000);

         $(first_name).shouldHave(Condition.exactValue(user.first_name));
         $(last_name).shouldHave(Condition.exactValue(user.last_name));
         $(reg_email).shouldHave(Condition.exactValue(user.reg_email));
         $(password).shouldHave(Condition.exactValue(user.password));
         $(birth_day).shouldBe(Condition.value(user.birth_day));
         $(birth_month).shouldBe(Condition.value(user.birth_month));
         $(birth_year).shouldBe(Condition.value(user.birth_year));
         $$(sex).get(user.sex).shouldBe(Condition.selected);
    }

     @Test
     public void testSubmitButton() {
        openRegForm();
        fillRegForm();

        $(submit_button).shouldBe(Condition.enabled);
    }

}
