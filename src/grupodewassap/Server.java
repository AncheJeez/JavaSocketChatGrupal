/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grupodewassap;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import grupodewassap.ServerThread;

/**
 *
 * @author AndJe
 */
public class Server {
    
    private static List<ServerThread> clientThreads = new ArrayList<>();
    
    public static void main(String[] args) throws Exception{
        
        ServerSocket svrsk = new ServerSocket(4444);
        System.out.println("Server started");
        
        try{
            while(true){
                //svrk.accept() bloquea hasta que un cliente se conecte al servidor
                ServerThread newClientThread = new ServerThread(svrsk.accept(), clientThreads);
                // el ServerThread se añade a la lista en el propio constructor, para que en el tambien se
                // pueda acceder al id basado en la posicion en la lista
                //clientThreads.add(newClientThread);
                newClientThread.start();
            }
        }finally{
            svrsk.close();
            System.out.println("Server closed.");
        }
    }
}
