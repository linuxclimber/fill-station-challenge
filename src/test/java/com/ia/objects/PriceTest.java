package com.ia.objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriceTest {

    @Test
    void getFormattedPriceInUSD() {
        String expectedPriceStr1 = "$0.01";
        Price price1 = new Price(1);
        assertEquals(expectedPriceStr1, price1.getFormattedPriceInUSD());

        String expectedPriceStr2 = "$1.00";
        Price price2 = new Price(100);
        assertEquals(expectedPriceStr2, price2.getFormattedPriceInUSD());

        String expectedPriceStr3 = "$9876.54";
        Price price3 = new Price(987654);
        assertEquals(expectedPriceStr3, price3.getFormattedPriceInUSD());
    }

    @Test
    void isValidPrice() {
        //Invalid Prices
        assertFalse(Price.isValidPrice(-10)); //Negative price
        assertFalse(Price.isValidPrice(0)); //Zero price

        //Valid Prices
        assertTrue(Price.isValidPrice(1)); //$0.01
        assertTrue(Price.isValidPrice(9999)); //$99.99
        assertTrue(Price.isValidPrice(100000000)); //$1,000,000.00
    }

    @Test
    void newPriceExceptions() {
        //Zero
        assertThrows(IllegalArgumentException.class, () -> {
            Price zeroPrice = new Price(0);
        });

        //Negative Price
        assertThrows(IllegalArgumentException.class, () -> {
            Price zeroPrice = new Price(-1);
        });
    }
}