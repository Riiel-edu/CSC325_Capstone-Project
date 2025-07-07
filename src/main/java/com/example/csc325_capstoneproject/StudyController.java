package com.example.csc325_capstoneproject;

import com.example.csc325_capstoneproject.model.CurrentUser;
import com.example.csc325_capstoneproject.model.Subject;
import com.example.csc325_capstoneproject.model.Test;
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

import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller class which controls the actions for the landing page of the application.
 * @since 6/14/2025
 * @author Nathaniel Rivera
 */
public class StudyController implements Initializable {

    @FXML
    protected ImageView pfp;

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
    protected TableColumn<Test, Double> tv_score;

    @FXML
    protected TableColumn<Test, Integer> tv_count;

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

        Test test = new Test(Subject.MATH, 20, 10, "6/30/2025");
        math_tests.add(test);

        tv_subject.setCellValueFactory(new PropertyValueFactory<>("Subject"));
        tv_date.setCellValueFactory(new PropertyValueFactory<>("DateTaken"));
        tv_score.setCellValueFactory(new PropertyValueFactory<>("Score"));
        tv_count.setCellValueFactory(new PropertyValueFactory<>("questionCount"));

        math_average = 0;
        english_average = 0;
        history_average = 0;
        science_average = 0;


        tv.setItems(math_tests);
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

        File file = (new FileChooser()).showOpenDialog(pfp.getScene().getWindow());

        if(file != null) {
            pfp.setImage(new Image(file.toURI().toString()));
        }

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
    protected void test() {

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

        sb.append("/com/example/csc325_capstoneproject/images/");

        switch(currentSubject) {
            case MATH -> sb.append("math_wheels/math_wheel_").append(math_average).append(".png");
            case ENGLISH -> sb.append("english_wheels/english_wheel_").append(english_average).append(".png");
            case HISTORY -> sb.append("history_wheels/history_wheel_").append(history_average).append(".png");
            case SCIENCE -> sb.append("science_wheels/science_wheel_").append(science_average).append(".png");
        }

        percentageWheel.setImage(new Image(Objects.requireNonNull(StudyController.class.getResourceAsStream(sb.toString()))));
    }
}