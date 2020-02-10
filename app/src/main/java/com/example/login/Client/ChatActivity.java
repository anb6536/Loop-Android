package com.example.login.Client;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.login.util.Protocols;
import com.example.login.R;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

/**
 * @author : Sanchit Monga
 * @author : Aahish Balimane
 * @author : Mehul Sen
 */
public class ChatActivity extends AppCompatActivity implements View.OnClickListener, Protocols {

    private static LinearLayout msgList;
    private static Handler handler;
    private int clientTextColor;
    private EditText messageBox;
    private EditText sendToBox;
    private String username;
    private static int loopID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setTitle("SnapLoops");

        clientTextColor = ContextCompat.getColor(this, R.color.colorAccent);
        handler = new Handler();

        msgList = findViewById(R.id.msgList); // sent messages
        messageBox = findViewById(R.id.edMessage); // text box where you type the message
        sendToBox = findViewById(R.id.usernm);  // username text box

        username = sendToBox.getText().toString(); // taking out the username from the user
        msgList.removeAllViews();
        MainActivity.client.addChatActivity(this);
    }

    public void showMessage(final String message, final int color) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                msgList.addView(textView(message, color));
            }
        });
    }
    public static void addLoopID(int id){
        loopID=id;
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        // after clicking the send button
        username = sendToBox.getText().toString();
        if (view.getId() == R.id.send_data) {
            String clientMessage = messageBox.getText().toString().trim();
            showMessage(clientMessage, Color.BLUE);
            MainActivity.client.sendMessage(clientMessage,username,loopID);
            messageBox.setText("");
            Intent intent =new Intent(ChatActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }
}
