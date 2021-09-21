package selenium;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Test;
import selenium.pages.MainPage;
import selenium.pages.MainPageWithFindBy;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class MarkupTest extends BaseTest {

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Button click with FindBy annotation")
    public void testButtonClickWithFindByAnnotationPage() {
        MainPageWithFindBy mainPage = new MainPageWithFindBy(driver);

        mainPage.open().clickButtonForInvisible().checkThatButtonInvisible();
        //find test button
        mainPage.open().clickTestButton();
        //confirm Alert
        mainPage.confirmAlert();
        screenshot();

        mainPage
                .generateMarkup() //regenerate Markup
                .clickTestButton(); //find test button again
        mainPage.confirmAlert();  //confirm Alert again

    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Description("Button click with findElement annotation")
    public void testButtonClickWithFindElementPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open()
                .clickTestButton();
        mainPage.confirmAlert();

        mainPage
                .generateMarkup()
                .clickTestButton(); //should be healed
        mainPage.confirmAlert();

    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Description("Button click with disable healing")
    public void testButtonClickWithDisableHealing() {
        MainPageWithFindBy mainPage = new MainPageWithFindBy(driver);
        mainPage.open()
                .clickTestButton()
                .confirmAlert();
        boolean result = mainPage
                .generateMarkup() //regenerate Markup
                .checkLocatorTestButtonDontHealing(); //find test button again
        assertTrue(result, "The locator was not healed");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Select checkboxes with findElements annotation")
    public void testSelectCheckboxes() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();

        while (!mainPage.displayedText())
            mainPage.generateMarkup();

        mainPage.selectAllCheckboxes(); //find via findElements

        boolean result = mainPage.verifyFirstCheckbox();  //should be healed
        assertTrue(result, "Locator for checkbox with findElements has been healed");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Button click with find element by id")
    public void testButtonClickWithId() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open()
                .clickTestButton();
        mainPage.confirmAlert();

        while (!mainPage.testButtonEnable())
            mainPage.generateMarkup();

        for (int i = 0; i < 3; i++)
        {
            mainPage.clickTestGeneratedButton();//should be healed
            mainPage.generateMarkup();
        }
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Description("Select first checkbox and verify using parent:: function in Xpath")
    public void testCheckboxesParentXpath(){
        MainPage mainPage = new MainPage(driver);
        mainPage.open()
                .generateMarkup();

        while (!mainPage.displayedText())
            mainPage.generateMarkup();

        mainPage.selectFirstCheckbox();
        boolean result = mainPage.verifyFirstAccountCheckbox();
        assertTrue(result, "Verify first account checkbox checked");

        mainPage.selectFirstAccountCheckbox();
        result = mainPage.verifyFirstAccountCheckbox(); //should be healed
        assertTrue(result, "Verify first account checkbox unchecked");
    }
}
