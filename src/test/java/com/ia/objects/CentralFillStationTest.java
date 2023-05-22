package com.ia.objects;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class CentralFillStationTest {
    Medication medication1 = new Medication("A");
    Medication medication2 = new Medication("B");
    Medication medication3 = new Medication("C");

    @Test
    void getCheapestMedication() {
        CentralFillStation fillStation = new CentralFillStation(1);
        assertThrows(NoSuchElementException.class, fillStation::getCheapestMedication);

        //Only entry so it should be the cheapest
        fillStation.addMedicationToInventory(this.medication1, new Price(500));
        Map.Entry<Medication, Price> cheapest = fillStation.getCheapestMedication();
        assertEquals(medication1, cheapest.getKey());
        assertEquals(500, cheapest.getValue().getPriceInUSCents());

        //Added more expensive medication, should be no change in cheapest
        fillStation.addMedicationToInventory(this.medication2, new Price(1000));
        cheapest = fillStation.getCheapestMedication();
        assertEquals(medication1, cheapest.getKey());
        assertEquals(500, cheapest.getValue().getPriceInUSCents());

        //New cheapest medication added- ensure cheapest gets updated
        fillStation.addMedicationToInventory(this.medication3, new Price(250));
        cheapest = fillStation.getCheapestMedication();
        assertEquals(medication3, cheapest.getKey());
        assertEquals(250, cheapest.getValue().getPriceInUSCents());
    }

    @Test
    void getMedicationPrice() {
        CentralFillStation fillStation = new CentralFillStation(1);

        //Trying to get price of medication not at facility should throw error
        assertThrows(IllegalArgumentException.class, () -> {
            fillStation.getMedicationPrice(medication1);
        });

        //Add missing medication and ensure it no longer errors/returns correct prices after
        long expectedPrice1 = 123;
        long expectedPrice2 = 456;
        fillStation.addMedicationToInventory(this.medication1, new Price(expectedPrice1));
        fillStation.addMedicationToInventory(this.medication2, new Price(expectedPrice2));

        assertEquals(expectedPrice1, fillStation.getMedicationPrice(medication1).getPriceInUSCents());
        assertEquals(expectedPrice2, fillStation.getMedicationPrice(medication2).getPriceInUSCents());
    }

    @Test
    void addMedicationToInventory() {
        CentralFillStation fillStation = new CentralFillStation(1);
        fillStation.addMedicationToInventory(this.medication1, new Price(500));
        fillStation.addMedicationToInventory(this.medication2, new Price(100));
        fillStation.addMedicationToInventory(this.medication3, new Price(500));

        //Ensure trying to add a medication that the station already has in inventory fails
        assertThrows(IllegalArgumentException.class, () -> {
            fillStation.addMedicationToInventory(this.medication1, new Price(250));
        });
    }
}