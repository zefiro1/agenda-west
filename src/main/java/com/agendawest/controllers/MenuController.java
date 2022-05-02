package com.agendawest.controllers;

import com.agendawest.AgendaApp;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    private Label header;
    @FXML
    private Button btnAnadir;
    @FXML
    private Button btnBuscar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        eventHover();
    }

    public void eventHover() {
        btnAnadir.hoverProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1) {
                    DropShadow shadow = new DropShadow();
                    btnAnadir.setEffect(shadow);

                    btnAnadir.setTextFill(Paint.valueOf("#ffffff"));
                } else {
                    btnAnadir.setEffect(null);
                    btnAnadir.setTextFill(Paint.valueOf("#000000"));
                }
            }
        });
        btnBuscar.hoverProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1) {
                    DropShadow shadow = new DropShadow();
                    btnBuscar.setEffect(shadow);

                    btnBuscar.setTextFill(Paint.valueOf("#ffffff"));
                } else {
                    btnBuscar.setEffect(null);
                    btnBuscar.setTextFill(Paint.valueOf("#000000"));
                }
            }
        });
    }

    @FXML
    public void changeLayoutAnadirLayout(ActionEvent actionEvent) {
        try {
            loadView("AnadirLayout");
            closeMenu(actionEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    public void changeLayoutBuscarLayout(ActionEvent actionEvent) {
        try {
            loadView("BuscarLayout");
            closeMenu(actionEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private void loadView(String nameLayout) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AgendaApp.class.getResource(nameLayout + ".fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    private void closeMenu(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage root = (Stage) n.getScene().getWindow();
        root.close();
    }
}
