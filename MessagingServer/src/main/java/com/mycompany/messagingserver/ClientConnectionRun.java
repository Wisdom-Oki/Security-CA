/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.messagingserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

/**
 *
 * @author wisdom
 */
public class ClientConnectionRun implements Runnable {
    static ArrayList<ClientConnectionRun> waitingConnections = new ArrayList<>();
    ClientConnectionRun linkedClient;
    String linkCode;
    String tempKey;
    String storedMessage = null;

    Socket clientSocket = null;
    String clientID;

    public ClientConnectionRun(Socket connection, String cID) {
        this.clientSocket = connection;
        clientID = cID;
    }

    public String getLinkCode() {
        return linkCode;
    }

    public void setLinkedClient(ClientConnectionRun linkedClient) {
        this.linkedClient = linkedClient;
    }

    public void setStoredMessage(String storedMessage) {
        this.storedMessage = storedMessage;
    }

    public void setTempKey(String tempKey) {
        this.tempKey = tempKey;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // Step 3.
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); // Step 3.

            linkCode = in.readLine().trim();
            for (int i = 0; i < waitingConnections.size(); i++) {
                if (waitingConnections.get(i).getLinkCode().equals(linkCode)) {
                    linkedClient = waitingConnections.get(i);
                    waitingConnections.remove(i);
                    continue;
                }
            }
            if (linkedClient != null) {
                linkedClient.setLinkedClient(this);
                tempKey = GoldenKeyMaker.makeKey();
                linkedClient.setTempKey(tempKey);
                synchronized (linkedClient) {
                    linkedClient.notify();
                }
            } else {
                waitingConnections.add(this);
                synchronized (this) {
                    wait();
                }
            }
            out.println(tempKey);
            tempKey = null;

            while (true) {
                String recievedMessage = in.readLine();

                if (recievedMessage.startsWith("/")) {
                    // use for commands such as STOP or any others we can think of, idk
                    String[] splitMessage = recievedMessage.substring(1).split(" ");
                    if (splitMessage[0].equals("STOP")) {
                        out.println("/TERMINATE disconnect_requested");
                        break;
                    }
                    continue;
                }

                if (linkedClient == null) {
                    out.println("/TERMINATE linked_client_was_disconnected");
                    break;
                }
                linkedClient.setStoredMessage(recievedMessage);
                if (storedMessage == null) {
                    synchronized (this) {
                        wait();
                    }
                } else {
                    synchronized (linkedClient) {
                        linkedClient.notify();
                    }
                }
                if (linkedClient == null) {
                    out.println("/TERMINATE linked_client_was_disconnected");
                    break;
                }
                out.println(storedMessage);
                storedMessage = null;
            }

            // String message = in.readLine();
            // String secretkey = GoldenKeyMaker.makeKey();
            // String encryptedMessage = Encryption.encrypt(message, secretkey);
            // String decryptedMessage = Encryption.decrypt(encryptedMessage, secretkey);
        } catch (IOException e) {
            // handle IOException
        } catch (InterruptedException e) {
            // handle InterruptedException
        }

        finally {
            // if disconnected during waiting phase, remove from waiting list
            if (waitingConnections.contains(this)) {
                waitingConnections.remove(this);
            }
            // if disconnected while linked to another client, unlink from other client
            if (linkedClient != null) {
                linkedClient.setLinkedClient(null);
                synchronized (linkedClient) {
                    linkedClient.notify();
                }
            }
            try {
                // close connection with linked client
                System.out.println("\n* Closing connection with the client " + clientID + " ... *");
                clientSocket.close(); // Step 5.
            } catch (IOException e) {
                System.out.println("Unable to disconnect!");
            }
        }
    }
}
