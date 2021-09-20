package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import pages.PracticeFormPage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.*;

public class PracticeFormTests extends BaseTest{

    Faker faker = new Faker();
    PracticeFormPage practiceFormPage = new PracticeFormPage();

    @Test
    void checkFormSubmit() {
        String name = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();
        String phoneNumber = faker.phoneNumber().subscriberNumber(10);
        String monthOfBirth = "July";
        String yearOfBirth = "1993";
        String dayOfBirth = "14";
        String subject = "Chemistry";
        String currentAddress = faker.address().fullAddress();
        String state = "NCR";
        String city = "Delhi";
        String fileName = "meme.jpg";

        open(practiceFormPage.urlPart);

        //заполняем все поля формы
        practiceFormPage.fillFormWithAllData(name, lastName, email, phoneNumber, monthOfBirth,
                yearOfBirth, subject, currentAddress, state, city, fileName, dayOfBirth);

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

        practiceFormPage.checkFilledForm(expectedFormValues);
    }


}
