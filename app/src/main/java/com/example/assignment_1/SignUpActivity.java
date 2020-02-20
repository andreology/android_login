package com.example.assignment_1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.text.TextUtils;
import android.util.Log;



import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "Sign Up";
    private Button signUpButton;
    private EditText userName;
    private EditText userPassword;
    private EditText userPassword0;
    private EditText userEmail;
    private EditText userCellPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //assigning elements to variables
        signUpButton = findViewById(R.id.signUpButton);
        userName = findViewById(R.id.usernameField);
        userPassword = findViewById(R.id.passwordField);
        userPassword0 = findViewById(R.id.passwordField2);
        userEmail = findViewById(R.id.emailField);
        userCellPhone = findViewById(R.id.phoneField);

        //----------Setting up listener for sign up button, collect input----------------
        /*signUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String userNameInput = userName.getText().toString();
                String passwordInput = userPassword.getText().toString();
                String passwordInput0 = userPassword0.getText().toString();
                String emailInput = userEmail.getText().toString();
                String cellPhoneInput = userCellPhone.getText().toString();

                //------------call sign up method--------------
                attemptSignUp(userNameInput, passwordInput, passwordInput0, emailInput, cellPhoneInput);
            }
        });*/
    }

    //------------Sign up logic---------------------
    private void attemptSignUp(String userNameInput, String passwordInput,String passwordInput0,String emailInput,String cellPhoneInput) {
        //---------validate form-------------------
        if(!validateAllInputs(userNameInput, passwordInput, passwordInput0, emailInput, cellPhoneInput)) { return; }
    }

    //-------------Project validation requirements--------------------------
    private boolean validateAllInputs(String userNameInput, String passwordInput,String passwordInput0,String emailInput,String cellPhoneInput) {
        boolean isValid = true;

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
        }

        //-----------

<<<<<<< HEAD
        return isValid;
=======


        return isValid;
    }

    public void signUp(View v) {
        String userNameInput = userName.getText().toString();
        String passwordInput = userPassword.getText().toString();
        String passwordInput0 = userPassword0.getText().toString();
        String emailInput = userEmail.getText().toString();
        String cellPhoneInput = userCellPhone.getText().toString();

        //------------call sign up method--------------
        attemptSignUp(userNameInput, passwordInput, passwordInput0, emailInput, cellPhoneInput);
>>>>>>> 5ecf3e8014024378b8e5ee51ceecbabe3c746221
    }
}