package com.duoc.Sistema.FastFood.Interfaz.Final.UI;

import com.duoc.Sistema.FastFood.Interfaz.Final.Gestor.ControladorPedido;
import com.duoc.Sistema.FastFood.Interfaz.Final.Model.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Ventana para Mostrar la lista de pedidos
 * Muestra un recuadro con el ID, TIPO, DIRECCION, ESTADO, NOMBRE DE REPARTIDOR
 * Y permite actualizar, pero los pedidos se actualizan por cuenta propia al cumplirse el tiempo de entrega.
 */


public class VentanaListaPedidos extends JFrame {

    private DefaultTableModel modelo;

    public VentanaListaPedidos(ControladorPedido controladorPedido){

        setTitle("Lista de Pedidos");
        setSize(500,250);

        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Tipo");
        modelo.addColumn("Dirección");
        modelo.addColumn("Estado");
        modelo.addColumn("Repartidor");

        JTable tabla = new JTable(modelo);
        setLayout(new BorderLayout());
        add(new JScrollPane(tabla),BorderLayout.CENTER);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> cargarDatos(controladorPedido));
        add(btnActualizar,BorderLayout.SOUTH);

        controladorPedido.setVentanaListaPedidos(this);
        new Timer(7000, e -> cargarDatos(controladorPedido)).start();

        cargarDatos(controladorPedido);
        setVisible(true);
    }

    public void cargarDatos(ControladorPedido controladorPedido){
        modelo.setRowCount(0);

        for(Pedido pedido : controladorPedido.obtenerPedidos()){
            modelo.addRow(new Object[]{
                    pedido.getId(),
                    pedido.getTipo(),
                    pedido.getDireccion(),
                    pedido.getEstado(),
                    pedido.getRepartidorAsignado()});
        }


    }


}
