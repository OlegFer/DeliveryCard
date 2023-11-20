
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;



public class DeliveryCardTest {
    public static String setDateDeliveryCard(long addDays, String pattern) {
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void testDeliveryCardPositive() {
        open("http://localhost:9999");
        $x("//span[@data-test-id='city']//input[@placeholder='Город']").setValue("Москва");
        String setDate = setDateDeliveryCard(3, "dd.MM.yyyy");
        $x("//span[@data-test-id='date']//input[@placeholder='Дата встречи']").doubleClick().sendKeys(setDate);
        $x("//span[@data-test-id='name']//input[@name='name']").setValue("Иванов Петр");
        $x("//span[@data-test-id='phone']//input[@name='phone']").setValue("+79507777777");
        $x("//label[@data-test-id='agreement']").click();
        $(".button").click();
        $x("//div[@class='notification__title']").shouldHave(text("Успешно!"),
                Duration.ofSeconds(15)).shouldBe(visible);
        $x("//div[@class='notification__content']")
                .shouldHave(text("Встреча успешно забронирована на " + setDate)
                        , Duration.ofSeconds(15)).shouldBe(visible);
    }
}
