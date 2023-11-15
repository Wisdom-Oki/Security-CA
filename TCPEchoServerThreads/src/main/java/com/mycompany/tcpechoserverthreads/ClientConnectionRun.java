/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tcpechoserverthreads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.Key;
import java.util.*;

/**
 *
 * @author wisdom
 */
public class ClientConnectionRun implements Runnable {
    static ArrayList<ClientConnectionRun> waitingConnections;
    ClientConnectionRun linkedClient;
    String linkCode;
    Key tempKey;
    String storedMessage=null;

    Socket clientSocket = null;
    String clientID;

    public ClientConnectionRun(Socket connection, String cID) {
        this.clientSocket = connection;
        clientID = cID;
    }

    public String getLinkCode() {
        return linkCode;
    }

    public void setTempKey(Key newKey){
        tempKey = newKey;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // Step 3.
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); // Step 3.

            //get link code from client
            //if waiting list has connection with link code
                //store and remove first entry with matching link code
                //notify linked connection to wake it's thread
                //generate and set key on each client connection
            //else
                //add connection to waiting list
                //make the thread wait
            //send key to client
            //once client responds, set key to null
            //while loop for forwarding messages
                //wait for message from client
                //when recieved, set message in linked connection and notify it
                //if message recieved from linked connection
                    //send message to client and clear message
                //else
                    //wait for message from linked client
            //



            String message = in.readLine();

            String secretKey = "SuperCoolKey";
            String encryptedMessage = Encryption.encrypt(message, secretKey);
            String decryptedMessage = Encryption.decrypt(encryptedMessage, secretKey);

            System.out.println("Message received from client: " + clientID + "  " + message);
            out.println("Echo Message: " + message + ", " + encryptedMessage + ", " + decryptedMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        finally {
            try {
                //close connection with linked client
                System.out.println("\n* Closing connection with the client " + clientID + " ... *");
                clientSocket.close(); // Step 5.
            } catch (IOException e) {
                System.out.println("Unable to disconnect!");
            }
        }
    }
}
