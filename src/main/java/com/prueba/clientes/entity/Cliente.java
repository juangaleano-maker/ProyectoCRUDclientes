package com.prueba.clientes.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100, message = "El apellido no puede superar los 100 caracteres")
    @Column(nullable = false, length = 100)
    private String apellido;

    @NotBlank(message = "El documento es obligatorio")
    @Pattern(regexp = "\\d{7,10}", message = "El documento debe contener entre 7 y 10 dígitos numéricos")
    @Column(nullable = false, unique = true, length = 20)
    private String documento;

    @Pattern(regexp = "\\+?[\\d\\s\\-()]{7,20}", message = "Formato de teléfono inválido")
    @Column(length = 30)
    private String telefono;

    @Size(max = 255, message = "La dirección no puede superar los 255 caracteres")
    @Column(length = 255)
    private String direccion;
}
