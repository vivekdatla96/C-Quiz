package com.example.c_quiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private TextView resultText;
    private Button logoutButton, restartQuizButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_result_screen);

        resultText = findViewById(R.id.resultText);
        logoutButton = findViewById(R.id.logoutButton);
        restartQuizButton = findViewById(R.id.restartQuizButton);

        // Display the quiz score
        int score = getIntent().getIntExtra("SCORE", 0);
        resultText.setText("Your score: " + score);

        logoutButton.setOnClickListener(v -> {
            // Redirect to LoginRegisterActivity for logout
            Intent intent = new Intent(ResultActivity.this, LoginRegisterActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear the activity stack
            startActivity(intent);
        });

        restartQuizButton.setOnClickListener(v -> {
            // Redirect to MainActivity or GameRulesActivity to start the quiz again
            Intent intent = new Intent(ResultActivity.this, GameRulesActivity.class); // or MainActivity
            startActivity(intent);
            finish(); // Close ResultActivity
        });
    }
}


