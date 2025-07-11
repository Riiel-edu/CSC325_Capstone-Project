package com.example.csc325_capstoneproject.service;



import com.example.csc325_capstoneproject.model.Question;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.RequestBody;
import okhttp3.MediaType;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class AIQuestionService {

    // ⚠️ IMPORTANT: Replace with your actual API key
    private static final String API_KEY = "YOUR_API_KEY_HERE"; //<- IMPORTANT: PASTE YOUR KEY HERE
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=" + API_KEY;
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    /**
     * Generates a specified number of questions for a given subject and grade level.
     * @param subject The topic for the questions (e.g., "History", "Math").
     * @param gradeLevel The academic level for the questions (e.g., 1, 2, 3, 4, 5).
     * @param count The number of questions to generate.
     * @return A list of Question objects.
     * @throws IOException If the API call fails.
     */
    public List<Question> generateQuestions(String subject, int gradeLevel, int count) throws IOException {
        // --- DYNAMIC PROMPT ---
        // This prompt now generates questions based on the subject and grade level parameters.
        String prompt = String.format(
                "Generate %d unique, multiple-choice questions about %s appropriate for a grade %d student. " +
                        "Provide your response as a raw JSON array of objects. " +
                        "Each object must have exactly three keys: " +
                        "a 'questionText' (String), " +
                        "an 'options' (an array of 4 Strings), " +
                        "and a 'correctAnswerIndex' (integer). " +
                        "The 'correctAnswerIndex' must be the zero-based index of the correct answer in the 'options' array. " +
                        "Do not include any introductory text, explanations, or markdown formatting like ```json.",
                count, subject, gradeLevel
        );

        String jsonBody = "{\"contents\":[{\"parts\":[{\"text\": \"" + prompt + "\"}]}]}";
        RequestBody body = RequestBody.create(jsonBody, MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response + " - " + response.body().string());
            }

            String responseBody = response.body().string();

            // Navigates the JSON response to extract the text containing the question array
            JsonObject responseObject = gson.fromJson(responseBody, JsonObject.class);
            String questionsJsonText = responseObject.getAsJsonArray("candidates")
                    .get(0).getAsJsonObject()
                    .get("content").getAsJsonObject()
                    .get("parts").getAsJsonArray()
                    .get(0).getAsJsonObject()
                    .get("text").getAsString();

            // Deserializes the extracted JSON text into a list of Question objects
            Type questionListType = new TypeToken<List<Question>>() {}.getType();
            return gson.fromJson(questionsJsonText, questionListType);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}