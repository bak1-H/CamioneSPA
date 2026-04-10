package com.Camionesspa.Usuario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Camionesspa.Usuario.model.Usuario;
import com.Camionesspa.Usuario.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;

	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	public List<Usuario> listarTodos() {
		return usuarioRepository.findAll();
	}

	public Usuario buscarPorId(String id) {
		return usuarioRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
	}

	public Usuario crear(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	public Usuario actualizar(String id, Usuario datosActualizados) {
		Usuario existente = buscarPorId(id);
		existente.setNombre(datosActualizados.getNombre());
		existente.setEmail(datosActualizados.getEmail());
		existente.setPassword(datosActualizados.getPassword());
		existente.setRol(datosActualizados.getRol());
		existente.setActivo(datosActualizados.isActivo());
		return usuarioRepository.save(existente);
	}

	public void eliminar(String id) {
		usuarioRepository.deleteById(id);
	}
}
