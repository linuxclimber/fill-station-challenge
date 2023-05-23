package com.ia.objects;

import com.ia.dto.FillStationDistanceDTO;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorldMapTest {

    @Test
    void addFillStationToMap() {
        WorldMap map = new WorldMap(-10, -10, 10, 10);
        Point location1 = new Point(0,0);
        Point location2 = new Point(5,5);
        CentralFillStation fillStation1 = new CentralFillStation(1);
        CentralFillStation fillStation2 = new CentralFillStation(2);

        map.addFillStationToMap(location1, fillStation1);

        assertThrows(IllegalArgumentException.class, () -> {
            map.addFillStationToMap(location1, fillStation2);
        });

        map.addFillStationToMap(location2, fillStation2);
    }

    @Test
    void getClosestFillStationsToLocation() {
        WorldMap map = new WorldMap(-10, -10, 10, 10);
        List<FillStationDistanceDTO> closestStations = map.getClosestFillStationsToLocation(3, new Point(0,0));
        assertEquals(0, closestStations.size());

        Point location1 = new Point(1,1);
        CentralFillStation fillStation1 = new CentralFillStation(1);
        map.addFillStationToMap(location1, fillStation1);
        closestStations = map.getClosestFillStationsToLocation(3, new Point(0,0));
        assertEquals(1, closestStations.size());

        Point location2 = new Point(-2,-2);
        CentralFillStation fillStation2 = new CentralFillStation(2);
        map.addFillStationToMap(location2, fillStation2);
        closestStations = map.getClosestFillStationsToLocation(3, new Point(0,0));
        assertEquals(2, closestStations.size());

        Point location3 = new Point(3, -3);
        CentralFillStation fillStation3 = new CentralFillStation(3);
        map.addFillStationToMap(location3, fillStation3);
        closestStations = map.getClosestFillStationsToLocation(3, new Point(0,0));
        assertEquals(3, closestStations.size());

        Point location4 = new Point(-1, 1);
        CentralFillStation fillStation4 = new CentralFillStation(4);
        map.addFillStationToMap(location4, fillStation4);
        closestStations = map.getClosestFillStationsToLocation(3, new Point(0,0));
        assertEquals(3, closestStations.size());

        Point location5 = new Point(-2, 0);
        CentralFillStation fillStation5 = new CentralFillStation(5);
        map.addFillStationToMap(location5, fillStation5);
        closestStations = map.getClosestFillStationsToLocation(3, new Point(0,0));
        assertEquals(3, closestStations.size());

        closestStations = map.getClosestFillStationsToLocation(2, new Point(0,0));
        assertEquals(2, closestStations.size());

        closestStations = map.getClosestFillStationsToLocation(1, new Point(3,-3));
        assertEquals(1, closestStations.size());
    }
}