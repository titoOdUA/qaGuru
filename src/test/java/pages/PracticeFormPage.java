package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import components.DatePickerComponent;

import java.io.File;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$x;

public class PracticeFormPage {

    public final String urlPart = "/automation-practice-form";
    public final DatePickerComponent datePicker = new DatePickerComponent();
    public final SelenideElement filledFormPopUp = $(".modal-content");

    public void uploadPicture(String fileName) {
        $("#uploadPicture").uploadFile(new File("./src/test/resources/" + fileName));
    }

    public void fillFormWithAllData(String name, String lastName, String email, String phoneNumber,
                                     String monthOfBirth, String yearOfBirth,
                                     String subject, String currentAddress, String state, String city,
                                     String fileName, String dayOfBirth) {
        $("#firstName").setValue(name);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(email);
        $("[for=gender-radio-1]").click();
        $("#userNumber").setValue(phoneNumber);
        datePicker.open();
        datePicker.selectMonth(monthOfBirth);
        datePicker.selectYear(yearOfBirth);
        datePicker.selectDay(dayOfBirth);
        $("#subjectsContainer").find("input").setValue(subject).pressEnter();
        $("[for=hobbies-checkbox-2]").click();
        $("#currentAddress").setValue(currentAddress);
        $("#state").find("input").setValue(state).pressEnter();
        $("#city").find("input").setValue(city).pressEnter();
        uploadPicture(fileName);
        $("#submit").scrollTo().click();
    }

    public void checkFilledForm(Map<String, String> expectedFormValues) {
        filledFormPopUp.shouldBe(Condition.visible);
        ElementsCollection formRows = $$x("//div[@class='modal-content']//tbody/tr");
        formRows.forEach(e -> {
                    ElementsCollection rowColumns = e.findAll("td");
                    rowColumns.get(1).shouldHave(Condition.text(
                            expectedFormValues.get(rowColumns.get(0).text())));
                }
        );
    }
}
