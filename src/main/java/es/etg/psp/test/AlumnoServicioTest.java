package es.etg.psp.test;

import es.etg.psp.prueba.modelo.Alumno;
import es.etg.psp.prueba.modelo.AlumnoDao;
import es.etg.psp.prueba.modelo.AlumnoServicio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlumnoServicioTest {

    // Simulación de la dependencia AlumnoDao
    @Mock
    private AlumnoDao alumnoDao;

    // Inyectar el mock del DAO en el servicio a probar
    @InjectMocks
    private AlumnoServicio alumnoServicio;

    /**
     * Prueba la inserción de un alumno con datos válidos
     */
    @Test
    public void testInsertar_ConDatosValidos() {
        // Datos de prueba
        String nombre = "Juan";
        String apellido = "Pérez";
        int edad = 20;
        Alumno alumnoEsperado = new Alumno(nombre, apellido, edad);

        // Simular el comportamiento del DAO
        when(alumnoDao.guardar(any(Alumno.class))).thenReturn(alumnoEsperado);

        // Ejecutar el método a probar
        Alumno resultado = alumnoServicio.insertar(nombre, apellido, edad);

        // Verificar resultados
        assertNotNull(resultado);
        assertEquals(nombre, resultado.getNombre());
        assertEquals(apellido, resultado.getApellido());
        assertEquals(edad, resultado.getEdad());
        
        // Verificar que se llamó al método del DAO
        verify(alumnoDao, times(1)).guardar(any(Alumno.class));
    }

    /**
     * Prueba el caso cuando el nombre es nulo
     */
    @Test
    public void testInsertar_NombreVacio() {
        String nombre = null;
        String apellido = "García";
        int edad = 22;

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> alumnoServicio.insertar(nombre, apellido, edad)
        );
        
        assertEquals("El nombre no puede estar vacío", exception.getMessage());
        verify(alumnoDao, never()).guardar(any(Alumno.class));
    }

    /**
     * Prueba el caso cuando el nombre contiene solo espacios en blanco
     */
    @Test
    public void testInsertar_NombreBlanco() {
        String nombre = "   ";
        String apellido = "López";
        int edad = 21;

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> alumnoServicio.insertar(nombre, apellido, edad)
        );
        
        assertEquals("El nombre no puede estar vacío", exception.getMessage());
        verify(alumnoDao, never()).guardar(any(Alumno.class));
    }

    /**
     * Prueba el caso cuando el apellido es nulo
     */
    @Test
    public void testInsertar_ApellidoVacio() {
        String nombre = "Ana";
        String apellido = null;
        int edad = 23;

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> alumnoServicio.insertar(nombre, apellido, edad)
        );
        
        assertEquals("El apellido no puede estar vacío", exception.getMessage());
        verify(alumnoDao, never()).guardar(any(Alumno.class));
    }

    /**
     * Prueba el caso cuando la edad es 0
     */
    @Test
    public void testInsertar_EdadCero() {
        String nombre = "Luis";
        String apellido = "Martínez";
        int edad = 0;

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> alumnoServicio.insertar(nombre, apellido, edad)
        );
        
        assertEquals("La edad debe ser mayor que 0", exception.getMessage());
        verify(alumnoDao, never()).guardar(any(Alumno.class));
    }

    /**
     * Prueba el caso cuando la edad es negativa
     */
    @Test
    public void testInsertar_EdadNegativa() {
        String nombre = "María";
        String apellido = "Sánchez";
        int edad = -5;

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> alumnoServicio.insertar(nombre, apellido, edad)
        );
        
        assertEquals("La edad debe ser mayor que 0", exception.getMessage());
        verify(alumnoDao, never()).guardar(any(Alumno.class));
    }

    /**
     * Prueba la consulta de la lista de alumnos cuando existen registros
     */
    @Test
    public void testListar_AlumnosExistentes() {
        Alumno alumno1 = new Alumno("Carlos", "Ruiz", 22);
        Alumno alumno2 = new Alumno("Sara", "Jiménez", 20);
        List<Alumno> alumnosEsperados = Arrays.asList(alumno1, alumno2);

        when(alumnoDao.listar()).thenReturn(alumnosEsperados);

        List<Alumno> resultado = alumnoServicio.listar();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.containsAll(alumnosEsperados));
        
        verify(alumnoDao, times(1)).listar();
    }

    /**
     * Prueba la consulta de la lista de alumnos cuando no hay registros
     */
    @Test
    public void testListar_SinAlumnos() {
        when(alumnoDao.listar()).thenReturn(List.of());

        List<Alumno> resultado = alumnoServicio.listar();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        
        verify(alumnoDao, times(1)).listar();
    }
}
