import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class SelenideTest {

    @Before
    public void startUp() {
        Configuration.browserSize = "1920x1080";
        open("https://www.facebook.com/");
    }

    private void openRegForm() {
        $(By.xpath(".//*[@data-testid='open-registration-form-button']")).click();
    }

    @Test
    public void testStartPage() {
        $(By.xpath(".//*[@data-testid='open-registration-form-button']")).should(Condition.enabled);
        openRegForm();
        $(".registration_redesign").should(Condition.appear);
    }

    @Test
    public void testRegFormPage() throws InterruptedException {
        openRegForm();

        $(By.xpath(".//*[@name='firstname']")).shouldBe(Condition.visible);
        $(By.xpath(".//*[@name='lastname']")).shouldBe(Condition.visible);
        $(By.xpath(".//*[@name='reg_email__']")).shouldBe(Condition.visible);
        $(By.xpath(".//*[@name='reg_email_confirmation__']")).shouldBe(Condition.hidden);
        $(By.xpath(".//*[@id='password_step_input']")).shouldBe(Condition.visible);
        $(By.xpath(".//*[@id='day']")).shouldBe(Condition.visible);
        $(By.xpath(".//*[@id='month']")).shouldBe(Condition.visible);
        $(By.xpath(".//*[@id='year']")).shouldBe(Condition.visible);

        $$(By.xpath(".//input[@name='sex']")).shouldHave(CollectionCondition.size(3));

        $(By.xpath(".//*[@name='firstname']")).setValue("User");
        $(By.xpath(".//*[@name='lastname']")).setValue("NewComer");
        $(By.xpath(".//*[@name='reg_email__']")).setValue("set.email@gmail.com");
        $(By.xpath(".//*[@id='password_step_input']")).setValue("Qwerty123");
        $(By.xpath(".//*[@id='day']")).selectOptionByValue("27");
        $(By.xpath(".//*[@id='month']")).selectOptionByValue("6");
        $(By.xpath(".//*[@id='year']")).selectOptionByValue("1984");

        $$(By.xpath(".//input[@name='sex']")).get(0).click();

        Thread.sleep(3000);

        $(By.xpath(".//*[@name='reg_email_confirmation__']")).shouldBe(Condition.visible);
        $(By.xpath(".//*[@name='reg_email_confirmation__']")).setValue("set.email@gmail.com");
        $(By.xpath(".//*[@name='reg_email_confirmation__']")).shouldHave(Condition.exactValue("set.email@gmail.com"));

        $(By.xpath(".//*[@name='firstname']")).shouldHave(Condition.exactValue("User"));
        $(By.xpath(".//*[@name='lastname']")).shouldHave(Condition.exactValue("NewComer"));
        $(By.xpath(".//*[@name='reg_email__']")).shouldHave(Condition.exactValue("set.email@gmail.com"));
        $(By.xpath(".//*[@id='password_step_input']")).shouldHave(Condition.exactValue("Qwerty123"));
        $(By.xpath(".//*[@id='day']")).shouldBe(Condition.value("27"));
        $(By.xpath(".//*[@id='month']")).shouldBe(Condition.value("6"));
        $(By.xpath(".//*[@id='year']")).shouldBe(Condition.value("1984"));

        $$(By.xpath(".//input[@name='sex']")).get(0).shouldBe(Condition.selected);

        $(By.xpath(".//button[@name='websubmit']")).shouldBe(Condition.enabled);

        Thread.sleep(3000);
    }


}
