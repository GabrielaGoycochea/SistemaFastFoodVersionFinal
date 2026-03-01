package com.duoc.Sistema.FastFood.Interfaz.Final.Gestor;

import com.duoc.Sistema.FastFood.Interfaz.Final.Conexion.ConexionBBDD;
import com.duoc.Sistema.FastFood.Interfaz.Final.Gestor.DAO.EntregaDAO;
import com.duoc.Sistema.FastFood.Interfaz.Final.Gestor.DAO.PedidoDAO;
import com.duoc.Sistema.FastFood.Interfaz.Final.Gestor.DAO.RepartidorDAO;
import com.duoc.Sistema.FastFood.Interfaz.Final.Model.EstadoPedido;
import com.duoc.Sistema.FastFood.Interfaz.Final.Model.Pedido;
import com.duoc.Sistema.FastFood.Interfaz.Final.Model.Repartidor;
import com.duoc.Sistema.FastFood.Interfaz.Final.UI.VentanaListaPedidos;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * El controladorPedido permite gestionar la lista de pedidos y repartidores
 * Permite agregar pedidos y agregar repartidores, asignación automatica y manual (opcional)
 * Muestra el listado de pedidos ingresados desde la app o la base de datos.
 * Los hilos permite la sincronización con la bbdd y la actualización de la misma.
 *
 */

public class ControladorPedido {

    private final PedidoDAO pedidoDAO = new PedidoDAO();
    private final RepartidorDAO repartidorDAO = new RepartidorDAO();
    private final EntregaDAO entregaDAO = new EntregaDAO();
    private VentanaListaPedidos ventanaListaPedidos;

    public void setVentanaListaPedidos(VentanaListaPedidos ventanaListaPedidos) {
        this.ventanaListaPedidos = ventanaListaPedidos;
    }


    public void agregarPedido(Pedido pedido) {
        boolean existe = pedidoDAO.listadoPedido().stream().
                anyMatch(p -> p.getId() == pedido.getId());

        if (!existe) {
            pedidoDAO.guardar(pedido);
            actualizarLista();
        }
    }

    public void agregarRepartidor(Repartidor repartidor) {
        boolean existe = repartidorDAO.listadoRepartidor().stream()
                .anyMatch(r -> r.getId() == repartidor.getId());

        if (!existe) {
            repartidorDAO.guardar(repartidor);
        }
    }

    public List<Pedido> obtenerPedidos() {
        return pedidoDAO.listadoPedido();
    }

    public List<Repartidor> obtenerRepartidor() {
        return repartidorDAO.listadoRepartidor();
    }

    public void actualizarLista() {
        if (ventanaListaPedidos != null) {
            SwingUtilities.invokeLater(() -> ventanaListaPedidos.cargarDatos(this));
        }
    }

    public void asignarAuto(Pedido pedido) throws SQLException {
        List<Repartidor> repartidores = repartidorDAO.listadoRepartidor();
        for (Repartidor repartidor : repartidores) {
            if (repartidor.estaDisponible() && repartidor.getTipoVehiculo() == pedido.getRequerimiento()) {
                asignarPedido(pedido, repartidor);
                return;
            }
        }
        throw new IllegalStateException("No hay repartidores disponibles para el pedido");
    }

    public void asignarManual(Pedido pedido, String nombreRepartidor) throws SQLException {
        Repartidor repartidor = obtenerRepartidor().stream()
                .filter(r -> r.getNombre().equalsIgnoreCase(nombreRepartidor))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Repartidor no encontrado"));
        if (!repartidor.estaDisponible()) {
            throw new IllegalStateException("Repartidor no disponible");
        }
        asignarPedido(pedido, repartidor);
    }

