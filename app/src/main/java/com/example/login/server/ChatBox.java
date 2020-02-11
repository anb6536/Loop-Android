package com.example.login.server;

/**
 * @author: Sanchit Monga
 */
public class ChatBox {
    private static String message; // contains all the messages with the name of the person who wrote them
    private static String chat; // contains all the messages

    public ChatBox(){
        this.message="";
        this.chat="";
    }

    /**
     * @param m The message to be added to the existing message
     * @param username
     */
    public static void addChat(String m, String username){
        message.concat("\n"+username+" "+m);
        chat.concat("Anonymous: \n" +
                m+"\n");
    }

    public String getMessage(){
        return chat;
    }
}
