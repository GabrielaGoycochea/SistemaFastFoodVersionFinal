package com.duoc.Sistema.FastFood.Interfaz.Final.Model;
/**
 * Clase que hereda lo solicitado desde Pedido agregando el tiempo personal
 * Requerimiento personal de la clase
 * Agrega el peso
 */


public class PedidoEnvio extends Pedido{

    private final double peso;

    public PedidoEnvio(String direccion,double distancia,double peso) {
        super(direccion, distancia);
        this.peso = peso;
    }

    public double getPeso(){return peso;}

    @Override
    public Requerimiento getRequerimiento(){return Requerimiento.VEHICULO_CARGA;}

    @Override
    public int calcularTiempoEntrega(){return(int)(20+(1.5*getDistancia()));}

    @Override
    public String getTipo(){return "Encomienda";}

}
