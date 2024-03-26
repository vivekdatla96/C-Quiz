package com.example.c_quiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        // Initialize EditTexts
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);

        findViewById(R.id.loginButton).setOnClickListener(view -> {
            login();
        });
    }

    private void login() {
        String inputEmail = emailEditText.getText().toString();
        String inputPassword = passwordEditText.getText().toString();

        // Retrieve stored credentials (using SharedPreferences for simplicity)
        SharedPreferences sharedPreferences = getSharedPreferences("CQuizPrefs", MODE_PRIVATE);
        String registeredEmail = sharedPreferences.getString("email", "");
        String registeredPassword = sharedPreferences.getString("password", "");

        // Validate input against stored credentials
        if (inputEmail.equals(registeredEmail) && inputPassword.equals(registeredPassword)) {
            // Login success
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();

            // Navigate to MainActivity
            Intent intent = new Intent(LoginActivity.this, GameRulesActivity.class);
            startActivity(intent);
            finish(); // Close the LoginActivity
        } else {
            // Login failure
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }
}



