package com.servicery.service.controller;
import com.servicery.service.model.Version;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/Consulta")
public class HelloController {

    @GetMapping("/hello")
    public String holaMundo() {
        return "Hola MundO!";
    }

    @GetMapping("/version")
    public Version Version() {
        Version ver = new Version();
        ver.setVersion("Version 1");
        return ver;
    }

}
