package com.Camionesspa.Arriendo.client.dto;

import lombok.Data;

@Data
public class UsuarioDto {
    private String id;
    private String nombre;
    private String email;
    private String rol;
    private boolean activo;
}
