package com.ia.objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Medication {
    private String medicationName;

    public Medication(String medicationName) {
        this.medicationName = medicationName;
    }
}
