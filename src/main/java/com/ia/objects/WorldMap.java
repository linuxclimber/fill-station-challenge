package com.ia.objects;

import lombok.Getter;
import lombok.Setter;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class WorldMap {
    private long xMin;
    private long xMax;
    private long yMin;
    private long yMax;
    private HashMap<Point2D, CentralFillStation> fillStations;

    public WorldMap(long xMin, long yMin, long xMax, long yMax) {
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
        this.fillStations = new HashMap<Point2D, CentralFillStation>();
    }

    public void addFillStationToMap() {
        //TODO
    }

    public List<CentralFillStation> getClosestFillStationsToLocation(long numberOfStations, Point2D location) {
        //TODO
        return new ArrayList<CentralFillStation>();
    }
}
