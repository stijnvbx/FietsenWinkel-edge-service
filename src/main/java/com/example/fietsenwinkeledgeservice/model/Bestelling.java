package com.example.fietsenwinkeledgeservice.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class Bestelling {
    private Integer id;
    private String leverancierBonNummer;
    private String klantnummer;

    private String email;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime bestelDatum;
    private int voorschot;
    private int prijs;
    private String fietsMerk;
    private String fietsModel;
    private String onderdeelMerk;
    private String onderdeelNaam;

    public Bestelling() {
    }

    public Bestelling(String leverancierBonNummer, String email, LocalDateTime bestelDatum, int prijs, int voorschot) {
        setLeverancierBonNummer(leverancierBonNummer);
        setEmail(email);
        setBestelDatum(bestelDatum);
        setPrijs(prijs);
        setVoorschot(voorschot);
    }

    public Bestelling(String leverancierBonNummer,String klantnummer, String email, int prijs, int voorschot,String fietsMerk, String fietsModel) {
        setLeverancierBonNummer(leverancierBonNummer);
        setKlantnummer(klantnummer);
        setEmail(email);
        setPrijs(prijs);
        setVoorschot(voorschot);
        setFietsMerk(fietsMerk);
        setFietsModel(fietsModel);
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

    public String getFietsMerk() {
        return fietsMerk;
    }

    public void setFietsMerk(String fietsMerk) {
        this.fietsMerk = fietsMerk;
    }

    public String getFietsModel() {
        return fietsModel;
    }

    public void setFietsModel(String fietsModel) {
        this.fietsModel = fietsModel;
    }

    public String getOnderdeelMerk() {
        return onderdeelMerk;
    }

    public void setOnderdeelMerk(String onderdeelMerk) {
        this.onderdeelMerk = onderdeelMerk;
    }

    public String getOnderdeelNaam() {
        return onderdeelNaam;
    }

    public void setOnderdeelNaam(String onderdeelNaam) {
        this.onderdeelNaam = onderdeelNaam;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKlantnummer() {
        return klantnummer;
    }

    public void setKlantnummer(String klantnummer) {
        this.klantnummer = klantnummer;
    }
}
