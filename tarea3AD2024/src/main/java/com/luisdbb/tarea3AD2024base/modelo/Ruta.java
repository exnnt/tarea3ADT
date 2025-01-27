package com.luisdbb.tarea3AD2024base.modelo;


import java.io.Serializable;

public class Ruta implements Serializable {
    private Long id;
    private Long peregrinoId; 
    private Long paradaId;    
    private int orden;        
    private float distanciaKm;

    public Ruta(Long id, Long peregrinoId, Long paradaId, int orden, float distanciaKm) {
        this.id = id;
        this.peregrinoId = peregrinoId;
        this.paradaId = paradaId;
        this.orden = orden;
        this.distanciaKm = distanciaKm;
    }    public Ruta(Long peregrinoId, Long paradaId, float distanciaKm) {
   
        this.peregrinoId = peregrinoId;
        this.paradaId = paradaId;

        this.distanciaKm = distanciaKm;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPeregrinoId() {
        return peregrinoId;
    }

    public void setPeregrinoId(Long peregrinoId) {
        this.peregrinoId = peregrinoId;
    }

    public Long getParadaId() {
        return paradaId;
    }

    public void setParadaId(Long paradaId) {
        this.paradaId = paradaId;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public float getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(float distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

   
}
