package com.agendawest.controllers;

import com.agendawest.AgendaApp;
import com.agendawest.models.crud.ContactoCRUD;
import com.agendawest.models.dao.ContactoDAO;
import com.agendawest.models.jdbc.ConexionMariaDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class AnadirController implements Initializable {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtPrimerApellido;
    @FXML
    private TextField txtSegundoApellido;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtCiudad;
    @FXML
    private TextField txtCodigoPostal;
    @FXML
    private DatePicker datePickerFechaNacimiento;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnAtras;






    private ObservableList<ContactoDAO> listaContactoDAO;

    private ContactoDAO contactoDAO;

    private ConexionMariaDB conexionMariaDB;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        conexionMariaDB = new ConexionMariaDB();
        conexionMariaDB.establishConnection();
        listaContactoDAO = FXCollections.observableArrayList();
        conexionMariaDB.close();
    }

    @FXML
    public void saveRecord() {


        try {

            contactoDAO = new ContactoDAO(
                    0,
                    txtNombre.getText()
                    , txtPrimerApellido.getText(),
                    txtSegundoApellido.getText(),
                    txtDireccion.getText(),
                    txtCiudad.getText(),
                    txtCodigoPostal.getText(),
                    Date.valueOf(datePickerFechaNacimiento.getValue())
            );
            conexionMariaDB.establishConnection();
            int resultado = ContactoCRUD.create(conexionMariaDB.getConnection(), contactoDAO);
            conexionMariaDB.close();
            if (resultado == 1) {
                listaContactoDAO.add(contactoDAO);
                Alert mensaje = new Alert(Alert.AlertType.CONFIRMATION);
                mensaje.setHeaderText("Resultado:");
                mensaje.setContentText("El registro ha sido guardado");
                mensaje.show();
            }
        } catch (NullPointerException | IllegalArgumentException e) {

            listaContactoDAO.add(contactoDAO);
            Alert mensaje = new Alert(Alert.AlertType.ERROR);
            mensaje.setHeaderText("Resultado:");
            mensaje.setContentText(e.getMessage());
            mensaje.show();
            if(e.getMessage().equals("Cannot invoke \"java.time.LocalDate.getYear()\" because \"date\" is null")){
                listaContactoDAO.add(contactoDAO);
                mensaje.setHeaderText("Resultado:");
                mensaje.setContentText("Error al agregar registro causado por: La fecha no puede ser nula");
                mensaje.show();

            }

        }


    }

    private void setStyleTxtField(TextField textField) {
        textField.setStyle("""
                -fx-border-color:red;
                -fx-background-color: #DEB01D;
                -fx-background-radius: 20;
                -fx-border-radius:20;
                """);
    }

    private void setStyleDatePicker(DatePicker datePickerFechaNacimiento) {
        datePickerFechaNacimiento.setStyle("""
                -fx-border-color:red;
                """);
    }


    @FXML
    public void goBack(ActionEvent actionEvent) {
        try {
            loadView();
            closeMenu(actionEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AgendaApp.class.getResource("MenuLayout.fxml"));
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