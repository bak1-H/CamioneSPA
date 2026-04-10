package com.Camionesspa.Arriendo.client.dto;

import lombok.Data;

@Data
public class ClienteDto {
    private String id;
    private String tipo; // PERSONA o EMPRESA
    private String nombreRazonSocial;
    private String identificacion;
    private DatosContacto contacto;

    @Data
    public static class DatosContacto {
        private String email;
        private String telefono;
        private String direccion;
        private String ciudad;
    }
}
