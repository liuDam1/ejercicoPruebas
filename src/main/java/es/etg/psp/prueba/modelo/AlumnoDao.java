package es.etg.psp.prueba.modelo;

import java.util.List;

public interface AlumnoDao {
    Alumno guardar(Alumno alumno);
    List<Alumno> listar();
}
