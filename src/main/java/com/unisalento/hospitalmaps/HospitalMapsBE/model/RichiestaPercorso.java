package com.unisalento.hospitalmaps.HospitalMapsBE.model;

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
