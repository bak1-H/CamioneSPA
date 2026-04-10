package com.Camionesspa.Usuario.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Camionesspa.Usuario.model.Usuario;
import com.Camionesspa.Usuario.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuario", description = "Operaciones relacionadas con los usuarios - Swagger")
public class UsuarioController {

	private final UsuarioService usuarioService;

	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@Operation(summary = "Listar todos los usuarios", description = "Obtiene una lista de todos los usuarios registrados")
	@GetMapping
	public ResponseEntity<List<Usuario>> listar() {
		return ResponseEntity.ok(usuarioService.listarTodos());
	}

	@Operation(summary = "Obtener usuario por ID", description = "Obtiene los detalles de un usuario específico por su ID")
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> obtenerPorId(@PathVariable String id) {
		return ResponseEntity.ok(usuarioService.buscarPorId(id));
	}

	@Operation(summary = "Crear un nuevo usuario", description = "Crea un nuevo usuario con los datos proporcionados")
	@PostMapping
	public ResponseEntity<Usuario> crear(@RequestBody Usuario usuario) {
		return ResponseEntity.ok(usuarioService.crear(usuario));
	}

	@Operation(summary = "Actualizar un usuario existente", description = "Actualiza los datos de un usuario existente por su ID")
	@PutMapping("/{id}")
	public ResponseEntity<Usuario> actualizar(@PathVariable String id, @RequestBody Usuario usuario) {
		return ResponseEntity.ok(usuarioService.actualizar(id, usuario));
	}

	@Operation(summary = "Eliminar un usuario", description = "Elimina un usuario específico por su ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable String id) {
		usuarioService.eliminar(id);
		return ResponseEntity.noContent().build();
	}
}
