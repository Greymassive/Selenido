package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;

public class CardRequestTest {
    @Test
    void shouldSendValidData() {
        open("http://localhost:9999");
        $("[data-test-id='name'] input").setValue("Пупкин Акакий Петкунович");
        $("[data-test-id='phone'] input").setValue("+79506669999");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='order-success']").shouldHave(exactText("   Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldInvalidNameTest() {
        open("http://localhost:9999");
        $("[data-test-id='name'] input").setValue("sdfdsfsf");
        $("[data-test-id='phone'] input").setValue("+79506669999");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldSkipNameTest() {
        open("http://localhost:9999");
        $("[data-test-id='name'] input").setValue("");
        $("[data-test-id='phone'] input").setValue("+79506669999");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldInvalidPhoneTest() {
        open("http://localhost:9999");
        $("[data-test-id='name'] input").setValue("Пупкин Акакий Петкунович");
        $("[data-test-id='phone'] input").setValue("234234");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldSkipPhoneTest() {
        open("http://localhost:9999");
        $("[data-test-id='name'] input").setValue("Пупкин Акакий Петкунович");
        $("[data-test-id='phone'] input").setValue("");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSendDataWithoutAgreement() {
        open("http://localhost:9999");
        $("[data-test-id='name'] input").setValue("Пупкин Акакий Петкунович");
        $("[data-test-id='phone'] input").setValue("+79506669999");
        $(".button").click();
        $("[data-test-id='agreement']").shouldHave(Condition.cssClass("input_invalid"));
    }
}
