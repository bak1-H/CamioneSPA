package com.Camionesspa.camion.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "camiones")
public class Camion {
    @Id
    private String id;
    @Indexed(unique = true)
    private String patente;
    private String marca;
    private String modelo;
    private Integer anio;
    private EstadoCamion estado; // Enum
    private Double precioDiaBase;

    public enum EstadoCamion {
        DISPONIBLE, ARRENDADO, MANTENIMIENTO, BAJA
    }
}