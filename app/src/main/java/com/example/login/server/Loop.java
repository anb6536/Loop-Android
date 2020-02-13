package com.example.login.server;

import java.util.ArrayList;

/**
 * @author : Sanchit Monga
 */
public class Loop {

    public static ArrayList<String> members;
    private static ChatBox chatBox;
    private int id;
    private int score;
    private static String creator;
    private static String name;

    public Loop(int id, String creator, String name){
        this.id=id;
        this.score=0;
        this.name=name;
        this.creator=creator;
        this.chatBox=new ChatBox();
        members=new ArrayList<>();
    }

    public static void setCreator(String username){
        creator=username;
    }

    public static String getName(){
        return name;
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
}
