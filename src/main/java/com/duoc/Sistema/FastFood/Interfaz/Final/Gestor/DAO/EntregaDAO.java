package com.duoc.Sistema.FastFood.Interfaz.Final.Gestor.DAO;


import com.duoc.Sistema.FastFood.Interfaz.Final.Conexion.ConexionBBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Permite guardar información en la base de datos sobre los pedidos ingresados en la interfaz.
 */


public class EntregaDAO {

    public void guardar(Connection connection,int idPedido, int idRepartidor, String estado) throws SQLException {
        String sql = "INSERT INTO entrega (id_pedido,id_repartidor,estado_entrega) VALUES (?,?,?)";

        try(PreparedStatement stnt = connection.prepareStatement(sql)){

            stnt.setInt(1,idPedido);
            stnt.setInt(2,idRepartidor);
            stnt.setString(3, estado);
            stnt.executeUpdate();

        }catch (SQLException exception){
            throw exception;
        }
    }

    public void actEstadoEntrega(Connection connection, int idPedido, String nuevoEstado) throws SQLException {
        String sql = "UPDATE entrega SET estado_entrega = ? WHERE id_pedido = ?";

        try (PreparedStatement stnt = connection.prepareStatement(sql)) {
            stnt.setString(1, nuevoEstado);
            stnt.setInt(2, idPedido);
            stnt.executeUpdate();
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM entrega WHERE id=? ";
        try (Connection connection = ConexionBBDD.obtenerConexion();
             PreparedStatement stnt = connection.prepareStatement(sql)) {

            stnt.setInt(1, id);
            stnt.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();}

    }
    public List<String> listado() {
        List<String> listaEntrega = new ArrayList<>();
        String sql = "SELECT * FROM entrega";

        try (Connection connection = ConexionBBDD.obtenerConexion();
             PreparedStatement stnt = connection.prepareStatement(sql);
             ResultSet resultSet = stnt.executeQuery()) {

            while (resultSet.next()) {listaEntrega.add(
                        "Pedido: " + resultSet.getInt("id_pedido") + " | " +
                        "Repartidor: " + resultSet.getInt("id_repartidor") + " | " +
                         "Fecha/Hora: " + resultSet.getTimestamp("fecha_hora"));
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return listaEntrega;
    }



    }












