package com.example.project.BuddyListScreen;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BuddyListScreen {

    public void start() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("BuddyListScreen.fxml"));
        Stage buddyListStage = new Stage();
        buddyListStage.setTitle("Buddy List");
        buddyListStage.setScene(new Scene(root, 268, 777));
        buddyListStage.show();
    }


}
