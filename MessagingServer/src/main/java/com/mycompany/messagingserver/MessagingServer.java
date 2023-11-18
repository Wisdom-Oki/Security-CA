/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.messagingserver;

import java.io.*;
import java.net.*;

/**
 *
 * @author
 */
public class MessagingServer {

    private static ServerSocket servSock;
    private static final int PORT = 1234;
    private static int clientConnections = 0;

    public static void main(String[] args) {
        System.out.println("Opening port...\n");
        try {
            servSock = new ServerSocket(PORT); 
        } catch (IOException e) {
            System.out.println("Unable to attach to port!");
            System.exit(1);
        }

        do {
            run();
        } while (true);

    }

    private static void run() {
        Socket link; 
        try {
            link = servSock.accept();
            clientConnections++;
            String client_ID = "Client " + clientConnections;
            Runnable resource = new ClientConnectionRun(link, client_ID);
            Thread t = new Thread(resource);
            t.start();
        } catch (IOException e1) {

        }
    } 
} 
