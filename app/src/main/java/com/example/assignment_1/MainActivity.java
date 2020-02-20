package com.example.assignment_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    // Dictionary of users here:
    public static Dictionary users = new Hashtable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        final EditText usernameText = findViewById(R.id.usernameField);
//        final EditText passwordTxt = findViewById(R.id.passwordField);
//        final Button loginBtn = findViewById(R.id.signUpButton);
//        final Button signUpBtn = findViewById(R.id.signUpButton);

        /*
        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Handler code here:

                // Getting user input for username and password
                String username = usernameText.getText().toString();
                String password = passwordTxt.getText().toString();

                if (password.length() < 6){
                    // Toast
                    Toast.makeText(getApplicationContext(), "Longer password required", Toast.LENGTH_SHORT);
                }
                else{
                    // Check username and password with Dictionary
                    if (password.equals(users.get(username))){ // check if the password matches the username's value pair
                        // if it matches, then go to other activity
                        Intent welcomeIntent = new Intent(getApplicationContext(), WelcomeActivity.class);
                        welcomeIntent.putExtra(USER_MESSAGE, username);
                        welcomeIntent.putExtra(USER_PASSWORD, password);
                        startActivity(welcomeIntent); // Starts the WelcomeActivity
                    }
                    else{
                        // Toast
                        Toast.makeText(getApplicationContext(), "Invalid Login", Toast.LENGTH_SHORT);
                    }

                }

            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Handler code here:
                // Go to sign up activity page
                Intent signUpIntent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });*/

    }
    public void sendMessage(View view){
        EditText user = findViewById(R.id.usernameField);
        EditText pass = findViewById(R.id.passwordField);
        String username = user.getText().toString();
        String password = pass.getText().toString();

        // Username/password completion validation
        if (password.length() < 1 && username.length() < 1){
            Toast completeToast = Toast.makeText(getApplicationContext(), "Please complete all fields", Toast.LENGTH_LONG);
            completeToast.show();
        }
        else{ // If they complete both fields, validate with dictionary
            if (username.equals(password)){ // If it matches a dictionary entry, temp test right now
                Intent welcomeIntent = new Intent(this, WelcomeActivity.class);
                welcomeIntent.putExtra("USERNAME", username);
                startActivity(welcomeIntent);
            }
            else{
                Toast invalidToast = Toast.makeText(getApplicationContext(), "Invalid login credentials", Toast.LENGTH_LONG);
                invalidToast.show();
                pass.setText(null);
            }

        }

    }

    public void openSignUp(View view){
        Intent signUpIntent = new Intent(this, SignUpActivity.class);
        startActivity(signUpIntent);
    }
}
