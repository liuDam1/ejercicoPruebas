package es.etg.psp.prueba.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class Alumno {
    private String nombre;
    private String apellido;
    private int edad;
    
}
