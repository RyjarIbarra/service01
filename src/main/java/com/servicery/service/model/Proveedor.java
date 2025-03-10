package com.servicery.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Proveedor {
    private long prvid;
    private String prvruc, prvnom, prvtel, prvdir, prvmail, prvtip, prvcontr;
}
