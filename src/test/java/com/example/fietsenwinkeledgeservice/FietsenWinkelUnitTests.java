package com.example.fietsenwinkeledgeservice;

import com.example.fietsenwinkeledgeservice.model.Bestelling;
import com.example.fietsenwinkeledgeservice.model.Fiets;
import com.example.fietsenwinkeledgeservice.model.Klant;
import com.example.fietsenwinkeledgeservice.model.Onderdeel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.given;


@SpringBootTest
@AutoConfigureMockMvc
public class FietsenWinkelUnitTests {

    @Value("${fietsenservice.baseurl}")
    private String fietsenServiceBaseUrl;

    @Value("${onderdeelservice.baseurl}")
    private String onderdeelServiceBaseUrl;

    @Value("${bestellingservice.baseurl}")
    private String bestellingServiceBaseUrl;

    @Value("${klantservice.baseurl}")
    private String klantServiceBaseUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();

    private Onderdeel onderdeel1 = new Onderdeel("ketting", "kettingmaker", 2, 30);
    private Onderdeel onderdeel2 = new Onderdeel("pedaal", "pedaalmaker", 20, 15);
    private Onderdeel onderdeel3 = new Onderdeel("reflector", "reflectormaker", 6, 2);
    private Onderdeel onderdeel4 = new Onderdeel("reflector2", "reflectormaker", 60, 2000);

    private List<Onderdeel> onderdelen1 = Arrays.asList(onderdeel3, onderdeel4);

    private Klant klant1 = new Klant(654L, "klant1", "achternaam1", "123", "04654654", "email1", "TM");
    private Klant klant2 = new Klant(948L, "klant2", "achternaam2", "124", "0654654168", "email2", "TM");


    private Fiets fiets1 = new Fiets("fietsmerk1", "fietsmodel1", 20, 250, 10);
    private Fiets fiets2 = new Fiets("fietsmerk2", "fietsmodel2", 10, 350, 5);
    private Fiets fiets3 = new Fiets("fietsmerk3", "fietsmodel3", 16, 423, 62);

    private List<Fiets> fietsen = Arrays.asList(fiets1, fiets2, fiets3);

    private Bestelling bestelling1 = new Bestelling("leverancierbonnummer1", "123",  "email1" , 2000, 52, "fietsmerk1", "fietsmodel1");
    private Bestelling bestelling2 = new Bestelling("leverancierbonnummer2", "124",  "email2" , 9561, 414, "fietsmerk2", "fietsmodel2");
    private Bestelling bestelling3 = new Bestelling("leverancierbonnummer3", "123",  "email1" , 10, 9, "fietsmerk2", "fietsmodel2");

    private List<Bestelling> allBestellingenEmail = Arrays.asList(bestelling1, bestelling3);
    private List<Bestelling> allBestellingen = Arrays.asList(bestelling1, bestelling2);

