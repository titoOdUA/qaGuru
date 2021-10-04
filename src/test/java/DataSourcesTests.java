import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;

public class DataSourcesTests {

    @BeforeAll
    static void setup() {
        Configuration.startMaximized = true;
        Configuration.baseUrl = "https://serpstat.com";
    }

    /*
    Dummy test с примером использования CsvSource провайдера
    */
    @ParameterizedTest
    @CsvSource({
            "example@gmailcom, An email has incorrect format!",
            ", This field can`t be empty."
    })
    void checkEmailValidation(String email, String expectedError) {
        open("/login");
        $("input[placeholder='Email']").setValue(email);
        $("button[type='submit']").click();
        $(".validate-error").shouldHave(exactTextCaseSensitive(expectedError));
    }

    /*
    Dummy test с примером использования EnumSource провайдера
    */
    @ParameterizedTest
    @EnumSource(mode = EXCLUDE, names = {"VALID_PASSWORD"})
    void checkPasswordValidation(PasswordENUM password) {
        open("/login");
        $("input[placeholder='Email']").setValue("example@gmail.com");
        $("input[placeholder='Password']").setValue(password.text);
        $("button[type='submit']").click();
        $(".validate-error").shouldBe(visible);
    }

    /*
    Dummy test с примером использования MethodSource провайдера
    */
    @ParameterizedTest
    @MethodSource("credentialsMethodSource")
    void checkLoginFormValidation(String email, String password) {
        open("/login");
        $("input[placeholder='Email']").setValue(email);
        $("input[placeholder='Password']").setValue(password);
        $("button[type='submit']").click();
        $(".validate-error").shouldBe(visible);
    }

    static Stream<Arguments> credentialsMethodSource() {
        return Stream.of(
                arguments("example@gmail.com", PasswordENUM.EMPTY_PASSWORD.text),
                arguments("example@gmailcom", PasswordENUM.VALID_PASSWORD.text)
        );
    }

}
