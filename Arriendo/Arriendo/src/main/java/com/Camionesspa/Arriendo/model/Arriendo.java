package com.Camionesspa.Arriendo.model;

import java.util.Date;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "arriendos")
public class Arriendo {
    @Id
    private String id;

    // Referencias a otras entidades (almacenamos id y un snapshot básico)
    private String clienteId;
    private String camionId;
    private String usuarioResponsableId;
    private LocalDateTime fechaInicio = LocalDateTime.now();
    private Date fechaDevolucionReal;

    private SnapshotCliente clienteSnapshot;
    private SnapshotCamion camionSnapshot;
    private SnapshotUsuario usuarioSnapshot;
    private EstadoArriendo estado;


    @Data
    public static class SnapshotCliente {
        private String id;
        private String nombreRazonSocial;
        private String identificacion;
        private String tipo; // PERSONA o EMPRESA
    }

    @Data
    public static class SnapshotCamion {
        private String id;
        private String patente;
        private String estado;
        private Double precioDiaBase;
    }

    @Data
    public static class SnapshotUsuario {
        private String id;
        private String nombre;
        private String email;
        private String rol;
    }

    public enum EstadoArriendo {
        ARRENDADO, ACTIVO, FINALIZADO, CANCELADO, ATRASADO , RESERVADO
    }
}