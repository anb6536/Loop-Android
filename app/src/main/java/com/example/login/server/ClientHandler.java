package com.example.login.server;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

class ClientHandler extends Thread implements Protocols {
    public Duplexer duplexer;
    private ArrayList<String> contacts;
    private String username;
    private HashMap<Integer,Loop> loops;
    private Game game;
    private int numberOfLoops;
    // Constructor
    public ClientHandler(Duplexer duplexer,String username, Game game) throws IOException {
        this.duplexer=duplexer;
        this.contacts=new ArrayList<>();
        this.username=username;
        this.loops=new HashMap<>();
        this.game=game;
        this.numberOfLoops=0;
    }

    @Override
    public void run() {
        /**
         * CONNECT
         * AUTHENTICATED
         * SEND
         * RECIEVE
         * LOOP_COMPLETE
         * DISCONNECT
         */
        while (true){
            if(!this.duplexer.nextLine()){
                // removing the player since it is going offline
                System.out.println("disc");
                game.clients.remove(username.hashCode());
                interrupt();
            }
            String message=this.duplexer.read();
            System.out.println(message);
            ArrayList<String> messages=new ArrayList<>();
            messages.addAll(Arrays.asList(message.split(" ")));
            int flag=0;
            switch (messages.get(0)) {
                case SEND:
                    String sendTo = messages.get(1);
                    String msg = "";
                    String loopID = messages.get(2); // extracting the loopID from the message
                    for (int i = 3; i < messages.size(); i++) {
                        msg = msg + " " + messages.get(i);
                    }

                    // Sending the message
                    // updating the loop
                    String sendMessage="";
                    int sendToKey=sendTo.hashCode();

                    for(Loop loop :game.loops.values()){
                        if(loop.id==Integer.parseInt(loopID)){
                            flag=1;
                        }
                    }

                    if(flag!=1){
                        if(numberOfLoops<3){

                            int newLoopID=game.getNewLoopID(username,numberOfLoops+1);
                            sendMessage = RECEIVE + " " + newLoopID + " " + msg;
                            Loop loop=new Loop(newLoopID);

                            loop.setCreator(username);
                            loop.addMember(sendTo);
                            loop.addMessage(sendMessage,username);
                            game.loops.put(loop.id, loop);

                            numberOfLoops++;
                        }
                        else{
                            // when the maximum number of loops have been reached
                            game.clients.get(username).duplexer.send(MAX_LOOP);
                            continue;
                        }
                    }
                    else{
                        // end Loop case implemented
                        sendMessage = RECEIVE + " " + loopID + " "+ msg;
                        game.loops.get(loopID).addMessage(sendMessage, username);
                        if (game.loops.get(loopID).userExists(sendTo)){
                            System.out.println("Loop complete");
                            int index = game.loops.get(loopID).members.indexOf(sendTo);
                            game.endLoop(game.loops.get(loopID).members, index);
                        }
                        else {
                            game.loops.get(loopID).addMember(sendTo);
                        }
                    }
                    // sending the actual message
                    if(game.clients.get(sendToKey)!=null){
                        game.clients.get(sendToKey).duplexer.send(sendMessage);
                    }
                    // completing the loop logic
                    break;
            }
        }
    }
}