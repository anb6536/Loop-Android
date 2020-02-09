package com.example.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int SERVERPORT = 5056;

    public static final String SERVER_IP = "129.21.132.199";
    private ClientThread clientThread;
    private Thread thread;
    private LinearLayout msgList;
    private Handler handler;
    private int clientTextColor;
    private EditText edMessage;
    private EditText usernm;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        setTitle("Client");
        clientTextColor = ContextCompat.getColor(this, R.color.colorAccent);
        handler = new Handler();
        msgList = findViewById(R.id.msgList);
        edMessage = findViewById(R.id.edMessage);
        usernm = findViewById(R.id.usernm);
        username = usernm.getText().toString();
        msgList.removeAllViews();
        clientThread = new ClientThread();
        thread = new Thread(clientThread);
        thread.start();
        showMessage("Connected to Server.", clientTextColor);
    }

    public TextView textView(String message, int color) {
        TextView tv = new TextView(this);
        if (null == message || message.trim().isEmpty()) {
            message = "<Empty Message>";
        }else{
            tv.setTextColor(color);
            tv.setText(message);
            tv.setTextSize(20);
            tv.setPadding(0, 5, 0, 0);
        }
        return tv;
    }

    public void showMessage(final String message, final int color) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                msgList.addView(textView(message, color));
            }
        });
    }

    @Override
    public void onClick(View view) {

        username = usernm.getText().toString();
        if (view.getId() == R.id.send_data) {
            String clientMessage = edMessage.getText().toString().trim();
            clientMessage = "SEND " + username + " " + "0 " + clientMessage;
            showMessage(clientMessage, Color.BLUE);
            if (null != clientThread) {
                clientThread.sendMessage(clientMessage);
            }
            edMessage.setText("");
        }
    }

    class ClientThread implements Runnable {

        private Socket socket;
        private Duplexer duplexer;

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void run() {

            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr, SERVERPORT);
                Intent intent = getIntent();
                String client = intent.getExtras().getString("clientUsername");
                System.out.println("CHAT activity "+client);
                sendMessage("CONNECT " + client);

                while (!Thread.currentThread().isInterrupted()) {

                    this.duplexer=new Duplexer(socket);
                    String message =  duplexer.read(); //input.readLine();
                    if (null == message || "Disconnect".contentEquals(message)) {
                        Thread.interrupted();
                        message = "Server Disconnected.";
                        showMessage(message, Color.RED);
                        break;
                    }
                    showMessage("Server: " + message, clientTextColor);
                }

            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

        public void sendMessage(final String message) {
            new Thread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void run() {
                    try {
                        if (null != socket) {
                            Duplexer duplexer1=new Duplexer(socket);
                            duplexer1.send(message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != clientThread) {
            clientThread.sendMessage("Disconnect");
            clientThread = null;
        }
    }
}
