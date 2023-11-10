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
import java.util.*;

/**
 *
 * @author razi
 */
public class ClientConnectionRun implements Runnable {
    Socket client_link = null;  
    String clientID;
    
    public ClientConnectionRun(Socket connection, String cID) {
        this.client_link = connection;
        clientID = cID;     
  }
    
    public void run() {
        try{
            BufferedReader in = new BufferedReader( new InputStreamReader(client_link.getInputStream())); //Step 3.
            PrintWriter out = new PrintWriter(client_link.getOutputStream(),true); //Step 3.
      
            String message = in.readLine();         //Step 4.
            System.out.println("Message received from client: " + clientID + "  "+ message);
            out.println("Echo Message: " + message);    
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        
        finally 
        {
            try {
                System.out.println("\n* Closing connection with the client " + clientID + " ... *");
                client_link.close();				    //Step 5.
            }
            catch(IOException e)
            {
                System.out.println("Unable to disconnect!");
            }
        }
    }   
}
