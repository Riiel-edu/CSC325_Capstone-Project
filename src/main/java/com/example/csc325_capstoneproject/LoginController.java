package com.example.csc325_capstoneproject;

import com.example.csc325_capstoneproject.model.CurrentUser;
import com.example.csc325_capstoneproject.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class LoginController implements Initializable {

    @FXML
    protected Button loginButton;

    @FXML
    protected Button registerButton;

    @FXML
    protected Button closeButton;

    @FXML
    protected TextField emailField;

    @FXML
    protected TextField passwordField;

    protected static boolean loginStatus = false;

    private final String apiKey = "AIzaSyC9E1Pb24XtJUCOwEuae9mqRIWtBjXpfxE";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Live validation with regex
        Pattern emailPattern = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
        Pattern passwordPattern = Pattern.compile("\\w{2,25}");

        emailField.textProperty().addListener((obs, oldText, newText) -> {
            boolean valid = emailPattern.matcher(newText).matches();
            emailField.setStyle(valid ? "-fx-border-color: Lime;" : "-fx-border-color: red;");
        });

        passwordField.textProperty().addListener((obs, oldText, newText) -> {
            boolean valid = passwordPattern.matcher(newText).matches();
            passwordField.setStyle(valid ? "-fx-border-color: Lime;" : "-fx-border-color: red;");
        });
    }

    /** Login button click handler (no-arg for FXML) **/
    @FXML
    protected void login() {
        login(null);
    }

    /** Actual login with optional ActionEvent **/
    protected void login(Void unused) {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            System.out.println("Error: All fields are required.");
            if (email.isEmpty()) emailField.setStyle("-fx-border-color: red;");
            if (password.isEmpty()) passwordField.setStyle("-fx-border-color: red;");
            return;
        }

        boolean success = firebaseLogin(email, password);

        if (success) {
            System.out.println("✅ Login successful!");
            loginStatus = true;

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
        } else {
            System.out.println("❌ Login failed. Incorrect email or password.");
        }
    }

    /** REST call to Firebase Auth **/
    private boolean firebaseLogin(String email, String password) {
        try {
            URL url = new URL("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + apiKey);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

            // ✅ No URLEncoder here. Raw JSON with email and password.
            String payload = String.format(
                    "{\"email\":\"%s\",\"password\":\"%s\",\"returnSecureToken\":true}",
                    email,
                    password
            );

            try (OutputStream os = conn.getOutputStream()) {
                os.write(payload.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            int responseCode = conn.getResponseCode();

            if (responseCode == 200) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }

                    JsonObject json = JsonParser.parseString(response.toString()).getAsJsonObject();
                    String idToken = json.get("idToken").getAsString();
                    String localId = json.get("localId").getAsString();

                    System.out.println("✅ Firebase says login is successful. ID Token: " + idToken);

                    User user = new User(email, email, localId);
                    CurrentUser.setCurrentUser(user);

                    return true;
                }
            } else {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                    StringBuilder errorResponse = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        errorResponse.append(line.trim());
                    }
                    System.out.println("❌ Firebase login error: " + errorResponse);
                }
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Register button click handler (for FXML) **/
    @FXML
    protected void register() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StudyApplication.class.getResource("register-view.fxml"));
            Stage stage = (Stage) registerButton.getScene().getWindow();

            Stage registerStage = new Stage();
            AnchorPane registerRoot = new AnchorPane();
            registerRoot.getChildren().add(fxmlLoader.load());

            Scene scene = new Scene(registerRoot, 650, 380);
            registerStage.setScene(scene);
            registerStage.setResizable(false);
            registerStage.getIcons().add(new Image(Objects.requireNonNull(StudyApplication.class.getResourceAsStream("/com/example/csc325_capstoneproject/images/learningtrack.png"))));
            registerStage.initStyle(StageStyle.UNDECORATED);
            stage.close();
            registerStage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /** Close button click handler (for FXML) **/
    @FXML
    protected void close() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Static method that updates the user's login status
     */
    public static void updateLoginStatus(boolean status) {
        loginStatus = status;
    }

    /**
     * Static method that returns the user's login status
     */
    public static boolean getLoginStatus() {
        return loginStatus;
    }
}
