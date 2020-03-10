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

    // USER STATE
    private static String emailAddress;                 // unique emailAddress of the clients
    private static ArrayList<String> messages;          // For storing all the messages received from the client
    private static Duplexer duplexer;                   // for network communication
    private static String username;                     // name of the user which will be also unique
    private static int userKey;                         // unique identifier for the user
    private static Game game;                           // an instance of game to keep track of everything
    private static int numberOfLoops;                   // the number of loops that the person can start in a day
    private int score;                                  // the actual score of the user that is updated everyTime there is a change


    // List of all the contacts of the user even those who are not in snapLoop (email ids) (will be updated everyTime)
    private ArrayList<String> allContacts;

    // The arrayList contains the unique userNames of all the clients
    private ArrayList<String> friends;                  // List of people who are friends on snapLoop
    private ArrayList<String> requestsReceived;         // List of people that have requested to follow
    private ArrayList<String> requestsMade;             // List of people that you have requested to follow

    // These are the loops <Loop_ID, Loop>
    private HashMap<Integer,Loop> activeLoops;          // The loops that the client is currently active in
    private HashMap<Integer,Loop> completedLoops;       // The loops that the client has completed already

    /**
     * Will be used by the user whenever a new user SIGNS_UP
     * @param duplexer The duplexer object that will be used for the communication
     * @param username The username of the new user
     * @param game     The instance of the game
     * @param emailID  The emailID of the user
     */
    public ClientHandler(Duplexer duplexer, String username, Game game, String emailID){
        this.duplexer=duplexer;
        this.username=username;
        this.game=game;
        this.emailAddress=emailID;
        this.messages= new ArrayList<>();
        this.userKey=0;
        this.numberOfLoops=5;
        this.score=0;
        this.allContacts=new ArrayList<>();
        this.friends= new ArrayList<>();
        this.requestsMade=new ArrayList<>();
        this.requestsReceived= new ArrayList<>();
        this.activeLoops= new HashMap<>();
        this.completedLoops= new HashMap<>();
    }

    /**
     * everyTime the user logs in these are the only states that needs to be updated
     * @param duplexer The duplexer that establishes the communication
     * @param game     The most active instance of the game
     */
    public void login(Duplexer duplexer, Game game){
        this.duplexer=duplexer;
        this.game=game;
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
    public void checkUserOffline(){
        if(!duplexer.nextLine()){
            // removing the player since it is going offline
            System.out.println("disconnected");
            this.game.removeClient(userKey);
            interrupt();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void run() {
        /**
         *
         *
         *
         *
         */
        while (true){
            checkUserOffline();
            String message=duplexer.read();

            // initializing the messages for each new input read from the Client
            this.messages=new ArrayList<>();
            messages.addAll(Arrays.asList(message.split(" ")));
            switch (messages.get(0)) {
                case SEND:
                    //SEND();
                case START:
                    //START();
                case GET:
                    //GET();
            }
        }
    }
    public static void GET(){

    }
    //    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    public static void START(){
//        // when the create loop button is clicked, then send a message to the server that the user
//        // is trying to start a new loop
//
//        if(messages.size()==1){
//            // if the maximum number of loops have been reached
//            if(numberOfLoops==0){
//                sendMessage(MAX_LOOP);
//            }
//            else{
//                // sending the message to the client that they can start the loop
//                sendMessage(START);
//            }
//        }
//
//        String receiverUsername = messages.get(1);
//        String text= extractMessage(3);
//        String loopName=messages.get(2);
//
//        // if the loop with the same name exist
//        if(loops.containsValue(loopName)){
//            sendMessage(LOOP_CREATION_FAILED);
//        }
//        else{
//            int loopID=generateLoopID(username,loopName);
//            Loop loop=new Loop(loopID,username,loopName);
//            loop.addMember(receiverUsername);
//            loop.addMessage(text,username);
//            game.loops.put(loopID,loop);
//            loops.put(loopID,loopName);// maintaining the list of the loopID with the loopName
//            numberOfLoops--;
//            // sending the message to the USER that the new loop has been successfully started
//            sendMessage(LOOP_STARTED);
//            String message=RECEIVE+" "+loopID+" "+" "+loopName+" "+loop.getChat();
//            // sending the message to the RECEIVER
//            game.getClient(receiverUsername).sendMessage(message);
//        }
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    public static void SEND(){
//        String receiverUsername = messages.get(1);
//        String text = extractMessage(3); // will extract the message from that index;
//        String loopID = messages.get(2); // extracting the loopID from the message
//        int receiverKey=receiverUsername.hashCode();
//
//        // getting the existing loop from the game
//        Loop loop=game.loops.get(loopID);
//        loop.addMessage(text,username);
//        String sendMessage=RECEIVE+" "+loopID+" "+loop.getName()+" "+loop.getChat();
//
//
//        // Logic for Loop Completion
//        // if the user that we are sending the message to already exists
//        if (game.loops.get(loopID).userExists(receiverUsername)){
//            game.endLoop(Integer.parseInt(loopID),receiverUsername);
//        }
//        else {
//            game.loops.get(loopID).addMember(receiverUsername);
//        }
//        game.clients.get(receiverKey).sendMessage(sendMessage);
//    }

//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    public Boolean checkUserOnlineAlready(){
//        if(!game.clients.containsKey(this.userKey)){
//            sendMessage(AUTHENTICATED + " " + Integer.toString(this.userKey)); // sending the authentication message so that the user is able to login into the application
//            game.addClient(this.userKey,this);
//            return true;
//        }
//        // if the login failed
//        sendMessage(LOGIN_FAILED);
//        return false;
//    }

}