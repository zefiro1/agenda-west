package com.agendawest;

import com.agendawest.models.jdbc.MyConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * AgendaApp inicia la aplicacion
 */
public class AgendaApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(AgendaApp.class.getResource("views/layout/MenuLayout.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.getIcons().add(new Image("/com/agendawest/icons/agenda.png"));
        stage.setTitle("Agenda West");
        stage.show();
        MyConnection.close();
    }

    public static void main(String[] args) {
        launch();
    }
}