package com.prueba.clientes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Datos del cliente para creación o modificación")
public class ClienteRequestDTO {

    @Schema(description = "Nombre del cliente", example = "Juan")
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;

    @Schema(description = "Apellido del cliente", example = "Pérez")
    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100, message = "El apellido no puede superar los 100 caracteres")
    private String apellido;

    @Schema(description = "Número de documento (7-10 dígitos)", example = "32145678")
    @NotBlank(message = "El documento es obligatorio")
    @Pattern(regexp = "\\d{7,10}", message = "El documento debe contener entre 7 y 10 dígitos numéricos")
    private String documento;

    @Schema(description = "Teléfono de contacto", example = "+54 11 4567-8901")
    @Pattern(regexp = "\\+?[\\d\\s\\-()]{7,20}", message = "Formato de teléfono inválido")
    private String telefono;

    @Schema(description = "Dirección del cliente", example = "Av. Corrientes 1234, CABA")
    @Size(max = 255, message = "La dirección no puede superar los 255 caracteres")
    private String direccion;
}
