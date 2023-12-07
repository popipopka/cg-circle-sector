package com.meow.rasterizationfxapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RasterizationApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RasterizationApplication.class.getResource("main-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
//        stage.setFullScreen(true);
        stage.setTitle("Rasterization App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}