package com.servicery.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Producto {
    private String codigo;
    private String descripcion;
    private long precio;
    private long stock;
}
