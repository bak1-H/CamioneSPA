package com.Camionesspa.Arriendo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.Camionesspa.Arriendo.client.dto.CamionDto;

@FeignClient(name = "camion-service", url = "${camion.service.url:http://localhost:8082}")
public interface CamionClient {

    @GetMapping("/api/camiones/{id}")
    CamionDto obtenerCamionPorId(@PathVariable("id") String id);
}
