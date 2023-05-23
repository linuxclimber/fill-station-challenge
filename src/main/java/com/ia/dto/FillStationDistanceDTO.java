package com.ia.dto;

import com.ia.objects.CentralFillStation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FillStationDistanceDTO {
    private CentralFillStation fillStation;
    private double distanceFromPoint;
}
