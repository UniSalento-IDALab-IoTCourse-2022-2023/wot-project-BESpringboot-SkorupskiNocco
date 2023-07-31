package com.unisalento.hospitalmaps.HospitalMapsBE.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "beacon")
public class BeaconInput {
    @Id
    String beaconUUID;
    String idOspedale;
    String nomeStanze;
    int piano;
    String reparto;
    List<BeaconVicino> vicini;
}