    @BeforeEach
    public void initializeMockServer() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void whenGetBestellingenByLeverancierBonNummerAndOnderdeelMerk_thenReturnFilledBestellingJson() throws Exception {

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + bestellingServiceBaseUrl + "/bestelling/leverancierbonnummer1")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(bestelling1))
                );

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + onderdeelServiceBaseUrl + "/onderdeel/merk/reflectormaker")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(onderdelen1))
                );

        mockMvc.perform(get("/bestellingen/leverancierBonNummer/{leverancierBonNummer}/onderdeelMerk/{merk}", "leverancierbonnummer1", "reflectormaker"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.leverancierBonNummer", is("leverancierbonnummer1")))
                .andExpect(jsonPath("$.email", is("email1")))
                .andExpect(jsonPath("$.onderdelen[0].naam", is("reflector")))
                .andExpect(jsonPath("$.onderdelen[0].merk", is("reflectormaker")))
                .andExpect(jsonPath("$.onderdelen[0].voorraad", is(6)))
                .andExpect(jsonPath("$.onderdelen[0].prijs", is(2)))
                .andExpect(jsonPath("$.onderdelen[1].naam", is("reflector2")))
                .andExpect(jsonPath("$.onderdelen[1].merk", is("reflectormaker")))
                .andExpect(jsonPath("$.onderdelen[1].voorraad", is(60)))
                .andExpect(jsonPath("$.onderdelen[1].prijs", is(2000)));



    }


    @Test
    public void whenGetBestellingen_thenReturnFilledBestellingenJson() throws Exception {

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + bestellingServiceBaseUrl + "/bestellingen/")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allBestellingen))
                );

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + klantServiceBaseUrl +"/klanten/klantnummer?klantnummer=123")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(klant1))
                );

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + klantServiceBaseUrl +"/klanten/klantnummer?klantnummer=124")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(klant2))
                );

        mockMvc.perform(get("/bestellingen"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].leverancierBonNummer", is("leverancierbonnummer1")))
                .andExpect(jsonPath("$[0].email", is("email1")))
                .andExpect(jsonPath("$[0].klantnummer", is("123")))
                .andExpect(jsonPath("$[1].leverancierBonNummer", is("leverancierbonnummer2")))
                .andExpect(jsonPath("$[1].email", is("email2")))
                .andExpect(jsonPath("$[1].klantnummer", is("124")));

    }

    @Test
    public void whenGetAllFietsen_thenReturnFietsenJson() throws Exception {
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + fietsenServiceBaseUrl + "/fietsen")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(fietsen))
                );

        mockMvc.perform(get("/fietsen"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].merk", is("fietsmerk1")))
                .andExpect(jsonPath("$[0].model", is("fietsmodel1")))
                .andExpect(jsonPath("$[0].grootte", is(20)))
                .andExpect(jsonPath("$[0].prijs", is(250)))
                .andExpect(jsonPath("$[0].voorraad", is(10)))
                .andExpect(jsonPath("$[1].merk", is("fietsmerk2")))
                .andExpect(jsonPath("$[1].model", is("fietsmodel2")))
                .andExpect(jsonPath("$[1].grootte", is(10)))
                .andExpect(jsonPath("$[1].prijs", is(350)))
                .andExpect(jsonPath("$[1].voorraad", is(5)))
                .andExpect(jsonPath("$[2].merk", is("fietsmerk3")))
                .andExpect(jsonPath("$[2].model", is("fietsmodel3")))
                .andExpect(jsonPath("$[2].grootte", is(16)))
                .andExpect(jsonPath("$[2].prijs", is(423)))
                .andExpect(jsonPath("$[2].voorraad", is(62)));
    }

    @Test
    public void whenGetBestellingenByEmail_thenReturnfilledBestellingJson() throws Exception {
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + bestellingServiceBaseUrl + "/bestellingen/email/email1")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allBestellingenEmail))
                );

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + fietsenServiceBaseUrl + "/fietsen/model/fietsmodel1")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(fiets1))
                );
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + klantServiceBaseUrl + "/klanten/klantnummer?klantnummer=123")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(klant1))
                );
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + fietsenServiceBaseUrl + "/fietsen/model/fietsmodel2")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(fiets2))
                );
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + klantServiceBaseUrl + "/klanten/klantnummer?klantnummer=123")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(klant1))
                );



        mockMvc.perform(get("/bestellingen/email/{email}", "email1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].leverancierBonNummer", is("leverancierbonnummer1")))
                .andExpect(jsonPath("$[0].email", is("email1")))
                .andExpect(jsonPath("$[0].merk", is("fietsmerk1")))
                .andExpect(jsonPath("$[0].model", is("fietsmodel1")))
                .andExpect(jsonPath("$[0].klantnummer", is("123")))
                .andExpect(jsonPath("$[1].leverancierBonNummer", is("leverancierbonnummer3")))
                .andExpect(jsonPath("$[1].email", is("email1")))
                .andExpect(jsonPath("$[1].merk", is("fietsmerk2")))
                .andExpect(jsonPath("$[1].model", is("fietsmodel2")))
                .andExpect(jsonPath("$[1].klantnummer", is("123")));
    }

    @Test
    public void whenGetklantByKlantNummer_ThenReturnKlantJson() throws Exception {

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + klantServiceBaseUrl + "/klanten/klantnummer?klantnummer=123")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(klant1))
                );

        mockMvc.perform(get("/klanten/klantnummer")
                .param("klantnummer", "123"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(654)))
                .andExpect(jsonPath("$.voornaam", is("klant1")))
                .andExpect(jsonPath("$.achternaam", is("achternaam1")))
                .andExpect(jsonPath("$.klantnummer", is("123")))
                .andExpect(jsonPath("$.gsmNummer", is("04654654")))
                .andExpect(jsonPath("$.email", is("email1")))
                .andExpect(jsonPath("$.bedrijf", is("TM")));

    }


    @Test
    public void whenAddBestelling_ThenReturnFilledBestellingJson()  throws Exception{

        Bestelling bestellingadd = new Bestelling("leverancierbonnummer4", "123",  "email1" , 2000, 52, "fietsmerk1", "fietsmodel1");

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + bestellingServiceBaseUrl + "/bestellingen")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(bestellingadd))
                );

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + klantServiceBaseUrl + "/klanten/klantnummer?klantnummer=123")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(klant1))
                );

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + fietsenServiceBaseUrl + "/fietsen/model/fietsmodel1")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(fiets1))
                );

        mockMvc.perform(post("/bestellingen")
                .param("leverancierBonNummer", bestellingadd.getLeverancierBonNummer())
                .param("klantnummer", bestellingadd.getKlantnummer())
                .param("email", bestellingadd.getEmail())
                .param("prijs", "2000")
                .param("voorschot", "52")
                .param("fietsMerk", bestellingadd.getFietsMerk())
                .param("fietsModel", bestellingadd.getFietsModel())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.leverancierBonNummer", is("leverancierbonnummer4")))
                .andExpect(jsonPath("$.email", is("email1")))
                .andExpect(jsonPath("$.merk", is("fietsmerk1")))
                .andExpect(jsonPath("$.model", is("fietsmodel1")))
                .andExpect(jsonPath("$.klantnummer", is("123")));

    }

    @Test
    public void whenDeleteRanking_thenReturnStatusOk() throws Exception {
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + bestellingServiceBaseUrl + "/bestelling/leverancierbonnummer3" )))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK));

        mockMvc.perform(delete("/bestelling/{leverancierBonNummer}", "leverancierbonnummer3"))
                .andExpect(status().isOk());
    }

