module com.example.csc325_capstoneproject {
        requires javafx.controls;
        requires javafx.fxml;
        requires javafx.graphics;
        requires java.logging;
        requires javafx.web;
        requires com.google.auth.oauth2;
        requires google.cloud.firestore;
        requires firebase.admin;
        requires com.google.api.apicommon;

        requires google.cloud.core;
        requires com.google.auth;
        requires java.desktop;
    requires com.google.gson;
    requires okhttp3;


    opens com.example.csc325_capstoneproject to javafx.fxml;
        exports com.example.csc325_capstoneproject;
        exports com.example.csc325_capstoneproject.model;
        opens com.example.csc325_capstoneproject.model to javafx.fxml;
}