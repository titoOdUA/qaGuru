import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.*;

public class PracticeFormTests {

    @BeforeAll
    static void setup() {
        Configuration.startMaximized = true;
    }

    @Test
    void checkFormSubmit() {
        String name = "Tito";
        String lastName = "Dou";
        String email = "tito@example.com";
        String phoneNumber = "1111111111";
        String monthOfBirth = "July";
        String yearOfBirth = "1993";
        String subject = "Chemistry";
        String currentAddress = "Ukraine, Odessa";
        String state = "NCR";
        String city = "Delhi";
        String fileName = "meme.jpg";

        open("https://demoqa.com/automation-practice-form");

        //заполняем все поля формы
        fillFormWithAllData(name, lastName, email, phoneNumber, monthOfBirth,
                yearOfBirth, subject, currentAddress, state, city, fileName);

        //проверяем что появилось модальное окно с заполненной формой
        $(".modal-content").shouldBe(Condition.visible);

        //словарь ожидаемых значений рядов формы
        Map<String, String> expectedFormValues = new HashMap<>();
        expectedFormValues.put("Student Name", name + " " + lastName);
        expectedFormValues.put("Student Email", email);
        expectedFormValues.put("Gender", "Male");
        expectedFormValues.put("Mobile", phoneNumber);
        expectedFormValues.put("Date of Birth", "14 July,1993");
        expectedFormValues.put("Subjects", subject);
        expectedFormValues.put("Hobbies", "Reading");
        expectedFormValues.put("Picture", "meme.jpg");
        expectedFormValues.put("Address", currentAddress);
        expectedFormValues.put("State and City", state + " " + city);

        //достаем коллекцию элементов рядов из тела таблицы, берем значение колонок и проверяем на соответствие мапе
        ElementsCollection formRows = $$x("//div[@class='modal-content']//tbody/tr");
        formRows.forEach(e -> {
                    ElementsCollection rowColumns = e.findAll("td");
                    rowColumns.get(1).shouldHave(Condition.text(
                            expectedFormValues.get(rowColumns.get(0).text())));
                }
        );
    }

    private void fillFormWithAllData(String name, String lastName, String email, String phoneNumber,
                                     String monthOfBirth, String yearOfBirth,
                                     String subject, String currentAddress, String state, String city,
                                     String fileName) {
        $("#firstName").setValue(name);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(email);
        $("[for=gender-radio-1]").click();
        $("#userNumber").setValue(phoneNumber);
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption(monthOfBirth);
        $(".react-datepicker__year-select").selectOption(yearOfBirth);
        $x("//div[contains(@class,\"react-datepicker__day react-datepicker__day--0" + "14" + "\")" +
                "and not(contains(@class,'outside-month'))]").click();
        $("#subjectsContainer").find("input").setValue(subject).pressEnter();
        $("[for=hobbies-checkbox-2]").click();
        $("#currentAddress").setValue(currentAddress);
        $("#state").find("input").setValue(state).pressEnter();
        $("#city").find("input").setValue(city).pressEnter();
        $("#uploadPicture").uploadFile(new File("./" + fileName));
        $("#submit").scrollTo().click();
    }
}
