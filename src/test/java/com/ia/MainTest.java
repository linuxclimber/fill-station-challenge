package com.ia;

import com.ia.objects.CentralFillStation;
import com.ia.objects.Medication;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void generateMedList() {
        ArrayList<Medication> medList = Main.generateMedList(3,26);
        assertTrue(3 <= medList.size() && 26 >= medList.size());
    }

    @Test
    void generateFillStations() {
        ArrayList<Medication> medList = Main.generateMedList(3,26);
        ArrayList<CentralFillStation> fillStationList = Main.generateFillStations(medList, 5, 250);
        assertTrue(5 <= fillStationList.size() && 250 >= fillStationList.size());
    }

    @Test
    void isValidCoordinateFormat() {
        assertTrue(Main.isValidCoordinateFormat("(4,2)"));
        assertTrue(Main.isValidCoordinateFormat(" ( 4 , 2 ) "));
        assertTrue(Main.isValidCoordinateFormat("(0, 0)"));
        assertTrue(Main.isValidCoordinateFormat("(-1,0)"));
        assertTrue(Main.isValidCoordinateFormat("(0,-2)"));
        assertTrue(Main.isValidCoordinateFormat("(-1,-1)"));

        assertFalse(Main.isValidCoordinateFormat("(4,2))"));
        assertFalse(Main.isValidCoordinateFormat(""));
        assertFalse(Main.isValidCoordinateFormat("5,0"));

    }

    @Test
    void coordinateIsInMapRange() {
        //TODO: Pull map size test values from config after they are moved out of Main and into a config
        assertTrue(Main.coordinateIsInMapRange(new Point(0,0)));
        assertTrue(Main.coordinateIsInMapRange(new Point(10,10)));
        assertTrue(Main.coordinateIsInMapRange(new Point(-10,-10)));

        assertFalse(Main.coordinateIsInMapRange(new Point(-11,-11)));
        assertFalse(Main.coordinateIsInMapRange(new Point(11,11)));
        assertFalse(Main.coordinateIsInMapRange(new Point(0,-100)));
        assertFalse(Main.coordinateIsInMapRange(new Point(-55,0)));

    }
}