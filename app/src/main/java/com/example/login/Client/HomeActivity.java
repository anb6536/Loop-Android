package com.example.login.Client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.login.R;

/**
 * @author : Sanchit Monga
 * @author : Aahish Balimane
 */
public class HomeActivity extends AppCompatActivity {

    Button create;
    Button forward;
    static Boolean flag=false;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("SnapLoops");
        create = findViewById(R.id.create);
        forward = findViewById(R.id.forward);

        // on clicking the create button
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( HomeActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });
        if(flag==true){
            forward.setText("The loop was completed");
        }

        // on clicking the forward button
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forward.setText("CREATE");
                flag=false;
                Intent intent = new Intent(HomeActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });
    }
    public static void setCompleteStatus(){
        flag=true;
    }
}
