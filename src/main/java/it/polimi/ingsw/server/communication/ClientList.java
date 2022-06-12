package it.polimi.ingsw.server.communication;

import java.util.HashSet;
import java.util.Set;

public class ClientList {

    private final Set<Client> clients;

    public ClientList() {
        clients = new HashSet<>();
    }

    public ClientList(ClientList clientList) {
        clients = new HashSet<>(clientList.getClients());
    }

    public ClientList add(Client client) {
        if (clients.contains(client))
            throw new IllegalArgumentException("client " + client.username() + " is already present in the list");
        clients.add(client);
        return this;
    }

    public void remove(Client client) {
        clients.remove(client);
    }

    public Set<Client> getClients() {
        return new HashSet<>(clients);
    }

    public void clear() {
        clients.clear();
    }

}
