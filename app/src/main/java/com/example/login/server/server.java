package com.example.login.server;
import android.os.Build;

import com.example.login.util.Duplexer;
import com.example.login.util.Protocols;
import java.io.*;
import java.net.*;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Random;

import androidx.annotation.RequiresApi;

/**
 * @author : Sanchit Monga
 */
public class server implements Protocols {
    public static HashMap<String,ClientHandler> clients;

    private static final Random random= new SecureRandom();
//    String getSaltedHash(String password)
//    boolean checkPassword(String password, String stored)

    public server(){
        clients=new HashMap<>(); // maintaining the list of all the clients
    }
    /**
     *
     * @param args
     * @throws IOException
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void main(String[] args) throws IOException
    {
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

                String[] input=m.split(" ");
                String username;

                if(input[0].equals(CONNECT)){
                    System.out.println("user connected");
                    username=input[1];
                    System.out.println("Client connected: "+username);
                }
                else {
                    System.out.println("UnAuthorized user connected");
                    continue;
                    // throw an error
                }

                String key=generatePassword(username);
                ClientHandler client = new ClientHandler(duplexer,username,game);

                // When a NEW Client is connected
                if(!clients.containsKey(key)){
                    clients.put(key,client);
                }
                Thread t = client;
                t.start();
            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
            }
        }
    }
    public static String generatePassword(String password){

    }
    public static boolean authenticate(String hashPassword){

    }
}