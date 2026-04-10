package com.Camionesspa.Cliente.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Camionesspa.Cliente.model.Cliente;
import com.Camionesspa.Cliente.repository.ClienteRepository;

@Service
public class ClienteService {

	private final ClienteRepository clienteRepository;

	public ClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	public List<Cliente> listarTodos() {
		return clienteRepository.findAll();
	}

	public Cliente buscarPorId(String id) {
		return clienteRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
	}

	public Cliente crear(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	public Cliente actualizar(String id, Cliente datosActualizados) {
		Cliente existente = buscarPorId(id);
		existente.setTipo(datosActualizados.getTipo());
		existente.setNombreRazonSocial(datosActualizados.getNombreRazonSocial());
		existente.setIdentificacion(datosActualizados.getIdentificacion());
		existente.setContacto(datosActualizados.getContacto());
		return clienteRepository.save(existente);
	}

	public void eliminar(String id) {
		clienteRepository.deleteById(id);
	}
}
