/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grupodewassap;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author AndJe
 */
public class ServerThread extends Thread{
    private Socket skt = null;
    private List<ServerThread> clientThreads;
    private int clientId;
    
    
    public ServerThread(Socket socket, List<ServerThread> clientThreads){
        this.skt = socket;
        this.clientThreads = clientThreads;
        synchronized (clientThreads) {
            this.clientThreads.add(this);
            this.clientId = this.clientThreads.indexOf(this);
        }
    }
    
    @Override
    public void run(){
        System.err.println("Client"+this.clientThreads.indexOf(this)+" connected to the Server");
        String inputLine;
        
        try{
            Scanner in = new Scanner(skt.getInputStream());			
            PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
            out.println("Bienvenido al chat global del servidor, introduzca \"quit\" para salir.");
            out.println("Es usted el cliente: "+this.clientThreads.indexOf(this));
            
            while(true){
                inputLine = in.nextLine();
                
                if (inputLine.equalsIgnoreCase("quit")){
                    out.println("Thank you for talking to us!");
                    break;
                }else{
                    broadcastMessage("Client "+getIdClient()+": " + inputLine);
                }
            }
            out.close();			
            in.close();
            skt.close();
            clientThreads.remove(this);
            System.err.println("Client connection termintated");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void broadcastMessage(String message) {
        for (ServerThread clientThread : clientThreads) {
            if (clientThread != this) {
                try {
                    PrintWriter clientOut = new PrintWriter(clientThread.skt.getOutputStream(), true);
                    clientOut.println(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private int getIdClient(){
        return clientId;
    }
}
