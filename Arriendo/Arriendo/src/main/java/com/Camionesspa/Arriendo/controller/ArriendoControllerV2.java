package com.Camionesspa.Arriendo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Camionesspa.Arriendo.model.Arriendo;
import com.Camionesspa.Arriendo.service.ArriendoService;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/arriendos")
@Tag(name = "Arriendo", description = "Operaciones relacionadas con los arriendos - Swagger")
public class ArriendoControllerV2 {

    @Autowired ArriendoService arriendoService;


    @Operation(summary = "Obtener todos los arriendos", description = "Obtiene una lista de todos los arriendos disponibles")
    @GetMapping
    public ResponseEntity<List<?>> obtenerTodosLosArriendos() {
        List<Arriendo> arriendos = arriendoService.listarTodos();
        return ResponseEntity.ok(arriendos);
    }


    @Operation (summary = "Obtener arriendo por ID", description = "Obtiene un arriendo específico por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Arriendo> obtenerArriendoPorId(@PathVariable String id) {
        Arriendo arriendo = arriendoService.buscarPorId(id);
        return ResponseEntity.ok(arriendo);
    }
    
    @Operation(summary = "Crear un nuevo arriendo", description = "Crea un nuevo arriendo con los datos proporcionados")
    @PostMapping
    public ResponseEntity<Arriendo> crearArriendo(@RequestBody Arriendo arriendo)
    {
        Arriendo nuevoArriendo = arriendoService.crear(arriendo);
        return ResponseEntity.ok(nuevoArriendo);
    }

    @Operation(summary = "Actualizar un arriendo existente", description = "Actualiza los datos de un arriendo existente por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<Arriendo> actualizarArriendo(@PathVariable String id, @RequestBody Arriendo arriendoActualizado) {
        arriendoActualizado.setId(id);
        Arriendo arriendo = arriendoService.actualizar(arriendoActualizado);
        return ResponseEntity.ok(arriendo);
    }

    @Operation(summary = "Eliminar un arriendo", description = "Elimina un arriendo específico por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarArriendo(@PathVariable String id) {
        arriendoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }


}
