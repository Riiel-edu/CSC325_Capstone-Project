package com.example.csc325_capstoneproject.model;


import java.util.List;

public class Question {
    private String questionText;
    private List<String> options;
    private int correctAnswerIndex; // Changed from String to int

    // Getters
    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
}