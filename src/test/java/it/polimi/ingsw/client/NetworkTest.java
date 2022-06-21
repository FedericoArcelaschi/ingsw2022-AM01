package it.polimi.ingsw.client;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.*;

public class NetworkTest {

    @Test
    void NetworkTesting() {
        String address = "localhost";
        SocketAddress socketAddress = new InetSocketAddress(address, 1234);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, socketAddress);
        try {
            Socket socket = new Socket(proxy);
            System.out.println(socket);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        System.out.println(socketAddress);
        address = "192.168.0.1";
        socketAddress = new InetSocketAddress(address, 1234);
        System.out.println(socketAddress);

        address = "Andrea Carta";
        socketAddress = new InetSocketAddress(address, 1234);
        System.out.println(socketAddress);
        proxy = new Proxy(Proxy.Type.HTTP, socketAddress);
        try {
            Socket socket = new Socket(proxy);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        address = "1234567865432134564324567";
        socketAddress = new InetSocketAddress(address, 1234);
        System.out.println(socketAddress);

    }
}
