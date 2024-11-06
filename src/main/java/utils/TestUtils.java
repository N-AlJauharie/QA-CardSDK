package utils;

import java.util.HashMap;
import java.util.Map;

public class TestUtils {

    // 3DS Yes
    public static Map<String, String> getVisaCardDetails() {
        Map<String, String> visaCard = new HashMap<>();
        visaCard.put("number", "4508750015741019");
        visaCard.put("expiry", "01/39");
        visaCard.put("cvv", "100");
        visaCard.put("brand", "VISA");
        //ADD SCHEME AND 3DS INCLUDED
        return visaCard;
    }

    // 3DS Yes
    public static Map<String, String> getMasterCardDetails() {
        Map<String, String> masterCard = new HashMap<>();
        masterCard.put("number", "5123450000000008");
        masterCard.put("expiry", "01/39");
        masterCard.put("cvv", "100");
        masterCard.put("brand", "MASTERCARD");
        return masterCard;
    }
}
