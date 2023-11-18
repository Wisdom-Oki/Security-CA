/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tcpechoclient;

import java.io.*;
import java.net.*;

/**
 *
 * @author wisdom
 */
public class TCPEchoClient {
    private static InetAddress host;
    private static final int PORT = 1234;
    private static String secretKey;
    public static String clientMessage;

    public static void main(String[] args) {
        ClientGUI.StartGUI();
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("Host ID not found!");
            System.exit(1);
        }
        run();
    }

    private static void run() {
        Socket link = null; // Step 1.
        try {
            link = new Socket(host, PORT); // Step 1.
            BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));// Step 2.
            PrintWriter out = new PrintWriter(link.getOutputStream(), true); // Step 2.

            // Set up stream for keyboard entry...
            //BufferedReader userEntry = new BufferedReader(new InputStreamReader(System.in));

            ClientGUI.AppendToOutput("Enter Link Code: ");
            
            clientMessage = null;
            while (clientMessage==null) {
                Thread.sleep(100);
            }
            ClientGUI.AppendToOutputLn(clientMessage);
            out.println(clientMessage);
            ClientGUI.AppendToOutputLn("Waiting For Connection...");
            secretKey = in.readLine();
            ClientGUI.AppendToOutputLn("Connection Established!");

            while (true) {
                ClientGUI.AppendToOutput("You         > ");

                clientMessage = null;
                while (clientMessage==null) {
                    Thread.sleep(100);
                }
                ClientGUI.AppendToOutputLn(clientMessage);
                if (clientMessage.startsWith("/")) {
                    out.println(clientMessage);
                } else {
                    out.println(Encryption.encrypt(clientMessage, secretKey));
                }

                String recievedMessage = in.readLine();
                if (recievedMessage!=null && recievedMessage.startsWith("/")) {
                    String[] splitMessage = recievedMessage.substring(1).split(" ");
                    if (splitMessage[0].equals("TERMINATE")) {
                        ClientGUI.AppendToOutput("Server terminated connection.");
                        if (splitMessage.length > 1) {
                            ClientGUI.AppendToOutput(" Reason: \"" + splitMessage[1].replace("_", " ")+"\"");
                        }
                        ClientGUI.AppendToOutputLn("");
                        break;
                    }
                } else {
                    ClientGUI.AppendToOutputLn("Remote > " + Encryption.decrypt(recievedMessage, secretKey));
                }
            }

            // send link code to server
            // wait for encryption key from server
            // store key, use to encrypt all future messages and decrypt all future messages
            // and responses
        } catch (IOException e) {
            //nono
            //e.printStackTrace();
        } catch (InterruptedException e) {
            //nono
            //e.printStackTrace();
        }finally {
            try {
                System.out.println("\n* Closing connection... *");
                link.close(); // Step 4.
            } catch (IOException e) {
                System.out.println("Unable to disconnect/close!");
                System.exit(1);
            }
        }
    } // finish run method
} // finish the class
