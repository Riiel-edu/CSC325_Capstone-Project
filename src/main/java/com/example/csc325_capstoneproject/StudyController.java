package com.example.csc325_capstoneproject;

import com.example.csc325_capstoneproject.model.CurrentUser;
import com.example.csc325_capstoneproject.model.Subject;
import com.example.csc325_capstoneproject.model.Test;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

/**
 * Controller class which controls the actions for the landing page of the application.
 * @since 6/14/2025
 * @author Nathaniel Rivera
 */
public class StudyController implements Initializable {

    @FXML
    protected ImageView pfp;

    @FXML
    protected Button logOutButton;

    @FXML
    protected Label past10ExamsLabel;

    @FXML
    protected Label usernameLabel;

    @FXML
    protected Label averageGradeLabel;

    @FXML
    protected Label percentLabel;

    @FXML
    protected Button mathButton;

    @FXML
    protected Button englishButton;

    @FXML
    protected Button historyButton;

    @FXML
    protected Button scienceButton;

    @FXML
    protected Button praticeButton;

    @FXML
    protected Button testButton;

    @FXML
    protected TableView<Test> tv;

    @FXML
    protected TableColumn<Test, String> tv_subject, tv_date;

    @FXML
    protected TableColumn<Test, Integer> tv_score, tv_count, tv_grade;

    @FXML
    protected ComboBox<Integer> gradeLevelBox;

    @FXML
    protected ComboBox<Integer> questionNumberBox;

    @FXML
    protected ImageView percentageWheel;

    public static Subject currentSubject;

    public static int currentGradeLevel;

    public static int questionCount;

    public static boolean isTimed;

    protected ObservableList<Test> math_tests = FXCollections.observableList(new LinkedList<>());

    protected ObservableList<Test> english_tests = FXCollections.observableList(new LinkedList<>());

    protected ObservableList<Test> history_tests = FXCollections.observableList(new LinkedList<>());

    protected ObservableList<Test> science_tests = FXCollections.observableList(new LinkedList<>());

    protected int math_average;

    protected int english_average;

    protected int history_average;

    protected int science_average;

    /**
     * Initializes the TableView and Percentage wheel on the main page. The default is math.
     * @param url URL
     * @param resourceBundle ResourceBundle
     * @since 6/30/2025
     * @author Nathaniel Rivera
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        usernameLabel.setText(CurrentUser.getCurrentUsername());

        gradeLevelBox.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));
        questionNumberBox.setItems(FXCollections.observableArrayList(10, 15, 20));

        currentSubject = Subject.MATH;
        currentGradeLevel = 1;
        questionCount = 10;

        retrieveMathTests();
        retrieveEnglishTests();
        retrieveHistoryTests();
        retrieveScienceTests();

        tv_grade.setCellValueFactory(new PropertyValueFactory<>("gradeLevel"));
        tv_subject.setCellValueFactory(new PropertyValueFactory<>("Subject"));
        tv_date.setCellValueFactory(new PropertyValueFactory<>("DateTaken"));
        tv_score.setCellValueFactory(new PropertyValueFactory<>("Score"));
        tv_count.setCellValueFactory(new PropertyValueFactory<>("questionCount"));

        math_average = 0;
        english_average = 0;
        history_average = 0;
        science_average = 0;

        averageUpdated();

        tv.setItems(math_tests);

        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future = StudyApplication.fstore.collection("PFPs").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents;
        try {
            documents = future.get().getDocuments();

            if (documents.size() > 0) {

                for (QueryDocumentSnapshot document : documents) {

                    if(document.getData().get("User").equals(CurrentUser.getCurrentUID())) {
                        CurrentUser.setPFP(document.getData().get("PFP").toString());
                        setPFP();
                    }
                }

            } else {
                System.out.println("No data");
            }

        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Switches the page into math mode changing the stylesheets and the average and the list of the past tests.
     * @since 6/30/2025
     * @author Nathaniel Rivera
     */
    @FXML
    protected void mathMode() {
        tv.setItems(math_tests);
        currentSubject = Subject.MATH;

        averageGradeLabel.setText("Your average grade on the past 10 math tests");
        past10ExamsLabel.setText("Past 10 Math Exams");
        percentLabel.setText(math_average + "%");

        try {

            Scene scene = mathButton.getScene();
            Stage stage = (Stage) scene.getWindow();
            stage.getScene().getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("math-theme.css").toExternalForm());
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

        averageUpdated();

    }

