package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * @author : Sanchit Monga
 * @author : Aahish Balimane
 */
public class HomeActivity extends AppCompatActivity {

    Button create;
    Button forward;
    String clientUsername;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        create = findViewById(R.id.create);
        forward = findViewById(R.id.forward);
        Intent intent = getIntent();
        clientUsername = intent.getExtras().getString("clientUsername");
        System.out.println(clientUsername);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( HomeActivity.this, ChatActivity.class);
                intent.putExtra("clientUsername", clientUsername);
                intent.putExtra("Mode", "create");
                startActivity(intent);
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ChatActivity.class);
                intent.putExtra("clientUsername", clientUsername);
                intent.putExtra("Mode", "forward");
                startActivity(intent);
            }
        });
    }
}
