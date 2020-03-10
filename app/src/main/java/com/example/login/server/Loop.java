package com.example.login.server;

import java.util.ArrayList;

/**
 * @author : Sanchit Monga
 */
public class Loop {

    private static ArrayList<String> members;        // UserNames of all the clients involved in the loop
    private static ChatBox chatBox;
    private static int loopID;
    private static String creator;
    private static String loopName;
    private static boolean loopActive;

    public Loop(int loopID, String creator, String loopName){
        this.loopID=loopID;
        this.loopName=loopName;
        this.creator=creator;
        this.chatBox=new ChatBox();
        this.members=new ArrayList<>();
        this.loopActive=true;
    }

    public static void setCreator(String username){
        creator=username;
    }

    public static String getName(){
        return loopName;
    }

    public static String getCreator(){
        return creator;
    }

    public static void addMessage(String message, String username){
        chatBox.addChat(message,username);
    }

    public static String getChat(){
        return chatBox.getMessage();
    }

    public static void addMember(String username){
        members.add(username);
    }

    public static boolean userExists(String username){
        return members.contains(username);
    }

    public static int getLoopID(){
        return loopID;
    }
    public static void setLoopActive(boolean isActive){
        loopActive=isActive;
    }
    public static boolean isLoopActive(){
        return loopActive;
    }
}
