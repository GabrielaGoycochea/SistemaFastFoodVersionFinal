package com.duoc.Sistema.FastFood.Interfaz.Final.Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de gestionar la conexión con la base de datos MySQL.
 * Se agregan las conexiones commit para vincular y sincronizar la bbdd y actualizacion de estado
 */
public class ConexionBBDD {

    private static final String URL = "jdbc:mysql://localhost:3306/FastFoodBD";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "qwe123";


    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }

    public static void iniciarTrs(Connection connection) throws SQLException {
        connection.setAutoCommit(false);
    }

    public static void confirmarTrs(Connection connection) throws SQLException {
        connection.commit();
    }

    public static void deshacerTrs(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.rollback();
        }
    }

    public static void cerrarConex(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}

