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
    static Boolean flag=false;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        create = findViewById(R.id.create);
        forward = findViewById(R.id.forward);
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

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forward.setText("CREATE");
                flag=false;
//                while(flag){
//                    flag=MainActivity.client.checkForRecieve();
//                    forward.setClickable(false);
//                }
//                if(flag!=false){
//                    forward.setClickable(true);
//                }
                Intent intent = new Intent(HomeActivity.this, ChatActivity.class);
                intent.putExtra("clientUsername", clientUsername);
                intent.putExtra("Mode", "forward");
                startActivity(intent);
            }
        });
    }
    public static void setCompleteStatus(){
        flag=true;
    }
}