    /**
     * Switches the page into english mode changing the stylesheets and the average and the list of the past tests.
     * @since 6/30/2025
     * @author Nathaniel Rivera
     */
    @FXML
    protected void englishMode() {
        tv.setItems(english_tests);
        currentSubject = Subject.ENGLISH;

        averageGradeLabel.setText("Your average grade on the past 10 english tests");
        past10ExamsLabel.setText("Past 10 English Exams");
        percentLabel.setText(english_average + "%");

        try {

            Scene scene = englishButton.getScene();
            Stage stage = (Stage) scene.getWindow();
            stage.getScene().getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("english-theme.css").toExternalForm());
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

        averageUpdated();

    }

    /**
     * Switches the page into history mode changing the stylesheets and the average and the list of the past tests.
     * @since 7/01/2025
     * @author Nathaniel Rivera
     */
    @FXML
    protected void historyMode() {
        tv.setItems(history_tests);
        currentSubject = Subject.HISTORY;

        averageGradeLabel.setText("Your average grade on the past 10 history tests");
        past10ExamsLabel.setText("Past 10 History Exams");
        percentLabel.setText(history_average + "%");

        try {
            Scene scene = historyButton.getScene();
            Stage stage = (Stage) scene.getWindow();
            stage.getScene().getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("history-theme.css").toExternalForm());
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

        averageUpdated();

    }

    /**
     * Switches the page into science mode changing the stylesheets and the average and the list of the past tests.
     * @since 7/01/2025
     * @author Nathaniel Rivera
     */
    @FXML
    protected void scienceMode() {
        tv.setItems(science_tests);
        currentSubject = Subject.SCIENCE;

        averageGradeLabel.setText("Your average grade on the past 10 science tests");
        past10ExamsLabel.setText("Past 10 Science Exams");
        percentLabel.setText(science_average + "%");

        try {
            Scene scene = scienceButton.getScene();
            Stage stage = (Stage) scene.getWindow();
            stage.getScene().getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("science-theme.css").toExternalForm());
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

        averageUpdated();

    }

    /**
     * Allows the user to change their profile picture.
     * @since 6/19/2025
     * @author Nathaniel Rivera
     */
    @FXML
    protected void changePFP() {

        FXMLLoader fxmlLoader = new FXMLLoader(StudyController.class.getResource("picture-view.fxml"));

        try {
            Stage pfpStage = new Stage();
            AnchorPane pfpRoot = new AnchorPane();
            pfpRoot.getChildren().add(fxmlLoader.load());

            Scene scene = new Scene(pfpRoot, 500, 300);
            //scene.getStylesheets().add(Objects.requireNonNull(StudyApplication.class.getResource("math-theme.css")).toExternalForm());
            pfpStage.setScene(scene);
            pfpStage.setResizable(false);
            pfpStage.initStyle(StageStyle.TRANSPARENT);
            //landingStage.getIcons().add(new Image(Objects.requireNonNull(StudyApplication.class.getResourceAsStream())));

            pfpStage.show();

        } catch(Exception _) { }

    }

    /**
     * Method to set the variables for the test on the TestController screen for currentSubject
     * @since 7/2/2025
     * @author Nathaniel Rivera
     */
    public static Subject getCurrentSubject() {
        return currentSubject;
    }

    /**
     * Method to set the variables for the test on the TestController screen for currentGradeLevel
     * @since 7/2/2025
     * @author Nathaniel Rivera
     */
    public static int getCurrentGradeLevel() {
        return currentGradeLevel;
    }

    /**
     * Method to set the variables for the test on the TestController screen for questionCount
     * @since 7/2/2025
     * @author Nathaniel Rivera
     */
    public static int getQuestionCount() {
        return questionCount;
    }

    /**
     * Method to set the variables for the test on the TestController screen for isTimed.
     * @since 7/2/2025
     * @author Nathaniel Rivera
     */
    public static boolean getTimed() {
        return isTimed;
    }

