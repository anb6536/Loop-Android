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

    public Game(){
        this.clients=new HashMap<>();
        this.loops=new HashMap<>();
    }

    /**
     * Adding a new user into the list
     * @param client
     */
    public static void addClient(int key,ClientHandler client){
        clients.put(key, client);
    }

//    public static ClientHandler getClient(String username){
//        return clients.get(username.hashCode());
//    }

    public static int getNewLoopID(String username, int i){
        return username.hashCode()+i;
    }
//    /**
//     * Adding a new loop that was created by a client in the list
//     * @param loop
//     */
//    public static void addNewLoop(int loopID, Loop loop){
//        loops.put(loopID,loop);
//    }
//
//    public static void updateLoop(int loopID){
//
//    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void endLoop(ArrayList<String> clients1, int index){
        // points TBD
        if(index!=0){
            clients.get(clients1.get(0).hashCode()).duplexer.send(LOOP_COMPLETE);
        }
        for(int i=index;i<clients.size();i++){
           clients.get(clients1.get(i).hashCode()).duplexer.send(LOOP_COMPLETE);
        }
        for (int j=1; j<index; j++){
            clients.get(clients1.get(j).hashCode()).duplexer.send(LOOP_BROKEN);
        }
    }
    @Override
    public void run() {
    }
}
