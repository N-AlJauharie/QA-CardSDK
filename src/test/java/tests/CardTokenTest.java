package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.CardPage;
import utils.CSVWriterUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CardTokenTest {

    private WebDriver driver;
    private CardPage cardPage;
    private CSVWriterUtil csvWriter;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://demo.beta.tap.company/v2/sdk/card");
        cardPage = new CardPage(driver);
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
    public void testAllCurrenciesAndCards() {

        List<String> currencies = cardPage.getAvailableCurrencies();

        List<Map<String, String>> cards = Arrays.asList(
                Map.of("number", "4464040000000007", "expiry", "01/39", "cvv", "100", "brand", "VISA")
           //     Map.of("number", "5200000000000007", "expiry", "01/39", "cvv", "100", "brand", "MASTERCARD"),
           //     Map.of("number", "5123450000000008", "expiry", "01/39", "cvv", "100", "brand", "MASTERCARD"),
           //     Map.of("number", "4508750015741019", "expiry", "01/39", "cvv", "100", "brand", "VISA"),
            //    Map.of("number", "345678901234564", "expiry", "01/39", "cvv", "100", "brand", "AMERICAN_EXPRESS")
           //     Map.of("number", "5111111111111118", "expiry", "01/39", "cvv", "100", "brand", "MASTERCARD"),
           //     Map.of("number", "4012000033330026", "expiry", "01/39", "cvv", "100", "brand", "VISA")

                );


        for (String currency : currencies) {
            for (Map<String, String> card : cards) {
                runTestForCurrencyAndCard(currency, card);
            }
        }
    }


    /*
    private void runTestForCurrencyAndCard(String currency, Map<String, String> cardDetails) {

        cardPage.selectCurrency(currency);
        cardPage.enterCardDetails(cardDetails.get("number"), cardDetails.get("expiry"), cardDetails.get("cvv"));
        cardPage.clickCreateToken();
        cardPage.clickResponseRadioButton();

        String tokenId = cardPage.getTokenId();
        String tokenBrand = cardPage.getTokenBrand();
        String tokenScheme = cardPage.getTokenScheme();

        csvWriter.writeRecord(new String[]{currency, "TokenAsCharge/Chrome", cardDetails.get("number"), tokenId, tokenBrand, tokenScheme});
        System.out.println("Currency: " + currency + ", Card: " + cardDetails.get("number") + ", Token ID: " + tokenId + ", Brand: " + tokenBrand + ", Scheme: " + tokenScheme);
    }

     */

    private void runTestForCurrencyAndCard(String currency, Map<String, String> cardDetails) {
        String status = "Pass";
        String failureReason = "";
        String tokenId = "";
        String tokenBrand = "";
        String tokenScheme = "";

        try {

            cardPage.selectCurrency(currency);
            cardPage.enterCardDetails(cardDetails.get("number"), cardDetails.get("expiry"), cardDetails.get("cvv"));
            cardPage.clickCreateToken();
            cardPage.clickResponseRadioButton();

            tokenId = cardPage.getTokenId();
            tokenBrand = cardPage.getTokenBrand();
            tokenScheme = cardPage.getTokenScheme();
        } catch (Exception e) {
            status = "Fail";
            failureReason = e.getMessage();
        }


        csvWriter.writeRecord(new String[]{
                currency,
                "TokenAsCharge/Chrome",
                cardDetails.get("number"),
                tokenId,
                tokenBrand,
                tokenScheme,
                status,
                failureReason
        });


        System.out.println("Currency: " + currency + ", Card: " + cardDetails.get("number") +
                ", Token ID: " + tokenId + ", Brand: " + tokenBrand +
                ", Scheme: " + tokenScheme + ", Status: " + status +
                (status.equals("Fail") ? ", Reason: " + failureReason : ""));
    }

}



