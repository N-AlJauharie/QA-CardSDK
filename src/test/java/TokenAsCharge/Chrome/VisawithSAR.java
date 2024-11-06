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

public class VisawithSAR {

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
    public void VisawithSAR() {
        WebElement currencyDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("currency")));
        Select selectCurrency = new Select(currencyDropdown);
        selectCurrency.selectByVisibleText("SAR");

        driver.switchTo().frame("tap-card-iframe");

        WebElement cardNumber = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='card_input_mini']")));
        cardNumber.sendKeys("4508750015741019");
        driver.findElement(By.id("date_input")).sendKeys("01/39");
        driver.findElement(By.id("cvv_input")).sendKeys("100");

        driver.switchTo().defaultContent();

        WebElement createTokenButton = driver.findElement(By.xpath("//*[@id='root']/section/article/div[3]/button[1]"));
        createTokenButton.click();

        WebElement radioMale = driver.findElement(By.cssSelector("input[name='preview'][value='response']"));
        radioMale.click();

        String tokenId = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='root']/section/aside/article[2]/pre/code/span[9]"))).getText();

        String tokenBrand = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='root']/section/aside/article[2]/pre/code/span[139]"))).getText();
        String tokenScheme = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='root']/section/aside/article[2]/pre/code/span[147]"))).getText();

        csvWriter.writeRecord(new String[]{"AED", "TokenAsCharge/Chrome", "4508750015741019", tokenId, tokenBrand, tokenScheme});
        System.out.println("Currency: AED, Token ID: " + tokenId + ", Brand: Visa, Scheme: Visa");
    }

}
