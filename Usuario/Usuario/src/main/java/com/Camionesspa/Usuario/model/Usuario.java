
package com.Camionesspa.Usuario.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Document(collection = "usuarios")
public class Usuario {
    @Id
    private String id;

    private String nombre;

    @Indexed(unique = true) // Crea un índice único en Mongo
    private String email;

    private String password; // Recuerda guardar esto encriptado (BCrypt)

    private RolUsuario rol; // Enum

    private boolean activo = true;
    
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    // Enum interno para control de tipos
    public enum RolUsuario {
        ADMIN, VENDEDOR, OPERARIO
    }
}