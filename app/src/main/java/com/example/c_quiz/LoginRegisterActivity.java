package com.example.c_quiz;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class LoginRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register_screen);

        findViewById(R.id.loginButton).setOnClickListener(view -> {
            startActivity(new Intent(LoginRegisterActivity.this, LoginActivity.class));
        });

        findViewById(R.id.registerButton).setOnClickListener(view -> {
            startActivity(new Intent(LoginRegisterActivity.this, RegistrationActivity.class));
        });
    }
}

