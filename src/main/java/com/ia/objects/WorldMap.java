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
    private HashMap<Point, CentralFillStation> fillStations;
    private int xMin;
    private int xMax;
    private int yMin;
    private int yMax;

    public WorldMap(int xMin, int yMin, int xMax, int yMax) {
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
        this.fillStations = new HashMap<>();
    }

    public void addFillStationToMap(Point location, CentralFillStation station) {
        if (this.fillStations.containsKey(location)) {
            throw new IllegalArgumentException("There is already a fill station at the given location: " + location.toString());
        }

        this.fillStations.put(location, station);
    }

    public List<FillStationDistanceDTO> getClosestFillStationsToLocation(int numberOfStations, Point location) {
        Comparator<FillStationDistanceDTO> comp =
                Comparator.comparing(FillStationDistanceDTO::getDistanceFromPoint).reversed();

        //Iterate over stations list once to find lowest X distances
        //Store lowest values in priority queue weighted with largest distance at head of queue
        //Compare each value against the head of the queue and if it is smaller, insert the new value and
        //remove the head to return the queue to the proper size
        PriorityQueue<FillStationDistanceDTO> closestStations = new PriorityQueue<>(numberOfStations, comp);
        for (Point currPoint : fillStations.keySet()) {
            int currDistance = calculateManhattanDistance(currPoint, location);

            FillStationDistanceDTO newEntry = new FillStationDistanceDTO();
            newEntry.setFillStation(fillStations.get(currPoint));
            newEntry.setDistanceFromPoint(currDistance);

            if (numberOfStations > closestStations.size()
                    || ((closestStations.peek() != null) && currDistance < closestStations.peek().getDistanceFromPoint())) {
                closestStations.add(newEntry);
            }

            //Remove highest values from priority queue until requested size is reached
            while (closestStations.size() > numberOfStations) {
                closestStations.poll();
            }
        }

        return closestStations.stream().toList();
    }

    private int calculateManhattanDistance(Point currPoint, Point location) {
        return Math.abs(currPoint.x - location.x) + Math.abs(currPoint.y - location.y);
    }
}
