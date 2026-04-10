package com.Camionesspa.Arriendo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.Camionesspa.Arriendo.client.dto.UsuarioDto;

@FeignClient(name = "usuario-service", url = "${usuario.service.url:http://localhost:8083}")
public interface UsuarioClient {

    @GetMapping("/api/usuarios/{id}")
    UsuarioDto obtenerUsuarioPorId(@PathVariable("id") String id);
}
