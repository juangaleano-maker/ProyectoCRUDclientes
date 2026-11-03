package com.prueba.clientes.service.impl;

import com.prueba.clientes.dto.ClienteRequestDTO;
import com.prueba.clientes.dto.ClienteResponseDTO;
import com.prueba.clientes.entity.Cliente;
import com.prueba.clientes.exception.ClienteNotFoundException;
import com.prueba.clientes.exception.DocumentoDuplicadoException;
import com.prueba.clientes.mapper.ClienteMapper;
import com.prueba.clientes.repository.ClienteRepository;
import com.prueba.clientes.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    @Override
    @Transactional
    public ClienteResponseDTO crear(ClienteRequestDTO dto) {
        log.debug("Creando cliente con documento: {}", dto.getDocumento());
        if (clienteRepository.existsByDocumento(dto.getDocumento())) {
            throw new DocumentoDuplicadoException(dto.getDocumento());
        }
        Cliente cliente = clienteMapper.toEntity(dto);
        Cliente guardado = clienteRepository.save(cliente);
        log.info("Cliente creado con id: {}", guardado.getId());
        return clienteMapper.toDTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClienteResponseDTO> listar(String nombre, String apellido, String documento, Pageable pageable) {
        log.debug("Listando clientes con filtros - nombre: {}, apellido: {}, documento: {}", nombre, apellido, documento);
        return clienteRepository.findByFilters(nombre, apellido, documento, pageable)
                .map(clienteMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDTO obtenerPorId(Long id) {
        log.debug("Buscando cliente con id: {}", id);
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));
        return clienteMapper.toDTO(cliente);
    }

    @Override
    @Transactional
    public ClienteResponseDTO actualizar(Long id, ClienteRequestDTO dto) {
        log.debug("Actualizando cliente con id: {}", id);
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));
        if (clienteRepository.existsByDocumentoAndIdNot(dto.getDocumento(), id)) {
            throw new DocumentoDuplicadoException(dto.getDocumento());
        }
        clienteMapper.updateEntity(cliente, dto);
        Cliente actualizado = clienteRepository.save(cliente);
        log.info("Cliente actualizado con id: {}", actualizado.getId());
        return clienteMapper.toDTO(actualizado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.debug("Eliminando cliente con id: {}", id);
        if (!clienteRepository.existsById(id)) {
            throw new ClienteNotFoundException(id);
        }
        clienteRepository.deleteById(id);
        log.info("Cliente eliminado con id: {}", id);
    }
}
