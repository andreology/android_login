package com.example.assignment_1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_activity);

        Intent intent = getIntent();
        String user = intent.getStringExtra("username");
        String pass = intent.getStringExtra("password");

        TextView usernameDisplay = findViewById(R.id.usernameView);
        usernameDisplay.setText(user);
    }
}


