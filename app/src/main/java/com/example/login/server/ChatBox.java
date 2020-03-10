package com.example.login.server;

/**
 * @author: Sanchit Monga
 */
public class ChatBox {
    private static String chat;                         // contains all the messages that are anonymous
    private static String originalMessage;              // contains all the messages with their username
    private static int i;
    public ChatBox(){
        this.originalMessage="";
        this.chat="";
        this.i=1;
    }

    /**
     * @param m The message to be added to the existing message
     * @param username
     */
    public static void addChat(String m, String username){
        originalMessage.concat(username+": \n"+m+"\n");
        chat.concat("Anonymous "+i+": \n" +
                m+"\n");
        i++;
    }

    public String getMessage(){
        return chat;
    }

    public String getOriginalMessage(){
        return originalMessage;
    }
}
