package com.unisalento.hospitalmaps.HospitalMapsBE.model;

import com.unisalento.hospitalmaps.HospitalMapsBE.model.enums.DirezioniPercorso;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reparto {
    String nomeReparto;
}
