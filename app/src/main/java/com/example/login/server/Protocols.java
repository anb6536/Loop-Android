package com.example.login.server;

/**
 * File: Protocols.java
 * Description: Defines the protocols for the client server interface.
 * Authors: Gnandeep, Snchit, Aahish
 */

/**
 * Defining the protocols for the server
 */
public interface Protocols {

    /**
     * From: Client
     * To: Server
     * Format: "CONNECT"_"USR"
     * Informs the server that the client wants to get connected to the server
     */
    public static final String CONNECT = "CONNECT";

    /**
     * From: server
     * To: Client
     * Format: "AUTH"_"UID"_"USR"
     * Informs the client that he has been authenticated if he/she logs in again
     */
    public static final String AUTHENTICATED = "AUTHENTICATED";

    /**
     * From: client
     * To: Server
     * Format: "SEND"_"USR"_"LOOP_ID"_"MSG" (username of the user to whom this message is being sent)
     * Requests the server to send the message
     */
    public static String SEND = "SEND";

    /**
     * From: Server
     * To: Client
     * Format:
     * Server sends through the
     */
    public static String CONTACTS="CONTACTS";
    /**
     * From : server
     * To : client
     * Format: "RECIEVE"_"LOOP_ID"_"MSG" ( username of the user who is
     * Notifies the client that he/she received a new loop
     */
    public static String RECEIVE = "RECEIVE";

    /**
     * from: Server
     * to: Client
     * Format: "MAX"
     * notifies the user that the daily loop creation limit has been reached
     */
    public static String MAX_LOOP="MAX";
    /**
     * From: server
     * To: client
     * Format: "LOOPCOMP"_"VAL"
     * Notifies the clients that the loop is complete
     */
    public static String LOOP_COMPLETE = "LOOP_COMPLETE";

    /**
     * from: server
     * to: client
     * format
     */
    public static String LOOP_BROKEN="LOOP_BROKEN";
    /**
     * From: client
     * To: Server
     * Format: "DISCONNECT"_"USR"
     * Informs the server it wants to disconnect and go offline
     */
    public static final String DISCONNECT = "DISCONNECT";
}
