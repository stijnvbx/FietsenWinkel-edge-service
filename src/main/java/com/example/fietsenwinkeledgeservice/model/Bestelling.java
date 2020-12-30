package com.example.fietsenwinkeledgeservice.model;


import java.time.LocalDateTime;

public class Bestelling {
    private Integer id;
    private String leverancierBonNummer;

    private String email;
    private LocalDateTime bestelDatum;
    private int voorschot;
    private int prijs;

    private String fietsSerienummer;
    // eventueel voor appart onderdelen te bestellen
    private String onderdeelSerienummer;


    public Bestelling(String leverancierBonNummer, String email, LocalDateTime bestelDatum, int prijs, int voorschot) {
        setLeverancierBonNummer(leverancierBonNummer);
        setEmail(email);
        setBestelDatum(bestelDatum);
        setPrijs(prijs);
        setVoorschot(voorschot);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getBestelDatum() {
        return bestelDatum;
    }

    public void setBestelDatum(LocalDateTime bestel_datum) {
        this.bestelDatum = bestel_datum;
    }

    public String getFietsSerienummer() {
        return fietsSerienummer;
    }

    public void setFietsSerienummer(String fiets_serienummer) {
        this.fietsSerienummer = fiets_serienummer;
    }

    public String getOnderdeelSerienummer() {
        return onderdeelSerienummer;
    }

    public void setOnderdeelSerienummer(String onderdeel_serienummer) {
        this.onderdeelSerienummer = onderdeel_serienummer;
    }

    public int getVoorschot() {
        return voorschot;
    }

    public void setVoorschot(int voorschot) {
        this.voorschot = voorschot;
    }

    public int getPrijs() {
        return prijs;
    }

    public void setPrijs(int prijs) {
        this.prijs = prijs;
    }

    public String getLeverancierBonNummer() {
        return leverancierBonNummer;
    }

    public void setLeverancierBonNummer(String leverancierBonNummer) {
        this.leverancierBonNummer = leverancierBonNummer;
    }
}
