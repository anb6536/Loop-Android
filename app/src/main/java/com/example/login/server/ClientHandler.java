package com.example.login.server;


import android.os.Build;

import com.example.login.util.Duplexer;
import com.example.login.util.Protocols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import androidx.annotation.RequiresApi;

/**
 * @author : Sanchit Monga
 * @author : Gnandeep Gotipatti
 */
class ClientHandler extends Thread implements Protocols {
    private static Duplexer duplexer;
    private ArrayList<String> contacts;
    private static String username;
    private static int userKey;
    //<LoopID, LoopName>
    private static HashMap<Integer,String> loops;
    private static Game game;
    private static int numberOfLoops;  // the number of loops that the person can start for the day
    private static ArrayList<String> messages; // storing all the messages received from the client
    private static int score;

    public ClientHandler(Duplexer duplexer,String username, Game game) {
        this.duplexer=duplexer;
        this.contacts=new ArrayList<>();
        this.username=username;
        this.userKey=username.hashCode();
        this.loops=new HashMap<>();
        this.game=game;
        this.numberOfLoops=10;
        this.messages=new ArrayList<>();
    }

    private static int generateLoopID(String username, String loopName){
        return (username+loopName).hashCode();
    }
    private static String extractMessage(int j){
        String text="";
        for (int i = j; i < messages.size(); i++) {
            text = text + " " + messages.get(i);
        }
        text.trim();
        return text;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void sendMessage(String message){
        duplexer.send(message);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getMessage(){
        return duplexer.read();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void START(){
        // when the create loop button is clicked, then send a message to the server that the user
        // is trying to start a new loop

        if(messages.size()==1){
            // if the maximum number of loops have been reached
            if(numberOfLoops==0){
                sendMessage(MAX_LOOP);
            }
            else{
                // sending the message to the client that they can start the loop
                sendMessage(START);
            }
        }

        String receiverUsername = messages.get(1);
        String text= extractMessage(3);
        String loopName=messages.get(2);

        // if the loop with the same name exist
        if(loops.containsValue(loopName)){
            sendMessage(LOOP_CREATION_FAILED);
        }
        else{
            int loopID=generateLoopID(username,loopName);
            Loop loop=new Loop(loopID,username,loopName);
            loop.addMember(receiverUsername);
            loop.addMessage(text,username);
            game.loops.put(loopID,loop);
            loops.put(loopID,loopName);// maintaining the list of the loopID with the loopName
            numberOfLoops--;
            // sending the message to the USER that the new loop has been successfully started
            sendMessage(LOOP_STARTED);
            String message=RECEIVE+" "+loopID+" "+" "+loopName+" "+loop.getChat();
            // sending the message to the RECEIVER
            game.getClient(receiverUsername).sendMessage(message);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void SEND(){
        String receiverUsername = messages.get(1);
        String text = extractMessage(3); // will extract the message from that index;
        String loopID = messages.get(2); // extracting the loopID from the message
        int receiverKey=receiverUsername.hashCode();

        // getting the existing loop from the game
        Loop loop=game.loops.get(loopID);
        loop.addMessage(text,username);
        String sendMessage=RECEIVE+" "+loopID+" "+loop.getName()+" "+loop.getChat();


        // Logic for Loop Completion
        // if the user that we are sending the message to already exists
        if (game.loops.get(loopID).userExists(receiverUsername)){
            game.endLoop(Integer.parseInt(loopID),receiverUsername);
        }
        else {
            game.loops.get(loopID).addMember(receiverUsername);
        }
        game.clients.get(receiverKey).sendMessage(sendMessage);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void checkUserOffline(){
        if(!duplexer.nextLine()){
            // removing the player since it is going offline
            System.out.println("disconnected");
            game.clients.remove(userKey);
            interrupt();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Boolean checkUserOnlineAlready(){
        if(!game.clients.containsKey(this.userKey)){
            sendMessage(AUTHENTICATED + " " + Integer.toString(this.userKey)); // sending the authentication message so that the user is able to login into the application
            game.addClient(this.userKey,this);
            return true;
        }
        // if the login failed
        sendMessage(LOGIN_FAILED);
        return false;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void run() {
        /**
         * SEND
         * RECIEVE
         * LOOP_COMPLETE
         * DISCONNECT
         */
        while (true){
            checkUserOffline();

            // checking if the player is already online and someone else is trying to login in with their name
            if(!checkUserOnlineAlready())
                continue;

            // reading the message from the Client
            String message=getMessage();
            this.messages=new ArrayList<>();
            messages.addAll(Arrays.asList(message.split(" ")));

            switch (messages.get(0)) {
                case SEND:
                    SEND();
                case START:
                    START();
            }
        }
    }
}