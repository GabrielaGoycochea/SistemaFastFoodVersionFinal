package com.duoc.Sistema.FastFood.Interfaz.Final.Model;

/**
 * Clase Repartidor para tener nombre de repartidor
 * tipo vehículo y si se encuentra disponible.
 * Si el pedido está tomado por otro no es reasignable
 *
 */

public class Repartidor {

    private int id;
    private final String nombre;
    private final Requerimiento tipoVehiculo;
    private boolean disponible = true;

    public Repartidor(String nombre, Requerimiento tipoVehiculo){
        this.nombre=nombre;
        this.tipoVehiculo=tipoVehiculo;
    }

    public int getId() {return id;}
    public void setId(int id){this.id=id;}
    public String getNombre(){return nombre;}
    public Requerimiento getTipoVehiculo(){return tipoVehiculo;}
    public boolean estaDisponible(){return disponible;}
    public void ocupar(){disponible=false;}
    public void liberar(){disponible=true;}


    @Override
    public String toString(){
        return getId()+ " - " + getNombre()+
                (estaDisponible() ? " (Disponible)" : " (Ocupado)");
    }


}
