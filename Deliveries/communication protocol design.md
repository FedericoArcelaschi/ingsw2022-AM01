## Comunicazione 
La comunicazione è half duplex sia da parte del server, che da parte del client:
il server rimane in ascolto dei client e risponde solo su richiesta;
il client può sempre inviare messaggi che eseguono comandi quando tocca a lui e riceve update da parte del server alla 
view ogni volta che è modificata.

### Connessione al server e inizio della partita
È previsto un protocollo di tipo heartbeat: ogni 10 secondi, il server si assicura che il client sia connesso,
inviando un messaggio di Ping. Se entro successivi 10 secondi non arriva un messaggio di Pong, allora
viene avviato un procedimento per la disconnessione.

Il client, nell'istante precedente alla connessione, richiede all'utente il suo username e le preferenze rispetto al tipo
di partita che vuole giocare (il numero di giocatori, la modalità se base o esperto).

Quando connessi al server i player vengono smistati in code differenti in base al tipo di partita. Una volta che si sono connessi i giocatori necessari
alla creazione della partita, viene creata. Si istanzia il modello e la gestione dei client viene passata ad un altro thread.\
Il server comunica a tutti i client che la partita è iniziata inviando un messaggio di benvenuto ed una view
stampata a video sul terminale fatta di caratteri ascii.


### Gestione della partita

#### Client
Quando è il turno del giocatore corrente, il server invia un prompt all'utente per invitarlo a inviare comandi.
Quindi l'utente compone comandi tramite inputstream e li manda al server, codificati in oggetti `Command` che possono essere dei seguenti tipi:
LAY_CARD; CHOOSE_CLOUD; MOVE_STUDENT; MOVE_MOTHER_NATURE; GET.\
Tali comandi vengono sottoposti ad un controllo dal lato client per verificare la sintassi. Se sono legali essi vengono 
inviati al server sotto forma di json.  

#### Server
Il server, una volta recepito il comando, verifica che il esso rispetti le regole della partita (turno, fase del turno, numero di studenti mossi).
* In caso di riscontro positivo, il comando viene eseguito. Se l'esecuzione del comando va a buon fine viene composta una 
  risposta che notifica un aggiornamento dello stato a ogni client.
* In caso di riscontro negativo, il server non effettua modifiche al modello e invia un messaggio di errore all'utente.
Dato che il modo in cui abbiamo scelto di gestire la partita comporta la suddivisione di ciascun turno in fasi, si aspetterà
che ciascun giocatore invii un comando legale per procedere col resto della partita.