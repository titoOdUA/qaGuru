package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import steps.WebSteps;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;
import static org.openqa.selenium.By.partialLinkText;

public class GithubTests {

    private final String targetRepo = "eroshenkoam/allure-example";

    @BeforeEach
    void setup() {
        Configuration.startMaximized = true;
    }

    @Test
    public void checkIssueIsExistWithPlainSelenide() {
        open("https://github.com");

        $(".header-search-input")
                .setValue(targetRepo)
                .submit();
        $(linkText(targetRepo)).click();
        $(partialLinkText("Issues")).click();
        $(withText("#68")).should(visible);
    }

    @Test
    public void checkIssueIsExistWithAllureLambdaSteps() {
        step("open github.com", () -> open("https://github.com"));

        step("find eroshenkoam's allure-example repository", () ->
                $(".header-search-input")
                        .setValue(targetRepo)
                        .submit()
        );

        step("open allure-example repo", () -> $(linkText(targetRepo)).click());
        step("open issues tab", () -> $(partialLinkText("Issues")).click());
        step("confirm issue #68 is visible", () -> $(withText("#68")).should(visible));
    }

    @Test
    public void checkIssueIsExistWithAnnotatedSteps() {
        WebSteps steps = new WebSteps();

        steps.openGithub();
        steps.findRepository(targetRepo);
        steps.openRepoFromSearchResults(targetRepo);
        steps.openIssuesTab();
        steps.checkIssueIsVisible(68);
    }


}
