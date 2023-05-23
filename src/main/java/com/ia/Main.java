package com.ia;

import com.ia.dto.FillStationDistanceDTO;
import com.ia.objects.CentralFillStation;
import com.ia.objects.Medication;
import com.ia.objects.Price;
import com.ia.objects.WorldMap;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;


public class Main {
    //TODO: These values should be moved to a separate config file ideally
    private static final int X_MIN = -10;
    private static final int Y_MIN = -10;
    private static final int X_MAX = 10;
    private static final int Y_MAX = 10;
    private static final int NUMBER_OF_STATIONS_TO_RETRIEVE = 3;
    private static final int MIN_MEDICATIONS = 5;
    private static final int MAX_MEDICATIONS = 26; //One per letter of the alphabet to keep with convention for now
    private static final int MIN_STATIONS = 5;
    private static final int MAX_STATIONS = 50;
    private static final int MAX_PRICE_IN_CENTS = 50000; //$500
    private static final String CLOSEST_FILL_MESSAGE_FORMAT = "Central Fill %03d - %s, %s, Distance %d";


    public static void main(String[] args) {
        //Randomly Generate Medications, Fill Stations, and Prices
        ArrayList<Medication> generatedMeds = generateMedList(MIN_MEDICATIONS, MAX_MEDICATIONS);
        ArrayList<CentralFillStation> generatedFillStations =
                generateFillStations(generatedMeds, MIN_STATIONS, MAX_STATIONS);
        WorldMap map = new WorldMap(X_MIN, Y_MIN, X_MAX, Y_MAX);

        //Attempt to assign each fill station a random location on the map
        for (CentralFillStation fillStation : generatedFillStations) {
            int x_coord = new Random().nextInt(X_MIN, X_MAX+1);
            int y_coord = new Random().nextInt(Y_MIN, Y_MAX+1);
            Point location = new Point(x_coord, y_coord);
            try {
                map.addFillStationToMap(location, fillStation);
            } catch (IllegalArgumentException e) {
                //TODO: Fill station already exists at this location- currently just skipping/continuing
                //      but ideally should probably retry/assign it a different location
                continue;
            }
        }

        //Get User Input and do some basic validation
        Scanner input = new Scanner(System.in);
        System.out.println("Please Input Coordinates: ");
        String coordinate = input.nextLine();
        if (!isValidCoordinateFormat(coordinate)) {
            System.out.println("Invalid coordinate!  Coordinate must be in the form (x,y)");
            System.exit(0);
        }

        //Extract coordinates from input string
        String[] splitStr = coordinate.split(",");
        String xCoordinate = splitStr[0].replace("(", "").trim();
        String yCoordinate = splitStr[1].replace(")", "").trim();
        Point inputCoordinate = new Point(Integer.parseInt(xCoordinate), Integer.parseInt(yCoordinate));

        if (!coordinateIsInMapRange(inputCoordinate)) {
            System.out.println("Invalid coordinate!  Coordinate must be within map range of -10 to +10 on both X and Y axis");
            System.exit(0);
        }

        //
        List<FillStationDistanceDTO> closestStations =
                map.getClosestFillStationsToLocation(NUMBER_OF_STATIONS_TO_RETRIEVE, inputCoordinate);
        System.out.println(String.format("Closest Central Fills to (%s,%s): ", xCoordinate, yCoordinate));

        for (FillStationDistanceDTO fillStationDto : closestStations) {
            CentralFillStation station = fillStationDto.getFillStation();
            Map.Entry<Medication, Price> cheapestMed = station.getCheapestMedication();

            int distance = fillStationDto.getDistanceFromPoint();

            String medicationName = cheapestMed.getKey().getMedicationName();
            String medicationPrice = cheapestMed.getValue().getFormattedPriceInUSD();

            System.out.println(String.format(CLOSEST_FILL_MESSAGE_FORMAT,
                    station.getFillStationId(),
                    medicationPrice,
                    medicationName,
                    distance));
        }

    }






    public static ArrayList<Medication> generateMedList(int minSize, int maxSize) {
        final String MEDICATION_NAMES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int totalMeds = new Random().nextInt(minSize, maxSize);

        ArrayList<Medication> medList = new ArrayList<>();
        for (int i = 0; i < totalMeds; i++) {
            Medication newMed = new Medication("Medication " + MEDICATION_NAMES.charAt(i));
            medList.add(newMed);
        }

        return medList;
    }

    public static ArrayList<CentralFillStation> generateFillStations(
            ArrayList<Medication> availableMeds, int minSize, int maxSize) {
        int totalFillStations = new Random().nextInt(minSize, maxSize);
        int maxMeds = availableMeds.size();

        ArrayList<CentralFillStation> fillStationList = new ArrayList<>();
        for (int i = 0; i < totalFillStations; i++) {
            CentralFillStation newFillStation = new CentralFillStation(i);

            int medCount = new Random().nextInt(1, maxMeds);
            int[] medIndexes = new Random().ints(medCount, 0, maxMeds).distinct().toArray();
            for (int currIndex : medIndexes) {
                Medication med = availableMeds.get(currIndex);
                Price medPrice = new Price(new Random().nextInt(MAX_PRICE_IN_CENTS));
                newFillStation.addMedicationToInventory(med, medPrice);
            }

            fillStationList.add(newFillStation);
        }

        return fillStationList;
    }


    public static boolean isValidCoordinateFormat(String coordinate) {
        //Regex matches coordinates in the format (x,y) with whitespace allowed (trimmed out when getting values)
        String coordinateRegex = "^\\s*\\(\\s*-?[0-9]+\\s*,\\s*-?[0-9]+\\s*\\)\\s*$";
        return Pattern.matches(coordinateRegex, coordinate);
    }

    public static boolean coordinateIsInMapRange(Point coordinate) {
        return ((coordinate.x >= X_MIN && coordinate.x <= X_MAX)
                && (coordinate.y >= Y_MIN && coordinate.y <= Y_MAX)
        );
    }
}