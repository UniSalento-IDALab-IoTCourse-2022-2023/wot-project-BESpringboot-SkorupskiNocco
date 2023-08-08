package com.unisalento.hospitalmaps.HospitalMapsBE.controller;

import com.unisalento.hospitalmaps.HospitalMapsBE.model.*;
import com.unisalento.hospitalmaps.HospitalMapsBE.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/utente")
public class UserController {
    @Autowired
    UserService service;

    @PostMapping(path = "/ottieniPercorso/{idOspedale}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RispostaMappa postOttieniPercorso(
            @RequestBody RichiestaPercorso richiestaPercorso,
            @PathVariable String idOspedale
            ){
        return service.postOttieniPercorso(richiestaPercorso, idOspedale);
    }

    @GetMapping(path = "/coordinate/{idOspedale}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CoordinateOspedale getCoordinate(
            @PathVariable String idOspedale
    ){
        return service.getCoordinate(idOspedale);
    }

    @GetMapping(path = "/reparti/{idOspedale}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Reparto> getReparti(
            @PathVariable String idOspedale
    ){
        return service.getRepartiByIdOspedale(idOspedale);
    }

    @GetMapping(path = "/stanze/{idOspedale}/{nomeReparto}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Stanza> getStanzeByReparto(
            @PathVariable String idOspedale,
            @PathVariable String nomeReparto
    ){
        return service.getStanzeByIdOspedaleAndReparto(idOspedale,nomeReparto);
    }

    @PostMapping(path = "/ottieniPercorsoDisabili/{idOspedale}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RispostaMappa postOttieniPercorsoDisabili(
            @RequestBody RichiestaPercorso richiestaPercorso,
            @PathVariable String idOspedale
    ){
        return service.postOttieniPercorsoDisabili(richiestaPercorso, idOspedale);
    }


}