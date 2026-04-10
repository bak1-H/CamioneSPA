package com.Camionesspa.Usuario.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.Camionesspa.Usuario.model.Usuario;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Usuario findByEmail(String email);

    List<Usuario> findByRol(Usuario.RolUsuario rol);
}