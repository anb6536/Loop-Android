package com.example.login.server;
import java.io.*;
import java.net.*;
import java.util.HashMap;

// Server class
public class server implements Protocols {
    public static HashMap<Integer,ClientHandler> clients;

    public server(){
    }

    /**
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException
    {
        clients=new HashMap<>();
        // server is listening on port 5056
        ServerSocket ss = new ServerSocket(5056);
        // running infinite loop for getting
        // client request
        Game game=new Game();
        game.run();
        while (true)
        {
            System.out.println("Server is running");
            Socket s = null;
            try
            {
                // socket object to receive incoming client requests
                s = ss.accept();
                System.out.println("A new client is connected : " + s);
                Duplexer duplexer= new Duplexer(s);
                String m=duplexer.read();
                System.out.println(m);
                String[] input=m.split(" ");
                String username="";
                if(input[0].equals(CONNECT)){
                    System.out.println("user connected");
                    username=input[1];
                }
                else {
                    // throw an error
                }
                int key=username.hashCode();
                ClientHandler client = new ClientHandler(duplexer,username,game);
                if(!clients.containsKey(key)){
                    clients.put(key,client);
                }
                if(!game.clients.containsKey(key)) {
                    duplexer.send(AUTHENTICATED + " " + Integer.toString(key)); // sending the authentication message so that the user is able to login into the application
                    game.addClient(key,client); // maintains the list of clients who are online
                }
                else{
                    continue;
                }
                Thread t = client;
                // Invoking the start() method
                t.start();
                System.out.println("Assigning new thread for this client");
                // create a new thread object
                // adding the client in the loop so that the new player can be updated
            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
            }
        }
    }
}