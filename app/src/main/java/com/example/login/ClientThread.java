//package com.example.login;
//
//import android.graphics.Color;
//import android.os.Build;
//import android.os.Handler;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import java.io.IOException;
//import java.net.InetAddress;
//import java.net.Socket;
//import java.net.UnknownHostException;
//
//import androidx.annotation.RequiresApi;
//
//
///**
// * @author : Sanchit Monga
// * @author : Aahish Balimane
// */
//class ClientThread implements Runnable {
//
//    private Socket socket;
//    private Duplexer duplexer;
//    private String client;
//    private String mode;
//    private Handler handler;
//    private LinearLayout msgList;
//    private int clientTextColor;
//    private ChatActivity chatActivity;
//    public ClientThread(String client, String mode, Handler handler, LinearLayout msgList, int clientTextColor, ChatActivity chatActivity){
//        this.client=client;
//        this.mode=mode;
//        this.handler=handler;
//        this.msgList=msgList;
//        this.clientTextColor=clientTextColor;
//        this.chatActivity=chatActivity;
//    }
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    @Override
//    public void run() {
//
//        try {
//            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
//            socket = new Socket(serverAddr, SERVERPORT);
//
//            sendMessage("CONNECT " + client);
//
//            while (!Thread.currentThread().isInterrupted()) {
//                this.duplexer=new Duplexer(socket);
//                String message=duplexer.read();
////                String[] message =  duplexer.read().split(" "); //input.readLine();
////                if(mode.equals("create")){
////
////                }
//                    if (null == message || "Disconnect".contentEquals(message)) {
//                        Thread.interrupted();
//                        message = "Server Disconnected.";
//                        showMessage(message, Color.RED);
//                        break;
//                    }
//                showMessage("Server: " + message, clientTextColor);
//            }
//
//        } catch (UnknownHostException e1) {
//            e1.printStackTrace();
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//    }
//    public void showMessage(final String message, final int color) {
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                msgList.addView(textView(message, color));
//            }
//        });
//    }
//    public TextView textView(String message, int color) {
//        TextView tv = new TextView(chatActivity);
//        if (null == message || message.trim().isEmpty()) {
//            message = "<Empty Message>";
//        }else{
//            tv.setTextColor(color);
//            tv.setText(message);
//            tv.setTextSize(20);
//            tv.setPadding(0, 5, 0, 0);
//        }
//        return tv;
//    }
//
//    public void sendMessage(final String message) {
//        new Thread(new Runnable() {
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void run() {
//                try {
//                    if (null != socket) {
//                        Duplexer duplexer1=new Duplexer(socket);
//                        duplexer1.send(message);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
//
//}