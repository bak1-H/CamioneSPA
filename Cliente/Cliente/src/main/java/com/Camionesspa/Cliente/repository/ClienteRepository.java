package com.Camionesspa.Cliente.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.Camionesspa.Cliente.model.Cliente;

@Repository
public interface ClienteRepository extends MongoRepository<Cliente, String> {
    Cliente findByIdentificacion(String identificacion);

    List<Cliente> findByTipo(Cliente.TipoCliente tipo);
}