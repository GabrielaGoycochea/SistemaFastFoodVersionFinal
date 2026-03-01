package com.duoc.Sistema.FastFood.Interfaz.Final.UI;

import com.duoc.Sistema.FastFood.Interfaz.Final.Gestor.ControladorPedido;
import com.duoc.Sistema.FastFood.Interfaz.Final.Model.Pedido;

import javax.swing.*;
import java.awt.*;

/**
 * GENERA UNA VENTANA.
 * Se asigna un nombre de repartidor al pedido. La asignación es opcional.
 * Si no se asigna manual lo realiza de manera automatica.
 */

public class VentanaAsignarRepartidor extends JFrame {

    public VentanaAsignarRepartidor(ControladorPedido controladorPedido){

        setTitle("Asignar Repartidor");
        setSize(500,250);
        setLayout(new GridLayout(4,2,15,15));
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JTextField txtNombre = new JTextField();
        JTextField txtId = new JTextField();
        JButton btnAsignar = new JButton("Asignar e iniciar");

        btnAsignar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                Pedido pedido = controladorPedido.buscarPorId(id);

                if (pedido == null) {
                    JOptionPane.showMessageDialog(this,"Pedido no encontrado");
                   return;
                }
                String nombre = txtNombre.getText().trim();
                if(nombre.isEmpty()) {
                    controladorPedido.asignarAuto(pedido);
                }else{ controladorPedido.asignarManual(pedido,nombre);}

                JOptionPane.showMessageDialog(this, "Repartidor asignado correctamente.");
                dispose();

            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(this, "Debe indicar un ID válido.");
            } catch (Exception ex){
                JOptionPane.showMessageDialog(this,"Error: "+ex.getMessage());
            }
            });

        add(new JLabel("ID Pedido: "));
        add(txtId);
        add(new JLabel("Nombre Repartidor (opcional): "));
        add(txtNombre);

        add(new JLabel());
        add(btnAsignar);

        setVisible(true);

    }
}
