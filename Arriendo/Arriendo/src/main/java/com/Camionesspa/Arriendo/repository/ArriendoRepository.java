package com.Camionesspa.Arriendo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.Camionesspa.Arriendo.model.Arriendo;
import com.Camionesspa.Arriendo.model.Arriendo.EstadoArriendo;

@Repository
public interface ArriendoRepository extends MongoRepository<Arriendo, String> {
	List<Arriendo> findByEstado(EstadoArriendo estado);

	List<Arriendo> findByClienteId(String clienteId);
}
