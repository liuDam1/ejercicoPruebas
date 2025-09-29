package es.etg.psp.prueba.modelo;

import java.util.List;

public class AlumnoServicio {
    private final AlumnoDao dao;
    
    private static final String ERROR_NOMBRE_VACIO = "El nombre no puede estar vacío";
    private static final String ERROR_APELLIDO_VACIO = "El apellido no puede estar vacío";
    private static final String ERROR_EDAD_INVALIDA = "La edad debe ser mayor que 0";
    
    public AlumnoServicio(AlumnoDao dao) {
        this.dao = dao;
    }

    public Alumno insertar(String nombre, String apellido, int edad) {
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException(ERROR_NOMBRE_VACIO);
        if (apellido == null || apellido.isBlank())
            throw new IllegalArgumentException(ERROR_APELLIDO_VACIO);
        if (edad <= 0)
            throw new IllegalArgumentException(ERROR_EDAD_INVALIDA);

        Alumno a = new Alumno(nombre, apellido, edad);
        return dao.guardar(a);
    }

    public List<Alumno> listar() {
        return dao.listar();
    }
}