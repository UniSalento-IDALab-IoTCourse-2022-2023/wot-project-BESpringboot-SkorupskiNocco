package com.unisalento.hospitalmaps.HospitalMapsBE.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BeaconDjikstra{
    String beaconUUID;
    Double pesoTotale;
    String precedente;
    Boolean verificato;
    List<BeaconVicino> vicini;
}
