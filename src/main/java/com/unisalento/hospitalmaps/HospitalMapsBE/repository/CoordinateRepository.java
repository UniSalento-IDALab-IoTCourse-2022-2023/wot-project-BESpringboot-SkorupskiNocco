package com.unisalento.hospitalmaps.HospitalMapsBE.repository;

import com.unisalento.hospitalmaps.HospitalMapsBE.model.Beacon;
import com.unisalento.hospitalmaps.HospitalMapsBE.model.CoordinateOspedale;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CoordinateRepository extends MongoRepository<CoordinateOspedale,String> {
}
