# wot-project-BESpringboot-SkorupskiNocco

## contenuti
1. [Descrizione del progetto](#descrizione-progetto)
2. [Architettura del sistema](#architettura)
3. [Link alle repository](#links)
4. [Descrizione del componente](#descrizione-componente)

<a name="descrizione-progetto"></a>
### Descrizione del progetto

Il progetto ha come obiettivo quello di guidare un individo all'interno di un ospedale(adattabile a strutture differenti) al fine di raggiungere un determinato reparto/stanza mediante l'uso di beacon. 
L'utente, installando l'applicazione sul proprio dispositivo android avrà la possibilità di selezionare un reparto di destinazione ed essere guidato attraverso a delle indicazioni visualizzate sullo schermo.
Le applicazioni sono due, una per l'utente ed una per l'amministratore. 
L'amministratore avrà il compito di aggiungere e mappare inizialmente i beacon attraverso l'applicazione fornita, assegnando un reparto ed un insieme di stanze al singolo beacon, inoltre dovrà inizialmente posizionarsi all'ingresso dell'edificio con il dispositivo puntato verso l'interno per calibrare le coordinate attraverso un determinato pulsante via app, in quanto l'applicazione usa un magnetometro per capire il verso di percorrenza rispetto al singolo beacon.
Una volta completato questo processo basterà semplicemente installare l'applicazione Hospital Maps per poter usufluire del navigatore. 
L'applicazione offre la possibilità di selezionare un percorso adeguato per persone diversamente abili.

<a name="architettura"></a>
### Architettura del sistema

L'architettura è molto semplice:
Le applicazioni amministratore dell'amministratore e dell'utente utilizzano un BackEnd in cloud deployato su AWS che mette a disposizione diverse API.
Infine il BE utilizza un database MongoDb, anche esso deployato su AWS, entrambi in un unica istanza di EC2.

<a name="links"></a>
### Link delle repository

FrontEnd Utente: https://github.com/UniSalento-IDALab-IoTCourse-2022-2023/wot-project-2022-2023-AppAndroidFE-NoccoSkorupski
FrontEnd Amministratore: https://github.com/UniSalento-IDALab-IoTCourse-2022-2023/wot-project-AdminAppAndroidFE-NoccoSkorupski
BackEnd: https://github.com/UniSalento-IDALab-IoTCourse-2022-2023/wot-project-BESpringboot-SkorupskiNocco

<a name="descrizione-componente"></a>
### Descrizione del Componente