package com.example.fietsenwinkeledgeservice.controller;

import com.example.fietsenwinkeledgeservice.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FietsenWinkelController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${fietsenservice.baseurl}")
    private String fietsenServiceBaseUrl;

    @Value("${onderdeelservice.baseurl}")
    private String onderdeelServiceBaseUrl;

    @Value("${bestellingservice.baseurl}")
    private String bestellingServiceBaseUrl;

    @Value("${klantservice.baseurl}")
    private String klantServiceBaseUrl;


    @GetMapping("/bestellingen/leverancierBonNummer/{leverancierBonNummer}/onderdeelMerk/{merk}")
    public FilledBestelling getBestellingenByLeverancierBonNummerAndOnderdeelNaam(@PathVariable String leverancierBonNummer, @PathVariable String merk) {

        Bestelling bestelling =
                restTemplate.getForObject("http://" + bestellingServiceBaseUrl + "/bestelling/{leverancierBonNummer}",
                        Bestelling.class, leverancierBonNummer);

        ResponseEntity<List<Onderdeel>> responseEntityOnderdelen =
                restTemplate.exchange("http://" + onderdeelServiceBaseUrl + "/onderdeel/merk/{merk}",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Onderdeel>>() {
                        }, merk);

        return new FilledBestelling(bestelling, responseEntityOnderdelen.getBody());
    }

    @GetMapping("/bestellingen/email/{email}")
    public List<FilledBestelling> getBestellingenByEmail(@PathVariable String email) {

        List<FilledBestelling> returnList = new ArrayList<>();

        ResponseEntity<List<Bestelling>> responseEntityBestellingen =
                restTemplate.exchange("http://" + bestellingServiceBaseUrl + "/bestellingen/email/{email}",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Bestelling>>() {
                        }, email);

        List<Bestelling> bestellingen = responseEntityBestellingen.getBody();

        for (Bestelling bestelling:
                bestellingen) {
            Fiets fiets =
                    restTemplate.getForObject("http://" + fietsenServiceBaseUrl + "/merk/{merk}/model/{model}",
                            Fiets.class, bestelling.getFietsMerk(), bestelling.getFietsModel());

            returnList.add(new FilledBestelling(bestelling, fiets));
        }

        return returnList;
    }

    @PostMapping("/bestellingen")
    public FilledBestelling createBestelling(@RequestBody Bestelling bestellingBody){
        Bestelling bestelling = restTemplate.postForObject("http://" + bestellingServiceBaseUrl + "/bestellingen",
                new Bestelling(bestellingBody.getLeverancierBonNummer(),bestellingBody.getKlantnummer() , bestellingBody.getEmail(),bestellingBody.getPrijs(), bestellingBody.getVoorschot(), bestellingBody.getFietsMerk() ,bestellingBody.getFietsModel()), Bestelling.class);
        Klant klant = restTemplate.getForObject("http://" + klantServiceBaseUrl + "/klanten/klantnummer?klantnummer=" + bestellingBody.getKlantnummer(), Klant.class);

        Fiets fiets = restTemplate.getForObject("http://" + fietsenServiceBaseUrl + "/fietsen/model/{model}", Fiets.class, bestellingBody.getFietsModel());

        return new FilledBestelling(bestelling, fiets, klant);
    }

    @DeleteMapping("/bestelling/{leverancierBonNummer}")
    public ResponseEntity deleteBestelling(@PathVariable String leverancierBonNummer) {
        restTemplate.delete("http://" + bestellingServiceBaseUrl + "/bestelling/" + leverancierBonNummer);

        return ResponseEntity.ok().build();
    }

}
