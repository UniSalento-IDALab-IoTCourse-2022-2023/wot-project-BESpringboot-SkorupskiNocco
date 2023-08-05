package com.unisalento.hospitalmaps.HospitalMapsBE.controller;

import com.unisalento.hospitalmaps.HospitalMapsBE.model.*;
import com.unisalento.hospitalmaps.HospitalMapsBE.services.AdministratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/amministratore")
public class AdministratorController {
    @Autowired
    AdministratorService service;

    @PostMapping(path = "/nuovoBeacon", produces = MediaType.APPLICATION_JSON_VALUE)
    public MessaggioRisposta postBeacon(
            @RequestBody BeaconInput beacon
            ){
        return service.postBeacon(beacon);
    }

    @GetMapping(path = "/allBeacon/{idOspedale}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RispostaGetBeacon> getListaBeacon(
            @PathVariable String idOspedale
    ){
            return service.getListaBeacon(idOspedale);
    }

    @PostMapping(path = "/beaconMappato", produces = MediaType.APPLICATION_JSON_VALUE)
    public MessaggioRisposta postBeaconMappato(
            @RequestBody Beacon beacon
    ){
        return service.postBeaconMappato(beacon);
    }

    @GetMapping(path = "/allBeacon/{idOspedale}/{uuidBeacon}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RispostaGetBeacon> getListaBeacon(
            @PathVariable String idOspedale,
            @PathVariable String uuidBeacon
    ){
        return service.getListaBeacon(idOspedale,uuidBeacon);
    }

    @PostMapping(path = "/nuoveCoordinate", produces = MediaType.APPLICATION_JSON_VALUE)
    public MessaggioRisposta postCoordinate(
            @RequestBody CoordinateOspedale coordinateOspedale
    ){
        return service.postCoordinate(coordinateOspedale);
    }


}