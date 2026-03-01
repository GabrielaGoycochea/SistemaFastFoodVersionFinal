package com.duoc.Sistema.FastFood.Interfaz.Final.Gestor.DAO;

import com.duoc.Sistema.FastFood.Interfaz.Final.Conexion.ConexionBBDD;
import com.duoc.Sistema.FastFood.Interfaz.Final.Model.*;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *Permite agregar pedidos a traves de la interfaz, utilizando el autoincremento asignado en la bbdd
 * La lista de los pedidos por tipo viene por defecto sengun la elección, por el momento siempre sera la misma distancia.
 * Actualiza la vista de la base de datos segun el tiempo entregado.
 *
 */
public class PedidoDAO {

    public void guardar(Pedido pedido) {
        String sql = "INSERT INTO pedido (direccion ,tipo ,estado ,distancia ) VALUES (?,?,?,?)";

        try (Connection connection = ConexionBBDD.obtenerConexion();
             PreparedStatement stnt = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {

            stnt.setString(1,pedido.getDireccion());
            stnt.setString(2,pedido.getTipo());
            stnt.setString(3,pedido.getEstado().name());
            stnt.setDouble(4, pedido.getDistancia());
            stnt.executeUpdate();

            try (ResultSet generatedKeys = stnt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pedido.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException exception) {exception.printStackTrace();
            JOptionPane.showMessageDialog(null,"Error al editar la base de datos.");
        }
    }

    public List<Pedido> listadoPedido() {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedido";

        try (Connection connection = ConexionBBDD.obtenerConexion();
             PreparedStatement stnt = connection.prepareStatement(sql);
             ResultSet resultSet = stnt.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String direccion = resultSet.getString("direccion");
                String tipo = resultSet.getString("tipo");
                double distancia = resultSet.getDouble("distancia");
                String estado = resultSet.getString("estado");
                String repartidor = resultSet.getString("repartidor");

                Pedido pedido;

                switch (tipo) {
                    case "Comida" -> pedido = new PedidoComida(direccion,distancia);
                    case "Encomienda" -> pedido = new PedidoEnvio(direccion,distancia, 2);
                    default -> pedido = new PedidoExpress(direccion, distancia);
                }
                pedido.setId(id);
                pedido.setEstado(EstadoPedido.valueOf(estado));
                pedido.setRepartidorAsignado(repartidor);
                lista.add(pedido);
            }


        }catch (SQLException excepcion){ excepcion.printStackTrace();}
        return lista;
    }

    public void actEstadoPedido(Connection connection, int idPedido, String nuevoEstado, String repartidor) throws SQLException {
        String sql = "UPDATE pedido SET estado = ?, repartidor = ? WHERE id = ?";

        try (PreparedStatement stnt = connection.prepareStatement(sql)) {
            stnt.setString(1, nuevoEstado);
            stnt.setString(2, repartidor);
            stnt.setInt(3, idPedido);
            stnt.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw exception;
        }

    }


    public Pedido buscarporId(int id){
        return listadoPedido().stream()
                .filter(p->p.getId() == id)
                .findFirst().orElse(null);

    }


    public void actualizar(Pedido pedido) {
        String sql = "UPDATE pedido SET direccion=?, tipo=?, estado=? WHERE id=?";
        try (Connection connection = ConexionBBDD.obtenerConexion();
             PreparedStatement stnt = connection.prepareStatement(sql)) {

            stnt.setString(1, pedido.getDireccion());
            stnt.setString(2, pedido.getTipo());
            stnt.setString(3, pedido.getEstado().name());
            stnt.setInt(4, pedido.getId());
            stnt.executeUpdate();

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, "ERROR al actualizar el pedido.");
        }
    }

    public void eliminar(int id){
        String sql = "DELETE FROM pedido WHERE id=?";
        try(Connection connection = ConexionBBDD.obtenerConexion();
            PreparedStatement stnt = connection.prepareStatement(sql)){

            stnt.setInt(1,id);
            stnt.executeUpdate();

        }catch (SQLException exception){
            JOptionPane.showMessageDialog(null,"Error, no se ha podido eliminar el pedido");
        }


    }

    public List<Pedido> filtroEstado(String estado) {
        List<Pedido> filtroLista = new ArrayList<>();
        String sql = " SELECT * FROM pedido WHERE estado=?";

        try (Connection connection = ConexionBBDD.obtenerConexion();
             PreparedStatement stnt = connection.prepareStatement(sql)) {

            stnt.setString(1, estado);
            ResultSet resultSet = stnt.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String direccion = resultSet.getString("direccion");
                String tipo = resultSet.getString("tipo");

                Pedido pedido;

            switch (tipo){
                case "Comida"-> pedido = new PedidoComida(direccion,5);
                case "Encomienda" -> pedido = new PedidoEnvio(direccion,10,2);
                default ->  pedido = new PedidoExpress(direccion,2);
            }

            pedido.setId(id);
            pedido.setEstado(EstadoPedido.valueOf(estado));
            filtroLista.add(pedido);}

        } catch (SQLException exception) {exception.printStackTrace();}

        return filtroLista;
    }



}







