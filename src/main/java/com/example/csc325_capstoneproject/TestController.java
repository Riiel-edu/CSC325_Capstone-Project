package com.example.csc325_capstoneproject;

import com.example.csc325_capstoneproject.model.CurrentUser;
import com.example.csc325_capstoneproject.model.Subject;
import com.example.csc325_capstoneproject.model.Test;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.Clock;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * TestController is the class which controls the tests for the application.
 * Both practice and timed test are directed to this page the only difference
 * is if the timer activates or not.
 * @since 7/2/2025
 * @author Nathaniel Rivera
 */
public class TestController implements Initializable {

    @FXML
    protected Label numQuestionsLabel;

    @FXML
    protected Label subjectLabel;

    @FXML
    protected Label timerText;

    @FXML
    protected Label gradeLabel;

    @FXML
    protected Label questionLabel;

    @FXML
    protected TextArea answerField;

    @FXML
    protected Button previousButton;

    @FXML
    protected Button nextButton;

    @FXML
    protected Button submitButton;

    protected ArrayList<String> questions;

    protected ArrayList<String> answers;

    protected int currentQuestion;

    protected int totalQuestions;

    protected Subject subject;

    protected int gradeLevel;

    protected boolean isTimed;

    protected int highestCount;

    protected int lowestCountTest;

    protected LinkedList<Test> tests = new LinkedList<Test>();

