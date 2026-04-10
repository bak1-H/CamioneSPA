package com.Camionesspa.Cliente.controller;

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

import com.Camionesspa.Cliente.model.Cliente;
import com.Camionesspa.Cliente.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Cliente", description = "Operaciones relacionadas con los clientes - Swagger")
public class ClienteController {

	private final ClienteService clienteService;

	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@Operation(summary = "Listar todos los clientes", description = "Obtiene una lista de todos los clientes registrados")
	@GetMapping
	public ResponseEntity<List<Cliente>> listar() {
		return ResponseEntity.ok(clienteService.listarTodos());
	}

	@Operation(summary = "Obtener cliente por ID", description = "Obtiene los detalles de un cliente específico por su ID")
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> obtenerPorId(@PathVariable String id) {
		return ResponseEntity.ok(clienteService.buscarPorId(id));
	}

	@Operation(summary = "Crear un nuevo cliente", description = "Crea un nuevo cliente con los datos proporcionados")
	@PostMapping
	public ResponseEntity<Cliente> crear(@RequestBody Cliente cliente) {
		return ResponseEntity.ok(clienteService.crear(cliente));
	}

	@Operation(summary = "Actualizar un cliente existente", description = "Actualiza los datos de un cliente existente por su ID")
	@PutMapping("/{id}")
	public ResponseEntity<Cliente> actualizar(@PathVariable String id, @RequestBody Cliente cliente) {
		return ResponseEntity.ok(clienteService.actualizar(id, cliente));
	}

	@Operation(summary = "Eliminar un cliente", description = "Elimina un cliente específico por su ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable String id) {
		clienteService.eliminar(id);
		return ResponseEntity.noContent().build();
	}
}
