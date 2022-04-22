-Comunicazione half duplex da parte del server, full duplex da parte del client.

-Il client, nell'istante precedente alla connessione, richiede all'utente il suo username e le preferenze rispetto al tipo
di partita che vuole giocare (n giocatori, regole di base/esperto); in seguito, viene instaurata la connessione al server.

-I player vengono smistati in code differenti in base alle preferenze. Una volta che si sono connessi i giocatori necessari
alla creazione della partita, la partita inizia: viene istanziato il modello e la gestione dei client viene passata a un ExcecutorService.

-Il client compone comandi, su richiesta dell'utente tramite inputstream, e li manda al server, dopo averli codificati in json.
Tali comandi vengono sottoposti a verifica per assicurare che essi siano conformi a uno standard definito.

-il server, una volta recepito il comando, verifica che il comando sia "legale", ovvero che rispetti le regole della partita.
In caso sia così, il comando viene eseguito. Se esso non modifica lo stato, il server compone una risposta contenente i dati richiesti
dall'utente e glieli invia, altrimenti viene composta una risposta che notifica un aggiornamento dello stato ad ogni client.