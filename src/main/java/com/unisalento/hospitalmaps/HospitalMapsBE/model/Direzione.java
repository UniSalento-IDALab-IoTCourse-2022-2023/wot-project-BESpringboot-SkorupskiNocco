package com.unisalento.hospitalmaps.HospitalMapsBE.model;

import com.unisalento.hospitalmaps.HospitalMapsBE.model.enums.DirezioniPercorso;
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
public class Direzione {
    String beaconUUID;
    DirezioniPercorso nord;
    DirezioniPercorso sud;
    DirezioniPercorso est;
    DirezioniPercorso ovest;
    int posizione;
}
