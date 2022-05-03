package com.agendawest.models.dao;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.jetbrains.annotations.NotNull;

import java.sql.*;

public class ContactoDAO {

    private IntegerProperty id;
    private StringProperty nombre;
    private StringProperty primerApellido;
    private StringProperty segundoApellido;
    private StringProperty direccion;
    private StringProperty ciudad;
    private StringProperty codigoPostal;
    private Date fechaNacimiento;

    public ContactoDAO(Integer id, String nombre, String primerApellido,
                       String segundoApellido, String direccion, String ciudad,
                       String codigo_postal, Date fechaNacimiento){
        setId(id);
        setNombre(nombre);
        setPrimerApellido(primerApellido);
        setSegundoApellido(segundoApellido);
        setDireccion(direccion);
        setCiudad(ciudad);
        setCodigoPostal(codigo_postal);
        setFechaNacimiento(fechaNacimiento);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public String getNombre() {
        return nombre.get();
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public void setNombre(@NotNull String nombre) {
        if(nombre.isEmpty()){
            throw new IllegalArgumentException("Error el nombre no puede estar vacio");
        }
        if(nombre.equalsIgnoreCase("null")){
            throw new NullPointerException("Error el nombre no puede ser null");
        }

        this.nombre = new SimpleStringProperty(nombre);
    }

    public String getPrimerApellido() {
        return primerApellido.get();
    }

    public StringProperty primerApellidoProperty() {
        return primerApellido;
    }

    public void setPrimerApellido(@NotNull String primerApellido) {
        if(primerApellido.isEmpty()){
            throw new IllegalArgumentException("Error el primer apellido no puede estar vacio");
        }

        this.primerApellido = new SimpleStringProperty(primerApellido);
    }

    public String getSegundoApellido() {
        return segundoApellido.get();
    }

    public StringProperty segundoApellidoProperty() {
        return segundoApellido;
    }

    public void setSegundoApellido(@NotNull String segundoApellido) {
        if(segundoApellido.isEmpty()){
            throw new IllegalArgumentException("Error el segundo apellido no puede estar vacio");
        }
        this.segundoApellido = new SimpleStringProperty(segundoApellido);
    }

    public String getDireccion() {
        return direccion.get();
    }

    public StringProperty direccionProperty() {
        return direccion;
    }

    public void setDireccion(@NotNull String direccion) {
        if(direccion.isEmpty()){
            throw new IllegalArgumentException("Error la direccion no puede estar vacio");
        }
        this.direccion = new SimpleStringProperty(direccion);
    }

    public String getCiudad() {
        return ciudad.get();
    }

    public StringProperty ciudadProperty() {
        return ciudad;
    }

    public void setCiudad(@NotNull String ciudad) {
        if(ciudad.isEmpty()){
            throw new IllegalArgumentException("Error la ciudad no puede estar vacio");
        }
        this.ciudad = new SimpleStringProperty(ciudad);
    }

    public String getCodigoPostal() {
        return codigoPostal.get();
    }

    public StringProperty codigoPostalProperty() {
        return codigoPostal;
    }

    public void setCodigoPostal(@NotNull String codigoPostal) {
        if(codigoPostal.isEmpty()){
            throw new IllegalArgumentException("Error la codigo postal no puede estar vacio");
        }
        this.codigoPostal = new SimpleStringProperty(codigoPostal);
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(@NotNull Date fechaNacimiento) {
        if(fechaNacimiento.toString().isEmpty()){
            throw new IllegalArgumentException("Error la fecha de nacimiento no puede estar vacio");
        }
        this.fechaNacimiento = fechaNacimiento;
    }


    @Override
    public String toString() {
        return "ContactoDAO{" +
                "id=" + id.get() +
                ", nombre=" + nombre.get() +
                ", primerApellido=" + primerApellido.get() +
                ", segundoApellido=" + segundoApellido.get() +
                ", direccion=" + direccion.get() +
                ", ciudad=" + ciudad.get() +
                ", codigoPostal=" + codigoPostal.get() +
                ", fechaNacimiento=" + fechaNacimiento +
                '}';
    }
}

