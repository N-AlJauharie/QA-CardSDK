package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CardPage {

    private WebDriver driver;
    private WebDriverWait wait;


    public CardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }


    private By currencyDropdown = By.id("currency");
    private By cardNumberField = By.xpath("//*[@id='card_input_mini']");
    private By expiryField = By.id("date_input");
    private By cvvField = By.id("cvv_input");
    private By createTokenButton = By.xpath("//*[@id='root']/section/article/div[3]/button[1]");
    private By tokenIdLocator = By.xpath("//*[@id='root']/section/aside/article[2]/pre/code/span[9]");
    private By tokenBrandLocator = By.xpath("//div[@id='root']/section/aside/article[2]/pre/code/span[139]");
    private By tokenSchemeLocator = By.xpath("//div[@id='root']/section/aside/article[2]/pre/code/span[147]");
    private By responseRadioButton = By.cssSelector("input[name='preview'][value='response']");
    private By authenticationStatus = By.xpath("//*[@id=\"root\"]/section/aside/article[2]/pre/code/span[41]");
    private By responseMessage = By.xpath("//*[@id=\"root\"]/section/aside/article[2]/pre/code/span[142]");
    private By authenticationTokenBrandLocator = By.xpath("//*[@id=\"root\"]/section/aside/article[2]/pre/code/span[374]");
    private By getAuthenticationTokenSchemeLocator = By.xpath("//*[@id=\"root\"]/section/aside/article[2]/pre/code/span[366]");
    private By SubmitBttn = By.id("acssubmit");


    public void selectCurrency(String currency) {
        WebElement currencyDropdownElement = wait.until(ExpectedConditions.visibilityOfElementLocated(currencyDropdown));
        Select selectCurrency = new Select(currencyDropdownElement);
        selectCurrency.selectByVisibleText(currency);
    }


    public void enterCardDetails(String cardNumber, String expiry, String cvv) {
        driver.switchTo().frame("tap-card-iframe");

        WebElement cardNumberElement = wait.until(ExpectedConditions.elementToBeClickable(cardNumberField));
        cardNumberElement.clear();
        cardNumberElement.sendKeys(cardNumber);

        WebElement expiryElement = wait.until(ExpectedConditions.elementToBeClickable(expiryField));
        expiryElement.clear();
        expiryElement.sendKeys(expiry);

        WebElement cvvElement = wait.until(ExpectedConditions.elementToBeClickable(cvvField));
        cvvElement.clear();
        cvvElement.sendKeys(cvv);

        driver.switchTo().defaultContent();
    }


    /*
        public void enterCardDetails(String cardNumber, String expiry, String cvv) {

            driver.switchTo().frame("tap-card-iframe");

            WebElement cardNumberElement = wait.until(ExpectedConditions.presenceOfElementLocated(cardNumberField));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cardNumberElement);

            ((JavascriptExecutor) driver).executeScript("arguments[0].value='';", cardNumberElement);
            ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + cardNumber + "';", cardNumberElement);


            WebElement expiryElement = wait.until(ExpectedConditions.presenceOfElementLocated(expiryField));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", expiryElement);
            ((JavascriptExecutor) driver).executeScript("arguments[0].value='';", expiryElement);
            ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + expiry + "';", expiryElement);

            WebElement cvvElement = wait.until(ExpectedConditions.presenceOfElementLocated(cvvField));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cvvElement);
            ((JavascriptExecutor) driver).executeScript("arguments[0].value='';", cvvElement);
            ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + cvv + "';", cvvElement);

            driver.switchTo().defaultContent();
        }


     */
    public void clickCreateToken() {
        wait.until(ExpectedConditions.elementToBeClickable(createTokenButton)).click();
        System.out.println("clicked on token bttn");

        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();


        }
    }

    // Method to click on the response radio button
    //  public void clickResponseRadioButton() {
    //    wait.until(ExpectedConditions.elementToBeClickable(responseRadioButton)).click();
    //  }

    public void clickResponseRadioButton() {
        try {
            WebElement responseRadio = new WebDriverWait(driver, Duration.ofSeconds(20))
                    .until(ExpectedConditions.elementToBeClickable(responseRadioButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", responseRadio);
            responseRadio.click();
        } catch (TimeoutException e) {
            System.out.println("Response radio button not clickable via standard method. Trying JavaScript click...");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.querySelector(\"input[name='preview'][value='response']\").click();");
        }
    }

/*
    public String getTokenId() {
        try {
            WebElement tokenIdElement = new WebDriverWait(driver, Duration.ofSeconds(20))
                    .until(ExpectedConditions.visibilityOfElementLocated(tokenIdLocator));
            return tokenIdElement.getText();
        } catch (TimeoutException e) {
            System.out.println("Token ID not visible via standard method. Trying JavaScript retrieval...");

            JavascriptExecutor js = (JavascriptExecutor) driver;
            return (String) js.executeScript("return document.querySelector('#root section aside article:nth-of-type(2) pre code span:nth-of-type(9)').innerText;");
        }
    }


 */

    public String getTokenId() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(tokenIdLocator)).getText();
    }

    public String getTokenBrand() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(tokenBrandLocator)).getText();
    }

    public String getTokenScheme() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(tokenSchemeLocator)).getText();
    }

    public String getAuthenticationStatus() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(authenticationStatus)).getText();
    }


    public String getResponseMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(responseMessage)).getText();
    }

    public String getAuthenticationTokenBrand() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(authenticationTokenBrandLocator)).getText();
    }

    public String getAuthenticationTokenScheme() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(getAuthenticationTokenSchemeLocator)).getText();
    }


    public List<String> getAvailableCurrencies() {
        List<String> currencies = new ArrayList<>();
        WebElement currencyDropdownElement = wait.until(ExpectedConditions.visibilityOfElementLocated(currencyDropdown));
        Select select = new Select(currencyDropdownElement);

        for (WebElement option : select.getOptions()) {
            currencies.add(option.getText());
        }
        return currencies;
    }

    public void selectScope(String scope) {
        WebElement scopeDropdown = driver.findElement(By.id("scope"));
        scopeDropdown.click();

        WebElement option = driver.findElement(By.xpath("//*[@id='scope']/option[text()='" + scope + "']"));
        option.click();
    }

    public void selectPurpose(String purpose) {
        WebElement purposeDropdown = driver.findElement(By.id("purpose"));
        Select select = new Select(purposeDropdown);
        select.selectByVisibleText(purpose);
    }

    /*
    public void handleNestedAuthenticationFrameAndSubmit() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.name("tapFrame")));

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.name("challengeFrame")));

        Thread.sleep(2000);

        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("acssubmit")));
        submitButton.click();


        driver.switchTo().defaultContent();

        /*
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60), Duration.ofMillis(1000));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            driver.switchTo().frame("challengeFrame");
            System.out.println("Switched to challengeFrame.");
        } catch (NoSuchFrameException e) {
            System.out.println("Frame not available. Proceeding without switching to the frame.");
        }
        try {
            js.executeScript("document.getElementById('acssubmit').click();");
            System.out.println("Submit button clicked via JavaScript.");
        } catch (Exception e) {
            System.out.println("Failed to click submit button via JavaScript.");
            e.printStackTrace();
        }
        try {
            WebElement submitButton = driver.findElement(By.id("acssubmit"));
            submitButton.click();
            System.out.println("Submit button clicked via Selenium.");
        } catch (Exception e) {
            System.out.println("Failed to click the submit button via Selenium.");
            e.printStackTrace();
        }

         */

