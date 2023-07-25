package com.unisalento.hospitalmaps.HospitalMapsBE.repository;

import com.unisalento.hospitalmaps.HospitalMapsBE.model.Beacon;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BeaconRepository extends MongoRepository<Beacon,String> {

    public List<Beacon> findBeaconByIdOspedale(String idOspedale);
}
