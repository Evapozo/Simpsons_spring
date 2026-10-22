package es.daw.simpsons_spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 *los datos del personaje por los que puedo filtar / ordenar
 * datos que se transfieren de la vista al controlador
 */

@NoArgsConstructor
@Data
public class DtoPersonaje {
//lugar ocupacion edadMax soloFAmilia ordenar r descendente y limite
    @NotBlank
    @Size(min = 1, max = 100)
    private String nombre;
    @NotBlank
    @Size(min = 1, max = 100)
    private String apellido;
    @NotBlank
    @Size(min = 1, max = 110)
    private int edad;
    @NotBlank
    @Size(min = 1, max = 100)
    private String ocupacion;
    @NotBlank
    @Size(min = 1, max = 100)
    private String lugar; // sitio donde se suele ver al personaje... casa, escuela
    private boolean principal; // ¿es de la familia protagista


}
