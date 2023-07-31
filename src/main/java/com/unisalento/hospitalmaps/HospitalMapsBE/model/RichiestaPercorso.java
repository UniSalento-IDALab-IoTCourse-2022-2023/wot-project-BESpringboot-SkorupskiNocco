package com.unisalento.hospitalmaps.HospitalMapsBE.model;

import com.unisalento.hospitalmaps.HospitalMapsBE.model.enums.Direzioni;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RichiestaPercorso {
    private String uuidPartenza;
    private String repartoArrivo;
    private String stanzaArrivo;
}
