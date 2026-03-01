package com.duoc.Sistema.FastFood.Interfaz.Final.UI;

import com.duoc.Sistema.FastFood.Interfaz.Final.Conexion.ConexionBBDD;
import com.duoc.Sistema.FastFood.Interfaz.Final.Gestor.ControladorPedido;
import com.duoc.Sistema.FastFood.Interfaz.Final.Model.*;
import com.duoc.Sistema.FastFood.Interfaz.Final.Model.Repartidor;
import com.duoc.Sistema.FastFood.Interfaz.Final.Model.Requerimiento;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Clase para ejecución de programa, verifica la conexión a la bbdd por consola.
 * Se encuentran cargados por defecto pedidos y repartidores
 */


public class Main {

    public static void main(String[] args) {

        try (Connection conexion = ConexionBBDD.obtenerConexion()) {
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos:");
            e.printStackTrace();
        }



        SwingUtilities.invokeLater(() -> {
            ControladorPedido controladorPedido = new ControladorPedido();

            if (controladorPedido.obtenerPedidos().isEmpty()) {
                controladorPedido.agregarPedido(new PedidoComida("Av. Macul 2356", 3));
                controladorPedido.agregarPedido(new PedidoEnvio("Calle Uno 465", 7, 5));
                controladorPedido.agregarPedido(new PedidoExpress("Universidad Católica", 4));
                System.out.println("Pedidos de prueba insertados en la BD.");
            }

            new VentanaPrincipal(controladorPedido);

            if(controladorPedido.obtenerRepartidor().isEmpty()){
                controladorPedido.agregarRepartidor(new Repartidor("Roberto", Requerimiento.MOCHILA_TERMICA));
                controladorPedido.agregarRepartidor(new Repartidor("Julian", Requerimiento.VEHICULO_CARGA));
                controladorPedido.agregarRepartidor(new Repartidor("Mariela", Requerimiento.VEHICULO_MOTORIZADO));
                System.out.println("REPARTIDORES DE PRUEBA INSERTADOS");}

            });
        }




}