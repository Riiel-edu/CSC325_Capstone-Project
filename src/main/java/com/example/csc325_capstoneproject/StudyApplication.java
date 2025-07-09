package com.example.csc325_capstoneproject;

import com.example.csc325_capstoneproject.model.FirestoreContext;
import com.example.csc325_capstoneproject.model.Test;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.util.Objects;

/**
 *
 * @since 6/14/2025
 * @author Nathaniel Rivera
 */
public class StudyApplication extends Application {

    public static Firestore fstore;
    public static FirebaseAuth fauth;
    public static Scene mainScene;
    private final FirestoreContext contxtFirebase = new FirestoreContext();


    /**
     * The initial start method for the study application. Launches and calls the setup for the splash screen.
     * @param stage The stage in which the splash screen is held
     * @throws IOException Throws an exception.
     * @author Nathaniel Rivera
     * @since 6/14/2025
     */
    @Override
    public void start(Stage stage) throws IOException {
        fstore = contxtFirebase.firebase();
        fauth = FirebaseAuth.getInstance();

        FXMLLoader fxmlLoader = new FXMLLoader(StudyApplication.class.getResource("splash-view.fxml"));

        AnchorPane root = new AnchorPane();
        root.getChildren().add(fxmlLoader.load());
        splashSetup(root, stage);

        mainScene = new Scene(root, 1200, 700);
        //scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("splashscreen.css")).toExternalForm());
        stage.setScene(mainScene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        //stage.getIcons().add(new Image(Objects.requireNonNull(StudyApplication.class.getResourceAsStream())));
        stage.show();
    }

    /**
     * Main method of the program. Launches the application when the program is run.
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Sets up the interactable parts of the Splash page.
     * These elements include the system tray replacements, the login button and the launcher.
     * @param root  The AnchorPane for the splash screen.
     * @param stage The stage the splash scene is set in.
     * @author Nathaniel Rivera
     * @since 6/14/2025
     */
    public static void splashSetup(AnchorPane root, Stage stage) {

        Button launcher = new Button();
        launcher.setPrefWidth(300);
        launcher.setPrefHeight(100);
        launcher.setLayoutX(850);
        launcher.setLayoutY(550);
        launcher.setText("Launch");
        root.getChildren().add(launcher);
        launcher.setOnAction(e -> {
            /*
            try {
                mainScene = new Scene(loadFXML("landing-view.fxml"));
                stage.setScene(mainScene);
                stage.show();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }*/

            FXMLLoader fxmlLoader = new FXMLLoader(StudyApplication.class.getResource("landing-view.fxml"));

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
        });

        /*------------------------------------------System Tray Replacement Buttons------------------------------------------*/

        Button close = new Button();
        close.setPrefWidth(25);
        close.setPrefHeight(25);
        close.setLayoutX(1160);
        close.setLayoutY(15);
        close.setOpacity(0);
        root.getChildren().add(close);

        close.setOnAction(e -> {
            stage.close();
        });

        Button minimize = new Button();
        minimize.setPrefWidth(25);
        minimize.setPrefHeight(25);
        minimize.setLayoutX(1120);
        minimize.setLayoutY(15);
        minimize.setOpacity(0);
        root.getChildren().add(minimize);

        minimize.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).setIconified(true);
            }
        });

        Button login = new Button();
        login.setPrefWidth(25);
        login.setPrefHeight(25);
        login.setLayoutX(1080);
        login.setLayoutY(15);
        login.setOpacity(0);
        root.getChildren().add(login);

        login.setOnAction(e -> {

            FXMLLoader fxmlLoader = new FXMLLoader(StudyApplication.class.getResource("login-view.fxml"));

            try {
                Stage loginStage = new Stage();
                AnchorPane loginRoot = new AnchorPane();
                loginRoot.getChildren().add(fxmlLoader.load());

                Scene scene = new Scene(loginRoot, 650, 380);
                //loginStage.getStylesheets().add(Objects.requireNonNull(getClass().getResource("splashscreen.css")).toExternalForm());
                loginStage.setScene(scene);
                loginStage.setResizable(false);
                loginStage.initStyle(StageStyle.TRANSPARENT);
                //loginStage.getIcons().add(new Image(Objects.requireNonNull(StudyApplication.class.getResourceAsStream())));
                loginStage.show();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        });
    }
        /*
    public static void setRoot(String fxml) throws IOException {
        mainScene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StudyApplication.class.getResource(fxml));
        return fxmlLoader.load();
    }*/
}