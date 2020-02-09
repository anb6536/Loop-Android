package com.example.login.server;

/**
 * @author: Sanchit Monga
 */
public class ChatBox {
    private static String message;

    public ChatBox(){
        this.message="";
    }

    /**
     * @param m The message to be added to the existing message
     * @param username
     */
    public static void addChat(String m, String username){
        message.concat("\n"+username+" "+m);
    }
}