    /**
     * Initializes the test values and generates the test.
     * @param url URL
     * @param resourceBundle ResourceBundle
     * @since 7/2/2025
     * @author Nathaniel Rivera
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        subject = StudyController.getCurrentSubject();
        gradeLevel = StudyController.getCurrentGradeLevel();
        totalQuestions = StudyController.getQuestionCount();
        isTimed = StudyController.getTimed();
        currentQuestion = 1;

        subjectLabel.setText("Subject: " + subject);
        gradeLabel.setText("Grade: " + gradeLevel);
        numQuestionsLabel.setText("Question " + currentQuestion + " of " + totalQuestions);
        previousButton.setDisable(true);

        questions = new ArrayList<>();
        for(int i = 0; i < totalQuestions; i++) {
            questions.add(i, "");
        }

        answers = new ArrayList<>();
        for(int i = 0; i < totalQuestions; i++) {
            answers.add(i, "");
        }

        switch(subject) {
            case MATH -> retrieveMathTests();
            case ENGLISH -> retrieveEnglishTests();
            case HISTORY -> retrieveHistoryTests();
            case SCIENCE -> retrieveScienceTests();
        }

        if(isTimed) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    System.out.println("Times out");
                    /*
                    FXMLLoader fxmlLoader = new FXMLLoader(StudyApplication.class.getResource("landing-view.fxml"));

                    Stage stage = (Stage) submitButton.getScene().getWindow();

                    try {
                        Stage landingStage = new Stage();
                        AnchorPane landingRoot = new AnchorPane();
                        landingRoot.getChildren().add(fxmlLoader.load());

                        Scene scene = new Scene(landingRoot, 1200, 800);
                        scene.getStylesheets().add(Objects.requireNonNull(StudyApplication.class.getResource("math-theme.css")).toExternalForm());
                        landingStage.setScene(scene);
                        landingStage.setResizable(false);
                        //landingStage.getIcons().add(new Image(Objects.requireNonNull(StudyApplication.class.getResourceAsStream())));
                        stage.close();
                        landingStage.show();
                    } catch(Exception _) { }*/
                    try {
                        timer();
                    } catch (InterruptedException e) {
                        System.out.println("failed");
                    }
                }
            }, 5000);
            try {
                timer();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Goes to the next question, disabled if it is the last question.
     * @since 7/2/2025
     * @author Nathaniel Rivera
     */
    @FXML
    protected void next() {

        answers.set(currentQuestion - 1, answerField.getText());

        currentQuestion++;
        numQuestionsLabel.setText("Question " + currentQuestion + " of " + totalQuestions);

        if(currentQuestion == totalQuestions) {
            nextButton.setDisable(true);
        }

        if(currentQuestion != 1 && previousButton.isDisable()) {
            previousButton.setDisable(false);
        }
    }

    /**
     * Goes to the previous question, disabled if it is the first question.
     * @since 7/2/2025
     * @author Nathaniel Rivera
     */
    @FXML
    protected void previous() {

        answers.set(currentQuestion - 1, answerField.getText());

        currentQuestion--;
        numQuestionsLabel.setText("Question " + currentQuestion + " of " + totalQuestions);

        if(currentQuestion != totalQuestions && nextButton.isDisable()) {
            nextButton.setDisable(false);
        }

        if(currentQuestion == 1) {
            previousButton.setDisable(true);
        }
    }

    /**
     * Submits the exam.
     * @since 7/2/2025
     * @author Nathaniel Rivera
     */
    @FXML
    protected void submit() {

        addTest();

        FXMLLoader fxmlLoader = new FXMLLoader(StudyApplication.class.getResource("landing-view.fxml"));

        Stage stage = (Stage) submitButton.getScene().getWindow();

        try {
            Stage landingStage = new Stage();
            AnchorPane landingRoot = new AnchorPane();
            landingRoot.getChildren().add(fxmlLoader.load());

            Scene scene = new Scene(landingRoot, 1200, 800);
            scene.getStylesheets().add(Objects.requireNonNull(StudyApplication.class.getResource("math-theme.css")).toExternalForm());
            landingStage.setScene(scene);
            landingStage.setResizable(false);
            //landingStage.getIcons().add(new Image(Objects.requireNonNull(StudyApplication.class.getResourceAsStream())));
            stage.close();
            landingStage.show();
        } catch(Exception _) { }
    }

    /**
     * Method to start and move the timer through the application.
     * @since 7/4/2025
     * @author Nathaniel Rivera
     */
    protected void timer() throws InterruptedException {
        Timer timer = new Timer();
        /*
        int min = 5;
        int sec = 0;

        setTimer(min, sec);

        while(min != 0 && sec != 0) {

            if(sec > 0) {
                sec--;
            } else if(min > 0 && sec == 0) {
                min--;
                sec = 59;
            } else if(min == 0 && sec == 1) {
                sec--;
            }

            wait(1000);
            setTimer(min, sec);
            System.out.println("timed");
        }*/
    }

    /**
     * Sets the timer to the newly updated time.
     * @since 7/4/2025
     * @author Nathaniel Rivera
     */
    protected void setTimer(int min, int sec) {
        if(min >= 10) {
            if(sec >= 10) {
                timerText.setText(min + ":" + sec);
            } else {
                timerText.setText(min + ":0" + sec);
            }
        } else if(sec >= 10) {
            timerText.setText("0" + min + ":" + sec);
        } else {
            timerText.setText("0" + min + ":0" + sec);
        }
    }

    /**
     * Adds the current test to the firebase storage, if 10 tests exist for the current subject it will replace the oldest one.
     * @since 7/7/2025
     * @author Nathaniel Rivera
     */
    protected void addTest() {

        StringBuilder sb = new StringBuilder();

        Clock zoneClock = Clock.system(ZoneId.of("-05:00"));
        sb.append(zoneClock.instant().toString(), 5, 7).append("/");
        sb.append(zoneClock.instant().toString(), 8, 10).append("/");
        sb.append(zoneClock.instant().toString(), 0, 4);
        String time  = sb.toString();


        DocumentReference docRef = StudyApplication.fstore.collection("Tests2").document(UUID.randomUUID().toString());

        Map<String, Object> new_tests = new HashMap<>();
        new_tests.put("Subject", subject);
        new_tests.put("Date", time);
        new_tests.put("Score", 0);
        new_tests.put("Questions", totalQuestions);
        new_tests.put("Grade", gradeLevel);
        new_tests.put("Count", highestCount + 1);
        new_tests.put("User", CurrentUser.getCurrentUID());

        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(new_tests);

        if(highestCount >= 10) {
            Test test = tests.get(lowestCountTest);

            //asynchronously retrieve all documents
            ApiFuture<QuerySnapshot> future = StudyApplication.fstore.collection("Tests2").get();
            // future.get() blocks on response
            List<QueryDocumentSnapshot> documents;
            try {
                documents = future.get().getDocuments();
                switch(subject) {
                    case MATH -> { if (!documents.isEmpty()) {
                        for (QueryDocumentSnapshot document : documents) {

                            if(document.getData().get("User").equals(CurrentUser.getCurrentUID()) && document.getData().get("Subject").equals("MATH") && Integer.parseInt(document.getData().get("Count").toString()) == test.getCount()) {
                                DocumentReference docRefDelete = StudyApplication.fstore.collection("Tests").document(document.getId());
                                docRefDelete.delete();
                                System.out.println("pass");
                            } else {
                                System.out.println("fail");
                            }
                        }

                    } else {
                        System.out.println("No data");
                    } }
                    case ENGLISH -> { if (!documents.isEmpty()) {
                        for (QueryDocumentSnapshot document : documents) {

                            if(document.getData().get("User").equals(CurrentUser.getCurrentUID()) && document.getData().get("Subject").equals("ENGLISH") && Integer.parseInt(document.getData().get("Count").toString()) == test.getCount()) {
                                DocumentReference docRefDelete = StudyApplication.fstore.collection("Tests").document(document.getId());
                                docRefDelete.delete();
                                System.out.println("pass");
                            } else {
                                System.out.println("fail");
                            }
                        }

                    } else {
                        System.out.println("No data");
                    }}
                    case HISTORY -> {if (!documents.isEmpty()) {
                        for (QueryDocumentSnapshot document : documents) {

                            if(document.getData().get("User").equals(CurrentUser.getCurrentUID()) && document.getData().get("Subject").equals("HISTORY") && Integer.parseInt(document.getData().get("Count").toString()) == test.getCount()) {
                                DocumentReference docRefDelete = StudyApplication.fstore.collection("Tests").document(document.getId());
                                docRefDelete.delete();
                                System.out.println("pass");
                            } else {
                                System.out.println("fail");
                            }
                        }

                    } else {
                        System.out.println("No data");
                    }}
                    case SCIENCE -> {if (!documents.isEmpty()) {
                        for (QueryDocumentSnapshot document : documents) {

                            if(document.getData().get("User").equals(CurrentUser.getCurrentUID()) && document.getData().get("Subject").equals("SCIENCE") && Integer.parseInt(document.getData().get("Count").toString()) == test.getCount()) {
                                DocumentReference docRefDelete = StudyApplication.fstore.collection("Tests").document(document.getId());
                                docRefDelete.delete();
                                System.out.println("pass");
                            } else {
                                System.out.println("fail");
                            }
                        }

                    } else {
                        System.out.println("No data");
                    }}
                }

            } catch (InterruptedException | ExecutionException ex) {
                ex.printStackTrace();
            }
        }
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

            if (!documents.isEmpty()) {

                for (QueryDocumentSnapshot document : documents) {

                    if(document.getData().get("User").equals(CurrentUser.getCurrentUID()) && document.getData().get("Subject").equals("MATH")) {
                        Test test = new Test(Subject.MATH, Integer.parseInt(String.valueOf(document.getData().get("Questions"))), Integer.parseInt(String.valueOf(document.getData().get("Score"))), (String) document.getData().get("Date"), Integer.parseInt(String.valueOf(document.getData().get("Grade"))), Integer.parseInt(String.valueOf(document.getData().get("Count"))));
                        tests.add(test);
                    }
                }

            } else {
                System.out.println("No data");
            }

        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }

        highestCount = 0;

        int lowestCount = Integer.MAX_VALUE;

        for (int i = 0; i < tests.size(); i++) {
            if (tests.get(i).getCount() > highestCount) {
                highestCount = tests.get(i).getCount();
            }
            if(tests.get(i).getCount() < lowestCount) {
                lowestCount = tests.get(i).getCount();
                lowestCountTest = i;
            }
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

            if (!documents.isEmpty()) {

                for (QueryDocumentSnapshot document : documents) {

                    if(document.getData().get("User").equals(CurrentUser.getCurrentUID()) && document.getData().get("Subject").equals("ENGLISH")) {
                        Test test = new Test(Subject.ENGLISH, Integer.parseInt(String.valueOf(document.getData().get("Questions"))), Integer.parseInt(String.valueOf(document.getData().get("Score"))), (String) document.getData().get("Date"), Integer.parseInt(String.valueOf(document.getData().get("Grade"))), Integer.parseInt(String.valueOf(document.getData().get("Count"))));
                        tests.add(test);
                    }
                }

            } else {
                System.out.println("No data");
            }

        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }

        highestCount = 0;

        int lowestCount = Integer.MAX_VALUE;

        for (int i = 0; i < tests.size(); i++) {
            if (tests.get(i).getCount() > highestCount) {
                highestCount = tests.get(i).getCount();
            }
            if(tests.get(i).getCount() < lowestCount) {
                lowestCount = tests.get(i).getCount();
                lowestCountTest = i;
            }
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

            if (!documents.isEmpty()) {

                for (QueryDocumentSnapshot document : documents) {

                    if(document.getData().get("User").equals(CurrentUser.getCurrentUID()) && document.getData().get("Subject").equals("HISTORY")) {
                        Test test = new Test(Subject.HISTORY, Integer.parseInt(String.valueOf(document.getData().get("Questions"))), Integer.parseInt(String.valueOf(document.getData().get("Score"))), (String) document.getData().get("Date"), Integer.parseInt(String.valueOf(document.getData().get("Grade"))), Integer.parseInt(String.valueOf(document.getData().get("Count"))));
                        tests.add(test);
                    }
                }

            } else {
                System.out.println("No data");
            }

        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }

        highestCount = 0;

        int lowestCount = Integer.MAX_VALUE;

        for (int i = 0; i < tests.size(); i++) {
            if (tests.get(i).getCount() > highestCount) {
                highestCount = tests.get(i).getCount();
            }
            if(tests.get(i).getCount() < lowestCount) {
                lowestCount = tests.get(i).getCount();
                lowestCountTest = i;
            }
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

            if (!documents.isEmpty()) {

                for (QueryDocumentSnapshot document : documents) {

                    if(document.getData().get("User").equals(CurrentUser.getCurrentUID()) && document.getData().get("Subject").equals("SCIENCE")) {
                        Test test = new Test(Subject.SCIENCE, Integer.parseInt(String.valueOf(document.getData().get("Questions"))), Integer.parseInt(String.valueOf(document.getData().get("Score"))), (String) document.getData().get("Date"), Integer.parseInt(String.valueOf(document.getData().get("Grade"))), Integer.parseInt(String.valueOf(document.getData().get("Count"))));
                        tests.add(test);
                    }
                }

            } else {
                System.out.println("No data");
            }

        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }

        highestCount = 0;

        int lowestCount = Integer.MAX_VALUE;

        for (int i = 0; i < tests.size(); i++) {
            if (tests.get(i).getCount() > highestCount) {
                highestCount = tests.get(i).getCount();
            }
            if(tests.get(i).getCount() < lowestCount) {
                lowestCount = tests.get(i).getCount();
                lowestCountTest = i;
            }
        }
    }

}

