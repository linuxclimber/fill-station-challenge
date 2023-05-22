package com.ia.objects;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class CentralFillStation {
    private long fillStationId;
    private HashMap<Medication, Price> medicationInventory;

    public CentralFillStation(long stationId) {
        this.fillStationId = stationId;
        this.medicationInventory = new HashMap<Medication, Price>();
    }

    public Map.Entry<Medication, Price> getCheapestMedication() {
        if (medicationInventory.isEmpty()) {
            throw new NoSuchElementException("No medications currently at fill center");
        }

        //Compare based on price in US cents
        Comparator<Map.Entry<Medication, Price>> comp =
                Comparator.comparing((Map.Entry<Medication, Price> entry)->entry.getValue().getPriceInUSCents());

        return Collections.min(medicationInventory.entrySet(), comp);
    }

    public Price getMedicationPrice(Medication targetMedication) {
        if (medicationInventory.containsKey(targetMedication)) {
            return medicationInventory.get(targetMedication);
        } else {
            throw new IllegalArgumentException("Medication does not exist in inventory: "
                    + targetMedication.getMedicationName());
        }
    }

    public void addMedicationToInventory(Medication newMedication, Price medicationPrice) {
        if (medicationInventory.containsKey(newMedication)) {
            throw new IllegalArgumentException("Medication already exists in fill station: "
                    + newMedication.getMedicationName());
        }
        this.medicationInventory.put(newMedication, medicationPrice);
    }
}
