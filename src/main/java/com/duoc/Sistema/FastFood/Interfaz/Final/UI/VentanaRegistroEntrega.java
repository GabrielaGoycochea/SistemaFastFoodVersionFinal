package com.duoc.Sistema.FastFood.Interfaz.Final.UI;

import com.duoc.Sistema.FastFood.Interfaz.Final.Gestor.ControladorPedido;
import com.duoc.Sistema.FastFood.Interfaz.Final.Model.Pedido;
import com.duoc.Sistema.FastFood.Interfaz.Final.Model.Repartidor;

import javax.swing.*;
import java.awt.*;


public class VentanaRegistroEntrega extends JFrame {

    private JComboBox <Pedido> comboPedido;
    private JComboBox<Repartidor> comboRepartidor;

public VentanaRegistroEntrega(ControladorPedido controladorPedido){

    setTitle("Registrar Entrega");
    setSize(500,250);
    setLocationRelativeTo(null);
    setLayout(new GridLayout(4,2,10,10));
    ((JPanel)getContentPane()).setBorder(
            BorderFactory.createEmptyBorder(20,20,20,20));

    comboPedido = new JComboBox<>();
    comboRepartidor = new JComboBox<>();

    JButton btnGuardar = new JButton("Registrar entrega");
    JButton btnCancelar = new JButton("Cancelar");

    for(Pedido pedido : controladorPedido.obtenerPedidos()) {
    if(pedido.getEstado().name().equals("PENDIENTE")){
        comboPedido.addItem(pedido);}
    }

    for(Repartidor repartidor : controladorPedido.obtenerRepartidor()){
        if(repartidor.estaDisponible()){
            comboRepartidor.addItem(repartidor);}
    }

    btnGuardar.addActionListener(e -> {
                try {
                    Pedido pedido = (Pedido) comboPedido.getSelectedItem();
                    Repartidor repartidor = (Repartidor) comboRepartidor.getSelectedItem();

                    if (pedido == null || repartidor == null) {
                        throw new IllegalArgumentException
                                ("Se debe seleccionar un pedido y repartidor disponibles");
                    }

                    try {
                        controladorPedido.registrarEntregaManual(pedido.getId(), repartidor.getId());
                        controladorPedido.actualizarLista();
                        JOptionPane.showMessageDialog(this, "Entrega registrada correctamente.");
                        dispose();
                    } catch (Exception sqlEX) {
                        sqlEX.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Error: " + sqlEX.getMessage());
                    }

                } catch (IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(this, exception.getMessage());
                } catch (Exception exception) {
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error inesperado" + exception.getMessage());
                }
            });


    btnCancelar.addActionListener(e -> dispose());

    add(new JLabel("Seleccione Pedido: "));
    add(comboPedido);

    add(new JLabel("Seleccione Repartidor: "));
    add(comboRepartidor);

    add(btnCancelar);
    add(btnGuardar);

    setVisible(true);
    }

}
