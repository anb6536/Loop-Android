package com.example.login;

import android.os.Build;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.RequiresApi;

public class Client implements Runnable, Protocols{
    public static final int SERVERPORT = 5056;
    public static final String SERVER_IP = "129.21.132.199";
    Socket socket;
    static Duplexer duplexer;
    String username;
    public InetAddress inetAddress;
    public ChatActivity chatActivity;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Client(String username) throws IOException {
        inetAddress=InetAddress.getByName(SERVER_IP);
        this.username=username;
        this.chatActivity=null;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void run(){
        try {
            this.socket=new Socket(inetAddress,SERVERPORT);
            this.duplexer=new Duplexer(this.socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        duplexer.send(CONNECT+" "+username);
        while(!Thread.currentThread().isInterrupted()){
            ArrayList<String> messages=new ArrayList<>();
            messages.addAll(Arrays.asList(duplexer.read().split(" ")));
            String msg="";
            if(messages.get(0).equals(RECEIVE)){
                String loopID=messages.get(1);
                ArrayList<String> mesaage=new ArrayList<>();
                mesaage.addAll(messages.subList(2,messages.size()));
                for(String m:mesaage){
                    msg+=m+" ";
                }
                this.chatActivity.showMessage(msg,R.color.colorAccent);
                this.chatActivity.addLoopID(Integer.parseInt(loopID));
            }
            else if(messages.get(0).equals(LOOP_COMPLETE)){
                this.chatActivity.showMessage("The loop has been completed",R.color.colorAccent);
                HomeActivity.setCompleteStatus();
            }
            else if(messages.get(0).equals(LOOP_BROKEN)){
                this.chatActivity.showMessage("The loop is broken",R.color.colorAccent);
            }

        }
    }

    public void addChatActivity(ChatActivity chatActivity){
        this.chatActivity=chatActivity;
    }
//    public static Boolean checkForRecieve(){
//        if(flag) return false;
//        else return true;
//    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void sendMessage(String message, String sendTo, int loopID){
        duplexer.send(SEND+" "+sendTo+" "+loopID+" "+message);
    }
}
