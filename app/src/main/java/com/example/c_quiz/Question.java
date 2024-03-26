package com.example.c_quiz;

import java.util.List;
import java.util.Set;

public class Question {
    private String questionText;
    private List<String> options;
    private Set<Integer> correctAnswerIndices;
    private boolean isMultipleChoice;

    public Question(String questionText, List<String> options, Set<Integer> correctAnswerIndices, boolean isMultipleChoice) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndices = correctAnswerIndices;
        this.isMultipleChoice = isMultipleChoice;
    }

    public String getQuestionText() { return questionText; }
    public List<String> getOptions() { return options; }
    public boolean isCorrect(Set<Integer> selectedIndices) { return correctAnswerIndices.equals(selectedIndices); }
    public boolean isMultipleChoice() { return isMultipleChoice; }
}







