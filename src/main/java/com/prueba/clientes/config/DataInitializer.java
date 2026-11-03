package com.prueba.clientes.config;

import com.prueba.clientes.entity.Cliente;
import com.prueba.clientes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    @Bean
    CommandLineRunner initData(ClienteRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(Cliente.builder()
                        .nombre("Juan").apellido("Pérez").documento("32145678")
                        .telefono("+54 11 4567-8901").direccion("Av. Corrientes 1234, CABA").build());
                repo.save(Cliente.builder()
                        .nombre("María").apellido("González").documento("28765432")
                        .telefono("+54 11 3456-7890").direccion("Calle Falsa 123, Rosario").build());
                repo.save(Cliente.builder()
                        .nombre("Carlos").apellido("López").documento("35987654")
                        .telefono("+54 351 456-7890").direccion("San Martín 456, Córdoba").build());
                log.info("Datos de ejemplo cargados correctamente.");
            }
        };
    }
}
