package com.example.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

/**
 * @author : Sanchit Monga
 * @author : Aahish Balimane
 * @author : Mehul Sen
 */
public class ChatActivity extends AppCompatActivity implements View.OnClickListener, Protocols {

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

        msgList = findViewById(R.id.msgList); // sent messages
        edMessage = findViewById(R.id.edMessage); // text box where you type the message
        usernm = findViewById(R.id.usernm);  // username text box

        username = usernm.getText().toString(); // taking out the username from the user

        msgList.removeAllViews();

        Intent intent = getIntent();
        String clientUsername = intent.getExtras().getString("clientUsername");
        String mode=intent.getExtras().getString("mode");

        clientThread = new ClientThread(clientUsername,mode, handler,msgList,clientTextColor,this);
        thread = new Thread(clientThread);
        thread.start();

        //showMessage("Connected to Server.", clientTextColor);
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
        // after clicking the send button

        username = usernm.getText().toString();
        if (view.getId() == R.id.send_data) {
            String clientMessage = edMessage.getText().toString().trim();
            showMessage(clientMessage, Color.BLUE);
            clientMessage = "SEND " + username + " " + "0 " + clientMessage;
            if (null != clientThread) {
                clientThread.sendMessage(clientMessage);
            }
            edMessage.setText("");
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
