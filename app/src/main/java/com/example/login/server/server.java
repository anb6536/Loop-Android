package com.example.login.server;
import android.os.Build;

import com.example.login.util.Duplexer;
import com.example.login.util.Protocols;
import java.io.*;
import java.net.*;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import androidx.annotation.RequiresApi;

/**
 * @author : Sanchit Monga
 */
public class server implements Protocols {


    private static HashMap<String,ClientHandler> clients;    // <Key:unique username, Value:Client>
    private static HashMap<String, String> credentials;      // <Key: username, Value:Password>
    private static HashSet<String> emails;                   // contains all the emails that already are registered within the system
    private static Game game;                                // instance of the game

    public server(){
        this.clients=new HashMap<>(); // maintaining the list of all the clients
        this.game= new Game();
        this.emails= new HashSet<>();
        this.credentials= new HashMap<>();
    }
    /**
     *
     * @param args
     * @throws IOException
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void main(String[] args) throws IOException
    {
        ServerSocket ss = new ServerSocket(5056);
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

                // Handles everything from here
                HandleAuthentication handleAuthentication=null;
                if(input[0].equals(CONNECT)){
                    handleAuthentication= new HandleAuthentication(input[1],input[2],duplexer,clients,credentials,emails,game);
                }
                else if(input[0].equals(SIGNUP)){
                    handleAuthentication= new HandleAuthentication(input[1],input[2],input[3],duplexer,clients,credentials,emails,game);
                }
                else {
                    System.out.println("Unauthorized user connected");
                    s.close();
                    continue;
                }
                if(handleAuthentication!=null){
                    Thread t= new Thread(handleAuthentication);
                    t.start();
                }
            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
            }
        }
    }
}