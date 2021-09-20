package steps;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.By.linkText;
import static org.openqa.selenium.By.partialLinkText;

public class WebSteps {
    @Step("Open github main page")
    public void openGithub() {
        open("https://github.com");
    }

    @Step("Find {repoName} repository")
    public void findRepository(String repoName) {
        $(".header-search-input")
                .setValue(repoName)
                .submit();
    }

    @Step("Open {repoName} repository from search results")
    public void openRepoFromSearchResults(String repoName) {
        $(linkText(repoName)).click();
    }

    @Step("Open issues tab")
    public void openIssuesTab() {
        $(partialLinkText("Issues")).click();
    }

    @Step("Check that issue {issueNumber} is visible")
    public void checkIssueIsVisible(int issueNumber) {
        $(withText("#" + issueNumber)).should(visible);
    }

}
