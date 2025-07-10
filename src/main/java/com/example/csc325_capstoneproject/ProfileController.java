package com.example.csc325_capstoneproject;

import com.example.csc325_capstoneproject.model.CurrentUser;
import com.example.csc325_capstoneproject.model.Subject;
import com.example.csc325_capstoneproject.model.Test;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
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
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * Controller class which controls the actions for the profile page of the application.
 * @since 7/10/2025
 * @author Nathaniel Rivera
 */
public class ProfileController {

    @FXML
    protected Button closeButton;

    /**
     * Sets the users pfp to the rainbow icon.
     * @since 7/10/2025
     * @author Nathaniel Rivera
     */
    @FXML
    protected void rainbowPFP() {

        CurrentUser.setPFP("/com/example/csc325_capstoneproject/images/pfps/rainbow.png");


        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future = StudyApplication.fstore.collection("PFPs").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents;
        try {
            documents = future.get().getDocuments();

            if (documents.size() > 0) {

                for (QueryDocumentSnapshot document : documents) {

                    if(document.getData().get("User").equals(CurrentUser.getCurrentUID())) {
                        DocumentReference docRef = StudyApplication.fstore.collection("PFPs").document(document.getId());
                        docRef.delete();
                    }
                }

            } else {
                System.out.println("No data");
            }

        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }

        DocumentReference docRef = StudyApplication.fstore.collection("PFPs").document(UUID.randomUUID().toString());

        Map<String, Object> pfpRef = new HashMap<>();
        pfpRef.put("PFP", "/com/example/csc325_capstoneproject/images/pfps/rainbow.png");
        pfpRef.put("User", CurrentUser.getCurrentUID());

        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(pfpRef);

        System.out.println("PFP changed");
    }

    /**
     * Sets the users pfp to the cat icon.
     * @since 7/10/2025
     * @author Nathaniel Rivera
     */
    @FXML
    protected void catPFP() {

        CurrentUser.setPFP("/com/example/csc325_capstoneproject/images/pfps/cat.png");

        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future = StudyApplication.fstore.collection("PFPs").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents;
        try {
            documents = future.get().getDocuments();

            if (documents.size() > 0) {

                for (QueryDocumentSnapshot document : documents) {

                    if(document.getData().get("User").equals(CurrentUser.getCurrentUID())) {
                        DocumentReference docRef = StudyApplication.fstore.collection("PFPs").document(document.getId());
                        docRef.delete();
                    }
                }

            } else {
                System.out.println("No data");
            }

        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }

        DocumentReference docRef = StudyApplication.fstore.collection("PFPs").document(UUID.randomUUID().toString());

        Map<String, Object> pfpRef = new HashMap<>();
        pfpRef.put("PFP", "/com/example/csc325_capstoneproject/images/pfps/cat.png");
        pfpRef.put("User", CurrentUser.getCurrentUID());

        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(pfpRef);

        System.out.println("PFP changed");
    }

    /**
     * Sets the users pfp to the robot icon.
     * @since 7/10/2025
     * @author Nathaniel Rivera
     */
    @FXML
    protected void robotPFP() {

        CurrentUser.setPFP("/com/example/csc325_capstoneproject/images/pfps/robot.png");

        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future = StudyApplication.fstore.collection("PFPs").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents;
        try {
            documents = future.get().getDocuments();

            if (documents.size() > 0) {

                for (QueryDocumentSnapshot document : documents) {

                    if(document.getData().get("User").equals(CurrentUser.getCurrentUID())) {
                        DocumentReference docRef = StudyApplication.fstore.collection("PFPs").document(document.getId());
                        docRef.delete();
                    }
                }

            } else {
                System.out.println("No data");
            }

        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }

        DocumentReference docRef = StudyApplication.fstore.collection("PFPs").document(UUID.randomUUID().toString());

        Map<String, Object> pfpRef = new HashMap<>();
        pfpRef.put("PFP", "/com/example/csc325_capstoneproject/images/pfps/robot.png");
        pfpRef.put("User", CurrentUser.getCurrentUID());

        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(pfpRef);

        System.out.println("PFP changed");
    }

