package com.example.csc325_capstoneproject.service;



import com.example.csc325_capstoneproject.model.Question;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class QuizDataService {

    // Path to your local questions file in the resources folder
    private static final String QUESTIONS_FILE_PATH = "/com/example/examsjavacapstoneproj/questions.json";
    private final AIQuestionService aiService = new AIQuestionService();

    /**
     * Loads all questions from the local questions.json file.
     * @return A list of all questions from the file.
     */
    public List<Question> loadAllQuestions() {
        try {
            // Use getResourceAsStream to read a file from the resources folder
            InputStream inputStream = QuizDataService.class.getResourceAsStream(QUESTIONS_FILE_PATH);
            if (inputStream == null) {
                throw new RuntimeException("Cannot find resource file: " + QUESTIONS_FILE_PATH);
            }
            InputStreamReader reader = new InputStreamReader(inputStream);

            // Define the type for Gson to parse the JSON array into a List<Question>
            Type questionListType = new TypeToken<List<Question>>() {}.getType();

            // Parse the JSON and return the list
            return new Gson().fromJson(reader, questionListType);

        } catch (Exception e) {
            System.err.println("Error loading questions from JSON file.");
            e.printStackTrace();
            return Collections.emptyList(); // Return an empty list on error
        }
    }

    /**
     * Generates a new test with 15 unique questions for a specific subject and grade level using AI.
     * @param subject The subject of the questions (e.g., "Math", "History", "English").
     * @param gradeLevel The grade level for the questions (e.g., 1-5).
     * @return A list containing 15 new questions.
     */
    public List<Question> generateAITest(String subject, int gradeLevel) {
        try {
            // Call the AI service to generate 15 questions for the given subject and grade
            return aiService.generateQuestions(subject, gradeLevel, 25);
        } catch (IOException e) {
            System.err.println("Failed to generate questions using AI: " + e.getMessage());
            e.printStackTrace();
            // You could have a fallback here to load from a local file if the API fails
            return null;
        }
    }
}