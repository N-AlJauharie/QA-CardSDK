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

public class CardAuthenticatedTokenTest {

    private WebDriver driver;
    private CardPage cardPage;
    private CSVWriterUtil csvWriter;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://demo.beta.tap.company/v2/sdk/card");
        cardPage = new CardPage(driver);
        csvWriter = new CSVWriterUtil("Authenticate-results.csv");
        csvWriter.writeHeader(new String[]{"Currency", "Purpose", "Card Number", "Brand", "Scheme", "Authenticate ID", "Authentication.Status", "Response.Message"});
        cardPage.selectScope("Authenticated Token");
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
        List<String> purposes = getAllPurposes();

        List<Map<String, String>> cards = Arrays.asList(
                Map.of("number", "5123450000000008", "expiry", "01/39", "cvv", "100", "brand", "MASTERCARD")
                //     Map.of("number", "5200000000000007", "expiry", "01/39", "cvv", "100", "brand", "MASTERCARD"),
                //     Map.of("number", "4508750015741019", "expiry", "01/39", "cvv", "100", "brand", "VISA"),
                //    Map.of("number", "345678901234564", "expiry", "01/39", "cvv", "100", "brand", "AMERICAN_EXPRESS")
                //     Map.of("number", "5111111111111118", "expiry", "01/39", "cvv", "100", "brand", "MASTERCARD"),
                //     Map.of("number", "4012000033330026", "expiry", "01/39", "cvv", "100", "brand", "VISA")
        );

        for (String currency : currencies) {
            for (String purpose : purposes) {
                for (Map<String, String> card : cards) {
                    runTestForCurrencyCardAndPurpose(currency, card, purpose);
                }
            }
        }
    }


    private List<String> getAllPurposes() {
        return Arrays.asList(
                "Charge",
                "Authorize",
                "Save Token"
        );
    }

    private void runTestForCurrencyCardAndPurpose(String currency, Map<String, String> cardDetails, String purpose) {
        String status = "Pass";
        String failureReason = "";
        String tokenId = "";
        String authenticationTokenBrandLocator = "";
        String getAuthenticationTokenSchemeLocator = "";
        String authenticationStatus = "";
        String responseMessage = "";

        try {
            cardPage.selectCurrency(currency);
            cardPage.selectPurpose(purpose);
            cardPage.enterCardDetails(cardDetails.get("number"), cardDetails.get("expiry"), cardDetails.get("cvv"));
            cardPage.clickCreateToken();

          //  cardPage.handleNestedAuthenticationFrameAndSubmit();

          //  cardPage.handleAuthenticationFrameAndSubmit();
          //  cardPage.clickSubmitBttn();

            cardPage.clickResponseRadioButton();

            tokenId = cardPage.getTokenId();
            authenticationTokenBrandLocator = cardPage.getAuthenticationTokenBrand();
            getAuthenticationTokenSchemeLocator = cardPage.getAuthenticationTokenScheme();
            authenticationStatus = cardPage.getAuthenticationStatus();
            responseMessage = cardPage.getResponseMessage();

        } catch (Exception e) {
            status = "Fail";
            failureReason = e.getMessage();
        }

        csvWriter.writeRecord(new String[]{
                currency,
                purpose,
                cardDetails.get("number"),
                tokenId,
                authenticationTokenBrandLocator,
                getAuthenticationTokenSchemeLocator,
                authenticationStatus,
                responseMessage,
                status,
                failureReason
        });

        System.out.println("Currency: " + currency + ", Purpose: " + purpose + ", Card: " + cardDetails.get("number") +
                ", Token ID: " + tokenId + ", Authentication Brand: " + authenticationTokenBrandLocator + ", Authentication Scheme: " + getAuthenticationTokenSchemeLocator +
                ", Authentication Status: " + authenticationStatus +
                ", Response Message: " + responseMessage + ", Status: " + status +
                (status.equals("Fail") ? ", Reason: " + failureReason : ""));
    }
}
