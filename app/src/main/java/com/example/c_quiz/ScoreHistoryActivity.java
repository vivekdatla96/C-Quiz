package com.example.c_quiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ScoreHistoryActivity extends AppCompatActivity {
    private TextView scoreHistoryText;
    private Button startQuizButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_history);

        scoreHistoryText = findViewById(R.id.scoreHistoryText);
        startQuizButton = findViewById(R.id.startQuizButton); // Make sure you have this button in your layout

        displayScoreHistory();

        startQuizButton.setOnClickListener(view -> {
            // Navigate to MainActivity to start the quiz
            startActivity(new Intent(ScoreHistoryActivity.this, MainActivity.class));
            finish();
        });
    }

    private void displayScoreHistory() {
        SharedPreferences sharedPreferences = getSharedPreferences("CQuizPrefs", MODE_PRIVATE);
        String gameScores = sharedPreferences.getString("gameScores", "No scores yet");
        scoreHistoryText.setText("Score History: \n" + gameScores.replace(",", "\n"));
    }
}


