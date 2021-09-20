package components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class DatePickerComponent {

    SelenideElement month = $(".react-datepicker__month-select");
    SelenideElement year = $(".react-datepicker__year-select");

    public void open() {
        $("#dateOfBirthInput").click();
    }

    public void selectMonth(String month) {
        $(".react-datepicker__month-select").selectOption(month);
    }

    public void selectYear(String year) {
        $(".react-datepicker__year-select").selectOption(year);
    }

    public void selectDay(String day) {
        $x(String.format("//div[contains(@class,\"react-datepicker__day react-datepicker__day--0%s\")" +
                "and not(contains(@class,'outside-month'))]", day)).click();
    }
}
