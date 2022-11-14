package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    void getLoginPage() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doMockSignUp(String firstName, String lastName, String userName, String password) {
        // Create a dummy account for logging in later.

        // Visit the sign-up page.
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        driver.get("http://localhost:" + this.port + "/signup");
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

        // Fill out credentials
        webDriverWait.until(visibilityOfElementLocated(By.id("inputFirstName")));
        WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
        inputFirstName.click();
        inputFirstName.sendKeys(firstName);

        webDriverWait.until(visibilityOfElementLocated(By.id("inputLastName")));
        WebElement inputLastName = driver.findElement(By.id("inputLastName"));
        inputLastName.click();
        inputLastName.sendKeys(lastName);

        webDriverWait.until(visibilityOfElementLocated(By.id("inputUsername")));
        WebElement inputUsername = driver.findElement(By.id("inputUsername"));
        inputUsername.click();
        inputUsername.sendKeys(userName);

        webDriverWait.until(visibilityOfElementLocated(By.id("inputPassword")));
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));
        inputPassword.click();
        inputPassword.sendKeys(password);

        // Attempt to sign up.
        webDriverWait.until(visibilityOfElementLocated(By.id("buttonSignUp")));
        WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
        buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
        assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
    }


    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doLogIn(String userName, String password) {
        // Log in to our dummy account.
        driver.get("http://localhost:" + this.port + "/login");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        webDriverWait.until(visibilityOfElementLocated(By.id("inputUsername")));
        WebElement loginUserName = driver.findElement(By.id("inputUsername"));
        loginUserName.click();
        loginUserName.sendKeys(userName);

        webDriverWait.until(visibilityOfElementLocated(By.id("inputPassword")));
        WebElement loginPassword = driver.findElement(By.id("inputPassword"));
        loginPassword.click();
        loginPassword.sendKeys(password);

        webDriverWait.until(visibilityOfElementLocated(By.id("login-button")));
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        webDriverWait.until(ExpectedConditions.titleContains("Home"));

    }

    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling redirecting users
     * back to the login page after a succesful sign up.
     * Read more about the requirement in the rubric:
     * https://review.udacity.com/#!/rubrics/2724/view
     */
    @Test
    void testRedirection() {
        // Create a test account
        doMockSignUp("Redirection", "Test", "RT", "123");

        // Check if we have been redirected to the log in page.
        assertTrue(driver.getCurrentUrl().contains("http://localhost:" + this.port + "/login"));
    }

    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling bad URLs
     * gracefully, for example with a custom error page.
     * <p>
     * Read more about custom error pages at:
     * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
     */
    @Test
    void testBadUrl() {
        // Create a test account
        doMockSignUp("URL", "Test", "UT", "123");
        doLogIn("UT", "123");

        // Try to access a random made-up URL.
        driver.get("http://localhost:" + this.port + "/some-random-page");
        Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
    }


    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling uploading large files (>1MB),
     * gracefully in your code.
     * <p>
     * Read more about file size limits here:
     * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
     */
    @Test
    void testLargeUpload() {
        // Create a test account
        doMockSignUp("Large File", "Test", "LFT", "123");
        doLogIn("LFT", "123");

        // Try to upload an arbitrary large file
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        String fileName = "upload5m.zip";

        webDriverWait.until(visibilityOfElementLocated(By.id("fileUpload")));
        WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
        fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

        WebElement uploadButton = driver.findElement(By.id("uploadButton"));
        uploadButton.click();
        try {
            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("Large File upload failed");
        }
        Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

    }

    @Test
    void testUnauthorizedUserCanOnlyAccessLoginAndSignupPages() {
        driver.get("http://localhost:" + this.port + "/login");
        assertEquals(driver.getTitle(), "Login");

        driver.get("http://localhost:" + this.port + "/signup");
        assertEquals(driver.getTitle(), "Sign Up");
    }

    @ParameterizedTest
    @ValueSource(strings = {"/some-random-page", "/home"})
    void testUnauthorizedUserCannotAccessAuthorizedPages(String path) {
        driver.get("http://localhost:" + this.port + path);
        assertEquals(driver.getTitle(), "Login");
    }

    @Test
    void testSignsUpLogsIn_HomePageIsAccessible_LogsOut_HomePageIsNoLongerAccessible() {
        doMockSignUp("Large File", "Test", "SULI", "123");
        doLogIn("SULI", "123");

        driver.get("http://localhost:" + this.port + "/home");
        assertEquals(driver.getTitle(), "Home");

        driver.findElement(By.id("logout")).click();
        assertEquals(driver.getTitle(), "Login");

        driver.get("http://localhost:" + this.port + "/home");
        assertEquals(driver.getTitle(), "Login");
    }

    @ParameterizedTest
    @MethodSource("provideNoteTestData")
    void testNoteCRUD(String usn, String pwd, String title, String desc) {
        var wait = new WebDriverWait(driver, 20);
        doMockSignUp("Large File", "Test", usn, pwd);
        doLogIn(usn, pwd);

        // create
        driver.findElement(By.id("nav-notes-tab")).click();
        wait.until(elementToBeClickable(By.cssSelector("#nav-notes button"))).click();
        wait.until(visibilityOfElementLocated(By.name("notetitle"))).sendKeys(title);
        driver.findElement(By.name("notedescription")).sendKeys(desc);
        driver.findElement(By.cssSelector("#noteModal .btn-primary")).click();
        driver.findElement(By.tagName("a")).click();
        wait.until(visibilityOfElementLocated(By.id("nav-notes-tab"))).click();
        var cols = wait.until(visibilityOfElementLocated(By.cssSelector("#noteTable tbody tr")))
                .findElements(By.cssSelector("td,th"));
        assertEquals(cols.get(1).getText(), title);
        assertEquals(cols.get(2).getText(), desc.replace("\n", " "));

        // update
        var newTitle = title + "test";
        var newDesc = desc + "test";
        driver.findElement(By.id("nav-notes-tab")).click();
        wait.until(visibilityOfElementLocated(By.cssSelector("#noteTable tbody tr")))
                .findElement(By.cssSelector("td button"))
                .click();
        wait.until(visibilityOfElementLocated(By.name("notetitle"))).clear();
        driver.findElement(By.name("notetitle")).sendKeys(newTitle);
        driver.findElement(By.name("notedescription")).clear();
        driver.findElement(By.name("notedescription")).sendKeys(newDesc);
        driver.findElement(By.cssSelector("#noteModal .btn-primary")).click();
        driver.findElement(By.tagName("a")).click();
        wait.until(visibilityOfElementLocated(By.id("nav-notes-tab"))).click();
        cols = wait.until(visibilityOfElementLocated(By.cssSelector("#noteTable tbody tr")))
                .findElements(By.cssSelector("td,th"));
        assertEquals(cols.get(1).getText(), newTitle);
        assertEquals(cols.get(2).getText(), newDesc.replace("\n", " "));

        // delete
        driver.findElement(By.id("nav-notes-tab")).click();
        wait.until(visibilityOfElementLocated(By.cssSelector("#noteTable tbody tr")))
                .findElement(By.cssSelector("td a"))
                .click();
        wait.until(visibilityOfElementLocated(By.id("nav-notes-tab"))).click();
        var rows = wait.until(visibilityOfElementLocated(By.cssSelector("#noteTable")))
                .findElements(By.cssSelector("tbody tr"));
        assertTrue(rows.isEmpty());
    }

    private static Stream<Arguments> provideNoteTestData() throws NoSuchAlgorithmException {
        return Stream.of(
                Arguments.of(genUsn("NOTE_CRUD"), "123", "test", "test"),
                Arguments.of(genUsn("NOTE_CRUD"), "123", "test1", "abc\ndef\nghj")
        );
    }

    private static String genUsn(String testId) throws NoSuchAlgorithmException {
        return testId + UUID.randomUUID();
    }
}
