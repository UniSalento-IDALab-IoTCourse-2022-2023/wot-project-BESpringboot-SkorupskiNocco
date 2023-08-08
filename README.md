# wot-project-BESpringboot-SkorupskiNocco

## contenuti
1. [Descrizione del progetto](#descrizione-progetto)
2. [Architettura del sistema](#architettura)
3. [Link alle repository](#links)
4. [Descrizione del componente](#descrizione-componente)


### Descrizione del progetto <a name="descrizione-progetto"></a>

Il progetto ha come obiettivo quello di guidare un individo all'interno di un ospedale(adattabile a strutture differenti) al fine di raggiungere un determinato reparto/stanza mediante l'uso di beacon. 
L'utente, installando l'applicazione sul proprio dispositivo android avrà la possibilità di selezionare un reparto di destinazione ed essere guidato attraverso a delle indicazioni visualizzate sullo schermo.
Le applicazioni sono due, una per l'utente ed una per l'amministratore. 
L'amministratore avrà il compito di aggiungere e mappare inizialmente i beacon attraverso l'applicazione fornita, assegnando un reparto ed un insieme di stanze al singolo beacon, inoltre dovrà inizialmente posizionarsi all'ingresso dell'edificio con il dispositivo puntato verso l'interno per calibrare le coordinate attraverso un determinato pulsante via app, in quanto l'applicazione usa un magnetometro per capire il verso di percorrenza rispetto al singolo beacon.
Una volta completato questo processo basterà semplicemente installare l'applicazione Hospital Maps per poter usufluire del navigatore. 
L'applicazione offre la possibilità di selezionare un percorso adeguato per persone diversamente abili.


### Architettura del sistema <a name="architettura"></a>

L'architettura è molto semplice:
Le applicazioni amministratore dell'amministratore e dell'utente utilizzano un BackEnd in cloud deployato su AWS che mette a disposizione diverse API.
Infine il BE utilizza un database MongoDb, anche esso deployato su AWS, entrambi in un unica istanza di EC2.


### Link delle repository <a name="links"></a>

- FrontEnd Utente: [Link](https://github.com/UniSalento-IDALab-IoTCourse-2022-2023/wot-project-2022-2023-AppAndroidFE-NoccoSkorupski)
- FrontEnd Amministratore: [Link](https://github.com/UniSalento-IDALab-IoTCourse-2022-2023/wot-project-AdminAppAndroidFE-NoccoSkorupski)
- BackEnd: [Link](https://github.com/UniSalento-IDALab-IoTCourse-2022-2023/wot-project-BESpringboot-SkorupskiNocco)


### Descrizione del Componente <a name="descrizione-componente"></a>
Il BFF è formato da due controller: AdministratorController ed UserController.
Nel Administrator controller troviamo le chiamate rest per poter aggiungere un nuovo beacon, mappare un beacon esistente ed aggiungere le coordinate inizali.
La mappatura avviene aggiungento ad ogni beacon i beacon adiacenti che posso trovarsi di fronte, dietro, a destra oppure a sinistra. Le coordinate invece dovranno essere prese posizionandosi all'ingresso dell'ospedale e puntando lo smartphone perpendicolarmente verso l'interno rispetto all'estrata.
Lo User Controller permette di ottenere la lista di Reparti, la lista di Stanze e la mappa completa da seguire, questa viene generata tramite l'algoritmo di Dijkstra e sfruttando la logica con cui sono bosizionati i beacon gli uni rispetto agli altri. La mappa restituita indica la direzione da seguire anche in base al direzionamento del proprio smartphone.
