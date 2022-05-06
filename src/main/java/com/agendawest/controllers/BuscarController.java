package com.agendawest.controllers;

import com.agendawest.AgendaApp;
import com.agendawest.models.contacto.Contacto;
import com.agendawest.models.contacto.ContactoDAO;
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
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controlador de la vista BuscarLayout
 */
public class BuscarController implements Initializable {

    @FXML
    private TableView<Contacto> tbViewContactoDao;
    @FXML
    private TableColumn<Contacto, Number> clmnId;
    @FXML
    private TableColumn<Contacto, String> clmnNombre;
    @FXML
    private TableColumn<Contacto, String> clmnPrimerApellido;
    @FXML
    private TableColumn<Contacto, String> clmnSegundoApellido;

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

    private ObservableList<Contacto> listaContacto;

    private Contacto contacto;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        listaContacto = FXCollections.observableArrayList();


        tbViewContactoDao.setItems(listaContacto);
        System.out.println(listaContacto);

        clmnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clmnPrimerApellido.setCellValueFactory(new PropertyValueFactory<>("primerApellido"));
        clmnSegundoApellido.setCellValueFactory(new PropertyValueFactory<>("segundoApellido"));

        ContactoDAO.read(listaContacto);
        search();

        showDate();


    }

    /**
     * Actualiza un registro
     */
    @FXML
    public  void updateRecord() {
        try {
            for (int i = 0; i < tbViewContactoDao.getItems().size(); i++) {
                contacto = new Contacto(
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

            int resultado = ContactoDAO.update(contacto);
            if (resultado == 1) {
                listaContacto.add(contacto);
                Alert mensaje = new Alert(Alert.AlertType.CONFIRMATION);
                mensaje.setHeaderText("Resultado:");
                mensaje.setContentText("El registro ha sido actualizado");
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
     * Borra un registro
     */
    @FXML
    public void deleteRecord(){

        contacto = tbViewContactoDao.getSelectionModel().getSelectedItem();
        int resultado = ContactoDAO.delete(contacto);

        if(resultado == 1){
            listaContacto.remove(tbViewContactoDao.getSelectionModel().getSelectedIndex());
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setContentText("El registro ha sido eliminado exitosamente");
            mensaje.setHeaderText("Resultado:");
            mensaje.show();
        }
    }

    /**
     * Busca un registro
     */
    public void search() {
        FilteredList<Contacto> filteredList = new FilteredList<>(listaContacto, b -> true);
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                filteredList.setPredicate(contacto1 -> {
                    if (t1 == null || t1.isEmpty()) {
                        return true;

                    }

                    String lowerCaseFilter = t1.toLowerCase(Locale.ROOT);
                    if (String.valueOf(contacto1.getId()).toLowerCase(Locale.ROOT).contains(lowerCaseFilter)) {
                        return true;
                    } else if (contacto1.getNombre().toLowerCase(Locale.ROOT).contains(lowerCaseFilter)) {
                        return true;
                    } else {
                        return false;
                    }
                });
            }
        });

        SortedList<Contacto> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tbViewContactoDao.comparatorProperty());
        tbViewContactoDao.setItems(sortedList);

    }

    /**
     * Muestra los datos de los registros en el table view
     */
    public void showDate() {
        tbViewContactoDao.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Contacto>() {
            @Override
            public void changed(ObservableValue<? extends Contacto> observableValue, Contacto contacto, Contacto t1) {
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
        FXMLLoader fxmlLoader = new FXMLLoader(AgendaApp.class.getResource("views/layout/MenuLayout.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.getIcons().add(new Image("/com/agendawest/icons/agenda.png"));
        stage.show();
    }

    private void closeMenu(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage root = (Stage) n.getScene().getWindow();
        root.close();
    }
}
