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
     * IMPLEMENTED in SERVER:
     * Logging in Authentication:
     *      *  Client to Server
     *      *      CONNECT "USERNAME" "PASSWORD"
     *      *  Server to Client
     *      *      CONNECT FAILED "Error message(either user already online or incorrect credentials)"
     *      *      CONNECT SUCCESSFUL
     *      *
     *      *                            Signing up
     *      * Client to Server:
     *      *      SIGNUP "USERNAME(no spaces in between)" "PASSWORD" "EmailAddress(should be unique)"
     *      * Server to Client:
     *      *      SIGNUP FAILED "Error message(username taken, or account with the emailID exists already, or user logged in already"
     *      *      SIGNUP SUCCESSFUL
     *      *
     *      *                          Getting homepage data:
     *      * Client to Server:
     *      *      GET NUMBER_OF_LOOPS_AVAILABLE
     *      * Server to Client
     *      *      SEND NUMBER_OF_LOOPS_AVAILABLE "number of loops available for the day"
     *      *      (if number of loops is 0 then create should be disabled in the UI)
     *      *
     *      * Client to Server:
     *      *      GET SCORE
     *      * Server to Client:
     *      *      SEND SCORE "actual score of the player"
     *      *
     *      * Client to Server:
     *      *      GET NUMBER_OF_REQUESTS
     *      * Server to Client:
     *      *      SEND NUMBER_OF_REQUESTS "number of requests"
     *      *      (number of new requests that other users have sent)
     *      *
     *      * Client to Server:
     *      *      GET LOOPS_COMPLETED
     *      * Server to Client
     *      *      SEND LOOPS_COMPLETED "number of loops that have been completed"
     *      *      (this will be an alert slider for if there is a new loop has been completed)
     *      *
     *      *                      Current loops Data(if the user clicks on current loops:
     *      * Client to Server:
     *      *      GET ACTIVE_LOOPS_INFO
     *      * Server to Client:
     *      *      SEND ACTIVE_LOOPS_INFO "LOOP_NAME,NUMBER_OF_PEOPLE(involved)".........(all the loops so there will be more)
     *      *      (will be separated by a ",", thus loop names cannot contain a comma
     *      *
     *      *                              getting Loop clicked(getting loop data)
     *      * Client to Server:
     *      *      GET LOOP_DATA "LOOP_ID"
     *      * Server to Client:
     *      *      SEND LOOP_DATA "MESSAGES"
     *      *      MESSAGES contain the complete chat box
     *      *
     *
     *
     */



    /**
     * TO be Implemented:
     *                          When trying to create a new loop
     * Client to Server:
     *      SEND "LOOP_NAME" "MESSAGE"
     * Server to Client:
     *      SEND START_LOOP_APPROVED
     *      (which is unique)
     *      SEND LOOP_START_FAILED "error message"
     *      (if the loop was not started successfully due to whether the loop name needs to be changed)
     *
     *      if the loop is approved
     *      UPDATE CONTACTS "emailID of the friends"......
     * (once the start of the loop is approved, UI needs to take it to the next step ie to the contacts
     * window that contains details of all the contacts)
     *
     * Client to Server:
     *      UPDATE CONTACTS
     * Server to Client:
     *      UPDATE CONTACTS "emailIDs"............
     *
     * Client to Server:
     *      SEND MESSAGE "LOOP_NAME" "emailAddress of the person"
     * Server to Client:
     *      SEND LOOP_STARTED "LOOP_ID"
     *
     *       To the other client
     *       RECEIVE "LOOP_NAME" "Message"
     */

    public static final String CONNECT = "CONNECT";
    public static final String SIGNUP="SIGNUP";
    public static final String GET="GET";
    public static final String NUMBER_OF_LOOPS_AVAILABLE="NUMBER_OF_LOOPS_AVAILABLE";
    public static final String NUMBER_OF_REQUESTS="NUMBER_OF_REQUESTS";
    public static final String LOOPS_COMPLETED="LOOPS_COMPLETED";
    public static final String ACTIVE_LOOPS_INFO="ACTIVE_LOOPS_INFO";
    public static final String SCORE="SCORE";
    public static final String LOOP_DATA="LOOP_DATA";
    public static final String UPDATE="UPDATE";
    public static final String SEND = "SEND";
    public static final String RECEIVE = "RECEIVE";
    public static final String LOOP_COMPLETE = "LOOP_COMPLETE";
    public static final String LOOP_BROKEN="LOOP_BROKEN";
    public static final String CONTACTS="CONTACTS";
    public static final String START="START";
    public static final String LOOP_STARTED="LOOP_STARTED";
    public static final String LOOP_CREATION_FAILED="LOOP_CREATION_FAILED";
    public static final String MAX_LOOP="MAX_LOOP";
}
