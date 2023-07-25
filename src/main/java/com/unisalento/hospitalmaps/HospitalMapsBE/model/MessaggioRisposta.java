package com.unisalento.hospitalmaps.HospitalMapsBE.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessaggioRisposta {
    String messaggio;
    boolean esito;
}
