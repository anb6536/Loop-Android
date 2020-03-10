package com.example.login.server;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.login.util.Duplexer;
import com.example.login.util.Protocols;
import java.util.HashMap;
import java.util.HashSet;

public class HandleAuthentication implements Runnable, Protocols {

    private static String username;
    private static String password;
    private static String emailAddress;
    private static Duplexer duplexer;
    private static HashMap<String,ClientHandler> clients;
    private static Boolean newUser;
    private static PasswordAuthentication pass;
    private static HashMap<String, String> credentials;
    private static HashSet<String> emails;
    private static Game game;

    public HandleAuthentication(String username, String password, Duplexer duplexer, HashMap<String, ClientHandler> clients, HashMap<String, String> credentials, HashSet<String> emails, Game game){
        this.username=username;
        this.password=password;
        this.duplexer=duplexer;
        this.clients=clients;
        this.newUser= false;
        this.pass=new PasswordAuthentication();
        this.credentials= credentials;
        this.emails=emails;
        this.game=game;
    }
    public HandleAuthentication(String username, String password, String emailAddress, Duplexer duplexer,HashMap<String, ClientHandler> clients,HashMap<String, String> credentials, HashSet<String> emails, Game game){
        this.username=username;
        this.password=password;
        this.duplexer=duplexer;
        this.clients=clients;
        this.emailAddress=emailAddress;
        this.newUser=true;
        this.pass=new PasswordAuthentication();
        this.credentials= credentials;
        this.emails=emails;
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
        //TODO check if the user is already online by using the game instance
        if(false) {// user is active already
            duplexer.send(CONNECT+" FAILED "+"User already logged in!");
        }
        if(authenticate(password,credentials.get(username))){
            duplexer.send(CONNECT+" SUCCESSFUL");
            clients.get(username).login(duplexer,game);
            Thread t=clients.get(username);
            // TODO update the game that a user has become active
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
            clients.put(username,clientHandler);
            // TODO update the list of the online players in the game
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