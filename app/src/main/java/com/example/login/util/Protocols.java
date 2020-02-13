package com.example.login.util;

/**
 * File: Protocols.java
 * Description: Defines the protocols for the client server interface.
 * @author: Sanchit Monga
 * @author: Gnandeep Gotipatti
 * @author: Aahish Balimane
 */

/**
 * Defining the protocols for the server
 */
public interface Protocols {

    /**
     * ....................CLIENT TO SERVER....................
     *
     * Used when a NEW USER is connected
     *          "CONNECT"_"USR"_"PHONE"
     *
     * Used when a USER starts a NEW LOOP, "USR" represents the name of the receiver of the "message"
     *          "START"_"USR"_"LOOP_NAME"_"message"
     *
     * Used when a message has to be sent in the existing loop, along with the name of the receiver
     * of the message, LOOP_ID and the LOOP_NAME.
     *           "SEND"_"USR"_"LOOP_ID"_"LOOP_NAME"_"message"
     *
     * Will be sent by the client when they click the Create button on the client side and will be
     * responded by the server, that whether or not they can start the loop
     *           "START"
     *
     * Will be communicated back and forth by the client and the server to maintain the list of contacts for each client
     *           "CONTACTS"_"NAMES"....
     * ....................SERVER TO CLIENT.....................
     *
     * Will be sent by the server to the client if the login failed or the user with the same name
     * already exists and is online
     *           "LOGIN"_"FAILED"
     *
     * Will be sent by the server to the client if the login was successful
     *           "AUTHENTICATED"_"UID"
     *
     * Will be sent by the server to the client if the loop was successfully started by the client
     *           "LOOP_STARTED"_"LOOP_NAME"_"LOOP_ID"
     *
     * Will be sent by the server to the receiver along with the loopID and loopNAME and the message
     *           "RECEIVE"_"LOOP_ID"_"LOOP_NAME"_"MSG"
     *
     * Will be communicated back and forth by the client and the server to maintain the list of contacts for each client
     *           "CONTACTS"_"NAMES"....
     *
     * will be sent by the server to the client when the maximum number of loops that can be created in a day has been reached
     *           "MAX"_"LOOP"
     *
     * Will be sent by the server to the client if the loop was successfully completed
     *           "LOOP"_"COMPLETE"
     *
     * Will be sent by the server to the client if the loop was accidentally broken
     *           "LOOP_BROKEN"_"LOOP_ID"
     *
     * will be sent by the server to the client if the loop creation failed(due to the same name of the loop)
     *           "LOOP_CREATION_FAILED"_"LOOP_NAME"
     */

    public static final String CONNECT = "CONNECT";
    public static final String LOGIN_FAILED="LOGIN_FAILED";
    public static final String AUTHENTICATED = "AUTHENTICATED";
    public static final String START="START";
    public static final String LOOP_STARTED="LOOP_STARTED";
    public static final String LOOP_CREATION_FAILED="LOOP_CREATION_FAILED";
    public static final String SEND = "SEND";
    public static final String CONTACTS="CONTACTS";
    public static final String RECEIVE = "RECEIVE";
    public static final String MAX_LOOP="MAX_LOOP";
    public static final String LOOP_COMPLETE = "LOOP_COMPLETE";
    public static final String LOOP_BROKEN="LOOP_BROKEN";
}
