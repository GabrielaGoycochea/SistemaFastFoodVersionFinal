package com.duoc.Sistema.FastFood.Interfaz.Final.Model;

/**
 * Clase que hereda lo solicitado desde Pedido agregando el tiempo personal
 * Requerimiento personal de la clase
 */

public class PedidoExpress extends Pedido{

    public PedidoExpress(String direccion,double distancia){
        super(direccion,distancia);
    }

    @Override
    public Requerimiento getRequerimiento(){return Requerimiento.VEHICULO_MOTORIZADO;}

    @Override
    public int calcularTiempoEntrega(){
        int tiempo = 10;
        if(getDistancia()>5){tiempo += 5;}
        return tiempo;
    }
    @Override public String getTipo(){return "Express";}

}
