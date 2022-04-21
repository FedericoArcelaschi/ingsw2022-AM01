package it.polimi.ingsw.controller;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Locale;

public class ClientMain {

    public static void main(String[] args){
        String hostName = "127.0.0.1";
        int portNumber = 1234;
        String playerID = args[0];
        Gson parser = new Gson();
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try (
                Socket socket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            out.println(playerID);
            String userInput;
            while (!socket.isClosed()) {
                System.out.println("run a command:");
                if((userInput = stdIn.readLine()) == null) break;
                String[] command = userInput.split(" ");
                Command c = null;
                for (CommandType ct : CommandType.values()) {
                    if(command[0].toLowerCase().equals(ct.getCommandString()))
                        c=new Command(playerID, ct, Arrays.copyOfRange(command, 1, command.length));
                }
                if(c == null){
                    System.out.println("command error");
                }
                else{
                    String json = parser.toJson(c);
                    out.println(json);
                    Response response = parser.fromJson(in.readLine(), Response.class);
                    if(response == null) socket.close();
                    System.out.println(response.toString());
                }
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