    /**
     * Swaps the screen to  a practice test without a timer
     * @since 7/2/2025
     * @author Nathaniel Rivera
     */
    @FXML
    protected void practiceTest() {

        isTimed = false;

        FXMLLoader fxmlLoader = new FXMLLoader(StudyApplication.class.getResource("test-view.fxml"));

        Stage stage = (Stage) testButton.getScene().getWindow();

        try {
            Stage testStage = new Stage();
            AnchorPane testRoot = new AnchorPane();
            testRoot.getChildren().add(fxmlLoader.load());

            Scene scene = new Scene(testRoot, 1200, 700);

            switch(currentSubject) {
                case MATH -> scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("math-test.css")).toExternalForm());
                case ENGLISH -> scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("english-test.css")).toExternalForm());
                case HISTORY -> scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("history-test.css")).toExternalForm());
                case SCIENCE -> scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("science-test.css")).toExternalForm());
            }

            testStage.setScene(scene);
            testStage.setResizable(false);
            //testStage.getIcons().add(new Image(Objects.requireNonNull(StudyApplication.class.getResourceAsStream())));
            stage.close();
            testStage.show();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Swaps the screen to a test with a timer
     * @since 7/2/2025
     * @author Nathaniel Rivera
     */
    @FXML
    public void test() {

        isTimed = true;

        FXMLLoader fxmlLoader = new FXMLLoader(StudyApplication.class.getResource("test-view.fxml"));

        Stage stage = (Stage) testButton.getScene().getWindow();

        try {
            Stage testStage = new Stage();
            AnchorPane testRoot = new AnchorPane();
            testRoot.getChildren().add(fxmlLoader.load());

            Scene scene = new Scene(testRoot, 1200, 700);
            switch(currentSubject) {
                case MATH -> scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("math-test.css")).toExternalForm());
                case ENGLISH -> scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("english-test.css")).toExternalForm());
                case HISTORY -> scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("history-test.css")).toExternalForm());
                case SCIENCE -> scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("science-test.css")).toExternalForm());
            }
            //testStage.getStylesheets().add(Objects.requireNonNull(getClass().getResource("splashscreen.css")).toExternalForm());
            testStage.setScene(scene);
            testStage.setResizable(false);
            //testStage.getIcons().add(new Image(Objects.requireNonNull(StudyApplication.class.getResourceAsStream())));
            stage.close();
            testStage.show();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Updates the grade level of the User
     * @since 7/2/2025
     * @author Nathaniel Rivera
     */
    @FXML
    protected void gradeLevelUpdate() {
        currentGradeLevel = gradeLevelBox.getValue();
    }

    /**
     * Updates the question count for the test
     * @since 7/2/2025
     * @author Nathaniel Rivera
     */
    @FXML
    protected void questionCountUpdate() {
        questionCount = questionNumberBox.getValue();
    }

    /**
     * Updates the average wheel and text prompt with the correct color and percentage filled
     * @since 7/5/2025
     * @author Nathaniel Rivera
     */
    @FXML
    protected void averageUpdated() {
        StringBuilder sb = new StringBuilder();

        sb.append("/com/example/csc325_capstoneproject/images/pinwheels/wheel_");

        switch(currentSubject) {
            case MATH -> sb.append(math_average).append(".png");
            case ENGLISH -> sb.append(english_average).append(".png");
            case HISTORY -> sb.append(history_average).append(".png");
            case SCIENCE -> sb.append(science_average).append(".png");
        }

        percentageWheel.setImage(new Image(Objects.requireNonNull(StudyController.class.getResourceAsStream(sb.toString()))));
    }

    /**
     * Gets all the math tests and places them in the List
     * @since 7/7/2025
     * @author Nathaniel Rivera
     */
    protected void retrieveMathTests() {

        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future = StudyApplication.fstore.collection("Tests2").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents;

        try {

            documents = future.get().getDocuments();

            if (documents.size() > 0) {
                for (QueryDocumentSnapshot document : documents) {

                    if(document.getData().get("User").equals(CurrentUser.getCurrentUID()) && document.getData().get("Subject").equals("MATH")) {
                        Test test = new Test(Subject.MATH, Integer.parseInt(String.valueOf(document.getData().get("Questions"))), Integer.parseInt(String.valueOf(document.getData().get("Score"))), (String) document.getData().get("Date"), Integer.parseInt(String.valueOf(document.getData().get("Grade"))));
                        math_tests.add(test);
                    }
                }

            } else {
                System.out.println("No data");
            }

        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Gets all the english tests and places them in the List
     * @since 7/7/2025
     * @author Nathaniel Rivera
     */
    protected void retrieveEnglishTests() {

        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future = StudyApplication.fstore.collection("Tests2").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents;
        try {

            documents = future.get().getDocuments();

            if (documents.size() > 0) {

                for (QueryDocumentSnapshot document : documents) {

                    if(document.getData().get("User").equals(CurrentUser.getCurrentUID()) && document.getData().get("Subject").equals("ENGLISH")) {
                        Test test = new Test(Subject.ENGLISH, Integer.parseInt((String) document.getData().get("Questions")), Integer.parseInt((String) document.getData().get("Score")), (String) document.getData().get("Date"), Integer.parseInt((String) document.getData().get("Grade")));
                        english_tests.add(test);
                    }
                }

            } else {
                System.out.println("No data");
            }

        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Gets all the history tests and places them in the List
     * @since 7/7/2025
     * @author Nathaniel Rivera
     */
    protected void retrieveHistoryTests() {

        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future = StudyApplication.fstore.collection("Tests2").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents;
        try {

            documents = future.get().getDocuments();

            if (documents.size() > 0) {

                for (QueryDocumentSnapshot document : documents) {

                    if(document.getData().get("User").equals(CurrentUser.getCurrentUID()) && document.getData().get("Subject").equals("HISTORY")) {
                        Test test = new Test(Subject.HISTORY, Integer.parseInt((String) document.getData().get("Questions")), Integer.parseInt((String) document.getData().get("Score")), (String) document.getData().get("Date"), Integer.parseInt((String) document.getData().get("Grade")));
                        history_tests.add(test);
                    }
                }

            } else {
                System.out.println("No data");
            }

        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Gets all the science tests and places them in the List
     * @since 7/7/2025
     * @author Nathaniel Rivera
     */
    protected void retrieveScienceTests() {

        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future = StudyApplication.fstore.collection("Tests2").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents;
        try {

            documents = future.get().getDocuments();

            if (documents.size() > 0) {

                for (QueryDocumentSnapshot document : documents) {

                    if(document.getData().get("User").equals(CurrentUser.getCurrentUID()) && document.getData().get("Subject").equals("SCIENCE")) {
                        Test test = new Test(Subject.SCIENCE, Integer.parseInt((String) document.getData().get("Questions")), Integer.parseInt((String) document.getData().get("Score")), (String) document.getData().get("Date"), Integer.parseInt((String) document.getData().get("Grade")));
                        science_tests.add(test);
                    }
                }

            } else {
                System.out.println("No data");
            }

        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Logs the user out of the app returning them to the home page.
     * @since 7/9/2025
     * @author Nathaniel Rivera
     */
    @FXML
    protected void logOut() {
        LoginController.updateLoginStatus(false);
        CurrentUser.logOut();

        FXMLLoader fxmlLoader = new FXMLLoader(StudyController.class.getResource("splash-view.fxml"));

        Stage stage = (Stage) logOutButton.getScene().getWindow();

        try {
            Stage splashStage = new Stage();
            AnchorPane splashRoot = new AnchorPane();
            splashRoot.getChildren().add(fxmlLoader.load());
            StudyApplication.splashSetup(splashRoot, splashStage);

            Scene scene = new Scene(splashRoot, 1200, 700);
            //scene.getStylesheets().add(Objects.requireNonNull(StudyApplication.class.getResource("math-theme.css")).toExternalForm());
            splashStage.setScene(scene);
            splashStage.setResizable(false);
            splashStage.initStyle(StageStyle.TRANSPARENT);
            //landingStage.getIcons().add(new Image(Objects.requireNonNull(StudyApplication.class.getResourceAsStream())));
            stage.close();
            splashStage.show();

        } catch(Exception _) { }
    }

    /**
     * Reloads the users PFP to the CurrentUsers pfp
     * @since 7/10/2025
     * @author Nathaniel Rivera
     */
    @FXML
    protected void setPFP() {
        pfp.setImage(new Image(Objects.requireNonNull(StudyController.class.getResourceAsStream(CurrentUser.getPFP()))));
    }
}