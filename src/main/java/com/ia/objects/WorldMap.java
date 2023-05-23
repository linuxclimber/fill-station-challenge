package com.ia.objects;

import com.ia.dto.FillStationDistanceDTO;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.*;
import java.util.List;

@Getter
@Setter
public class WorldMap {
    private long xMin;
    private long xMax;
    private long yMin;
    private long yMax;
    private HashMap<Point, CentralFillStation> fillStations;

    public WorldMap(long xMin, long yMin, long xMax, long yMax) {
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
        this.fillStations = new HashMap<Point, CentralFillStation>();
    }

    public void addFillStationToMap(Point location, CentralFillStation station) {
        if (this.fillStations.containsKey(location)) {
            throw new IllegalArgumentException("There is already a fill station at the given location: " + location.toString());
        }

        this.fillStations.put(location, station);
    }

    public List<FillStationDistanceDTO> getClosestFillStationsToLocation(int numberOfStations, Point location) {
        Comparator<FillStationDistanceDTO> comp =
                Comparator.comparing((FillStationDistanceDTO stationDistanceDto)
                        ->stationDistanceDto.getDistanceFromPoint()).reversed();

        PriorityQueue<FillStationDistanceDTO> closestStations = new PriorityQueue(numberOfStations, comp);
        for (Point currPoint : fillStations.keySet()) {
            double currDistance = currPoint.distance(location);
            System.out.println("Point: " + currPoint.toString() + " distance: " + currDistance); //TODO: Remove/cleanup after testing

            FillStationDistanceDTO newEntry = new FillStationDistanceDTO();
            newEntry.setFillStation(fillStations.get(currPoint));
            newEntry.setDistanceFromPoint(currDistance);

            if (numberOfStations > closestStations.size()
                    || currDistance < closestStations.peek().getDistanceFromPoint()) {
                closestStations.add(newEntry);
            }

            while (closestStations.size() > numberOfStations) {
                closestStations.poll();
            }
        }

        //TODO: Remove after testing
        System.out.println("Closest Stations:");
        for (FillStationDistanceDTO stationDTO : closestStations) {
            System.out.println("Station: " + stationDTO.getFillStation().getFillStationId());
        }
        System.out.println("====================");

        return closestStations.stream().toList();
    }
}
