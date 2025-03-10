package com.servicery.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    private long id;
    private String clinom, cliruc, clitel, clidir, clitip, climail, clicontr;
}
