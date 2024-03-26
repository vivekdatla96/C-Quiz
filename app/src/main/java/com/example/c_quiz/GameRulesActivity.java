package com.example.c_quiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class GameRulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_rules_screen);

        TextView gameRulesText = findViewById(R.id.gameRulesText);
        gameRulesText.setText("Choose The Correct answers. Some will have multiple answers, the ones with multiple answers the options will be squraes.");

        Button proceedButton = findViewById(R.id.proceedButton);
        proceedButton.setOnClickListener(view -> {
            // Navigate to ScoreHistoryActivity
            Intent intent = new Intent(GameRulesActivity.this, ScoreHistoryActivity.class);
            startActivity(intent);
            finish();
        });
    }
}






