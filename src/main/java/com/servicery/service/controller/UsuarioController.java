package com.servicery.service.controller;

import com.servicery.service.implement.UsuarioImpl;
import com.servicery.service.model.Usuario;
import com.servicery.service.modelDto.Login.UserData;
import com.servicery.service.modelDto.Login.UserResponse;
import com.servicery.service.modelDto.Response.DefaultResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/Usuario")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioImpl serviceUser;

    @PostMapping("/list")
    public DefaultResponse<Usuario> Listar(@RequestParam(name = "Filtro", defaultValue = "") String Filtro) {
        return serviceUser.Listar(Filtro);
    }

    @PostMapping("/insert")
    public DefaultResponse<Usuario> insert(@RequestBody Usuario dato){
        return serviceUser.insert(dato);
    }

    @PutMapping("/update")
    public DefaultResponse<Usuario> update(@RequestBody Usuario dato){
        return serviceUser.update(dato);
    }

    @DeleteMapping("/delete")
    public DefaultResponse<Usuario> delete(@RequestBody Usuario dato){
        return serviceUser.delete(dato);
    }

    @PostMapping("/sign_in")
    public UserResponse sign_in(@RequestBody UserData userData) {
        return serviceUser.sign_in(userData);
    }

}
