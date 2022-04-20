package it.polimi.ingsw.controller;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Locale;

public class ClientMain {

    public static void main(String[] args) throws IOException {
        String hostName = "127.0.0.1";
        int portNumber = 1234;
        Gson parser = new Gson();
        try (
                Socket socket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            String userInput;
            while (!socket.isClosed()) {
                System.out.println("run a command:");
                if((userInput = stdIn.readLine()) == null) break;
                String[] command = userInput.split(" ");
                Command c = null;
                switch(command[0].toLowerCase()){
                    case "playcard": c = new Command(CommandType.PLAY_CARD, command);
                }
                String json = parser.toJson(c);
                out.println(json);
                String response = in.readLine();
                if(response == null) socket.close();
                System.out.println(response);
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }



}
