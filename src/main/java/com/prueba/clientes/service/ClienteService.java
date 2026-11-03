package com.prueba.clientes.service;

import com.prueba.clientes.dto.ClienteRequestDTO;
import com.prueba.clientes.dto.ClienteResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteService {
    ClienteResponseDTO crear(ClienteRequestDTO dto);
    Page<ClienteResponseDTO> listar(String nombre, String apellido, String documento, Pageable pageable);
    ClienteResponseDTO obtenerPorId(Long id);
    ClienteResponseDTO actualizar(Long id, ClienteRequestDTO dto);
    void eliminar(Long id);
}
