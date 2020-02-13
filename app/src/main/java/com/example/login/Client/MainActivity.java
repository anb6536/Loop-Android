package com.example.login.Client;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.login.R;
import java.io.IOException;
import java.net.Socket;

/**
 * @author : Sanchit Monga
 * @author : Aahish Balimane
 */

public class MainActivity extends AppCompatActivity {

    private String username;
    public Socket socket;
    Button button;
    EditText usernameTextBox;
    TextView txt;
    public static Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("SnapLoops");
        this.button = findViewById(R.id.login);
        this.txt = findViewById(R.id.txt);
        this.usernameTextBox = findViewById(R.id.usrnm);
        this.client=null;
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                try {
                    login();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void login() throws IOException {
        username = usernameTextBox.getText().toString();
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

            client=new Client(username);
            Thread thread= new Thread(client);
            thread.start();
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }
}
