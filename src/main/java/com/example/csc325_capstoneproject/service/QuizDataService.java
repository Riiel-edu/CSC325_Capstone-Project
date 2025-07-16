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
     * @since 7/10/25
     * @author Robert Shupe
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
     * Generates a new test with unique questions for a specific subject and grade level using AI.
     * If the AI service fails, it loads a default set of questions from a local file.
     * @param subject The subject of the questions (e.g., "Math", "History", "English", "Science").
     * @param gradeLevel The grade level for the questions (e.g., 1-5).
     * @param questionCount The amount of questions for the test.
     * @return A list of new questions.
     * @since 7/10/25
     * @author Robert Shupe
     */
    public List<Question> generateAITest(String subject, int gradeLevel, int questionCount) {
        try {
            // Attempt to generate questions using the AI service
            System.out.println("Attempting to generate AI test...");
            return aiService.generateQuestions(subject, gradeLevel, questionCount);
        } catch (IOException e) {
            System.err.println("AI service failed: " + e.getMessage());

            // --- ✅ FALLBACK IMPLEMENTATION ---
            // If the AI fails, load questions from the local file instead.
            System.err.println("Falling back to local questions.json file.");
            return loadAllQuestions(); // <-- USAGE IS HERE
        }
    }
}