package automation.practice;

import java.util.Collection;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

@RunWith(Parameterized.class)
public class SearchTest {
	// Driver for both of browsers: Firefox & Chrome
	static WebDriver driver;
	private WebDriver browser;

	// The website element
	WebElement element;

	public SearchTest(WebDriver browser) {
		this.browser = browser;
	}

	@Parameters
	public static Collection<Object[]> data() {
		// Path for Chrome driver
		String path = "D:\\Installer\\chromedriver.exe";

		// Specify path for Chrome driver
		System.setProperty("webdriver.chrome.driver", path);

		Object[][] data = new Object[][] { { new ChromeDriver() },
				{ new FirefoxDriver() } };

		return Arrays.asList(data);
	}

	@Before
	public void setUp() {
		// Get the browser
		driver = browser;

		// Wait for 10 seconds before throwing an exception
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Launch the Automation Practice website
		driver.get("http://automationpractice.com");
	}

	@Test
	public void testSearchBoxExists() {
		// Find the Search box element by its id
		element = driver.findElement(By.id("searchbox"));

		// Asserts that the element isn't null
		Assert.assertNotNull(element);
	}

	@Test
	public void testSearchButtonExists() {
		// Find the Search button element by its name
		element = driver.findElement(By.name("submit_search"));

		// Asserts that the element isn't null
		Assert.assertNotNull(element);
	}

	@Test
	public void testSearchResultUsingEmptyKeyword() {
		// Launch the Automation Practice website
		driver.get("http://automationpractice.com");

		// The expected result
		String expected = "Please enter a search keyword";

		// Find the Search button element by its name
		element = driver.findElement(By.name("submit_search"));

		// Perform click event
		element.click();

		// Find the Search result
		element = driver.findElement(By.className("alert-warning"));

		// Get the text from Search result
		String actual = element.getText();

		// Compare the expected VS actual result
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testSearchResultUsingInvalidKeyword() {
		// The expected result
		String expected = "0 results have been found.";

		// Text input for keyword
		String keyword = "InvalidKeyword@123";

		// Find the Search box element by its name
		element = driver.findElement(By.name("search_query"));

		// Enter keyword to search for
		element.sendKeys(keyword);

		// Now submit the form
		element.submit();

		// Find the Search result
		element = driver.findElement(By.className("heading-counter"));

		// Get the text from Search result
		String actual = element.getText();

		// Compare the expected VS actual result
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testSearchResultUsingValidKeyword() {
		// Text input for keyword
		String keyword = "Dress";

		// The expected result
		String expected = "\"" + keyword.toUpperCase() + "\"";

		// Find the Search box element by its name
		element = driver.findElement(By.name("search_query"));

		// Enter keyword to search for
		element.sendKeys(keyword);

		// Now submit the form
		element.submit();

		// Find the Search result
		element = driver.findElement(By.className("lighter"));

		// Get the text from Search result
		String actual = element.getText();

		// Compare the expected VS actual result
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testUrlFromSearchResult() {
		// Launch the Automation Practice website
		driver.get("http://automationpractice.com");

		// Text input for keyword
		String keyword = "Blouse";

		// The expected result
		String expected = "http://automationpractice.com/index.php?controller=search&orderby=position&orderway=desc&search_query=Blouse";

		// Find the Search box element by its name
		element = driver.findElement(By.name("search_query"));

		// Enter keyword to search for
		element.sendKeys(keyword);

		// Now submit the form
		element.submit();

		// Get the Url from Search result
		String actual = driver.getCurrentUrl();

		// Compare the expected VS actual result
		Assert.assertEquals(expected, actual);
	}

	@AfterClass
	public static void closeBrowser() {
		// Close the browser
		driver.close();
	}
}
