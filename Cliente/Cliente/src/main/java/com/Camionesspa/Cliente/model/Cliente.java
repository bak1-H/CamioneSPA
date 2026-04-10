package com.Camionesspa.Cliente.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.time.LocalDate;

@Data
@Document(collection = "clientes")
public class Cliente {
    @Id
    private String id;
    private TipoCliente tipo; // Enum: EMPRESA o PERSONA
    private String nombreRazonSocial;

    @Indexed(unique = true)
    private String identificacion; // RUT o DNI
    private LocalDate fechaRegistro = LocalDate.now();
    private DatosContacto contacto;

    // Clases anidadas para estructura limpia en JSON
    @Data
    public static class DatosContacto {
        private String email;
        private String telefono;
        private String direccion;
        private String ciudad;
    }

    public enum TipoCliente {
        PERSONA, EMPRESA
    }
}