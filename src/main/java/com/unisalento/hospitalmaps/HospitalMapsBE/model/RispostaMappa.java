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
public class RispostaMappa {
    Double gradiPartenza;
    List<Direzione> mappa;
}
