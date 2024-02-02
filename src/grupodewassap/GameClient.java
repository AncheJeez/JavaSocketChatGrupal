/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grupodewassap;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class GameClient {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 4444);
            System.out.println("Connected to the server");

            // Setup input and output streams
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Read and print server welcome message
            System.err.println("Server: " + in.nextLine());
            System.err.println("Server: " + in.nextLine());

            // Start a separate thread for receiving messages
            new Thread(() -> {
                while (true) {
                    String message = in.nextLine();
                    System.out.println(message);
                }
            }).start();

            // Take user input and send it to the server
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String userInput = scanner.nextLine();
                out.println(userInput);

                if (userInput.equalsIgnoreCase("quit")) {
                    break;
                }
            }

            // Close resources
            scanner.close();
            in.close();
            out.close();
            socket.close();
            System.out.println("Disconnected from the server");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

