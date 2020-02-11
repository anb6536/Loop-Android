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
    private static HashMap<Integer,Loop> loops;
    private static Game game;
    private static int numberOfLoops;  // the number of loops that the person has started in one day
    private static ArrayList<String> messages; // storing all the messages received from the client
    private static int score;

    public ClientHandler(Duplexer duplexer,String username, Game game) {
        this.duplexer=duplexer;
        this.contacts=new ArrayList<>();
        this.username=username;
        this.userKey=username.hashCode();
        this.loops=new HashMap<>();
        this.game=game;
        this.numberOfLoops=0;
        this.messages=new ArrayList<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void SEND(){
        int flag=0; // to check whether or not a new loop is being created
        String receiverUsername = messages.get(1);
        String text = "";
        String loopID = messages.get(2); // extracting the loopID from the message

        String sendMessage="";

        // Extracting the actual Text message that has to be sent to the user
        for (int i = 3; i < messages.size(); i++) {
            text = text + " " + messages.get(i);
        }
        text.trim();

        // Sending the message
        // updating the loop
        int receiverKey=receiverUsername.hashCode();

        // checking if the LoopID already exists
        for(Loop loop :game.loops.values()){
            if(loop.id==Integer.parseInt(loopID)){
                flag=1;
            }
        }

        if(flag!=1){
            if(numberOfLoops<3){

                int newLoopID=game.getNewLoopID(username,numberOfLoops+1);
                sendMessage = RECEIVE + " " + newLoopID + " " + text;

                // Creating the new loop and initializing it
                Loop loop=new Loop(newLoopID,username);
                loop.addMember(receiverUsername);
                loop.addMessage(text,username);

                //Maintaining the list of the loops
                game.loops.put(loop.id, loop);
                loops.put(loop.id,loop);

                // incrementing the number of loops
                numberOfLoops++;
            }
            else{
                // when the maximum number of loops has been reached
                game.clients.get(username).duplexer.send(MAX_LOOP);
            }
        }
        else{
            // end Loop case implemented
            //sendMessage = RECEIVE + " " + loopID + " "+ text;

            // adding the message in the existing loop
            game.loops.get(loopID).addMessage(text, username);

            sendMessage = RECEIVE + " " + loopID + " "+ game.loops.get(loopID).chatBox.getMessage();
            if (game.loops.get(loopID).userExists(receiverUsername)){
                System.out.println("Loop complete");
                int index = game.loops.get(loopID).members.indexOf(receiverUsername);
                game.endLoop(game.loops.get(loopID).members, index);
            }
            else {
                game.loops.get(loopID).addMember(receiverUsername);
            }
        }
        // sending the actual message
        if(game.clients.get(receiverKey)!=null){
            game.clients.get(receiverKey).duplexer.send(sendMessage);
        }
        // completing the loop logic
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
            duplexer.send(AUTHENTICATED + " " + Integer.toString(this.userKey)); // sending the authentication message so that the user is able to login into the application
            game.addClient(this.userKey,this);
            return true;
        }
        // if the login failed
        duplexer.send(LOGIN_FAILED);
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
            String message=this.duplexer.read();
            this.messages=new ArrayList<>();
            messages.addAll(Arrays.asList(message.split(" ")));

            switch (messages.get(0)) {
                case SEND:
                    SEND();
            }
        }
    }
}