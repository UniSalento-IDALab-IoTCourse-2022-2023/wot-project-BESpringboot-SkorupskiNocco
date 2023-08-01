package com.unisalento.hospitalmaps.HospitalMapsBE.model;

import com.unisalento.hospitalmaps.HospitalMapsBE.model.enums.DirezioniMappatura;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeaconVicino {
    private DirezioniMappatura direzione;
    private String beaconUUID; // ID del beacon vicino
    private double distanza; // Distanza in metri
}
