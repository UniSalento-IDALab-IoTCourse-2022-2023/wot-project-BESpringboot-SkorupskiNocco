package com.unisalento.hospitalmaps.HospitalMapsBE.services;

import com.unisalento.hospitalmaps.HospitalMapsBE.model.Beacon;
import com.unisalento.hospitalmaps.HospitalMapsBE.model.MessaggioRisposta;
import com.unisalento.hospitalmaps.HospitalMapsBE.model.RispostaGetBeacon;
import com.unisalento.hospitalmaps.HospitalMapsBE.repository.BeaconRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdministratorService {
    @Autowired
    BeaconRepository beaconRepository;

    public MessaggioRisposta postBeacon(Beacon beacon){
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
                RispostaGetBeacon rispostaBeacon = new RispostaGetBeacon(beacon.getBeaconUUID(),beacon.getIdStanza(), beacon.getNomeStanza());
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
                    RispostaGetBeacon rispostaBeacon = new RispostaGetBeacon(beacon.getBeaconUUID(), beacon.getIdStanza(), beacon.getNomeStanza());
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
}
