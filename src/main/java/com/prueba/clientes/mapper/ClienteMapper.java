package com.prueba.clientes.mapper;

import com.prueba.clientes.dto.ClienteRequestDTO;
import com.prueba.clientes.dto.ClienteResponseDTO;
import com.prueba.clientes.entity.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public Cliente toEntity(ClienteRequestDTO dto) {
        return Cliente.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .documento(dto.getDocumento())
                .telefono(dto.getTelefono())
                .direccion(dto.getDireccion())
                .build();
    }

    public ClienteResponseDTO toDTO(Cliente entity) {
        return ClienteResponseDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .apellido(entity.getApellido())
                .documento(entity.getDocumento())
                .telefono(entity.getTelefono())
                .direccion(entity.getDireccion())
                .build();
    }

    public void updateEntity(Cliente entity, ClienteRequestDTO dto) {
        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setDocumento(dto.getDocumento());
        entity.setTelefono(dto.getTelefono());
        entity.setDireccion(dto.getDireccion());
    }
}
