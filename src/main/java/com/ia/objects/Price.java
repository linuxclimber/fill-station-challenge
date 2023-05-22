package com.ia.objects;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Price {
    private long priceInUSCents;

    public Price (long priceInUSCents) {
        if (isValidPrice(priceInUSCents)) {
            this.priceInUSCents = priceInUSCents;
        } else {
            throw new IllegalArgumentException("Invalid price given: " + priceInUSCents);
        }
    }

    public String getFormattedPriceInUSD() {
        BigDecimal usdValue = new BigDecimal(priceInUSCents).movePointLeft(2);
        return "$".concat(usdValue.toString());
    }

    //Ensure price is positive and non-zero
    public static boolean isValidPrice(long priceInUSCents) {
        return (priceInUSCents > 0);
    }
}
