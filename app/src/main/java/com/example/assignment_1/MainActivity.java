package com.example.assignment_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "UserPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Creates a shared file to store data in (i.e. username & password for login verification)
        SharedPreferences userDate = getSharedPreferences(PREFS_NAME, 0);
        // userDate.getString
        final EditText usernameText = findViewById(R.id.usernameField);
        final EditText passwordTxt = findViewById(R.id.passwordField);
        Button loginBtn = findViewById(R.id.signUpButton);
        Button signUpBtn = findViewById(R.id.signUpButton);

        loginBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                // Handler code here:

                // Getting user input for username and password
                String username = usernameText.getText().toString();
                String password = passwordTxt.getText().toString();

                if (password.length() < 6){
                    Toast.makeText(getApplicationContext(), "Longer password required", Toast.LENGTH_SHORT);
                }
                else{
                    // Check username and password with Dictionary

                    // if it matches, then go to other activity
                    Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("password", password);
                    startActivity(intent); // Starts the WelcomeActivity
                }

            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                // Handler code here:
                // Go to sign up activity page
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

    }
}
