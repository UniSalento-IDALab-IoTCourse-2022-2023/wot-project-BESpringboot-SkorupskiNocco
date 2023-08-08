package com.unisalento.hospitalmaps.HospitalMapsBE.services;

import com.unisalento.hospitalmaps.HospitalMapsBE.model.*;
import com.unisalento.hospitalmaps.HospitalMapsBE.repository.BeaconRepository;
import com.unisalento.hospitalmaps.HospitalMapsBE.repository.CoordinateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdministratorService {
    @Autowired
    BeaconRepository beaconRepository;
    @Autowired
    CoordinateRepository coordinateRepository;

    public MessaggioRisposta postBeacon(BeaconInput beaconInput){
        Beacon beacon = new Beacon();
        beacon.setBeaconUUID(beaconInput.getBeaconUUID());
        beacon.setPiano(beaconInput.getPiano());
        beacon.setReparto(beaconInput.getReparto());
        beacon.setIdOspedale(beaconInput.getIdOspedale());
        if(beaconInput.getNomeStanze() != null)
            beacon.setNomiStanze(Arrays.asList(beaconInput.getNomeStanze().split(",")));
        else
            return new MessaggioRisposta("Inserire le stanze separate da virgola" , false);

        Beacon beaconSaved = beaconRepository.save(beacon);
        if(beaconSaved != null){
            return new MessaggioRisposta("Il beacon con UUID: " + beaconSaved.getBeaconUUID() + " è stato aggiunto correttamente.", true);
        } else{
            return new MessaggioRisposta("Si è verificato un errore durante il salvataggio, riprovare" , false);
        }
    }

    public List<RispostaGetBeacon> getListaBeacon(String idOspedale){
        List<RispostaGetBeacon> risposta = new ArrayList<>();
        List<Beacon> beaconList= beaconRepository.findBeaconByIdOspedale(idOspedale);
        if(beaconList == null){
            return null;
        }else{
            for(Beacon beacon : beaconList){
                RispostaGetBeacon rispostaBeacon = new RispostaGetBeacon(beacon.getBeaconUUID(), beacon.getNomiStanze().stream().collect(Collectors.joining(",")), beacon.getReparto());
                risposta.add(rispostaBeacon);
            }
            return risposta;
        }

    }

    public List<RispostaGetBeacon> getListaBeacon(String idOspedale,String uuidBeacon){
        List<RispostaGetBeacon> risposta = new ArrayList<>();
        List<Beacon> beaconList= beaconRepository.findBeaconByIdOspedale(idOspedale);
        if(beaconList == null){
            return null;
        }else{
            for(Beacon beacon : beaconList){
                if(!beacon.getBeaconUUID().equals(uuidBeacon)) {
                    RispostaGetBeacon rispostaBeacon = new RispostaGetBeacon(beacon.getBeaconUUID(),beacon.getNomiStanze().stream().collect(Collectors.joining(",")), beacon.getReparto());
                    risposta.add(rispostaBeacon);
                }
            }
            return risposta;
        }

    }

    public MessaggioRisposta postBeaconMappato(Beacon beaconMappato){
        Beacon beaconSaved = beaconRepository.findById(beaconMappato.getBeaconUUID()).orElse(null);
        if(beaconSaved == null) {
            return new MessaggioRisposta("Si è verificato un errore durante la mappatura, riprovare", false);
        }
        beaconSaved.setVicini(beaconMappato.getVicini());
        beaconSaved = beaconRepository.save(beaconSaved);
        if(beaconSaved != null){
            return new MessaggioRisposta("Il beacon con UUID: " + beaconSaved.getBeaconUUID() + " è stato mappato correttamente.", true);
        } else{
            return new MessaggioRisposta("Si è verificato un errore durante la mappatura, riprovare" , false);
        }
    }

    public MessaggioRisposta postCoordinate(CoordinateOspedale coordinateOspedale){
        if(coordinateOspedale.getCoordinataIniziale() == null || coordinateOspedale.getCoordinataIniziale() > 360.0 || coordinateOspedale.getCoordinataIniziale() < 0.0){
            return new MessaggioRisposta("Errore durante il rilevamento delle coordinate, riprovare!",false);
        }else if(coordinateOspedale.getIdOspedale() == null){
            return new MessaggioRisposta("Errore, l'id dell'ospedale è nullo!",false);
        }else{
            coordinateRepository.save(coordinateOspedale);
            return new MessaggioRisposta("Coordinate salvate con successo!",true);
        }
    }

    public MessaggioRisposta postBeaconMappatoDisabili(Beacon beaconMappato){
        Beacon beaconSaved = beaconRepository.findById(beaconMappato.getBeaconUUID()).orElse(null);
        if(beaconSaved == null) {
            return new MessaggioRisposta("Si è verificato un errore durante la mappatura, riprovare", false);
        }
        beaconSaved.setViciniPerDisabili(beaconMappato.getViciniPerDisabili());
        beaconSaved = beaconRepository.save(beaconSaved);
        if(beaconSaved != null){
            return new MessaggioRisposta("Il beacon con UUID: " + beaconSaved.getBeaconUUID() + " è stato mappato correttamente.", true);
        } else{
            return new MessaggioRisposta("Si è verificato un errore durante la mappatura, riprovare" , false);
        }
    }

}
