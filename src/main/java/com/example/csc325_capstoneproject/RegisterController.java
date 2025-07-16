package com.example.csc325_capstoneproject;

import com.example.csc325_capstoneproject.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.ListUsersPage;
import com.google.firebase.auth.UserRecord;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.LinkedList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterController implements Initializable {

    @FXML protected TextField usernameField;
    @FXML protected Label usernameError;

    @FXML protected TextField emailField;
    @FXML protected Label emailError;

    @FXML protected TextField passwordField;
    @FXML protected Label passwordError;

    @FXML protected TextField firstNameField;
    @FXML protected Label fNameError;

    @FXML protected TextField lastNameField;
    @FXML protected Label lNameError;

    @FXML protected Button closeButton;
    @FXML protected Button loginButton;

    protected LinkedList<User> users = new LinkedList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ListUsersPage page = FirebaseAuth.getInstance().listUsers(null);
            for (UserRecord user : page.iterateAll()) {
                User newUser = new User(user.getDisplayName(), user.getEmail(), user.getUid());
                users.add(newUser);
            }
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e);
        }

        /*--------------------------------------------Regex Patterns--------------------------------------------------*/
        Pattern usernamePattern = Pattern.compile("[\\w|-]{2,25}");
        Pattern emailPattern = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
        Pattern passwordPattern = Pattern.compile("\\w{2,25}");
        Pattern namePattern = Pattern.compile("\\w{2,25}+");

        /*------------------------------------------Live Updates to UI------------------------------------------------*/
        usernameField.textProperty().addListener((obs, oldText, newText) -> {
            boolean valid = usernamePattern.matcher(newText).matches();
            usernameField.setStyle(valid ? "-fx-border-color: Lime;" : "-fx-border-color: red;");
        });
        usernameField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                boolean valid = usernamePattern.matcher(usernameField.getText()).matches();
                usernameError.setText(valid ? "" : "2–25 characters, only letters, digits, or '-' allowed");
                usernameField.setStyle(valid ? "-fx-border-color: Lime;" : "-fx-border-color: red;");
            }
        });

        emailField.textProperty().addListener((obs, oldText, newText) -> {
            boolean valid = emailPattern.matcher(newText).matches();
            emailField.setStyle(valid ? "-fx-border-color: Lime;" : "-fx-border-color: red;");
        });
        emailField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                boolean valid = emailPattern.matcher(emailField.getText()).matches();
                emailError.setText(valid ? "" : "Must be a valid email address format");
                emailField.setStyle(valid ? "-fx-border-color: Lime;" : "-fx-border-color: red;");
            }
        });

        passwordField.textProperty().addListener((obs, oldText, newText) -> {
            boolean valid = passwordPattern.matcher(newText).matches();
            passwordField.setStyle(valid ? "-fx-border-color: Lime;" : "-fx-border-color: red;");
        });
        passwordField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                boolean valid = passwordPattern.matcher(passwordField.getText()).matches();
                passwordError.setText(valid ? "" : "2–25 characters, letters or digits only");
                passwordField.setStyle(valid ? "-fx-border-color: Lime;" : "-fx-border-color: red;");
            }
        });

        firstNameField.textProperty().addListener((obs, oldText, newText) -> {
            boolean valid = namePattern.matcher(newText).matches();
            firstNameField.setStyle(valid ? "-fx-border-color: Lime;" : "-fx-border-color: red;");
        });
        firstNameField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                boolean valid = namePattern.matcher(firstNameField.getText()).matches();
                fNameError.setText(valid ? "" : "2–25 letters only");
                firstNameField.setStyle(valid ? "-fx-border-color: Lime;" : "-fx-border-color: red;");
            }
        });

        lastNameField.textProperty().addListener((obs, oldText, newText) -> {
            boolean valid = namePattern.matcher(newText).matches();
            lastNameField.setStyle(valid ? "-fx-border-color: Lime;" : "-fx-border-color: red;");
        });
        lastNameField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                boolean valid = namePattern.matcher(lastNameField.getText()).matches();
                lNameError.setText(valid ? "" : "2–25 letters only");
                lastNameField.setStyle(valid ? "-fx-border-color: Lime;" : "-fx-border-color: red;");
            }
        });
    }

    /**
     * Registers a new User into the program.
     */
    @FXML
    protected void register() {
        boolean canCreate = true;

        String username = usernameField.getText();
        String password = passwordField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();

        Pattern usernamePattern = Pattern.compile("[\\w|-]{2,25}");
        Pattern emailPattern = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
        Pattern passwordPattern = Pattern.compile("\\w{2,25}");
        Pattern namePattern = Pattern.compile("\\w{2,25}+");

        Matcher userNameMatcher = usernamePattern.matcher(username);
        Matcher passwordMatcher = passwordPattern.matcher(password);
        Matcher firstNameMatcher = namePattern.matcher(firstName);
        Matcher lastNameMatcher = namePattern.matcher(lastName);
        Matcher emailMatcher = emailPattern.matcher(email);

        if (username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
            System.out.println("❌ Error: One or more fields are empty.");
            canCreate = false;

            if (username.isEmpty()) usernameField.setStyle("-fx-border-color: red;");
            if (password.isEmpty()) passwordField.setStyle("-fx-border-color: red;");
            if (firstName.isEmpty()) firstNameField.setStyle("-fx-border-color: red;");
            if (lastName.isEmpty()) lastNameField.setStyle("-fx-border-color: red;");
            if (email.isEmpty()) emailField.setStyle("-fx-border-color: red;");
        }

        if (!userNameMatcher.matches()) {
            System.out.println("❌ Error: Invalid username format.");
            usernameError.setText("2–25 characters, only letters, digits, or '-' allowed");
            usernameField.setStyle("-fx-border-color: red;");
            canCreate = false;
        }

        if (!passwordMatcher.matches()) {
            System.out.println("❌ Error: Invalid password format.");
            passwordError.setText("2–25 characters, letters or digits only");
            passwordField.setStyle("-fx-border-color: red;");
            canCreate = false;
        }

        if (!firstNameMatcher.matches()) {
            System.out.println("❌ Error: Invalid first name format.");
            fNameError.setText("2–25 letters only");
            firstNameField.setStyle("-fx-border-color: red;");
            canCreate = false;
        }

        if (!lastNameMatcher.matches()) {
            System.out.println("❌ Error: Invalid last name format.");
            lNameError.setText("2–25 letters only");
            lastNameField.setStyle("-fx-border-color: red;");
            canCreate = false;
        }

        if (!emailMatcher.matches()) {
            System.out.println("❌ Error: Invalid email format.");
            emailError.setText("Must be a valid email address format");
            emailField.setStyle("-fx-border-color: red;");
            canCreate = false;
        }

        for (User user : users) {
            if (user.getEmail().equals(email) || user.getUsername().equals(username)) {
                System.out.println("❌ Error: Username or email already exists.");
                canCreate = false;
            }
        }

        if (canCreate) {
            try {
                UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                        .setEmail(email)
                        .setEmailVerified(false)
                        .setPassword(password)
                        .setDisplayName(username)
                        .setDisabled(false);

                UserRecord userRecord = StudyApplication.fauth.createUser(request);
                System.out.println("✅ Firebase user created: " + userRecord.getUid());

            } catch (FirebaseAuthException ex) {
                System.out.println("❌ FirebaseAuthException during user creation: " + ex.getMessage());
                ex.printStackTrace();
                canCreate = false;
            }
        }

        if (canCreate) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(StudyApplication.class.getResource("login-view.fxml"));
                Stage stage = (Stage) loginButton.getScene().getWindow();

                Stage loginStage = new Stage();
                AnchorPane loginRoot = new AnchorPane();
                loginRoot.getChildren().add(fxmlLoader.load());

                Scene scene = new Scene(loginRoot, 650, 380);
                loginStage.setScene(scene);
                loginStage.setResizable(false);
                loginStage.initStyle(StageStyle.UNDECORATED);
                stage.close();
                loginStage.show();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    protected void close() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void login() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StudyApplication.class.getResource("login-view.fxml"));
            Stage stage = (Stage) loginButton.getScene().getWindow();

            Stage loginStage = new Stage();
            AnchorPane loginRoot = new AnchorPane();
            loginRoot.getChildren().add(fxmlLoader.load());

            Scene scene = new Scene(loginRoot, 650, 380);
            loginStage.setScene(scene);
            loginStage.setResizable(false);
            loginStage.getIcons().add(new Image(Objects.requireNonNull(StudyApplication.class.getResourceAsStream("/com/example/csc325_capstoneproject/images/learningtrack.png"))));
            loginStage.initStyle(StageStyle.UNDECORATED);
            stage.close();
            loginStage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
