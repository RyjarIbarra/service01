package com.servicery.service.controller;

import com.servicery.service.implement.ClienteService;
import com.servicery.service.model.Cliente;
import com.servicery.service.modelDto.Filter.DefaultFilter;
import com.servicery.service.modelDto.Response.DefaultResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/Cliente")
@AllArgsConstructor
public class ClienteController {

    private final ClienteService serviceCli;

    @PostMapping("/list")
    public DefaultResponse<Cliente> lista(@RequestBody DefaultFilter Filtro){
        return serviceCli.Listar(Filtro);
    }

    @PostMapping("/insert")
    @ResponseBody
    public DefaultResponse<Cliente> insert(@RequestBody Cliente cli){
        return serviceCli.insert(cli);
    }

    @PutMapping("/update")
    @ResponseBody
    public DefaultResponse<Cliente> update(@RequestBody Cliente cli){
        return serviceCli.update(cli);
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public DefaultResponse<Cliente> delete(@RequestBody Cliente cli) {
        return serviceCli.delete(cli);
    }

    @PostMapping("/cantidad")
    public Map<String, String> cantiadCliente(){
        Map<String, String> map = new HashMap<>();
        map.put("respuesta", ""+serviceCli.CantidadCliente());
        return map;
    }

}
