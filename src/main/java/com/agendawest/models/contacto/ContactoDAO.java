package com.agendawest.models.crud;

import com.agendawest.models.dao.Contacto;
import javafx.collections.ObservableList;

import java.sql.*;

public class ContactoDAO {

    private ContactoDAO() {
    }

    public static int create(Connection connection, Contacto contacto) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                                        
                    INSERT
                        INTO
                            Contacto(
                                      Nombre,
                                      Primer_Apellido,
                                      Segundo_Apellido,
                                      Direccion,
                                      Ciudad,
                                      Codigo_Postal,
                                      Fecha_Nacimiento
                                      
                                      )
                        VALUES(?,?,?,?,?,?,?);
                                        
                    """);
            preparedStatement.setString(1, contacto.getNombre());
            preparedStatement.setString(2, contacto.getPrimerApellido());
            preparedStatement.setString(3, contacto.getSegundoApellido());
            preparedStatement.setString(4, contacto.getDireccion());
            preparedStatement.setString(5, contacto.getCiudad());
            preparedStatement.setString(6, contacto.getCodigoPostal());
            preparedStatement.setDate(7, contacto.getFechaNacimiento());
            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void read(Connection connection, ObservableList<Contacto> lista) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("""
                    SELECT
                            id,
                           Nombre,
                           Primer_Apellido,
                           Segundo_Apellido,
                           Direccion,
                           Ciudad,
                           Codigo_Postal,
                           Fecha_Nacimiento
                       
                       FROM
                           Contacto;
                                        
                    """);
            while (resultSet.next()) {
                lista.add(
                        new Contacto(
                                resultSet.getInt("id"),
                                resultSet.getString("Nombre"),
                                resultSet.getString("Primer_Apellido"),
                                resultSet.getString("Segundo_Apellido"),
                                resultSet.getString("Direccion"),
                                resultSet.getString("Ciudad"),
                                resultSet.getString("Codigo_Postal"),
                                resultSet.getDate("Fecha_Nacimiento")
                        )
                );

            }


        } catch (SQLException s) {
            s.printStackTrace();
        }

    }

    public static int update(Connection connection, Contacto contacto){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    UPDATE Contacto
                        SET Nombre = ?,
                            Primer_Apellido = ?,
                            Segundo_Apellido = ?,
                            Direccion = ?,
                            Ciudad = ?,
                            Codigo_Postal = ?,
                            Fecha_Nacimiento = ?
                        WHERE 
                            id = ?
                    """);
            preparedStatement.setString(1, contacto.getNombre());
            preparedStatement.setString(2,contacto.getPrimerApellido());
            preparedStatement.setString(3,contacto.getSegundoApellido());
            preparedStatement.setString(4, contacto.getDireccion());
            preparedStatement.setString(5,contacto.getCiudad());
            preparedStatement.setString(6,contacto.getCodigoPostal());
            preparedStatement.setDate(7,contacto.getFechaNacimiento());
            preparedStatement.setInt(8,contacto.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }

    public static int delete(Connection connection, Contacto contacto){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    DELETE FROM Contacto
                        WHERE 
                            id = ?
                    """);
            preparedStatement.setInt(1, contacto.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }

}

