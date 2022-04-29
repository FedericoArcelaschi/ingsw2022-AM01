#Comunicazione 
La comunicazione è half duplex sia da parte del server, che da parte del client:
il server rimane in ascolto dei client e risponde solo su richiesta;
il client esegue comandi quando è il suo turno e riceve update del model da parte del server.

##Connessione al server e inizio della partita
È previsto un protocollo di tipo heartbeat: ogni 10 secondi, il server si assicura che il client sia connesso,
inviando un pacchetto di ping. Se entro successivi 10 secondi non arriva un messaggio di risposta ("pong"), allora
viene avviato un procedimento per la disconnessione.

Il client, nell'istante precedente alla connessione, richiede all'utente il suo username e le preferenze rispetto al tipo
di partita che vuole giocare (n giocatori, regole di base/esperto); successivamente, 
I player vengono smistati in code differenti in base a suddette preferenze. Una volta che si sono connessi i giocatori necessari
alla creazione della partita, essa inizia: si istanzia il modello e la gestione dei client viene passata a un ExcecutorService.
Il server comunica a tutti i client che la partita è iniziata e invia una copia del modello che i client possono visualizzare,
sotto forma di view con ascii artwork da stampare a video sul terminale.

##Gestione della partita

####Client
Quando è il turno del giocatore corrente, il server invia un prompt all'utente per invitarlo a inviare comandi.
Quindi, il client compone comandi, su richiesta dell'utente tramite inputstream, e li manda al server, dopo averli codificati in json.
Tali comandi vengono sottoposti a verifica per assicurare che essi siano conformi a uno standard definito; se lo sono,
essi vengono inviati al server. 

####Server
Il server, una volta recepito il comando, verifica che il esso rispetti le regole della partita.
* In caso di riscontro positivo, il comando viene eseguito. Se l'esecuzione del comando va a buon fine viene composta una 
  risposta che notifica un aggiornamento dello stato a ogni client.
* In caso di riscontro negativo, il server non effettua modifiche al modello e invia un messaggio di errore all'utente.
Dato che il modo in cui abbiamo scelto di gestire la partita comporta la suddivisione di ciascun turno in fasi, si aspetterà
che ciascun giocatore invii un comando legale per procedere col resto della partita.