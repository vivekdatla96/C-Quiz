package com.example.c_quiz;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class QuizManager {
    private List<Question> questions;
    private int currentQuestionIndex;
    private int score;

    public QuizManager() {
        questions = new ArrayList<>();
        currentQuestionIndex = 0;
        score = 0;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public Question getCurrentQuestion() {
        return questions.get(currentQuestionIndex);
    }

    public Question nextQuestion() {
        if (currentQuestionIndex < questions.size() - 1) {
            currentQuestionIndex++;
            return getCurrentQuestion();
        }
        return null;
    }

    public boolean submitAnswer(Set<Integer> selectedIndices) {
        Question currentQuestion = getCurrentQuestion();
        if (currentQuestion.isCorrect(selectedIndices)) {
            score++;
            return true;
        }
        return false;
    }

    public int getScore() {
        return score;
    }
}



