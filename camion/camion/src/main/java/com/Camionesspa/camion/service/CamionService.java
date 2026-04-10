package com.Camionesspa.camion.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Camionesspa.camion.model.Camion;
import com.Camionesspa.camion.repository.CamionRepository;

@Service
public class CamionService {

	private final CamionRepository camionRepository;

	public CamionService(CamionRepository camionRepository) {
		this.camionRepository = camionRepository;
	}

	public List<Camion> listarTodos() {
		return camionRepository.findAll();
	}

	public Camion buscarPorId(String id) {
		return camionRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Camion no encontrado"));
	}

	public Camion crear(Camion camion) {
		return camionRepository.save(camion);
	}

	public Camion actualizar(String id, Camion datosActualizados) {
		Camion existente = buscarPorId(id);
		existente.setPatente(datosActualizados.getPatente());
		existente.setMarca(datosActualizados.getMarca());
		existente.setModelo(datosActualizados.getModelo());
		existente.setAnio(datosActualizados.getAnio());
		existente.setEstado(datosActualizados.getEstado());
		existente.setPrecioDiaBase(datosActualizados.getPrecioDiaBase());
		return camionRepository.save(existente);
	}

	public void eliminar(String id) {
		camionRepository.deleteById(id);
	}
}
