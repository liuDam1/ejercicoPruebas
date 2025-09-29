package es.etg.psp.prueba.modelo;

import java.util.ArrayList;
import java.util.List;

public class AlumnoDaoMemoria implements AlumnoDao{
    private final List<Alumno> almacenamiento = new ArrayList<>();

    @Override
    public Alumno guardar(Alumno alumno) {
        almacenamiento.add(alumno);
        return alumno;
    }

    @Override
    public List<Alumno> listar() {
        return new ArrayList<>(almacenamiento);

    }
}
