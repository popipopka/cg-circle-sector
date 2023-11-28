module com.cgvsu.rasterizationfxapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.meow.rasterizationfxapp to javafx.fxml;
    exports com.meow.rasterizationfxapp;
}
