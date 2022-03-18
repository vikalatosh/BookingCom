package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.BookingMainPage;
import pages.BookingSearchPage;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

public class SearchSteps {
    private static final String BOOKING_URL = "https://www.booking.com/searchresults.en-gb.html";
    private static final String RATING_LOCATOR = "//*[contains(text(),'%s')]//ancestor::" +
            "*[@class='sr_property_block_main_row']//*[@class='bui-review-score__badge']";
    private static final String APARTMENTS_TEXT_LOCATOR = "//a/*[contains(text(),'%s')]";
    String apartmentsName;
    String apartmentsRating;
    WebDriver driver;
    BookingSearchPage searchPage;
    BookingMainPage bookingMainPage;

    @Before
    public void init() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
//        options.addArguments("--headless");
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Given("Keyword for apartments search is {string}")
    public void keywordForApartmentsSearchIs(String name) {
        apartmentsName = name;
    }

    @When("User does search")
    public void userDoesSearch() {
        driver.get(BOOKING_URL);
        WebElement element = driver.findElement(By.id("ss"));
        element.sendKeys(apartmentsName);
        element.submit();
    }

    @Then("Apartments {string} are on the first page")
    public void apartmentsAreOnTheFirstPage(String result) {
        apartmentsName = result;
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(By.xpath(String.format(APARTMENTS_TEXT_LOCATOR, apartmentsName)));
        js.executeScript("arguments[0].setAttribute('style', 'background: #3cff3c; border: 2px solid red;');", element);
        assertEquals(element.getText().trim(), result);
    }

    @After
    public void tearDown() {
        driver.close();
    }

    @And("Apartments should have {string}")
    public void apartmentsShouldHaveRating(String rating) {
        WebElement element = driver.findElement(By.xpath(String.format(RATING_LOCATOR, apartmentsName)));
        assertEquals(element.getText().trim(), rating);
    }
}