    /**
     * Sets the users pfp to the baseball icon.
     * @since 7/10/2025
     * @author Nathaniel Rivera
     */
    @FXML
    protected void baseballPFP() {

        CurrentUser.setPFP("/com/example/csc325_capstoneproject/images/pfps/baseball.png");

        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future = StudyApplication.fstore.collection("PFPs").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents;
        try {
            documents = future.get().getDocuments();

            if (documents.size() > 0) {

                for (QueryDocumentSnapshot document : documents) {

                    if(document.getData().get("User").equals(CurrentUser.getCurrentUID())) {
                        DocumentReference docRef = StudyApplication.fstore.collection("PFPs").document(document.getId());
                        docRef.delete();
                    }
                }

            } else {
                System.out.println("No data");
            }

        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }

        DocumentReference docRef = StudyApplication.fstore.collection("PFPs").document(UUID.randomUUID().toString());

        Map<String, Object> pfpRef = new HashMap<>();
        pfpRef.put("PFP", "/com/example/csc325_capstoneproject/images/pfps/baseball.png");
        pfpRef.put("User", CurrentUser.getCurrentUID());

        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(pfpRef   );

        System.out.println("PFP changed");
    }

    /**
     * Sets the users pfp to the dog icon.
     * @since 7/10/2025
     * @author Nathaniel Rivera
     */
    @FXML
    protected void dogPFP() {

        CurrentUser.setPFP("/com/example/csc325_capstoneproject/images/pfps/dog.png");

        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future = StudyApplication.fstore.collection("PFPs").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents;
        try {
            documents = future.get().getDocuments();

            if (documents.size() > 0) {

                for (QueryDocumentSnapshot document : documents) {

                    if(document.getData().get("User").equals(CurrentUser.getCurrentUID())) {
                        DocumentReference docRef = StudyApplication.fstore.collection("PFPs").document(document.getId());
                        docRef.delete();
                    }
                }

            } else {
                System.out.println("No data");
            }

        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }

        DocumentReference docRef = StudyApplication.fstore.collection("PFPs").document(UUID.randomUUID().toString());

        Map<String, Object> pfpRef = new HashMap<>();
        pfpRef.put("PFP", "/com/example/csc325_capstoneproject/images/pfps/dog.png");
        pfpRef.put("User", CurrentUser.getCurrentUID());

        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(pfpRef);

        System.out.println("PFP changed");
    }

    /**
     * Sets the users pfp to the horse icon.
     * @since 7/10/2025
     * @author Nathaniel Rivera
     */
    @FXML
    protected void horsePFP() {

        CurrentUser.setPFP("/com/example/csc325_capstoneproject/images/pfps/horse.png");

        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future = StudyApplication.fstore.collection("PFPs").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents;
        try {
            documents = future.get().getDocuments();

            if (documents.size() > 0) {

                for (QueryDocumentSnapshot document : documents) {

                    if(document.getData().get("User").equals(CurrentUser.getCurrentUID())) {
                        DocumentReference docRef = StudyApplication.fstore.collection("PFPs").document(document.getId());
                        docRef.delete();
                    }
                }

            } else {
                System.out.println("No data");
            }

        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }

        DocumentReference docRef = StudyApplication.fstore.collection("PFPs").document(UUID.randomUUID().toString());

        Map<String, Object> pfpRef = new HashMap<>();
        pfpRef.put("PFP", "/com/example/csc325_capstoneproject/images/pfps/horse.png");
        pfpRef.put("User", CurrentUser.getCurrentUID());

        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(pfpRef);

        System.out.println("PFP changed");
    }

    /**
     * Closes the PFP Screen
     * @since 7/10/2025
     * @author Nathaniel Rivera
     */
    @FXML
    protected void close() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}