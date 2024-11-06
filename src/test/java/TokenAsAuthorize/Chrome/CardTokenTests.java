package TokenAsAuthorize.Chrome;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import pages.CardPage;
import utils.CSVWriterUtil;

import static org.bouncycastle.util.test.SimpleTest.runTest;

public class CardTokenTests {

    private WebDriver driver;
    private CardPage cardPage;
    private CSVWriterUtil csvWriter;

    @BeforeTest
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://demo.beta.tap.company/v2/sdk/card");
        cardPage = new CardPage(driver);
        csvWriter = new CSVWriterUtil("token-results.csv");
        csvWriter.writeHeader(new String[]{"Currency", "Purpose", "Card Number", "Token ID", "Brand", "Scheme"});
    }

    @AfterTest
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
        csvWriter.close();
    }

    @Test
    public void MadawithAED() {
        cardPage.selectCurrency("AED");
        cardPage.enterCardDetails("4464040000000007", "01/39", "100");
        cardPage.clickCreateToken();
        cardPage.clickResponseRadioButton();

        String tokenId = cardPage.getTokenId();
        String tokenBrand = cardPage.getTokenBrand();
        String tokenScheme = cardPage.getTokenScheme();

        csvWriter.writeRecord(new String[]{"AED", "TokenAsCharge/Chrome", "4464040000000007", tokenId, tokenBrand, tokenScheme});
        System.out.println("Currency: AED, Token ID: " + tokenId + ", Brand: Visa, Scheme: Mada");
    }

    @Test
    public void MadawithSAR() {
        cardPage.selectCurrency("SAR");
        cardPage.enterCardDetails("4464040000000007", "01/39", "100");
        cardPage.clickCreateToken();
        cardPage.clickResponseRadioButton();

        String tokenId = cardPage.getTokenId();
        String tokenBrand = cardPage.getTokenBrand();
        String tokenScheme = cardPage.getTokenScheme();

        csvWriter.writeRecord(new String[]{"AED", "TokenAsCharge/Chrome", "4464040000000007", tokenId, tokenBrand, tokenScheme});
        System.out.println("Currency: USD, Token ID: " + tokenId + ", Brand: Visa, Scheme: Mada");
    }


    @Test
    public void AMEXwithUSD() {
        cardPage.selectCurrency("USD");
        cardPage.enterCardDetails("345678901234564", "01/39", "1000");
        cardPage.clickCreateToken();
        cardPage.clickResponseRadioButton();

        String tokenId = cardPage.getTokenId();
        String tokenBrand = cardPage.getTokenBrand();
        String tokenScheme = cardPage.getTokenScheme();

        csvWriter.writeRecord(new String[]{"AED", "TokenAsCharge/Chrome", "345678901234564", tokenId, tokenBrand, tokenScheme});
        System.out.println("Currency: USD, Token ID: " + tokenId + ", Brand: AMEX, Scheme: AMEX");
    }


    @Test
    public void BenefitwithBHD() {
        cardPage.selectCurrency("BHD");
        cardPage.enterCardDetails("4600410123456789", "12/27", "1234");
        cardPage.clickCreateToken();
        cardPage.clickResponseRadioButton();

        String tokenId = cardPage.getTokenId();
        String tokenBrand = cardPage.getTokenBrand();
        String tokenScheme = cardPage.getTokenScheme();

        csvWriter.writeRecord(new String[]{"AED", "TokenAsCharge/Chrome", "4600410123456789", tokenId, tokenBrand, tokenScheme});
        System.out.println("Currency: BHD, Token ID: " + tokenId + ", Brand: Visa, Scheme: Benefit");
    }

    @Test
    public void BenefitwithAED() {
        cardPage.selectCurrency("AED");
        cardPage.enterCardDetails("4600410123456789", "12/27", "1234");
        cardPage.clickCreateToken();
        cardPage.clickResponseRadioButton();

        String tokenId = cardPage.getTokenId();
        String tokenBrand = cardPage.getTokenBrand();
        String tokenScheme = cardPage.getTokenScheme();

        csvWriter.writeRecord(new String[]{"AED", "TokenAsCharge/Chrome", "4600410123456789", tokenId, tokenBrand, tokenScheme});
        System.out.println("Currency: AED, Token ID: " + tokenId + ", Brand: Visa, Scheme: Benefit");
    }

    @Test
    public void VisawithAED() {
        cardPage.selectCurrency("AED");
        cardPage.enterCardDetails("4508750015741019", "01/39", "100");
        cardPage.clickCreateToken();
        cardPage.clickResponseRadioButton();

        String tokenId = cardPage.getTokenId();
        String tokenBrand = cardPage.getTokenBrand();
        String tokenScheme = cardPage.getTokenScheme();

        csvWriter.writeRecord(new String[]{"AED", "TokenAsCharge/Chrome", "4508750015741019", tokenId, tokenBrand, tokenScheme});
        System.out.println("Currency: AED, Token ID: " + tokenId + ", Brand: Visa, Scheme: Visa");
    }


    @Test
    public void VisawithSAR() {
        cardPage.selectCurrency("SAR");
        cardPage.enterCardDetails("4508750015741019", "01/39", "100");
        cardPage.clickCreateToken();
        cardPage.clickResponseRadioButton();

        String tokenId = cardPage.getTokenId();
        String tokenBrand = cardPage.getTokenBrand();
        String tokenScheme = cardPage.getTokenScheme();

        csvWriter.writeRecord(new String[]{"SAR", "TokenAsCharge/Chrome", "4508750015741019", tokenId, tokenBrand, tokenScheme});
        System.out.println("Currency: AED, Token ID: " + tokenId + ", Brand: Visa, Scheme: Visa");
    }

    @Test
    public void MCwithSAR() {
        cardPage.selectCurrency("SAR");
        cardPage.enterCardDetails("5123450000000008", "01/39", "100");
        cardPage.clickCreateToken();
        cardPage.clickResponseRadioButton();

        String tokenId = cardPage.getTokenId();
        String tokenBrand = cardPage.getTokenBrand();
        String tokenScheme = cardPage.getTokenScheme();

        csvWriter.writeRecord(new String[]{"SAR", "TokenAsCharge/Chrome", "5123450000000008", tokenId, tokenBrand, tokenScheme});
        System.out.println("Currency: SAR, Token ID: " + tokenId + ", Brand: MasterCard, Scheme: MasterCard");
    }

    @Test
    public void MCwithAED() {
        cardPage.selectCurrency("AED");
        cardPage.enterCardDetails("5123450000000008", "01/39", "100");
        cardPage.clickCreateToken();
        cardPage.clickResponseRadioButton();

        String tokenId = cardPage.getTokenId();
        String tokenBrand = cardPage.getTokenBrand();
        String tokenScheme = cardPage.getTokenScheme();

        csvWriter.writeRecord(new String[]{"AED", "TokenAsCharge/Chrome", "5123450000000008", tokenId, tokenBrand, tokenScheme});
        System.out.println("Currency: SAR, Token ID: " + tokenId + ", Brand: MasterCard, Scheme: MasterCard");
    }

}