    public void asignarPedido(Pedido pedido, Repartidor repartidor) throws SQLException {
        Connection connection = null;
        try {
            connection = ConexionBBDD.obtenerConexion();
            ConexionBBDD.iniciarTrs(connection);

            pedido.enReparto(repartidor.getNombre());
            repartidor.ocupar();

            pedidoDAO.actEstadoPedido(connection, pedido.getId(), EstadoPedido.EN_REPARTO.name(), repartidor.getNombre());
            repartidorDAO.actualizarDispo(connection,repartidor.getNombre(), false);
            entregaDAO.guardar(connection, pedido.getId(), repartidor.getId(), EstadoPedido.EN_REPARTO.name());

            ConexionBBDD.confirmarTrs(connection);
            actualizarLista();


            new Thread(() -> {
                try (Connection hiloConnection = ConexionBBDD.obtenerConexion()) {
                    long tiempo = pedido.calcularTiempoEntrega() * 1000L;
                    System.out.println("Tiempo de entrega: " + (tiempo / 1000) + " segundos");

                    Thread.sleep(tiempo);
                    ConexionBBDD.iniciarTrs(hiloConnection);

                    pedido.entregado();
                    repartidor.liberar();

                    pedidoDAO.actEstadoPedido(hiloConnection, pedido.getId(), EstadoPedido.ENTREGADO.name(), repartidor.getNombre());
                    entregaDAO.actEstadoEntrega(hiloConnection, pedido.getId(), EstadoPedido.ENTREGADO.name());
                    repartidorDAO.actualizarDispo(hiloConnection,repartidor.getNombre(), true);

                    ConexionBBDD.confirmarTrs(hiloConnection);

                    actualizarLista();

                } catch (InterruptedException | SQLException ex) {
                    ex.printStackTrace();
                }
            }).start();

        } catch (SQLException e) {
            if (connection != null && !connection.isClosed()) {
                try {
                    ConexionBBDD.deshacerTrs(connection);
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al asignar el pedido: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    ConexionBBDD.cerrarConex(connection);
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }

    public Pedido buscarPorId(int id) {
        return pedidoDAO.listadoPedido()
                .stream().filter(pedido -> pedido.getId() == id)
                .findFirst().orElse(null);
    }

    public void registrarEntregaManual(int idPedido, int idRepartidor) {
        Connection connection = null;
        try {
            connection = ConexionBBDD.obtenerConexion();
            ConexionBBDD.iniciarTrs(connection);

            Pedido pedido = buscarPorId(idPedido);
            Repartidor repartidor = obtenerRepartidor().stream()
                    .filter(r -> r.getId() == idRepartidor)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Repartidor no encontrado"));

            pedido.enReparto(repartidor.getNombre());
            repartidor.ocupar();

            repartidorDAO.actualizarDispo(connection,repartidor.getNombre(), false);
            pedidoDAO.actEstadoPedido(connection, pedido.getId(), EstadoPedido.EN_REPARTO.name(), repartidor.getNombre());
            entregaDAO.guardar(connection, pedido.getId(), repartidor.getId(), EstadoPedido.EN_REPARTO.name());

            ConexionBBDD.confirmarTrs(connection);

            actualizarLista();
            JOptionPane.showMessageDialog(null, "Pedido en reparto. Se entregará automáticamente.");

            new Thread(() -> {
                try (Connection hiloConnection = ConexionBBDD.obtenerConexion()) {
                    long tiempo = pedido.calcularTiempoEntrega() * 1000L;
                    System.out.println("Tiempo de entrega manual: " + tiempo / 1000 + " segundos");

                    Thread.sleep(tiempo);

                    ConexionBBDD.iniciarTrs(hiloConnection);

                    pedido.entregado();
                    repartidor.liberar();

                    pedidoDAO.actEstadoPedido(hiloConnection, pedido.getId(), EstadoPedido.ENTREGADO.name(), repartidor.getNombre());
                    entregaDAO.actEstadoEntrega(hiloConnection, pedido.getId(), EstadoPedido.ENTREGADO.name());
                    repartidorDAO.actualizarDispo(hiloConnection,repartidor.getNombre(), true);

                    ConexionBBDD.confirmarTrs(hiloConnection);

                    actualizarLista();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

            JOptionPane.showMessageDialog(null,
                    "Pedido en reparto. Se entregará automáticamente.");

        } catch (Exception e) {

            if (connection != null) {
                try {
                    ConexionBBDD.deshacerTrs(connection);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error al registrar entrega: " + e.getMessage());

        } finally {
            if (connection != null) {
                try {
                    ConexionBBDD.cerrarConex(connection);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}