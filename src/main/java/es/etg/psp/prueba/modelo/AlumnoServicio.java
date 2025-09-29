package es.etg.psp.prueba.modelo;

import java.util.List;

public class AlumnoServicio {
    private final AlumnoDao dao;

    public AlumnoServicio(AlumnoDao dao) {
        this.dao = dao;
    }

    public Alumno insertar(String nombre, String apellido, int edad) {
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        if (apellido == null || apellido.isBlank())
            throw new IllegalArgumentException("El apellido no puede estar vacío");
        if (edad <= 0)
            throw new IllegalArgumentException("La edad debe ser mayor que 0");

        Alumno a = new Alumno(nombre, apellido, edad);
        return dao.guardar(a);
    }

    public List<Alumno> listar() {
        return dao.listar();
    }
}
