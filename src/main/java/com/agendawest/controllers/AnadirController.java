package com.agendawest.controllers;

import com.agendawest.AgendaApp;
import com.agendawest.models.contacto.ContactoDAO;
import com.agendawest.models.contacto.Contacto;
import com.agendawest.models.jdbc.MyConnection;
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
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

/**
 * Controlador de la vista AnadirLayout
 */
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






    private ObservableList<Contacto> listaContacto;

    private Contacto contacto;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        listaContacto = FXCollections.observableArrayList();

    }

    /**
     * Guarda un registro
     */
    @FXML
    public void saveRecord() {


        try {

            contacto = new Contacto(
                    0,
                    txtNombre.getText()
                    , txtPrimerApellido.getText(),
                    txtSegundoApellido.getText(),
                    txtDireccion.getText(),
                    txtCiudad.getText(),
                    txtCodigoPostal.getText(),
                    Date.valueOf(datePickerFechaNacimiento.getValue())
            );

            int resultado = ContactoDAO.create(contacto);

            if (resultado == 1) {
                listaContacto.add(contacto);
                Alert mensaje = new Alert(Alert.AlertType.CONFIRMATION);
                mensaje.setHeaderText("Resultado:");
                mensaje.setContentText("El registro ha sido guardado");
                mensaje.show();
            }
        } catch (NullPointerException | IllegalArgumentException e) {

            listaContacto.add(contacto);
            Alert mensaje = new Alert(Alert.AlertType.ERROR);
            mensaje.setHeaderText("Resultado:");
            mensaje.setContentText(e.getMessage());
            mensaje.show();
            if(e.getMessage().equals("Cannot invoke \"java.time.LocalDate.getYear()\" because \"date\" is null")){
                listaContacto.add(contacto);
                mensaje.setHeaderText("Resultado:");
                mensaje.setContentText("Error al agregar registro causado por: La fecha no puede ser nula");
                mensaje.show();

            }

        }


    }


    /**
     *
     * Vuelve atras
     * @param actionEvent
     */
    @FXML
    public void goBack(ActionEvent actionEvent) {
        try {
            loadView();
            closeMenu(actionEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Carga la vista MenuLayout
     * @throws IOException
     */
    private void loadView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AgendaApp.class.getResource("views/layout/MenuLayout.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.getIcons().add(new Image("/com/agendawest/icons/agenda.png"));
        stage.show();
    }

    /**
     * Cierra el stage MenuLayout
     * @param actionEvent
     */
    private void closeMenu(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage root = (Stage) n.getScene().getWindow();
        root.close();
    }
}