/*
    public void handleAuthenticationFrameAndSubmit() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));


        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.name("tapFrame")));

        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("acssubmit")));
        submitButton.click();

        driver.switchTo().defaultContent();
    }

 */
/*
    public void handleAuthenticationFrameAndSubmit() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.name("tapFrame")));

        try {
            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("acssubmit")));
            submitButton.click();
        } catch (Exception e) {
            WebElement submitButton = driver.findElement(By.id("acssubmit"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);
        }

        driver.switchTo().defaultContent();
    }

    public void clickSubmitBttn() {
        wait.until(ExpectedConditions.elementToBeClickable(SubmitBttn)).click();
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

 */

/*
    public void handleAuthenticationFrameAndSubmit() {



        driver.switchTo().frame("tap-card-iframe");
        System.out.println("1st frame loaded...");


        try {

            WebElement authenticationFrame = (WebElement) wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("tap-card-iframe-authentication")));
            System.out.println("Second frame loaded...");

            WebElement challengeFrame = (WebElement) wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("challengeFrame")));
            System.out.println("third frame loaded...");

            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("acssubmit")));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", submitButton);

        } catch (TimeoutException toee) {
            try {

                WebElement redirectTo3ds1Frame = (WebElement) wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("redirectTo3ds1Frame")));
                WebElement redirectSubmitButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div/div/div[3]/center/form/table/tbody/tr[13]/td/input")));
                redirectSubmitButton.click();
            } catch (TimeoutException toeee) {
                System.out.println("Authentication frames not available within the expected time.");
            }
        }
    }


 */

    /*
    public void handleAuthenticationFrameAndSubmit() {
        try {
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("tap-card-iframe")));
            System.out.println("1st frame (tap-card-iframe) loaded...");

            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("tap-card-iframe-authentication")));
            System.out.println("2nd frame (tap-card-iframe-authentication) loaded...");

            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("challengeFrame")));
            System.out.println("3rd frame (challengeFrame) loaded...");

            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("acssubmit")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);
            System.out.println("Submit button clicked successfully.");

        } catch (TimeoutException e) {
            System.out.println("Timeout while waiting for frames or elements: " + e.getMessage());
        }
    }

     */

    /*

    public void handleAuthenticationFrameAndSubmit() {

        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(30));

        try {
            if (isFramePresent("tap-card-iframe")) {
                customWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("tap-card-iframe")));
                System.out.println("1st frame (tap-card-iframe) loaded...");
            } else {
                System.out.println("1st frame (tap-card-iframe) not found.");
                return;
            }

            if (isFramePresent("tap-card-iframe-authentication")) {
                customWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("tap-card-iframe-authentication")));
                System.out.println("2nd frame (tap-card-iframe-authentication) loaded...");
            } else {
                System.out.println("2nd frame (tap-card-iframe-authentication) not found.");
                return;
            }

            if (isFramePresent("challengeFrame")) {
                customWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("challengeFrame")));
                System.out.println("3rd frame (challengeFrame) loaded...");
            } else {
                System.out.println("3rd frame (challengeFrame) not found.");
                return;
            }

            WebElement submitButton = customWait.until(ExpectedConditions.elementToBeClickable(By.id("acssubmit")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);
            System.out.println("Submit button clicked successfully.");

        } catch (TimeoutException e) {
            System.out.println("Timeout while waiting for frames or elements: " + e.getMessage());
        }
    }

    // Helper method to check if a frame is present in the DOM
    private boolean isFramePresent(String frameId) {
        return driver.findElements(By.id(frameId)).size() > 0;
    }


     */

    /*
    public void handleAuthenticationFrameAndSubmit() {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(30));

        try {
            if (isFramePresent("tap-card-iframe")) {
                customWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("tap-card-iframe")));
                System.out.println("1st frame (tap-card-iframe) loaded...");
                Thread.sleep(1000);
            } else {
                System.out.println("1st frame (tap-card-iframe) not found.");
                return;
            }

            System.out.println(driver.getPageSource());

            if (isFramePresent("tap-card-iframe-authentication")) {
                customWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("tap-card-iframe-authentication")));
                System.out.println("2nd frame (tap-card-iframe-authentication) loaded...");
                Thread.sleep(1000);
            } else {
                System.out.println("2nd frame (tap-card-iframe-authentication) not found.");
                return;
            }

            if (isFramePresent("challengeFrame")) {
                customWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("challengeFrame")));
                System.out.println("3rd frame (challengeFrame) loaded...");
            } else {
                System.out.println("3rd frame (challengeFrame) not found.");
                return;
            }

            WebElement submitButton = customWait.until(ExpectedConditions.elementToBeClickable(By.id("acssubmit")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);
            System.out.println("Submit button clicked successfully.");

        } catch (TimeoutException e) {
            System.out.println("Timeout while waiting for frames or elements: " + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean isFramePresent(String frameId) {
        return driver.findElements(By.id(frameId)).size() > 0;
    }

     */

    public void handleAuthenticationFrameAndSubmit() {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(30));

        try {
            if (isFramePresent("tap-card-iframe")) {
                customWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("tap-card-iframe")));
                System.out.println("1st frame (tap-card-iframe) loaded...");
                Thread.sleep(1000);
            } else {
                System.out.println("1st frame (tap-card-iframe) not found.");
                return;
            }

            if (isFramePresent("tap-card-iframe-authentication")) {
                customWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("tap-card-iframe-authentication")));
                System.out.println("2nd frame (tap-card-iframe-authentication) loaded...");
                Thread.sleep(1000);
            } else {
                System.out.println("2nd frame (tap-card-iframe-authentication) not found.");
                return;
            }

            boolean isChallengeFrameLoaded = false;
            for (int i = 0; i < 30; i++) {
                if (isFramePresent("challengeFrame")) {
                    customWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("challengeFrame")));
                    System.out.println("3rd frame (challengeFrame) loaded...");
                    isChallengeFrameLoaded = true;
                    break;
                } else {
                    System.out.println("Waiting for challengeFrame to load...");
                    Thread.sleep(1000);
                }
            }

            if (!isChallengeFrameLoaded) {
                System.out.println("3rd frame (challengeFrame) not found.");
                return;
            }


            WebElement submitButton = customWait.until(ExpectedConditions.elementToBeClickable(By.id("acssubmit")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);
            System.out.println("Submit button clicked successfully.");

        } catch (TimeoutException e) {
            System.out.println("Timeout while waiting for frames or elements: " + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private boolean isFramePresent(String frameId) {
        return driver.findElements(By.id(frameId)).size() > 0;
    }

}