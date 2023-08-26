package com.unisalento.hospitalmaps.HospitalMapsBE.services;

import com.unisalento.hospitalmaps.HospitalMapsBE.model.*;
import com.unisalento.hospitalmaps.HospitalMapsBE.model.enums.DirezioniMappatura;
import com.unisalento.hospitalmaps.HospitalMapsBE.model.enums.DirezioniPercorso;
import com.unisalento.hospitalmaps.HospitalMapsBE.repository.BeaconRepository;
import com.unisalento.hospitalmaps.HospitalMapsBE.repository.CoordinateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    BeaconRepository beaconRepository;
    @Autowired
    CoordinateRepository coordinateRepository;


    public RispostaMappa postOttieniPercorso(RichiestaPercorso richiestaPercorso, String idOspedale) {
        CoordinateOspedale coordinateOspedale = coordinateRepository.findById(idOspedale).orElse(null);
        List<Beacon> listaOrdiantaBeacon = ottieniListaPercorso(richiestaPercorso,idOspedale);
        RispostaMappa rispostaMappa = new RispostaMappa();
        if(coordinateOspedale!= null) {
            rispostaMappa.setGradiPartenza(coordinateOspedale.getCoordinataIniziale());
        }
        rispostaMappa.setMappa(new ArrayList<>());

        if (listaOrdiantaBeacon == null)
            return null;

        if(listaOrdiantaBeacon.size() == 1){
            Direzione direzione = new Direzione();
            direzione.setPosizione(1);
            direzione.setNord(DirezioniPercorso.ARRIVATO);
            direzione.setOvest(DirezioniPercorso.ARRIVATO);
            direzione.setEst(DirezioniPercorso.ARRIVATO);
            direzione.setSud(DirezioniPercorso.ARRIVATO);
            direzione.setBeaconUUID(richiestaPercorso.getUuidPartenza());
            rispostaMappa.getMappa().add(direzione);
            return rispostaMappa;
        }

        for(int i = 0; i < listaOrdiantaBeacon.size() - 1; i++){
            Beacon precedente = listaOrdiantaBeacon.get(i);
            Beacon successivo = listaOrdiantaBeacon.get(i+1);
            Direzione direzione = new Direzione();
            direzione.setPosizione(i+1);
            direzione.setBeaconUUID(precedente.getBeaconUUID());
            DirezioniMappatura direzioniMappaturaPrecedente = DirezioniMappatura.FRONTE;
            DirezioniMappatura direzioniMappaturaSuccessivo = DirezioniMappatura.FRONTE;

            for(BeaconVicino beaconVicino : precedente.getVicini()){
                if(beaconVicino.getBeaconUUID().equals(successivo.getBeaconUUID())){
                    direzioniMappaturaPrecedente = beaconVicino.getDirezione();
                    break;
                }
            }

            for(BeaconVicino beaconVicino : successivo.getVicini()){
                if(beaconVicino.getBeaconUUID().equals(precedente.getBeaconUUID())){
                    direzioniMappaturaSuccessivo = beaconVicino.getDirezione();
                    break;
                }
            }
            if(precedente.getPiano() < successivo.getPiano()){
                direzione.setNord(DirezioniPercorso.SALI_SCALE);
                direzione.setOvest(DirezioniPercorso.SALI_SCALE);
                direzione.setEst(DirezioniPercorso.SALI_SCALE);
                direzione.setSud(DirezioniPercorso.SALI_SCALE);
            }else if(precedente.getPiano() > successivo.getPiano()){
                direzione.setNord(DirezioniPercorso.SCENDI_SCALE);
                direzione.setOvest(DirezioniPercorso.SCENDI_SCALE);
                direzione.setEst(DirezioniPercorso.SCENDI_SCALE);
                direzione.setSud(DirezioniPercorso.SCENDI_SCALE);
            }else if(direzioniMappaturaPrecedente.equals(DirezioniMappatura.FRONTE) && direzioniMappaturaSuccessivo.equals(DirezioniMappatura.RETRO)){
                direzione.setNord(DirezioniPercorso.DRITTO);
                direzione.setOvest(DirezioniPercorso.DESTRA);
                direzione.setEst(DirezioniPercorso.SINISTRA);
                direzione.setSud(DirezioniPercorso.DIETRO);
            }else if(direzioniMappaturaPrecedente.equals(DirezioniMappatura.SINISTRA) && direzioniMappaturaSuccessivo.equals(DirezioniMappatura.DESTRA)){
                direzione.setNord(DirezioniPercorso.SINISTRA);
                direzione.setOvest(DirezioniPercorso.DRITTO);
                direzione.setEst(DirezioniPercorso.DIETRO);
                direzione.setSud(DirezioniPercorso.DESTRA);
            }else if(direzioniMappaturaPrecedente.equals(DirezioniMappatura.RETRO) && direzioniMappaturaSuccessivo.equals(DirezioniMappatura.FRONTE)){
                direzione.setNord(DirezioniPercorso.DIETRO);
                direzione.setOvest(DirezioniPercorso.SINISTRA);
                direzione.setEst(DirezioniPercorso.DESTRA);
                direzione.setSud(DirezioniPercorso.DRITTO);
            }else if(direzioniMappaturaPrecedente.equals(DirezioniMappatura.DESTRA) && direzioniMappaturaSuccessivo.equals(DirezioniMappatura.SINISTRA)){
                direzione.setNord(DirezioniPercorso.DESTRA);
                direzione.setOvest(DirezioniPercorso.DIETRO);
                direzione.setEst(DirezioniPercorso.DRITTO);
                direzione.setSud(DirezioniPercorso.SINISTRA);
            }else if(direzioniMappaturaPrecedente.equals(DirezioniMappatura.FRONTE) && direzioniMappaturaSuccessivo.equals(DirezioniMappatura.SINISTRA)){
                direzione.setNord(DirezioniPercorso.DRITTO_DESTRA);
                direzione.setOvest(DirezioniPercorso.DIETRO);
                direzione.setEst(DirezioniPercorso.DRITTO_SINISTRA);
                direzione.setSud(DirezioniPercorso.DIETRO);
            }else if(direzioniMappaturaPrecedente.equals(DirezioniMappatura.SINISTRA) && direzioniMappaturaSuccessivo.equals(DirezioniMappatura.FRONTE)){
                direzione.setNord(DirezioniPercorso.DIETRO);
                direzione.setOvest(DirezioniPercorso.DRITTO_SINISTRA);
                direzione.setEst(DirezioniPercorso.DRITTO_DESTRA);
                direzione.setSud(DirezioniPercorso.DIETRO);
            }else if(direzioniMappaturaPrecedente.equals(DirezioniMappatura.DESTRA) && direzioniMappaturaSuccessivo.equals(DirezioniMappatura.RETRO)){
                direzione.setNord(DirezioniPercorso.DRITTO_DESTRA);
                direzione.setOvest(DirezioniPercorso.DIETRO);
                direzione.setEst(DirezioniPercorso.DRITTO_SINISTRA);
                direzione.setSud(DirezioniPercorso.SINISTRA);
            }else if(direzioniMappaturaPrecedente.equals(DirezioniMappatura.RETRO) && direzioniMappaturaSuccessivo.equals(DirezioniMappatura.DESTRA)){
                direzione.setNord(DirezioniPercorso.DIETRO);
                direzione.setOvest(DirezioniPercorso.DRITTO_SINISTRA);
                direzione.setEst(DirezioniPercorso.DIETRO);
                direzione.setSud(DirezioniPercorso.DRITTO_DESTRA);
            }else if(direzioniMappaturaPrecedente.equals(DirezioniMappatura.FRONTE) && direzioniMappaturaSuccessivo.equals(DirezioniMappatura.DESTRA)){
                direzione.setNord(DirezioniPercorso.DRITTO_SINISTRA);
                direzione.setOvest(DirezioniPercorso.DRITTO_DESTRA);
                direzione.setEst(DirezioniPercorso.DIETRO);
                direzione.setSud(DirezioniPercorso.DIETRO);
            }else if(direzioniMappaturaPrecedente.equals(DirezioniMappatura.DESTRA) && direzioniMappaturaSuccessivo.equals(DirezioniMappatura.FRONTE)){
                direzione.setNord(DirezioniPercorso.DIETRO);
                direzione.setOvest(DirezioniPercorso.DIETRO);
                direzione.setEst(DirezioniPercorso.DRITTO_DESTRA);
                direzione.setSud(DirezioniPercorso.DRITTO_SINISTRA);
            }else if(direzioniMappaturaPrecedente.equals(DirezioniMappatura.SINISTRA) && direzioniMappaturaSuccessivo.equals(DirezioniMappatura.RETRO)){
                direzione.setNord(DirezioniPercorso.DRITTO_SINISTRA);
                direzione.setOvest(DirezioniPercorso.DRITTO_DESTRA);
                direzione.setEst(DirezioniPercorso.DIETRO);
                direzione.setSud(DirezioniPercorso.DIETRO);
            }else if(direzioniMappaturaPrecedente.equals(DirezioniMappatura.RETRO) && direzioniMappaturaSuccessivo.equals(DirezioniMappatura.SINISTRA)){
                direzione.setNord(DirezioniPercorso.DIETRO);
                direzione.setOvest(DirezioniPercorso.DIETRO);
                direzione.setEst(DirezioniPercorso.DRITTO_DESTRA);
                direzione.setSud(DirezioniPercorso.DRITTO_SINISTRA);
            }
            rispostaMappa.getMappa().add(direzione);
        }
        Direzione direzioneFinale = new Direzione();
        direzioneFinale.setPosizione(listaOrdiantaBeacon.size());
        direzioneFinale.setBeaconUUID(listaOrdiantaBeacon.get(listaOrdiantaBeacon.size() - 1).getBeaconUUID());
        direzioneFinale.setNord(DirezioniPercorso.ARRIVATO);
        direzioneFinale.setSud(DirezioniPercorso.ARRIVATO);
        direzioneFinale.setOvest(DirezioniPercorso.ARRIVATO);
        direzioneFinale.setEst(DirezioniPercorso.ARRIVATO);
        rispostaMappa.getMappa().add(direzioneFinale);
        return rispostaMappa;
    }

    private List<Beacon> ottieniListaPercorso(RichiestaPercorso richiestaPercorso, String idOspedale) {
        List<Beacon> beaconList = beaconRepository.findBeaconByIdOspedale(idOspedale);
        for(Beacon beacon : beaconList){
            if(beacon.getReparto().equals(richiestaPercorso.getRepartoArrivo())){
                for(String stanza : beacon.getNomiStanze()){
                    if(stanza.equals(richiestaPercorso.getStanzaArrivo())){
                        if(beacon.getBeaconUUID().equals(richiestaPercorso.getUuidPartenza())){
                            List<Beacon> beacons = new ArrayList<>();
                            beacons.add(beacon);
                            return beacons;
                        }
                        return djjkstraPercorso(beaconList, richiestaPercorso.getUuidPartenza(), beacon.getBeaconUUID());

                    }
                }

            }
        }
        return null;

    }

    private List<Beacon> djjkstraPercorso(List<Beacon> beaconList, String beaconPartenza, String beaconArrivo){
        List<BeaconDjikstra> beaconDjikstraList = new ArrayList<>();

        for(Beacon beacon: beaconList){
            BeaconDjikstra beaconDjikstra = new BeaconDjikstra();
            beaconDjikstra.setVicini(new ArrayList<>(beacon.getVicini()));
            beaconDjikstra.setBeaconUUID(beacon.getBeaconUUID());
            if(beacon.getBeaconUUID().equals(beaconPartenza))
                beaconDjikstra.setPesoTotale(0.0);
            else
                beaconDjikstra.setPesoTotale(9999999.0);
            beaconDjikstra.setVerificato(false);
            beaconDjikstra.setPrecedente(null);
            beaconDjikstraList.add(beaconDjikstra);
        }
        Boolean fine = false;
        String beaconAttuale = beaconPartenza;
        for(int i = 0; fine == false && i < beaconDjikstraList.size(); i++){
            fine = true;
            int indexAttuale = findIndexOfBeaconDjikstraByUUID(beaconDjikstraList, beaconAttuale);
            for(BeaconVicino beaconVicino : beaconDjikstraList.get(indexAttuale).getVicini()) {
                int indexVicino = findIndexOfBeaconDjikstraByUUID(beaconDjikstraList, beaconVicino.getBeaconUUID());
                    fine = false;
                    BeaconDjikstra beaconDjikstraVicino = beaconDjikstraList.get(indexVicino);
                    Double distanza = beaconVicino.getDistanza() + beaconDjikstraList.get(indexAttuale).getPesoTotale();
                    if(beaconDjikstraVicino.getPesoTotale() > distanza){
                        beaconDjikstraList.get(indexVicino).setPesoTotale(distanza);
                        beaconDjikstraList.get(indexVicino).setPrecedente(beaconDjikstraList.get(indexAttuale).getBeaconUUID());
                    }
            }
            beaconDjikstraList.get(indexAttuale).setVerificato(true);
            Double pesoMinimo = 99999.0;
            for(BeaconDjikstra beaconDjikstra : beaconDjikstraList){
                if(pesoMinimo > beaconDjikstra.getPesoTotale() && beaconDjikstra.getVerificato() == false){
                    pesoMinimo = beaconDjikstra.getPesoTotale();
                    beaconAttuale = beaconDjikstra.getBeaconUUID();
                }
            }
        }

        return listaOrdinata(beaconDjikstraList, beaconList, beaconPartenza, beaconArrivo);

    }


    private int findIndexOfBeaconDjikstraByUUID(List<BeaconDjikstra> beaconList, String targetUUID) {
        for (int i = 0; i < beaconList.size(); i++) {
            BeaconDjikstra beacon = beaconList.get(i);
            if (beacon.getBeaconUUID().equals(targetUUID)) {
                return i; // Restituisce l'indice del beacon con l'UUID specificato
            }
        }
        return -1; // Restituisce -1 se il beacon con l'UUID specificato non è stato trovato nella lista
    }

    private int findIndexOfBeaconByUUID(List<Beacon> beaconList, String targetUUID) {
        for (int i = 0; i < beaconList.size(); i++) {
            Beacon beacon = beaconList.get(i);
            if (beacon.getBeaconUUID().equals(targetUUID)) {
                return i; // Restituisce l'indice del beacon con l'UUID specificato
            }
        }
        return -1; // Restituisce -1 se il beacon con l'UUID specificato non è stato trovato nella lista
    }

    private List<Beacon> listaOrdinata(List<BeaconDjikstra> beaconDjikstraList, List<Beacon> beaconList, String beaconPartenza, String beaconArrivo){
        int indexArrivo = findIndexOfBeaconDjikstraByUUID(beaconDjikstraList, beaconArrivo);
        List<Beacon> listaBeaconOutput = new ArrayList<>();
        String next = beaconArrivo;
        boolean finePercorso = false;
        int indexAttuale = findIndexOfBeaconByUUID(beaconList,beaconArrivo);
        listaBeaconOutput.add(beaconList.get(indexAttuale));
        while(!finePercorso) {
           next = beaconDjikstraList.get(findIndexOfBeaconDjikstraByUUID(beaconDjikstraList,next)).getPrecedente();
           if(next != null) {
               indexAttuale = findIndexOfBeaconByUUID(beaconList, next);
           } else{
               throw new NullPointerException("Problema generazione mappa");
           }
            listaBeaconOutput.add(beaconList.get(indexAttuale));
           if(next.equals(beaconPartenza)){
               finePercorso = true;
           }
        }
        Collections.reverse(listaBeaconOutput);
        return listaBeaconOutput;
    }

    public CoordinateOspedale getCoordinate(String idOspedale){
        return coordinateRepository.findById(idOspedale).orElse(null);
    }

    public List<Reparto> getRepartiByIdOspedale(String idOspedale){
        List<Beacon> beaconList = beaconRepository.findBeaconByIdOspedale(idOspedale);
        List<Reparto> reparti = new ArrayList<>();
        for(Beacon beacon : beaconList){
            boolean presente = false;
            for(int i = 0; i < reparti.size(); i++){
                if(beacon.getReparto().equals(reparti.get(i).getNomeReparto()) || beacon.getReparto().equalsIgnoreCase("none")){
                    presente = true;
                }
            }
            if(presente == false){
                Reparto reparto = new Reparto(beacon.getReparto());
                reparti.add(reparto);
            }
        }
        return reparti;
    }

    public List<Stanza> getStanzeByIdOspedaleAndReparto(String idOspedale, String nomeReparto) {
        List<Beacon> beaconList = beaconRepository.findBeaconByIdOspedale(idOspedale);
        List<Stanza> stanze = new ArrayList<>();
        for (Beacon beacon : beaconList) {
            if (beacon.getReparto().equals(nomeReparto)) {
                boolean presente = false;
                for(int j = 0; j < beacon.getNomiStanze().size(); j++) {
                    for (int i = 0; i < stanze.size(); i++) {
                        if (beacon.getNomiStanze().get(j).equals(stanze.get(i).getNomeStanza())) {
                            presente = true;
                        }
                    }
                    if (presente == false) {
                        Stanza stanza = new Stanza(beacon.getNomiStanze().get(j));
                        stanze.add(stanza);
                    }
                }
            }

        }
        return stanze;
    }

    public RispostaMappa postOttieniPercorsoDisabili(RichiestaPercorso richiestaPercorso, String idOspedale) {
        CoordinateOspedale coordinateOspedale = coordinateRepository.findById(idOspedale).orElse(null);
        List<Beacon> listaOrdiantaBeacon = ottieniListaPercorsoDisabili(richiestaPercorso,idOspedale);
        RispostaMappa rispostaMappa = new RispostaMappa();
        if(coordinateOspedale!= null) {
            rispostaMappa.setGradiPartenza(coordinateOspedale.getCoordinataIniziale());
        }
        rispostaMappa.setMappa(new ArrayList<>());

        if (listaOrdiantaBeacon == null)
            return null;

        if(listaOrdiantaBeacon.size() == 1){
            Direzione direzione = new Direzione();
            direzione.setPosizione(1);
            direzione.setNord(DirezioniPercorso.ARRIVATO);
            direzione.setOvest(DirezioniPercorso.ARRIVATO);
            direzione.setEst(DirezioniPercorso.ARRIVATO);
            direzione.setSud(DirezioniPercorso.ARRIVATO);
            direzione.setBeaconUUID(richiestaPercorso.getUuidPartenza());
            rispostaMappa.getMappa().add(direzione);
            return rispostaMappa;
        }

        for(int i = 0; i < listaOrdiantaBeacon.size() - 1; i++){
            Beacon precedente = listaOrdiantaBeacon.get(i);
            Beacon successivo = listaOrdiantaBeacon.get(i+1);
            Direzione direzione = new Direzione();
            direzione.setPosizione(i+1);
            direzione.setBeaconUUID(precedente.getBeaconUUID());
            DirezioniMappatura direzioniMappaturaPrecedente = DirezioniMappatura.FRONTE;
            DirezioniMappatura direzioniMappaturaSuccessivo = DirezioniMappatura.FRONTE;

            for(BeaconVicino beaconVicino : precedente.getViciniPerDisabili()){
                if(beaconVicino.getBeaconUUID().equals(successivo.getBeaconUUID())){
                    direzioniMappaturaPrecedente = beaconVicino.getDirezione();
                    break;
                }
            }

            for(BeaconVicino beaconVicino : successivo.getViciniPerDisabili()){
                if(beaconVicino.getBeaconUUID().equals(precedente.getBeaconUUID())){
                    direzioniMappaturaSuccessivo = beaconVicino.getDirezione();
                    break;
                }
            }
            if(precedente.getPiano() < successivo.getPiano()){
                direzione.setNord(DirezioniPercorso.SALI_SCALE);
                direzione.setOvest(DirezioniPercorso.SALI_SCALE);
                direzione.setEst(DirezioniPercorso.SALI_SCALE);
                direzione.setSud(DirezioniPercorso.SALI_SCALE);
            }else if(precedente.getPiano() > successivo.getPiano()){
                direzione.setNord(DirezioniPercorso.SCENDI_SCALE);
                direzione.setOvest(DirezioniPercorso.SCENDI_SCALE);
                direzione.setEst(DirezioniPercorso.SCENDI_SCALE);
                direzione.setSud(DirezioniPercorso.SCENDI_SCALE);
            }else if(direzioniMappaturaPrecedente.equals(DirezioniMappatura.FRONTE) && direzioniMappaturaSuccessivo.equals(DirezioniMappatura.RETRO)){
                direzione.setNord(DirezioniPercorso.DRITTO);
                direzione.setOvest(DirezioniPercorso.DESTRA);
                direzione.setEst(DirezioniPercorso.SINISTRA);
                direzione.setSud(DirezioniPercorso.DIETRO);
            }else if(direzioniMappaturaPrecedente.equals(DirezioniMappatura.SINISTRA) && direzioniMappaturaSuccessivo.equals(DirezioniMappatura.DESTRA)){
                direzione.setNord(DirezioniPercorso.SINISTRA);
                direzione.setOvest(DirezioniPercorso.DRITTO);
                direzione.setEst(DirezioniPercorso.DIETRO);
                direzione.setSud(DirezioniPercorso.DESTRA);
            }else if(direzioniMappaturaPrecedente.equals(DirezioniMappatura.RETRO) && direzioniMappaturaSuccessivo.equals(DirezioniMappatura.FRONTE)){
                direzione.setNord(DirezioniPercorso.DIETRO);
                direzione.setOvest(DirezioniPercorso.SINISTRA);
                direzione.setEst(DirezioniPercorso.DESTRA);
                direzione.setSud(DirezioniPercorso.DRITTO);
            }else if(direzioniMappaturaPrecedente.equals(DirezioniMappatura.DESTRA) && direzioniMappaturaSuccessivo.equals(DirezioniMappatura.SINISTRA)){
                direzione.setNord(DirezioniPercorso.DESTRA);
                direzione.setOvest(DirezioniPercorso.DIETRO);
                direzione.setEst(DirezioniPercorso.DRITTO);
                direzione.setSud(DirezioniPercorso.SINISTRA);
            }else if(direzioniMappaturaPrecedente.equals(DirezioniMappatura.FRONTE) && direzioniMappaturaSuccessivo.equals(DirezioniMappatura.SINISTRA)){
                direzione.setNord(DirezioniPercorso.DRITTO_DESTRA);
                direzione.setOvest(DirezioniPercorso.DIETRO);
                direzione.setEst(DirezioniPercorso.DRITTO_SINISTRA);
                direzione.setSud(DirezioniPercorso.DIETRO);
            }else if(direzioniMappaturaPrecedente.equals(DirezioniMappatura.SINISTRA) && direzioniMappaturaSuccessivo.equals(DirezioniMappatura.FRONTE)){
                direzione.setNord(DirezioniPercorso.DIETRO);
                direzione.setOvest(DirezioniPercorso.DRITTO_SINISTRA);
                direzione.setEst(DirezioniPercorso.DRITTO_DESTRA);
                direzione.setSud(DirezioniPercorso.DIETRO);
            }else if(direzioniMappaturaPrecedente.equals(DirezioniMappatura.DESTRA) && direzioniMappaturaSuccessivo.equals(DirezioniMappatura.RETRO)){
                direzione.setNord(DirezioniPercorso.DRITTO_DESTRA);
                direzione.setOvest(DirezioniPercorso.DIETRO);
                direzione.setEst(DirezioniPercorso.DRITTO_SINISTRA);
                direzione.setSud(DirezioniPercorso.SINISTRA);
            }else if(direzioniMappaturaPrecedente.equals(DirezioniMappatura.RETRO) && direzioniMappaturaSuccessivo.equals(DirezioniMappatura.DESTRA)){
                direzione.setNord(DirezioniPercorso.DIETRO);
                direzione.setOvest(DirezioniPercorso.DRITTO_SINISTRA);
                direzione.setEst(DirezioniPercorso.DIETRO);
                direzione.setSud(DirezioniPercorso.DRITTO_DESTRA);
            }else if(direzioniMappaturaPrecedente.equals(DirezioniMappatura.FRONTE) && direzioniMappaturaSuccessivo.equals(DirezioniMappatura.DESTRA)){
                direzione.setNord(DirezioniPercorso.DRITTO_SINISTRA);
                direzione.setOvest(DirezioniPercorso.DRITTO_DESTRA);
                direzione.setEst(DirezioniPercorso.DIETRO);
                direzione.setSud(DirezioniPercorso.DIETRO);
            }else if(direzioniMappaturaPrecedente.equals(DirezioniMappatura.DESTRA) && direzioniMappaturaSuccessivo.equals(DirezioniMappatura.FRONTE)){
                direzione.setNord(DirezioniPercorso.DIETRO);
                direzione.setOvest(DirezioniPercorso.DIETRO);
                direzione.setEst(DirezioniPercorso.DRITTO_DESTRA);
                direzione.setSud(DirezioniPercorso.DRITTO_SINISTRA);
            }else if(direzioniMappaturaPrecedente.equals(DirezioniMappatura.SINISTRA) && direzioniMappaturaSuccessivo.equals(DirezioniMappatura.RETRO)){
                direzione.setNord(DirezioniPercorso.DRITTO_SINISTRA);
                direzione.setOvest(DirezioniPercorso.DRITTO_DESTRA);
                direzione.setEst(DirezioniPercorso.DIETRO);
                direzione.setSud(DirezioniPercorso.DIETRO);
            }else if(direzioniMappaturaPrecedente.equals(DirezioniMappatura.RETRO) && direzioniMappaturaSuccessivo.equals(DirezioniMappatura.SINISTRA)){
                direzione.setNord(DirezioniPercorso.DIETRO);
                direzione.setOvest(DirezioniPercorso.DIETRO);
                direzione.setEst(DirezioniPercorso.DRITTO_DESTRA);
                direzione.setSud(DirezioniPercorso.DRITTO_SINISTRA);
            }
            rispostaMappa.getMappa().add(direzione);
        }
        Direzione direzioneFinale = new Direzione();
        direzioneFinale.setPosizione(listaOrdiantaBeacon.size());
        direzioneFinale.setBeaconUUID(listaOrdiantaBeacon.get(listaOrdiantaBeacon.size() - 1).getBeaconUUID());
        direzioneFinale.setNord(DirezioniPercorso.ARRIVATO);
        direzioneFinale.setSud(DirezioniPercorso.ARRIVATO);
        direzioneFinale.setOvest(DirezioniPercorso.ARRIVATO);
        direzioneFinale.setEst(DirezioniPercorso.ARRIVATO);
        rispostaMappa.getMappa().add(direzioneFinale);
        return rispostaMappa;
    }

    private List<Beacon> djjkstraPercorsoDisabili(List<Beacon> beaconList, String beaconPartenza, String beaconArrivo){
        List<BeaconDjikstra> beaconDjikstraList = new ArrayList<>();

        for(Beacon beacon: beaconList){
            BeaconDjikstra beaconDjikstra = new BeaconDjikstra();
            beaconDjikstra.setVicini(new ArrayList<>(beacon.getViciniPerDisabili()));
            beaconDjikstra.setBeaconUUID(beacon.getBeaconUUID());
            if(beacon.getBeaconUUID().equals(beaconPartenza))
                beaconDjikstra.setPesoTotale(0.0);
            else
                beaconDjikstra.setPesoTotale(9999999.0);
            beaconDjikstra.setVerificato(false);
            beaconDjikstra.setPrecedente(null);
            beaconDjikstraList.add(beaconDjikstra);
        }
        Boolean fine = false;
        String beaconAttuale = beaconPartenza;
        for(int i = 0; fine == false && i < beaconDjikstraList.size(); i++){
            fine = true;
            int indexAttuale = findIndexOfBeaconDjikstraByUUID(beaconDjikstraList, beaconAttuale);
            for(BeaconVicino beaconVicino : beaconDjikstraList.get(indexAttuale).getVicini()) {
                int indexVicino = findIndexOfBeaconDjikstraByUUID(beaconDjikstraList, beaconVicino.getBeaconUUID());
                fine = false;
                BeaconDjikstra beaconDjikstraVicino = beaconDjikstraList.get(indexVicino);
                Double distanza = beaconVicino.getDistanza() + beaconDjikstraList.get(indexAttuale).getPesoTotale();
                if(beaconDjikstraVicino.getPesoTotale() > distanza){
                    beaconDjikstraList.get(indexVicino).setPesoTotale(distanza);
                    beaconDjikstraList.get(indexVicino).setPrecedente(beaconDjikstraList.get(indexAttuale).getBeaconUUID());
                }
            }
            beaconDjikstraList.get(indexAttuale).setVerificato(true);
            Double pesoMinimo = 99999.0;
            for(BeaconDjikstra beaconDjikstra : beaconDjikstraList){
                if(pesoMinimo > beaconDjikstra.getPesoTotale() && beaconDjikstra.getVerificato() == false){
                    pesoMinimo = beaconDjikstra.getPesoTotale();
                    beaconAttuale = beaconDjikstra.getBeaconUUID();
                }
            }
        }

        return listaOrdinata(beaconDjikstraList, beaconList, beaconPartenza, beaconArrivo);

    }

    private List<Beacon> ottieniListaPercorsoDisabili(RichiestaPercorso richiestaPercorso, String idOspedale) {
        List<com.unisalento.hospitalmaps.HospitalMapsBE.model.Beacon> beaconList = beaconRepository.findBeaconByIdOspedale(idOspedale);
        for(com.unisalento.hospitalmaps.HospitalMapsBE.model.Beacon beacon : beaconList){
            if(beacon.getReparto().equals(richiestaPercorso.getRepartoArrivo())){
                for(String stanza : beacon.getNomiStanze()){
                    if(stanza.equals(richiestaPercorso.getStanzaArrivo())){
                        if(beacon.getBeaconUUID().equals(richiestaPercorso.getUuidPartenza())){
                            List<Beacon> beacons = new ArrayList<>();
                            beacons.add(beacon);
                            return beacons;
                        }
                        return djjkstraPercorsoDisabili(beaconList, richiestaPercorso.getUuidPartenza(), beacon.getBeaconUUID());

                    }
                }

            }
        }
        return null;

    }


}
