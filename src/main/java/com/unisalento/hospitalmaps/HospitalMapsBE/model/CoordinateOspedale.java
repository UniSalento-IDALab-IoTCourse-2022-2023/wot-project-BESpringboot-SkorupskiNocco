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
@Document(collection = "CoordinateOspedali")
public class CoordinateOspedale {
    @Id
    String idOspedale;
    Double coordinataIniziale;
}
