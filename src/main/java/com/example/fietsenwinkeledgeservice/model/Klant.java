package com.example.fietsenwinkeledgeservice.model;

public class Klant {

    private Long id;

    private String voornaam;

    private String achternaam;

    private String klantnummer;

    private String gsmNummer;

    private String email;

    private String bedrijf;

    public Klant() {
    }

    public Klant(Long id, String voornaam, String achternaam, String klantnummer, String gsmNummer, String email, String bedrijf) {
        this.id = id;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.klantnummer = klantnummer;
        this.gsmNummer = gsmNummer;
        this.email = email;
        this.bedrijf = bedrijf;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getKlantnummer() {
        return klantnummer;
    }

    public void setKlantnummer(String klantnummer) {
        this.klantnummer = klantnummer;
    }

    public String getGsmNummer() {
        return gsmNummer;
    }

    public void setGsmNummer(String gsmNummer) {
        this.gsmNummer = gsmNummer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBedrijf() {
        return bedrijf;
    }

    public void setBedrijf(String bedrijf) {
        this.bedrijf = bedrijf;
    }
}
