package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.communication.message.Message;

import javax.management.InstanceAlreadyExistsException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

class MessageUsernameSet {
    Set<MessageUsername> messageUsernameSet = new HashSet<>();

    public MessageUsernameSet() {}

    public MessageUsernameSet(MessageUsername messageUsernameSet) {
        this.messageUsernameSet.add(messageUsernameSet);
    }

    public static MessageUsernameSet of(Message message, String addressee) {
        return new MessageUsernameSet(new MessageUsername(message, addressee));
    }

    public MessageUsernameSet add(Message message, String addressee) {
        messageUsernameSet.add(new MessageUsername(message, addressee));
        return this;
    }

    public Set<MessageUsername> values() {
        return messageUsernameSet;
    }

}
record MessageUsername(Message message, String addressee) {}