//    @Test
//    public void givenReview_whenPutReview_thenReturnJsonReview() throws  Exception{
//        Bestelling bestelling = new Bestelling("leverancierbonnummer1", "123",  "email1" , 2000, 52, "fietsmerk1", "fietsmodel1");
//
//        mockServer.expect(ExpectedCount.once(),
//                requestTo(new URI("http://" + bestellingServiceBaseUrl + "/bestellingen")))
//                .andExpect(method(HttpMethod.PUT))
//                .andRespond(withStatus(HttpStatus.OK)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(mapper.writeValueAsString(bestelling)));
//
//        mockServer.expect(ExpectedCount.once(),
//                requestTo(new URI("http://" + bestellingServiceBaseUrl + "/bestellingen/email/email1")))
//                .andExpect(method(HttpMethod.GET))
//                .andRespond(withStatus(HttpStatus.OK)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(mapper.writeValueAsString(bestelling)));
//
//
//
//        mockMvc.perform(put("/bestellingen")
//                        .param("leverancierBonNummer", bestelling.getLeverancierBonNummer())
//        .param("fietsMerk", bestelling.getFietsMerk())
//        .param("fietsModel", bestelling.getFietsModel())
//                        .contentType(MediaType.APPLICATION_JSON)
//        )
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//    }


}
