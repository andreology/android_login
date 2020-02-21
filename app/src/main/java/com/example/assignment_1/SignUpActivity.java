package com.example.assignment_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.text.TextUtils;
import android.util.Log;

import java.io.Serializable;
import java.util.Dictionary;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "Sign Up";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    //------------Sign up logic---------------------
    private void attemptSignUp(String userNameInput, String passwordInput,String passwordInput0,String emailInput,String cellPhoneInput) {
        //---------validate form-------------------
        if(!validateAllInputs(userNameInput, passwordInput, passwordInput0, emailInput, cellPhoneInput)) {
            return;
        }
    }

    //-------------Project validation requirements--------------------------
    private boolean validateAllInputs(String userNameInput, String passwordInput,String passwordInput0,String emailInput,String cellPhoneInput) {
        EditText userName = findViewById(R.id.usernameField);
        EditText userPassword = findViewById(R.id.passwordField);
        EditText userPassword0 = findViewById(R.id.passwordField2);
        EditText userEmail = findViewById(R.id.emailField);
        EditText userCellPhone = findViewById(R.id.phoneField);

        boolean isValid = true;
        //-----------Java util library for regex--------------
        //-----------Email REGEX---------------
        String regexForEmails = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern patternForEmail = Pattern.compile(regexForEmails);
        Matcher matcherForEmail = patternForEmail.matcher(emailInput);
        //-----------cell phone U.S. REGEX---------------
        String regexForCell = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";
        Pattern patternForCell = Pattern.compile(regexForCell);
        Matcher matcherForCell = patternForCell.matcher(cellPhoneInput);
        Log.w(TAG, "Checking regex for email " + matcherForEmail.matches());
        Log.w(TAG, "Checking regex for cell " + matcherForCell.matches());

        //------------Assignment 1 requirements, 2bi. All fields must be filled----------------
        if (TextUtils.isEmpty(userNameInput)){
            userName.setError("All Fields Are Required.");
            isValid = false;
        }else if(TextUtils.isEmpty(passwordInput)){
            userPassword.setError("All Fields Are Required.");
            isValid = false;
        }else if(TextUtils.isEmpty(passwordInput0)){
            userPassword0.setError("All Fields Are Required.");
            isValid = false;
        }else if(TextUtils.isEmpty(emailInput)){
            userEmail.setError("All Fields Are Required.");
            isValid = false;
        }else if(TextUtils.isEmpty(cellPhoneInput)){
            userCellPhone.setError("All Fields Are Required.");
            isValid = false;

            //-----------Assignment 1 requirements, 2biii. Password and retype password must be the same----------------
        }else if(passwordInput.compareTo(passwordInput0) !=0) {
            userPassword.setError("Passwords must match");
            isValid = false;
            //-----------Assignment 1 requirements, 2biv. email must be in correct format----------------
        }else if(!matcherForEmail.matches()) {
            userEmail.setError("Email Must be in correct format.");
            isValid = false;
            //-----------Assignment 1 requirements, 2biv. phone must be in correct format----------------
        }else if(!matcherForCell.matches()) {
            userCellPhone.setError("Cell Must be in correct format.");
            isValid = false;
        }
        return isValid;
    }

    public void returnLogin(View view){
        EditText userName = findViewById(R.id.usernameField);
        EditText userPassword = findViewById(R.id.passwordField);
        EditText userPassword0 = findViewById(R.id.passwordField2);
        EditText userEmail = findViewById(R.id.emailField);
        EditText userCellPhone = findViewById(R.id.phoneField);

        String userNameInput = userName.getText().toString();
        String passwordInput = userPassword.getText().toString();
        String passwordInput0 = userPassword0.getText().toString();
        String emailInput = userEmail.getText().toString();
        String cellPhoneInput = userCellPhone.getText().toString();

        //------------call sign up method--------------
        attemptSignUp(userNameInput, passwordInput, passwordInput0, emailInput, cellPhoneInput);


        EditText user = findViewById(R.id.usernameField);
        EditText pass1 = findViewById(R.id.passwordField);
        EditText pass2 = findViewById(R.id.passwordField2);

        String username = user.getText().toString();
        String password1 = pass1.getText().toString();
        String password2 = pass2.getText().toString();

        // Input validation
        if (password1.equals(password2)){
            // Write new entry to dictionary
            MainActivity.users.put(username, password1);

            Intent loginIntent = new Intent(this, MainActivity.class);
            startActivity(loginIntent);
        }
    }
}