package com.Camionesspa.Arriendo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Camionesspa.Arriendo.client.*;
import com.Camionesspa.Arriendo.client.dto.*;
import com.Camionesspa.Arriendo.model.Arriendo;
import com.Camionesspa.Arriendo.repository.ArriendoRepository;
import java.util.List;

@Service
public class ArriendoService {
    
    @Autowired
    private ArriendoRepository arriendoRepository;
    
    @Autowired
    private ClienteClient clienteClient;
    
    @Autowired
    private CamionClient camionClient;
    
    @Autowired
    private UsuarioClient usuarioClient;
    
    public Arriendo crear(Arriendo arriendo) {
        // Obtener datos de otros microservicios mediante Feign
        ClienteDto cliente = clienteClient.obtenerClientePorId(arriendo.getClienteId());
        CamionDto camion = camionClient.obtenerCamionPorId(arriendo.getCamionId());
        UsuarioDto usuario = usuarioClient.obtenerUsuarioPorId(arriendo.getUsuarioResponsableId());
        
        // Poblar snapshots automáticamente
        arriendo.setClienteSnapshot(crearSnapshotCliente(cliente));
        arriendo.setCamionSnapshot(crearSnapshotCamion(camion));
        arriendo.setUsuarioSnapshot(crearSnapshotUsuario(usuario));
        
        // Establecer estado inicial
        arriendo.setEstado(Arriendo.EstadoArriendo.ARRENDADO);
        
        return arriendoRepository.save(arriendo);
    }
    
    private Arriendo.SnapshotCliente crearSnapshotCliente(ClienteDto dto) {
        Arriendo.SnapshotCliente snapshot = new Arriendo.SnapshotCliente();
        snapshot.setId(dto.getId());
        snapshot.setNombreRazonSocial(dto.getNombreRazonSocial());
        snapshot.setIdentificacion(dto.getIdentificacion());
        snapshot.setTipo(dto.getTipo());
        return snapshot;
    }
    
    private Arriendo.SnapshotCamion crearSnapshotCamion(CamionDto dto) {
        Arriendo.SnapshotCamion snapshot = new Arriendo.SnapshotCamion();
        snapshot.setId(dto.getId());
        snapshot.setPatente(dto.getPatente());
        snapshot.setEstado(dto.getEstado());
        snapshot.setPrecioDiaBase(dto.getPrecioDiaBase());
        return snapshot;
    }
    
    private Arriendo.SnapshotUsuario crearSnapshotUsuario(UsuarioDto dto) {
        Arriendo.SnapshotUsuario snapshot = new Arriendo.SnapshotUsuario();
        snapshot.setId(dto.getId());
        snapshot.setNombre(dto.getNombre());
        snapshot.setEmail(dto.getEmail());
        snapshot.setRol(dto.getRol());
        return snapshot;
    }
    
    public List<Arriendo> listarTodos() {
        return arriendoRepository.findAll();
    }
    
    public Arriendo buscarPorId(String id) {
        return arriendoRepository.findById(id).orElse(null);
    }
    
    //actualiza arriendo existente y trae los snapshots actualizados
    public Arriendo actualizar(Arriendo arriendo) {
        // Verificar que el arriendo existe
        Arriendo arriendoExistente = arriendoRepository.findById(arriendo.getId())
            .orElseThrow(() -> new RuntimeException("Arriendo no encontrado con ID: " + arriendo.getId()));
        
        // Obtener datos actualizados de otros microservicios mediante Feign
        ClienteDto cliente = clienteClient.obtenerClientePorId(arriendo.getClienteId());
        CamionDto camion = camionClient.obtenerCamionPorId(arriendo.getCamionId());
        UsuarioDto usuario = usuarioClient.obtenerUsuarioPorId(arriendo.getUsuarioResponsableId());
        
        // Actualizar snapshots automáticamente
        arriendo.setClienteSnapshot(crearSnapshotCliente(cliente));
        arriendo.setCamionSnapshot(crearSnapshotCamion(camion));
        arriendo.setUsuarioSnapshot(crearSnapshotUsuario(usuario));
        arriendo.setEstado(Arriendo.EstadoArriendo.ARRENDADO);
        
        return arriendoRepository.save(arriendo);
    }
    
    public void eliminar(String id) {
        arriendoRepository.deleteById(id);
    }
}