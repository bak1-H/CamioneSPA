package com.Camionesspa.Arriendo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.Camionesspa.Arriendo.client.dto.ClienteDto;

@FeignClient(name = "cliente-service", url = "${cliente.service.url:http://localhost:8081}")
public interface ClienteClient {

    @GetMapping("/api/clientes/{id}")
    ClienteDto obtenerClientePorId(@PathVariable("id") String id);
}
