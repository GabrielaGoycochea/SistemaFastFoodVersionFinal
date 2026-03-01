package com.duoc.Sistema.FastFood.Interfaz.Final.Model;


/**
 * Clase abstracta principal para los datos del pedido.
 * Incluye requerimientos para los pedidos, estado, distancia, tipo, tiempo.
 */

public abstract class Pedido {
    private int id;
    private final String direccion;
    private final double distancia;

    private EstadoPedido estado;
    private String repartidorAsignado;

    public Pedido(String direccion,double distancia){
        this.direccion=direccion;
        this.distancia=distancia;
        this.estado= EstadoPedido.PENDIENTE;
    }

    public void cancelar() {
        if (estado == EstadoPedido.ENTREGADO) {
            throw new IllegalStateException("No es posible cancelar un pedido entregado");
        }
        estado = EstadoPedido.CANCELADO;
    }

    public void enReparto(String nombreRepartidor){
        if(estado!= EstadoPedido.PENDIENTE){
            throw new IllegalStateException("Pedido no esta disponible para reparto");
        }
        this.repartidorAsignado=nombreRepartidor;
        this.estado=EstadoPedido.EN_REPARTO;
    }

    public void entregado(){
        if(estado!= EstadoPedido.EN_REPARTO){
            throw new IllegalStateException("El pedido no se encuentra en reparto");
        }
        this.estado=EstadoPedido.ENTREGADO;
    }
    public int getId(){return id;}
    public String getDireccion(){return direccion;}
    public double getDistancia(){return distancia;}
    public EstadoPedido getEstado(){return estado;}
    public String getRepartidorAsignado(){return repartidorAsignado;}


    public abstract Requerimiento getRequerimiento();
    public abstract int calcularTiempoEntrega();
    public abstract String getTipo();


    public void setEstado(EstadoPedido estado){this.estado= estado;}
    public void setRepartidorAsignado(String repartidor){this.repartidorAsignado=repartidor;}

    public void setId(int id){this.id = id;}

    @Override
    public String toString(){
        return getId() + " - " +getDireccion() + " ("+ getEstado()+ ")";
    }




}
