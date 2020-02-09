package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String username;
    Button button;
    EditText usrnm;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.button = findViewById(R.id.login);
        this.txt = findViewById(R.id.txt);
        this.usrnm = findViewById(R.id.usrnm);
        System.out.println("mainActivity "+username);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login(){
        username = usrnm.getText().toString();
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        intent.putExtra("clientUsername", username);
        startActivity(intent);
    }
}
