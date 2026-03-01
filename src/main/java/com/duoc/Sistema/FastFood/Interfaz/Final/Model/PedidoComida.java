package com.duoc.Sistema.FastFood.Interfaz.Final.Model;

/**
 * Clase que hereda lo solicitado desde Pedido agregando el tiempo personal
 * Requerimiento personal de la clase
 */

public class PedidoComida extends Pedido{

    public PedidoComida(String direccion,double distancia){
        super(direccion,distancia);
    }

    @Override
    public Requerimiento getRequerimiento(){return  Requerimiento.MOCHILA_TERMICA;}

    @Override
    public int calcularTiempoEntrega(){return (int)(15+(2* getDistancia()));
    }

    @Override
    public String getTipo(){return "Comida";}
}
