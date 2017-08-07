package com.example.project.WelcomeScreen;

import com.example.project.PasswordSalter.PasswordSalter;
import com.example.project.SessionManager.SessionManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class WelcomeScreen extends Application {
    private SessionManager sessionManager = SessionManager.getInstance();

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("WelcomeScreen.fxml"));
        Parent root = loader.load();
        WelcomeScreenController welcomeScreenController = loader.getController();
        sessionManager.setWelcomeScreenController(welcomeScreenController);
        Stage welcomeScreenStage = new Stage();
        welcomeScreenStage.setTitle("Chattr");

        welcomeScreenStage.setMinHeight(275);
        welcomeScreenStage.setMinWidth(300);
        welcomeScreenStage.getIcons().add(new Image("images/appIcon.jpg"));

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double height = primaryScreenBounds.getHeight();
        double width = primaryScreenBounds.getWidth() / 8;
        welcomeScreenStage.setX(primaryScreenBounds.getMinX());
        welcomeScreenStage.setY(primaryScreenBounds.getMinY());
        welcomeScreenStage.setMinHeight(height/4);
        welcomeScreenStage.setMinWidth(width/2);

        welcomeScreenStage.setScene(new Scene(root, width, height));

        welcomeScreenStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
