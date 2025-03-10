package com.servicery.service.controller;

import com.servicery.service.implement.ProveedorService;
import com.servicery.service.model.Cliente;
import com.servicery.service.model.Proveedor;
import com.servicery.service.modelDto.Response.DefaultResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/Proveedor")
@AllArgsConstructor
public class ProveedorController {

    private final ProveedorService proveedorService;

    @PostMapping("/list")
    public DefaultResponse<Proveedor> lista(@RequestParam(name = "Filtro", defaultValue = "") String Filtro){
        return proveedorService.Listar(Filtro);
    }

    @PostMapping("/insert")
    @ResponseBody
    public DefaultResponse<Proveedor> insert(@RequestBody Proveedor prov){
        return proveedorService.insert(prov);
    }

    @PutMapping("/update")
    @ResponseBody
    public DefaultResponse<Proveedor> update(@RequestBody Proveedor prov){
        return proveedorService.update(prov);
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public DefaultResponse<Proveedor> delete(@RequestBody Proveedor prov) {
        return proveedorService.delete(prov);
    }

}
