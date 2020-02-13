package com.example.login.server;


import android.os.Build;

import com.example.login.util.Protocols;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.RequiresApi;

/**
 * @author : Sanchit Monga
 */
public class Game implements Runnable, Protocols {
    public static HashMap<Integer,ClientHandler> clients;
    public static HashMap<Integer,Loop> loops;
    private static HashMap<Integer,Integer> userScores;

    public Game(){
        this.clients=new HashMap<>();
        this.loops=new HashMap<>();
        this.userScores=new HashMap<>();
    }

    /**
     * Adding a new user into the list
     * @param client
     */
    public static void addClient(int key,ClientHandler client){
        clients.put(key, client);
    }

    public static ClientHandler getClient(String username){
        return clients.get(username.hashCode());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void endLoop(int loopID, String receiverUsername){
        // points TBD

        // Getting all the members of the loop
        ArrayList<String> members =loops.get(loopID).members;
        int index=members.indexOf(receiverUsername);
        int loopCreatorKey=members.get(0).hashCode();

        if(index!=0){
            clients.get(loopCreatorKey).sendMessage(LOOP_COMPLETE);
        }
        for(int i=index;i<members.size();i++){
           clients.get(members.get(i).hashCode()).sendMessage(LOOP_COMPLETE);
        }
        for (int j=1; j<index; j++){
            clients.get(members.get(j).hashCode()).sendMessage(LOOP_BROKEN);
        }
    }

    @Override
    public void run() {
    }
}
