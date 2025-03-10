package com.servicery.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Usuario {
    private int id;
    private String usuario;
    private String nickname;
    private String telefono;
    private String nivel;
    private String password;
}
