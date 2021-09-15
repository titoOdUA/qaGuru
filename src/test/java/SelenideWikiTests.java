import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SelenideWikiTests {

    @BeforeAll
    static void setup() {
        Configuration.startMaximized = true;
        Configuration.baseUrl = "https://github.com/";
    }

    @Test
    void checkSelenideWikiContainsJunit5AssertionsBlock() {
        open("selenide/selenide");
        $("#wiki-tab").click();
        $("#wiki-pages-box").$(byText("Show 2 more pages…")).click();
        $("#wiki-pages-box").$(byText("SoftAssertions")).click();
        $("#wiki-body").$$("ol")
                .findBy(text("Using JUnit5"))
                .sibling(0).shouldHave(text("@ExtendWith"));
    }
}
