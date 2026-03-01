package com.duoc.Sistema.FastFood.Interfaz.Final.Gestor.DAO;

import com.duoc.Sistema.FastFood.Interfaz.Final.Conexion.ConexionBBDD;
import com.duoc.Sistema.FastFood.Interfaz.Final.Model.Repartidor;
import com.duoc.Sistema.FastFood.Interfaz.Final.Model.Requerimiento;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Permite registro de atributos de repartidor como tipo de vehículo para asi cumplir el requerimiento
 * y si se encuentra disponible para evitar sobrecargar a un repartidor de + de 1 pedido
 * Actualiza el estado del repartidor, en la bbdd al entregar el pedido vuelve a estar disponible.
 *
 */
public class RepartidorDAO {

    public void guardar(Repartidor repartidor){
        String sql = "INSERT INTO repartidor (nombre,tipo_vehiculo,disponible) VALUES (?,?,?)";

        try(Connection connection = ConexionBBDD.obtenerConexion();
            PreparedStatement stnt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            stnt.setString(1,repartidor.getNombre());
            stnt.setString(2,repartidor.getTipoVehiculo().name());
            stnt.setBoolean(3,true);
            stnt.executeUpdate();


        try(ResultSet idGenerado = stnt.getGeneratedKeys()){
            if(idGenerado.next()){
                repartidor.setId(idGenerado.getInt(1));}
        }


        }catch (SQLException exception){exception.printStackTrace();}
    }


public List<Repartidor> listadoRepartidor(){
    List<Repartidor> lista = new ArrayList<>();
    String sql = "SELECT * FROM repartidor";

    try( Connection connection = ConexionBBDD.obtenerConexion();
    PreparedStatement stnt = connection.prepareStatement(sql);
         ResultSet resultSet = stnt.executeQuery()){

        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String nombre = resultSet.getString("nombre");
            String tipo = resultSet.getString("tipo_vehiculo");
            boolean disponible = resultSet.getBoolean("disponible");

            Requerimiento requerimiento = Requerimiento.valueOf(tipo);

            Repartidor repartidor = new Repartidor(nombre, requerimiento);
            repartidor.setId(id);
            if(!disponible){repartidor.ocupar();}
            lista.add(repartidor);}

    }catch (SQLException excepcion){excepcion.printStackTrace();}
    return  lista;
    }

    public void actualizarDispo(Connection connection,String nombre,boolean disponible) throws SQLException{
        String sql = "UPDATE repartidor SET disponible = ? WHERE nombre = ?";

        try(PreparedStatement stnt = connection.prepareStatement(sql)){
            stnt.setBoolean(1, disponible);
            stnt.setString(2, nombre);
            stnt.executeUpdate();
        }

    }

    public void actualizar(Repartidor repartidor){
        String sql = "UPDATE repartidor SET nombre=?, tipo_vehiculo = ? , disponible=? WHERE id=?";
        try(Connection connection = ConexionBBDD.obtenerConexion();
        PreparedStatement stnt = connection.prepareStatement(sql)) {

            stnt.setString(1, repartidor.getNombre());
            stnt.setString(2, repartidor.getTipoVehiculo().name());
            stnt.setBoolean(3, repartidor.estaDisponible());
            stnt.setInt(4, repartidor.getId());
            stnt.executeUpdate();

        }catch (SQLException exception){
            JOptionPane.showMessageDialog(null,"Error al actualizar el Repartidor");
        }

    }

    public void eliminar(int id){
        String sql = "DELETE FROM repartidor WHERE id=? ";
        try(Connection connection = ConexionBBDD.obtenerConexion();
        PreparedStatement stnt = connection.prepareStatement(sql)){

            stnt.setInt(1,id);
            stnt.executeUpdate();

        }catch
        (SQLException exception){JOptionPane.showMessageDialog(null, "Error al eliminar Repartidor");}
    }


}
