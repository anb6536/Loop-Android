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

    //<Key: Client key, Value: Client object>
    private static HashMap<Integer,ClientHandler> clients;  // List of all the users that are online
    //<Key: Loop Key, Value: Loop Object>
    private static HashMap<Integer,Loop> loops;             // List of all the loops that exist

    public Game(){
        this.clients=new HashMap<>();
        this.loops=new HashMap<>();
    }

    public static void addClient(int key,ClientHandler client){
        clients.put(key, client);
    }

    public static void addClient(String username, ClientHandler client){
        clients.put(username.hashCode(),client);
    }

    public static boolean clientOnline(String username){
        return clients.containsKey(username.hashCode());
    }

    public static ClientHandler getClient(int clientKey){
        return clients.get(clientKey);
    }

    public static Loop getLoop(int loopKey){
        return loops.get(loopKey);
    }

    public static void addLoop(int loopKey, Loop loop){
        loops.put(loopKey,loop);
    }

    public static void removeClient(int clientKey){
        loops.remove(clientKey);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void endLoop(int loopID, String receiverUsername){
        // points TBD

        // Getting all the members of the loop
//        ArrayList<String> members =loops.get(loopID).members;
//        int index=members.indexOf(receiverUsername);
//        int loopCreatorKey=members.get(0).hashCode();

//        if(index!=0){
//            clients.get(loopCreatorKey).sendMessage(LOOP_COMPLETE);
//        }
//        for(int i=index;i<members.size();i++){
//           clients.get(members.get(i).hashCode()).sendMessage(LOOP_COMPLETE);
//        }
//        for (int j=1; j<index; j++){
//            clients.get(members.get(j).hashCode()).sendMessage(LOOP_BROKEN);
//        }
    }

    @Override
    public void run() {
    }
}
