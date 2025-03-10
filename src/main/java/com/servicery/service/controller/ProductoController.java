package com.servicery.service.controller;

import com.servicery.service.implement.ProductoImpl;
import com.servicery.service.model.Producto;
import com.servicery.service.modelDto.Filter.DefaultFilter;
import com.servicery.service.modelDto.Response.DefaultResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/Producto")
@AllArgsConstructor
public class ProductoController {

    private final ProductoImpl serviceProd;

    @PostMapping("/list")
    public DefaultResponse<Producto> Listar(@RequestBody DefaultFilter Filtro) {
        return serviceProd.Listar(Filtro);
    }

    @PostMapping("insert")
    public DefaultResponse<Producto> insert(@RequestBody Producto prod){
        return serviceProd.insert(prod);
    }

}
