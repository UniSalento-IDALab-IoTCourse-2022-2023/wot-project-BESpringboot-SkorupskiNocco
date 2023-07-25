package com.unisalento.hospitalmaps.HospitalMapsBE.model;

import com.unisalento.hospitalmaps.HospitalMapsBE.model.enums.Direzioni;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "beaconStanza")
public class Beacon {
    @Id
    String beaconUUID;
    String idOspedale;
    String nomeStanza;
    String idStanza;
    int piano;
    String idReparto;
    String reparto;
    List<BeaconVicino> vicini;
}
