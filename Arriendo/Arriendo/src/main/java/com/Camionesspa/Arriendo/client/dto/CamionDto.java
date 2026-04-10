package com.Camionesspa.Arriendo.client.dto;

import lombok.Data;

@Data
public class CamionDto {
    private String id;
    private String patente;
    private String marca;
    private String modelo;
    private Integer anio;
    private String estado;
    private Double precioDiaBase;
}
