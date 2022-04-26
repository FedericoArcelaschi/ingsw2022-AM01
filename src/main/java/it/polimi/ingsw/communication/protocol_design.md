* Comunicazione half duplex da parte del server, full duplex da parte del client.




* Il client, nell'istante precedente alla connessione, richiede all'utente il suo username e le preferenze rispetto al tipo
  di partita che vuole giocare (n giocatori, regole di base/esperto); in seguito, viene instaurata la connessione al server.




* I player vengono smistati in code differenti in base alle preferenze. Una volta che si sono connessi i giocatori necessari
  alla creazione della partita, essa inizia: si istanzia il modello e la gestione dei client viene passata a un ExcecutorService.
  Il server comunica a tutti i client che la partita è iniziata e invia una copia del modello che i client possono visualizzare.




* Il client compone comandi, su richiesta dell'utente tramite inputstream, e li manda al server, dopo averli codificati in json.
  Tali comandi vengono sottoposti a verifica per assicurare che essi siano conformi a uno standard definito.
  I comandi vengono divisi in due gruppi: quelli che modificano lo stato e quelli che richiedono la sola visualizzazione di una parte del modello:
  * i comandi per la sola visualizzazione si limitano a interrogare la copia del modello salvata in locale.
  * i comandi per la modifica dello stato vengono inviati al server e se essa viene eseguita si notifica a ogni client il cambiamento e si invia nuovamente il modello aggiornato.




* il server, una volta recepito il comando, verifica che il esso rispetti le regole della partita.
  In caso di riscontro positivo, il comando viene eseguito. Se l'esecuzione del comando va a buon fine viene composta una risposta che notifica un aggiornamento dello stato a ogni client.