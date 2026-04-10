package com.Camionesspa.camion.controller;

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

import com.Camionesspa.camion.model.Camion;
import com.Camionesspa.camion.service.CamionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/camiones")
@Tag(name = "Camion", description = "Operaciones relacionadas con los camiones - Swagger")
public class CamionController {

	private final CamionService camionService;

	public CamionController(CamionService camionService) {
		this.camionService = camionService;
	}

	@Operation(summary = "Listar todos los camiones", description = "Obtiene una lista de todos los camiones registrados")
	@GetMapping
	public ResponseEntity<List<Camion>> listar() {
		return ResponseEntity.ok(camionService.listarTodos());
	}

	@Operation(summary = "Obtener camion por ID", description = "Obtiene los detalles de un camion específico por su ID")
	@GetMapping("/{id}")
	public ResponseEntity<Camion> obtenerPorId(@PathVariable String id) {
		return ResponseEntity.ok(camionService.buscarPorId(id));
	}

	@Operation(summary = "Crear un nuevo camion", description = "Crea un nuevo camion con los datos proporcionados")
	@PostMapping
	public ResponseEntity<Camion> crear(@RequestBody Camion camion) {
		return ResponseEntity.ok(camionService.crear(camion));
	}

	@Operation(summary = "Actualizar un camion existente", description = "Actualiza los datos de un camion existente por su ID")
	@PutMapping("/{id}")
	public ResponseEntity<Camion> actualizar(@PathVariable String id, @RequestBody Camion camion) {
		return ResponseEntity.ok(camionService.actualizar(id, camion));
	}

	@Operation(summary = "Eliminar un camion", description = "Elimina un camion específico por su ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable String id) {
		camionService.eliminar(id);
		return ResponseEntity.noContent().build();
	}
}
