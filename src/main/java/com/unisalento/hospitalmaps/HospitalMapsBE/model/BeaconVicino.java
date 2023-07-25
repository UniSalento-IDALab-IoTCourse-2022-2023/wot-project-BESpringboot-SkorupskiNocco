package com.unisalento.hospitalmaps.HospitalMapsBE.model;

import com.unisalento.hospitalmaps.HospitalMapsBE.model.enums.Direzioni;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeaconVicino {
    private Direzioni direzione;
    private String beaconUUID; // ID del beacon vicino
    private double distanza; // Distanza in metri
}
