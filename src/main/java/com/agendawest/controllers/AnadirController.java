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

    @FXML
    private Label lError1;

    @FXML
    private Label lError2;
    @FXML
    private Label lError3;
    @FXML
    private Label lError4;
    @FXML
    private Label lError5;
    @FXML
    private Label lError6;
    @FXML
    private Label lError7;


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

            if (e.getMessage().equals("Error el nombre no puede estar vacio")
                    || e.getMessage().equals("Error el nombre tiene que ser >= 3")
                    || e.getMessage().equals("Error el nombre no puede ser null")) {

                setStyleTxtField(txtNombre);
                lError1.setText(e.getMessage());
                lError1.setVisible(true);
            }
            if (e.getMessage().equals("Error el primer apellido no puede estar vacio")
                    || e.getMessage().equals("Error el primer apellido tiene que ser >= 6")
            ) {
                setStyleTxtField(txtPrimerApellido);
                lError2.setText(e.getMessage());
                lError2.setVisible(true);
            }
            if (e.getMessage().equals("Error el segundo apellido no puede estar vacio")
                    || e.getMessage().equals("Error el segundo apellido tiene que ser >= 6")
            ) {
                setStyleTxtField(txtSegundoApellido);
                lError3.setText(e.getMessage());
                lError3.setVisible(true);
            }
            if (e.getMessage().equals("Error la direccion no puede estar vacio")
                    || e.getMessage().equals("Error la direccion tiene que ser >= 6")
            ) {
                setStyleTxtField(txtDireccion);
                lError4.setText(e.getMessage());
                lError4.setVisible(true);
            }
            if (e.getMessage().equals("Error la ciudad no puede estar vacio")
                    || e.getMessage().equals("Error la ciudad tiene que ser >= 4")
            ) {
                setStyleTxtField(txtCiudad);
                lError5.setText(e.getMessage());
                lError5.setVisible(true);
            }
            if (e.getMessage().equals("Error la codigo postal no puede estar vacio")
                    || e.getMessage().equals("Error la codigo postal tiene que ser >= 5")
            ) {
                setStyleTxtField(txtCodigoPostal);
                lError6.setText(e.getMessage());
                lError6.setVisible(true);
            }
            if (e.getMessage().equals("Cannot invoke \"java.time.LocalDate.getYear()\" because \"date\" is null")) {
                setStyleDatePicker(datePickerFechaNacimiento);
                lError7.setText("Error la fecha no puede estar en blanco");
                lError7.setVisible(true);
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