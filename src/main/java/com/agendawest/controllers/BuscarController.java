package com.agendawest.controllers;

import com.agendawest.AgendaApp;
import com.agendawest.models.crud.ContactoCRUD;
import com.agendawest.models.dao.ContactoDAO;
import com.agendawest.models.jdbc.ConexionMariaDB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class BuscarController implements Initializable {

    @FXML
    private TableView<ContactoDAO> tbViewContactoDao;
    @FXML
    private TableColumn<ContactoDAO, Number> clmnId;
    @FXML
    private TableColumn<ContactoDAO, String> clmnNombre;
    @FXML
    private TableColumn<ContactoDAO, String> clmnPrimerApellido;
    @FXML
    private TableColumn<ContactoDAO, String> clmnSegundoApellido;

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
    private TextField txtSearch;
    @FXML
    private DatePicker datePickerFechaNacimiento;

    @FXML
    private Button btnAtras;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnBorrar;

    private ObservableList<ContactoDAO> listaContactoDAO;

    private ContactoDAO contactoDAO;

    private ConexionMariaDB conexionMariaDB;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        conexionMariaDB = new ConexionMariaDB();
        conexionMariaDB.establishConnection();


        listaContactoDAO = FXCollections.observableArrayList();


        tbViewContactoDao.setItems(listaContactoDAO);
        System.out.println(listaContactoDAO);

        clmnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clmnPrimerApellido.setCellValueFactory(new PropertyValueFactory<>("primerApellido"));
        clmnSegundoApellido.setCellValueFactory(new PropertyValueFactory<>("segundoApellido"));

        ContactoCRUD.read(conexionMariaDB.getConnection(), listaContactoDAO);
        search();

        showDate();
        conexionMariaDB.close();
    }

    @FXML
    public  void updateRecord() {
        try {
            for (int i = 0; i < tbViewContactoDao.getItems().size(); i++) {
                contactoDAO = new ContactoDAO(
                        tbViewContactoDao.getItems().get(i).getId(),
                        txtNombre.getText()
                        , txtPrimerApellido.getText(),
                        txtSegundoApellido.getText(),
                        txtDireccion.getText(),
                        txtCiudad.getText(),
                        txtCodigoPostal.getText(),
                        Date.valueOf(datePickerFechaNacimiento.getValue())
                );
            }
            conexionMariaDB.establishConnection();
            int resultado = ContactoCRUD.update(conexionMariaDB.getConnection(), contactoDAO);
            conexionMariaDB.close();
            if (resultado == 1) {
                listaContactoDAO.add(contactoDAO);
                Alert mensaje = new Alert(Alert.AlertType.CONFIRMATION);
                mensaje.setHeaderText("Resultado:");
                mensaje.setContentText("El registro ha sido actualizado");
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

    @FXML
    public void deleteRecord(){
        conexionMariaDB.establishConnection();
        contactoDAO = tbViewContactoDao.getSelectionModel().getSelectedItem();
        int resultado = ContactoCRUD.delete(conexionMariaDB.getConnection(),contactoDAO);
        conexionMariaDB.close();
        if(resultado == 1){
            listaContactoDAO.remove(tbViewContactoDao.getSelectionModel().getSelectedIndex());
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setContentText("El registro ha sido eliminado exitosamente");
            mensaje.setHeaderText("Resultado:");
            mensaje.show();
        }
    }

    public void search() {
        FilteredList<ContactoDAO> filteredList = new FilteredList<>(listaContactoDAO, b -> true);
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                filteredList.setPredicate(contactoDAO1 -> {
                    if (t1 == null || t1.isEmpty()) {
                        return true;

                    }

                    String lowerCaseFilter = t1.toLowerCase(Locale.ROOT);
                    if (String.valueOf(contactoDAO1.getId()).toLowerCase(Locale.ROOT).contains(lowerCaseFilter)) {
                        return true;
                    } else if (contactoDAO1.getNombre().toLowerCase(Locale.ROOT).contains(lowerCaseFilter)) {
                        return true;
                    } else {
                        return false;
                    }
                });
            }
        });

        SortedList<ContactoDAO> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tbViewContactoDao.comparatorProperty());
        tbViewContactoDao.setItems(sortedList);

    }

    public void showDate() {
        tbViewContactoDao.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ContactoDAO>() {
            @Override
            public void changed(ObservableValue<? extends ContactoDAO> observableValue, ContactoDAO contactoDAO, ContactoDAO t1) {
                if (t1 != null) {
                    txtNombre.setText(t1.getNombre());
                    txtPrimerApellido.setText(t1.getPrimerApellido());
                    txtSegundoApellido.setText(t1.getSegundoApellido());
                    txtCiudad.setText(t1.getCiudad());
                    txtCodigoPostal.setText(t1.getCodigoPostal());
                    txtDireccion.setText(t1.getDireccion());
                    datePickerFechaNacimiento.setValue(t1.getFechaNacimiento().toLocalDate());


                }
            }
        });

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
