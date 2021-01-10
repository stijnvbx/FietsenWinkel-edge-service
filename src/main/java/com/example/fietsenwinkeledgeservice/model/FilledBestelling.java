package com.example.fietsenwinkeledgeservice.model;

import org.springframework.http.ResponseEntity;

import java.util.List;

public class FilledBestelling {

    private String leverancierBonNummer;
    private String email;
    private String merk;
    private String model;
    private String klantnummer;

    private List<Onderdeel> onderdelen;

    public FilledBestelling(Bestelling bestelling, Fiets fiets) {
        setLeverancierBonNummer(bestelling.getLeverancierBonNummer());
        setEmail(bestelling.getEmail());
        setMerk(fiets.getMerk());
        setModel(fiets.getModel());
    }

    public FilledBestelling(Bestelling bestelling, Klant klant) {
        setLeverancierBonNummer(bestelling.getLeverancierBonNummer());
        setEmail(bestelling.getEmail());
        setKlantnummer(klant.getKlantnummer());
    }

    public FilledBestelling(Bestelling bestelling, Fiets fiets, Klant klant) {
        setLeverancierBonNummer(bestelling.getLeverancierBonNummer());
        setEmail(bestelling.getEmail());
        setMerk(fiets.getMerk());
        setModel(fiets.getModel());
        setKlantnummer(klant.getKlantnummer());
    }

    public FilledBestelling(Bestelling bestelling, List<Onderdeel> onderdelen) {
        setLeverancierBonNummer(bestelling.getLeverancierBonNummer());
        setEmail(bestelling.getEmail());
        setOnderdelen(onderdelen);
    }

    public String getLeverancierBonNummer() {
        return leverancierBonNummer;
    }

    public void setLeverancierBonNummer(String leverancierBonNummer) {
        this.leverancierBonNummer = leverancierBonNummer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Onderdeel> getOnderdelen() {
        return onderdelen;
    }

    public void setOnderdelen(List<Onderdeel> onderdelen) {
        this.onderdelen = onderdelen;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getKlantnummer() {
        return klantnummer;
    }

    public void setKlantnummer(String klantnummer) {
        this.klantnummer = klantnummer;
    }
}
