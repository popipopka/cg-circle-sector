package com.meow.rasterizationfxapp;

import com.meow.rasterization.Rasterization;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class RasterizationController {

    @FXML
    AnchorPane anchorPane;
    @FXML
    private Canvas canvas;

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        Rasterization.drawSector(canvas.getGraphicsContext2D(), 100, 100, 20, 270, 100, Color.DARKCYAN, Color.BLUEVIOLET);
        Rasterization.drawSector(canvas.getGraphicsContext2D(), 100, 320, 10, 370, 100, Color.DARKCYAN, Color.BLUEVIOLET);
        Rasterization.drawSector(canvas.getGraphicsContext2D(), 100, 540, 40, 390, 100, Color.DARKCYAN, Color.BLUEVIOLET);

        Rasterization.drawSector(canvas.getGraphicsContext2D(), 320, 100, -90, -400, 100, Color.DARKCYAN, Color.BLUEVIOLET);
        Rasterization.drawSector(canvas.getGraphicsContext2D(), 320, 320, -10, -370, 100, Color.DARKCYAN, Color.BLUEVIOLET);
        Rasterization.drawSector(canvas.getGraphicsContext2D(), 320, 540, -40, -390, 100, Color.DARKCYAN, Color.BLUEVIOLET);

        Rasterization.drawSector(canvas.getGraphicsContext2D(), 540, 100, 10, -10, 100, Color.DARKCYAN, Color.BLUEVIOLET);
        Rasterization.drawSector(canvas.getGraphicsContext2D(), 540, 320, -70, 30, 100, Color.DARKCYAN, Color.BLUEVIOLET);
        Rasterization.drawSector(canvas.getGraphicsContext2D(), 540, 540, -90, 100, 100, Color.DARKCYAN, Color.BLUEVIOLET);

        Rasterization.drawSector(canvas.getGraphicsContext2D(), 1000, 320, 370, 720, 300, Color.DARKCYAN, Color.BLUEVIOLET);
    }
}