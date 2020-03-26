package com.example.assignment_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    // Dictionary of users here:
    public static HashMap<String, String> users = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        users.put("user", "pass");
        System.out.println(users.size());

    }
    public void logIn(View view){
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

            if (users.containsKey(username) && users.get(username).equals(password)){ // If it matches a dictionary entry, temp test right now
                Intent carListIntent = new Intent(this, carListActivity.class);
                carListIntent.putExtra("USERNAME", username);
                startActivity(carListIntent);
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
        signUpIntent.putExtra("users", this.users);
        startActivity(signUpIntent);
    }

}
