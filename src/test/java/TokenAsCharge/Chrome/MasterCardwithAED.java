package TokenAsCharge.Chrome;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import utils.CSVWriterUtil;
import org.testng.annotations.Test;
import java.time.Duration;

public class MasterCardwithAED {
    private WebDriver driver;
    private WebDriverWait wait;
    private CSVWriterUtil csvWriter;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get("https://demo.beta.tap.company/v2/sdk/card");
        csvWriter = new CSVWriterUtil("token-results.csv");
        csvWriter.writeHeader(new String[]{"Currency", "Purpose", "Card Number", "Token ID", "Brand", "Scheme"});
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
        csvWriter.close();
    }

    @Test
    public void MCwithAED() {
        WebElement currencyDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("currency")));
        Select selectCurrency = new Select(currencyDropdown);
        selectCurrency.selectByVisibleText("AED");

        driver.switchTo().frame("tap-card-iframe");

        WebElement cardNumber = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='card_input_mini']")));
        cardNumber.sendKeys("5123450000000008");
        driver.findElement(By.id("date_input")).sendKeys("01/39");
        driver.findElement(By.id("cvv_input")).sendKeys("100");

        driver.switchTo().defaultContent();

        WebElement createTokenButton = driver.findElement(By.xpath("//*[@id='root']/section/article/div[3]/button[1]"));
        createTokenButton.click();



       // WebElement radioMale = driver.findElement(By.name("preview"));
       // radioMale.click();

        WebElement radioMale = driver.findElement(By.cssSelector("input[name='preview'][value='response']"));
        radioMale.click();


        String tokenId = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='root']/section/aside/article[2]/pre/code/span[9]"))).getText();
        String tokenBrand = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='root']/section/aside/article[2]/pre/code/span[139]"))).getText();
        String tokenScheme = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='root']/section/aside/article[2]/pre/code/span[147]"))).getText();

        csvWriter.writeRecord(new String[]{"AED", "TokenAsCharge/Chrome", "5123450000000008", tokenId, tokenBrand, tokenScheme});
        System.out.println("Currency: AED, Token ID: " + tokenId + ", Brand: MasterCard, Scheme: MasterCard");
    }


 /*   @Test
    public void MCwithAED() {
        WebElement currencyDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("currency")));
        Select selectCurrency = new Select(currencyDropdown);
        selectCurrency.selectByVisibleText("AED");

        driver.switchTo().frame("tap-card-iframe");

        WebElement cardNumber = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='card_input_mini']")));
        cardNumber.sendKeys("5123450000000008");
        driver.findElement(By.id("date_input")).sendKeys("01/39");
        driver.findElement(By.id("cvv_input")).sendKeys("100");

        driver.switchTo().defaultContent();

        WebElement createTokenButton = driver.findElement(By.xpath("//*[@id='root']/section/article/div[3]/button[1]"));
        createTokenButton.click();

        String tokenId = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='root']/section/aside/article[2]/pre/code/span[9]"))).getText();

        String fullBlock = driver.findElement(By.xpath("//*[@id='root']/section/aside/article[2]/pre/code")).getText();
        System.out.println("Full Block: " + fullBlock);

        try {
            String tokenBrand = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/div[@id='root']/section/aside/article[2]/pre/code/span[139]"))).getText();
            String tokenScheme = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"root\"]/section/aside/article[2]/pre/code/span[343]"))).getText();

            csvWriter.writeRecord(new String[]{"AED", "Charge", "5123450000000008", tokenId, tokenBrand, tokenScheme});
            System.out.println("Currency: AED, Token ID: " + tokenId + ", Brand: " + tokenBrand + ", Scheme: " + tokenScheme);
        } catch (Exception e) {
            System.out.println("Brand or Scheme not found");
            csvWriter.writeRecord(new String[]{"AED", "Charge", "5123450000000008", tokenId, "N/A", "N/A"});
        }
    }

  */

}
