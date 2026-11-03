package com.prueba.clientes.service;

import com.prueba.clientes.dto.ClienteRequestDTO;
import com.prueba.clientes.dto.ClienteResponseDTO;
import com.prueba.clientes.entity.Cliente;
import com.prueba.clientes.exception.ClienteNotFoundException;
import com.prueba.clientes.exception.DocumentoDuplicadoException;
import com.prueba.clientes.mapper.ClienteMapper;
import com.prueba.clientes.repository.ClienteRepository;
import com.prueba.clientes.service.impl.ClienteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock ClienteRepository clienteRepository;
    @Mock ClienteMapper clienteMapper;
    @InjectMocks ClienteServiceImpl clienteService;

    @Test
    void crear_conDocumentoNuevo_debeRetornarDTO() {
        ClienteRequestDTO req = new ClienteRequestDTO("Juan", "Pérez", "32145678", "+54 11 1234-5678", "Calle 123");
        Cliente entity = Cliente.builder().id(1L).nombre("Juan").apellido("Pérez").documento("32145678").build();
        ClienteResponseDTO resp = ClienteResponseDTO.builder().id(1L).nombre("Juan").build();

        when(clienteRepository.existsByDocumento("32145678")).thenReturn(false);
        when(clienteMapper.toEntity(req)).thenReturn(entity);
        when(clienteRepository.save(entity)).thenReturn(entity);
        when(clienteMapper.toDTO(entity)).thenReturn(resp);

        ClienteResponseDTO result = clienteService.crear(req);

        assertThat(result.getId()).isEqualTo(1L);
        verify(clienteRepository).save(entity);
    }

    @Test
    void crear_conDocumentoDuplicado_debeLanzarExcepcion() {
        ClienteRequestDTO req = new ClienteRequestDTO("Juan", "Pérez", "32145678", null, null);
        when(clienteRepository.existsByDocumento("32145678")).thenReturn(true);

        assertThatThrownBy(() -> clienteService.crear(req))
                .isInstanceOf(DocumentoDuplicadoException.class);
    }

    @Test
    void obtenerPorId_noExiste_debeLanzarExcepcion() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clienteService.obtenerPorId(99L))
                .isInstanceOf(ClienteNotFoundException.class);
    }

    @Test
    void eliminar_noExiste_debeLanzarExcepcion() {
        when(clienteRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> clienteService.eliminar(99L))
                .isInstanceOf(ClienteNotFoundException.class);
    }
}
