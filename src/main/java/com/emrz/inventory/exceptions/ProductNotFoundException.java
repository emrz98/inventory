package com.emrz.inventory.exceptions;

public class ProductNotFoundException extends RuntimeException{
    private static final String MSG = "Producto con id %s no encontrado";

    public ProductNotFoundException(){
        super(MSG);
    }

    public String getMessage(){
        return MSG;
    }
}
