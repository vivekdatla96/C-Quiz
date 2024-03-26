package com.example.c_quiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Collections;


public class MainActivity extends AppCompatActivity {

    private TextView questionTextView;
    private LinearLayout optionsLayout;
    private Button nextButton;
    private QuizManager quizManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_question_screen);

        questionTextView = findViewById(R.id.questionTextView);
        optionsLayout = findViewById(R.id.optionsLayout);
        nextButton = findViewById(R.id.nextButton);

        quizManager = new QuizManager();
        initializeQuestions();
        displayQuestion();

        nextButton.setOnClickListener(view -> {
            Set<Integer> selectedIndices = new HashSet<>();
            boolean isMultipleChoice = quizManager.getCurrentQuestion().isMultipleChoice();
            if (isMultipleChoice) {
                for (int i = 0; i < optionsLayout.getChildCount(); i++) {
                    View child = optionsLayout.getChildAt(i);
                    if (child instanceof CheckBox && ((CheckBox) child).isChecked()) {
                        selectedIndices.add(i);
                    }
                }
            } else {
                RadioGroup radioGroup = (RadioGroup) optionsLayout.getChildAt(0);
                int radioButtonID = radioGroup.getCheckedRadioButtonId();
                View radioButton = radioGroup.findViewById(radioButtonID);
                int idx = radioGroup.indexOfChild(radioButton);
                selectedIndices.add(idx);
            }

            if (selectedIndices.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please select an option", Toast.LENGTH_SHORT).show();
                return;
            }

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Confirm Answer")
                    .setMessage("Are you sure you want to submit this answer?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        if (quizManager.submitAnswer(selectedIndices)) {
                            Toast.makeText(MainActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Incorrect!", Toast.LENGTH_SHORT).show();
                        }

                        if (quizManager.nextQuestion() != null) {
                            displayQuestion();
                        } else {
                            saveScore(quizManager.getScore()); // Save the score when the quiz is completed
                            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                            intent.putExtra("SCORE", quizManager.getScore());
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    private void initializeQuestions() {

        List<Question> questions = new ArrayList<>();

        questions.add(new Question(
                "Who wrote 'Romeo and Juliet'?",
                Arrays.asList("William Shakespeare", "Jane Austen", "Charles Dickens", "Mark Twain"),
                new HashSet<>(Collections.singletonList(0)),
                false
        ));

        questions.add(new Question(
                "What element does 'Na' represent on the periodic table?",
                Arrays.asList("Nitrogen", "Nickel", "Sodium", "Neon"),
                new HashSet<>(Collections.singletonList(2)),
                false
        ));

        // Modified question with multiple correct answers
        questions.add(new Question(
                "Which countries have the Great Barrier Reef in their territorial waters?",
                Arrays.asList("Australia", "Papua New Guinea", "Solomon Islands", "Indonesia"),
                new HashSet<>(Arrays.asList(0, 1, 2)), // Assuming hypothetical scenario for multiple correct answers
                true
        ));

        questions.add(new Question(
                "Who is known as the Father of Computer Science?",
                Arrays.asList("Charles Babbage", "Alan Turing", "John von Neumann", "Ada Lovelace"),
                new HashSet<>(Collections.singletonList(1)),
                false
        ));

        // Modified question with multiple correct answers
        questions.add(new Question(
                "Which of these animals are mammals?",
                Arrays.asList("Dolphin", "Shark", "Whale", "Bat"),
                new HashSet<>(Arrays.asList(0, 2, 3)), // Sharks are not mammals
                true
        ));

        questions.add(new Question(
                "What is the main ingredient in guacamole?",
                Arrays.asList("Tomato", "Avocado", "Pepper", "Onion"),
                new HashSet<>(Collections.singletonList(1)),
                false
        ));

        questions.add(new Question(
                "Which planet is known as the Red Planet?",
                Arrays.asList("Mars", "Jupiter", "Saturn", "Venus"),
                new HashSet<>(Collections.singletonList(0)),
                false
        ));

        questions.add(new Question(
                "What is the square root of 64?",
                Arrays.asList("6", "8", "10", "12"),
                new HashSet<>(Collections.singletonList(1)),
                false
        ));

        questions.add(new Question(
                "Which country hosted the 2016 Summer Olympics?",
                Arrays.asList("China", "Brazil", "Russia", "Japan"),
                new HashSet<>(Collections.singletonList(1)),
                false
        ));

        questions.add(new Question(
                "What is the currency of Japan?",
                Arrays.asList("Yen", "Won", "Dollar", "Euro"),
                new HashSet<>(Collections.singletonList(0)),
                false
        ));

        // Shuffle the questions for randomization (optional)
        Collections.shuffle(questions);

        // Add all questions to the quiz manager
        for (Question question : questions) {
            quizManager.addQuestion(question);
        }
    }



    private void displayQuestion() {
        Question currentQuestion = quizManager.getCurrentQuestion();
        if (currentQuestion != null) {
            questionTextView.setText(currentQuestion.getQuestionText());
            optionsLayout.removeAllViews(); // Clear previous question options

            if (currentQuestion.isMultipleChoice()) {
                // For multiple-choice questions, add CheckBoxes directly to the LinearLayout
                for (String option : currentQuestion.getOptions()) {
                    CheckBox checkBox = new CheckBox(this);
                    checkBox.setText(option);
                    optionsLayout.addView(checkBox);
                }
            } else {
                // For single-choice questions, use a RadioGroup to ensure only one selection
                RadioGroup radioGroup = new RadioGroup(this);
                radioGroup.setOrientation(LinearLayout.VERTICAL);

                for (String option : currentQuestion.getOptions()) {
                    RadioButton radioButton = new RadioButton(this);
                    radioButton.setText(option);
                    radioGroup.addView(radioButton);
                }

                // Add the RadioGroup to the LinearLayout
                optionsLayout.addView(radioGroup);
            }
        }
    }


    private void saveScore(int score) {
        SharedPreferences sharedPreferences = getSharedPreferences("CQuizPrefs", MODE_PRIVATE);
        String existingScores = sharedPreferences.getString("gameScores", "");
        existingScores += existingScores.isEmpty() ? String.valueOf(score) : "," + score; // Append new score
        sharedPreferences.edit().putString("gameScores", existingScores).apply();
    }
}











