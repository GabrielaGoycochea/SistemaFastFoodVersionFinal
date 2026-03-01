package com.duoc.Sistema.FastFood.Interfaz.Final.UI;

import com.duoc.Sistema.FastFood.Interfaz.Final.Gestor.ControladorPedido;
import com.duoc.Sistema.FastFood.Interfaz.Final.Model.Pedido;
import com.duoc.Sistema.FastFood.Interfaz.Final.Model.PedidoComida;
import com.duoc.Sistema.FastFood.Interfaz.Final.Model.PedidoEnvio;
import com.duoc.Sistema.FastFood.Interfaz.Final.Model.PedidoExpress;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana para registrar pedidos.
 * Solicita ID y direccion y tipo de pedido // No permite guardar información ya ingresada.
 */


public class VentanaRegistroPedido extends JFrame {

    public VentanaRegistroPedido(ControladorPedido controladorPedido){

        setTitle("Registrar Pedido");
        setSize(500,250);
        setLayout(new GridLayout(4,2,15,15));
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JTextField txtDireccion = new JTextField();

        JComboBox<String> comboTipo = new JComboBox<>(new String[]{"Comida","Encomienda","Express"});

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {

            try {
                if (txtDireccion.getText().isEmpty()) {
                    throw new IllegalArgumentException("Campo dirección vacío");
                }

                String direccion = txtDireccion.getText();
                String tipo = (String) comboTipo.getSelectedItem();

                Pedido pedido;

                switch (tipo) {
                    case "Comida" -> pedido = new PedidoComida(direccion, 5);
                    case "Encomienda" -> pedido = new PedidoEnvio(direccion, 10, 2);
                    default -> pedido = new PedidoExpress(direccion, 2.5);
                }

                controladorPedido.agregarPedido(pedido);

                JOptionPane.showMessageDialog(this,
                        "Pedido registrador correctamente al ID: " +pedido.getId());
                dispose();

            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this, "Error: "+exception.getMessage());
            }

        } );

        add(new JLabel("Dirección:"));
        add(txtDireccion);
        add(new JLabel("Tipo:"));
        add(comboTipo);
        add(btnGuardar);

        setVisible(true);

    }
}
