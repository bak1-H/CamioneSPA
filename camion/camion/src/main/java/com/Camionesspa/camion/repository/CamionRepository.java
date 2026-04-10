package com.Camionesspa.camion.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.Camionesspa.camion.model.Camion;

@Repository
public interface CamionRepository extends MongoRepository<Camion, String> {
    Camion findByPatente(String patente);

    List<Camion> findByEstado(Camion.EstadoCamion estado);
}