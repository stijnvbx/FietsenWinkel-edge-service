# Project advanced programming topics

## Thema : Fietsen winkel

### Beschrijving

## Diagram microservices architectuur

### Visual version

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

DockerHubLink :

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

DockerHubLink :

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

DockerHubLink :

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

## Swagger werking

### Route 1
### Route 2
### Route 3
