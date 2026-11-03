package com.prueba.clientes.repository;

import com.prueba.clientes.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByDocumento(String documento);

    boolean existsByDocumentoAndIdNot(String documento, Long id);

    Optional<Cliente> findByDocumento(String documento);

    @Query("SELECT c FROM Cliente c WHERE " +
           "(:nombre IS NULL OR LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) AND " +
           "(:apellido IS NULL OR LOWER(c.apellido) LIKE LOWER(CONCAT('%', :apellido, '%'))) AND " +
           "(:documento IS NULL OR c.documento LIKE CONCAT('%', :documento, '%'))")
    Page<Cliente> findByFilters(
            @Param("nombre") String nombre,
            @Param("apellido") String apellido,
            @Param("documento") String documento,
            Pageable pageable
    );
}
