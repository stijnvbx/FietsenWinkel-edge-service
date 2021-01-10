package com.example.fietsenwinkeledgeservice.controller;

import com.example.fietsenwinkeledgeservice.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
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


    @GetMapping("/bestellingen")
    public List<FilledBestelling> getAllBestellingen() {
        List<FilledBestelling> returnList = new ArrayList<>();

        ResponseEntity<List<Bestelling>> responseEntityBestellingen =
                restTemplate.exchange("http://" + bestellingServiceBaseUrl + "/bestellingen/",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Bestelling>>() {});

        List<Bestelling> bestellingen = responseEntityBestellingen.getBody();

        for (Bestelling bestelling:
                bestellingen) {
            Klant klant = restTemplate.getForObject("http://" + klantServiceBaseUrl + "/klanten/klantnummer?klantnummer=" + bestelling.getKlantnummer(), Klant.class);

            returnList.add(new FilledBestelling(bestelling, klant));
        }

        return returnList;
    }

    @GetMapping("/fietsen")
    public List<Fiets> getAllFietsen() {

        ResponseEntity<List<Fiets>> responseEntityFietsen =
                restTemplate.exchange("http://" + fietsenServiceBaseUrl + "/fietsen",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Fiets>>() {});

        List<Fiets> fietsen = responseEntityFietsen.getBody();

        return fietsen;
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
            Fiets fiets = restTemplate.getForObject("http://" + fietsenServiceBaseUrl + "/fietsen/model/{model}", Fiets.class, bestelling.getFietsModel());
            Klant klant = restTemplate.getForObject("http://" + klantServiceBaseUrl + "/klanten/klantnummer?klantnummer=" + bestelling.getKlantnummer(), Klant.class);


            returnList.add(new FilledBestelling(bestelling, fiets, klant));
        }

        return returnList;
    }

    @GetMapping("/klanten/klantnummer")
    public Klant getKlantByKlantnummer(@RequestParam String klantnummer) {
        Klant klant = restTemplate.getForObject("http://" + klantServiceBaseUrl + "/klanten/klantnummer?klantnummer=" + klantnummer, Klant.class);

        return klant;
    }

    @PostMapping("/bestellingen")
    public FilledBestelling createBestelling(@RequestParam String leverancierBonNummer, @RequestParam String klantnummer, @RequestParam String email, @RequestParam Integer prijs, @RequestParam Integer voorschot, @RequestParam String fietsMerk, @RequestParam String fietsModel){
        Bestelling bestelling = restTemplate.postForObject("http://" + bestellingServiceBaseUrl + "/bestellingen",
                new Bestelling(leverancierBonNummer, klantnummer, email, prijs, voorschot, fietsMerk, fietsModel), Bestelling.class);
        Klant klant = restTemplate.getForObject("http://" + klantServiceBaseUrl + "/klanten/klantnummer?klantnummer=" + klantnummer, Klant.class);

        Fiets fiets = restTemplate.getForObject("http://" + fietsenServiceBaseUrl + "/fietsen/model/{model}", Fiets.class, fietsModel);

        return new FilledBestelling(bestelling, fiets, klant);
    }

    @PutMapping("/bestellingen")
    public FilledBestelling updateBestelling(@RequestParam String leverancierBonNummer, @RequestParam String fietsMerk, @RequestParam String fietsModel) {
        Bestelling bestelling = restTemplate.getForObject("http://" + bestellingServiceBaseUrl + "/bestelling/{leverancierBonNummer}", Bestelling.class, leverancierBonNummer);
        bestelling.setFietsMerk(fietsMerk);
        bestelling.setFietsModel(fietsModel);

        ResponseEntity<Bestelling> responseEntityBestelling = restTemplate.exchange("http://" + bestellingServiceBaseUrl + "/bestellingen", HttpMethod.PUT, new HttpEntity<>(bestelling), Bestelling.class);

        Bestelling retrievedBestelling = responseEntityBestelling.getBody();

        Fiets fiets = new Fiets(fietsMerk, fietsModel);

        Klant klant = restTemplate.getForObject("http://" + klantServiceBaseUrl + "/klanten/klantnummer?klantnummer=" + retrievedBestelling.getKlantnummer(), Klant.class);

        return new FilledBestelling(retrievedBestelling, fiets, klant);
    }

    @DeleteMapping("/bestelling/{leverancierBonNummer}")
    public ResponseEntity deleteBestelling(@PathVariable String leverancierBonNummer) {
        restTemplate.delete("http://" + bestellingServiceBaseUrl + "/bestelling/" + leverancierBonNummer);

        return ResponseEntity.ok().build();
    }

}
