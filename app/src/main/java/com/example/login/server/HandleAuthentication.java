package com.example.login.server;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.login.util.Duplexer;
import com.example.login.util.Protocols;
import java.util.HashMap;
import java.util.HashSet;

public class HandleAuthentication implements Runnable, Protocols {

    private static HashMap<String,ClientHandler> clients;    // <Key:unique username, Value:Client>
    private static HashMap<String, String> credentials;      // <Key: username, Value:Password>
    private static HashSet<String> emails;                   // contains all the emails that already are registered within the system
    private static String username;
    private static String password;
    private static String emailAddress;
    private static Duplexer duplexer;
    private static Boolean newUser;
    private static PasswordAuthentication pass;
    private static Game game;

    public HandleAuthentication(String username, String password, Duplexer duplexer, Game game){
        this.username=username;
        this.password=password;
        this.duplexer=duplexer;
        this.clients=new HashMap<>();
        this.newUser= false;
        this.pass=new PasswordAuthentication();
        this.credentials= new HashMap<>();
        this.emails=new HashSet<>();
        this.game=game;
    }

    public HandleAuthentication(String username, String password, String emailAddress, Duplexer duplexer,Game game){
        this.username=username;
        this.password=password;
        this.duplexer=duplexer;
        this.clients=new HashMap<>();
        this.emailAddress=emailAddress;
        this.newUser=true;
        this.pass=new PasswordAuthentication();
        this.credentials= new HashMap<>();
        this.emails=new HashSet<>();
        this.game=game;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void run() {
        try {
            if (!newUser) {
                handleLogin(this.username, this.password, this.duplexer);
            } else {
                handleSignUp(this.username, this.password, this.emailAddress, this.duplexer);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void handleLogin(String username, String password, Duplexer duplexer) throws Exception {
        if(game.clientOnline(username)) {// user is active already
            duplexer.send(CONNECT+" FAILED "+"User already logged in!");
        }
        if(authenticate(password,credentials.get(username))){
            duplexer.send(CONNECT+" SUCCESSFUL");
            clients.get(username).login(duplexer,game);                     // Updating the instance of the Duplexer and the game everyTime a user logs in
            Thread t=clients.get(username);
            game.addClient(username,clients.get(username));                 // adding the client in the update list
            t.start();
        }
        else{
            duplexer.send(CONNECT+" FAILED "+"INVALID username or password");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static void handleSignUp(String username, String password, String emailAddress, Duplexer duplexer) throws Exception {
        if(clients.keySet().contains(username)){
            duplexer.send(SIGNUP+" FAILED "+"Username already taken");
        }
        else if(emails.contains(emailAddress)){
            duplexer.send(SIGNUP+" FAILED "+"Email address already taken");
        }
        else {
            ClientHandler clientHandler = new ClientHandler(duplexer, username, game, emailAddress);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                credentials.put(username,generatePassword(password));
            }
            emails.add(emailAddress);                                     // Adding emailAddress of the new user
            clients.put(username,clientHandler);
            game.addClient(username,clientHandler);                       // A new client has been added to the game
            duplexer.send(SIGNUP+" SUCCESSFUL");
            Thread t= clientHandler;
            t.start();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String generatePassword(String password) throws Exception {
        return pass.getSaltedHash(password);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static boolean authenticate(String passwordEntered, String hashPassword) throws Exception {
        return pass.check(passwordEntered,hashPassword);
    }

}