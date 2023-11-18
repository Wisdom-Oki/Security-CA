/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.messagingclient;

import java.io.*;
import java.net.*;

/**
 *
 * @author wisdom
 */
public class MessagingClient {
    private static InetAddress host;
    private static final int PORT = 1234;
    private static String secretKey;
    public static String clientMessage;

    public static void main(String[] args) {
        MessagingClientGUI.StartGUI();
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("Host ID not found!");
            System.exit(1);
        }
        run();
    }

    private static void run() {
        Socket link = null; 
        try {
            link = new Socket(host, PORT); 
            BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            PrintWriter out = new PrintWriter(link.getOutputStream(), true); 



            MessagingClientGUI.AppendToOutput("Enter Link Code: ");
            
            clientMessage = null;
            while (clientMessage==null) {
                Thread.sleep(100);
            }
            
            MessagingClientGUI.AppendToOutputLn(clientMessage);
            out.println(clientMessage);
            MessagingClientGUI.AppendToOutputLn("Waiting For Connection...");
            secretKey = in.readLine();
            MessagingClientGUI.AppendToOutputLn("Connection Established!");
            MessagingClientGUI.AppendToOutputLn("To disconnect from server, type /STOP ");

            
            while (true) {
                MessagingClientGUI.AppendToOutput("You         > ");

                clientMessage = null;
                while (clientMessage==null) {
                    Thread.sleep(100);
                }
                MessagingClientGUI.AppendToOutputLn(clientMessage);
                if (clientMessage.startsWith("/")) {
                    out.println(clientMessage);
                } else {
                    out.println(Encryption.encrypt(clientMessage, secretKey));
                }

                String recievedMessage = in.readLine();
                if (recievedMessage!=null && recievedMessage.startsWith("/")) {
                    String[] splitMessage = recievedMessage.substring(1).split(" ");
                    if (splitMessage[0].equals("TERMINATE")) {
                        MessagingClientGUI.AppendToOutput("Server terminated connection.");
                        if (splitMessage.length > 1) {
                            MessagingClientGUI.AppendToOutput(" Reason: \"" + splitMessage[1].replace("_", " ")+"\"");
                        }
                        MessagingClientGUI.AppendToOutputLn("");
                        break;
                    }
                } else {
                    MessagingClientGUI.AppendToOutputLn("Remote > " + Encryption.decrypt(recievedMessage, secretKey));
                }
            }

        } catch (IOException e) {

        } catch (InterruptedException e) {

            
        }
    } 
} 
