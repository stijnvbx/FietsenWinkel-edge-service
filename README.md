# Project advanced programming topics

## Thema : Fietsen winkel

### Beschrijving
De backend voor een fietsen winkel applicatie met behulp van microservices.
De applictie zal dienen voor de werknmers om in de winkel te gebruiken.
Er zullen fietsen,onderdelen,klanten en bestellingen in het syteem te vinden zijn.

## Diagram microservices architectuur

### Visual version

![Architectuur](https://user-images.githubusercontent.com/45101479/103455368-bb1a7c00-4cec-11eb-9c31-f3a9cf0a2947.PNG)

### Written version

#### Edge service
Naam : FietsenWinkel-edge-service

ServerPort : 8050
##### Endpoints
##### Connections 
- fietsen-service ( Iebe Maes )
- onderdelen-service ( Stijn Van Braband )
- bestllings-service ( Axel Huybrechts )
- Klanten-service ( Ruben Horemans )

#### fietsen-service
Gemaakt door : Iebe Maes

ServerPort : 8051

Githublink : https://github.com/IebeMaes/fietsen-service

DockerHubLink : https://hub.docker.com/r/iebemaes/fietsen-service

##### Database
Type : MongoDB
##### Endpoints
- GET /fietsen
- GET /fietsen/merk/{merk}
- GET /fietsen/merk/{merk}/model/{model}
- POST /fietsen
- PUT /fietsen
- DELETE /fietsen/merk/{merk}/model/{model}
#### onderdelen-service
Gemaakt door : Stijn Van Braband

ServerPort : 8052

Githublink : https://github.com/stijnvbx/onderdelen-service

DockerHubLink : https://hub.docker.com/r/stijnvbx/onderdelen-service

##### Database
Type : MongoDB
##### Endpoints
- GET /onderdelen
- GET /onderdelen/{merk}
- GET /onderdelen/merk/{merk}/naam/{naam}
- POST /onderdelen
- PUT /onderdelen
- DELETE /onderdelen/merk/{merk}/naam/{naam}
#### bestllings-service
Gemaakt door : Axel Huybrechts

ServerPort : 8055

Githublink : https://github.com/axei500/bestllings_service

DockerHubLink : https://hub.docker.com/repository/docker/axei500/bestellings-service

##### Database
Type : PostgresSql
##### Endpoints
- GET /bestellingen/email/{email}
- GET /bestellingen/onderdeel/{onderdeelNaam}
- GET /bestellingen/onderdeelMerk/{onderdeelMerk}
- GET /bestellingen/onderdeelMerk/{onderdeelMerk}/onderdeelNaam/{onderdeelNaam}
- GET /bestellingen/fiets/{fietsMerk}
- GET /bestellingen/fietsModel/{fietsModel}
- GET /bestellingen/fietsModel/{fietsModel}/fietsMerk/{fietsMerk}
- GET /bestelling/{leverancierBonNummer}
- POST /bestellingen
- PUT /bestellingen
- DELETE /bestelling/{leverancierBonNummer}
#### Klanten-service
Gemaakt door : Ruben Horemans

ServerPort : 8054

Githublink : https://github.com/RubenH1999/klanten?fbclid=IwAR2HfmQM6e0Bj0vGY-TmU71r0lepu4h4mhYUZz87dmbPc_YME-m31TyOmn0

DockerHubLink : 

##### Database
Type : PostgresSql
##### Endpoints
- GET /klanten
- GET /klanten/klantnummer
- GET /klanten/bedrijf/{bedrijf}
## Swagger werking
Om dit te testen zorg eerst dat je docker hebt geinstalleerd.
Nu kan je met behulp van docker compose alle ander service binnen halen al images van dockerhub, met behulp van het commando docker-compose up(Gebruik in de roet van je project).
Daartna kan je naar deze link surfen http://localhost:8050/swagger-ui.html/ .
### Route 1
### Route 2
### Route 3
