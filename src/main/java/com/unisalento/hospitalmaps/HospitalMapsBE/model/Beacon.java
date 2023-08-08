package com.unisalento.hospitalmaps.HospitalMapsBE.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "beacon")
public class Beacon {
    @Id
    String beaconUUID;
    String idOspedale;
    List<String> nomiStanze;
    int piano;
    String reparto;
    List<BeaconVicino> vicini;
    List<BeaconVicino> viciniPerDisabili;
}
