

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.etg.psp.prueba.modelo.Alumno;
import es.etg.psp.prueba.modelo.AlumnoDao;
import es.etg.psp.prueba.modelo.AlumnoServicio;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AlumnoServicioTest {

    @Mock
    private AlumnoDao alumnoDao;

    @InjectMocks
    private AlumnoServicio alumnoServicio;

    @Test
    @DisplayName("Insertar alumno con datos válidos")
    void insertarConDatosValidos() {
        // Given
        when(alumnoDao.guardar(any(Alumno.class))).thenReturn(new Alumno("Juan", "Pérez", 20));

        // When
        Alumno resultado = alumnoServicio.insertar("Juan", "Pérez", 20);

        // Then
        assertAll("Datos del alumno",
            () -> assertEquals("Juan", resultado.getNombre()),
            () -> assertEquals("Pérez", resultado.getApellido()),
            () -> assertEquals(20, resultado.getEdad())
        );
        verify(alumnoDao, times(1)).guardar(any(Alumno.class));
    }

    @Test
    @DisplayName("No insertar cuando el nombre es nulo")
    void insertarNombreNulo() {
        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> alumnoServicio.insertar(null, "García", 22)
        );
        
        assertEquals("El nombre no puede estar vacío", exception.getMessage());
        verify(alumnoDao, never()).guardar(any(Alumno.class));
    }

    @Test
    @DisplayName("No insertar cuando el nombre está en blanco")
    void insertarNombreBlanco() {
        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> alumnoServicio.insertar("   ", "López", 21)
        );
        
        assertEquals("El nombre no puede estar vacío", exception.getMessage());
        verify(alumnoDao, never()).guardar(any(Alumno.class));
    }

    @Test
    @DisplayName("No insertar cuando el apellido es nulo")
    void insertarApellidoNulo() {
        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> alumnoServicio.insertar("Ana", null, 23)
        );
        
        assertEquals("El apellido no puede estar vacío", exception.getMessage());
        verify(alumnoDao, never()).guardar(any(Alumno.class));
    }

    @Test
    @DisplayName("No insertar cuando la edad es cero")
    void insertarEdadCero() {
        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> alumnoServicio.insertar("Luis", "Martínez", 0)
        );
        
        assertEquals("La edad debe ser mayor que 0", exception.getMessage());
        verify(alumnoDao, never()).guardar(any(Alumno.class));
    }

    @Test
    @DisplayName("No insertar cuando la edad es negativa")
    void insertarEdadNegativa() {
        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> alumnoServicio.insertar("María", "Sánchez", -5)
        );
        
        assertEquals("La edad debe ser mayor que 0", exception.getMessage());
        verify(alumnoDao, never()).guardar(any(Alumno.class));
    }

    @Test
    @DisplayName("Listar alumnos cuando existen registros")
    void listarConAlumnos() {
        // Given
        List<Alumno> alumnosEsperados = Arrays.asList(
            new Alumno("Carlos", "Ruiz", 22),
            new Alumno("Sara", "Jiménez", 20)
        );
        when(alumnoDao.listar()).thenReturn(alumnosEsperados);

        // When
        List<Alumno> resultado = alumnoServicio.listar();

        // Then
        assertAll("Lista de alumnos",
            () -> assertNotNull(resultado),
            () -> assertEquals(2, resultado.size()),
            () -> assertEquals("Carlos", resultado.get(0).getNombre()),
            () -> assertEquals("Sara", resultado.get(1).getNombre())
        );
        verify(alumnoDao, times(1)).listar();
    }

    @Test
    @DisplayName("Listar cuando no hay alumnos")
    void listarSinAlumnos() {
        // Given
        when(alumnoDao.listar()).thenReturn(List.of());

        // When
        List<Alumno> resultado = alumnoServicio.listar();

        // Then
        assertAll("Lista vacía",
            () -> assertNotNull(resultado),
            () -> assertTrue(resultado.isEmpty())
        );
        verify(alumnoDao, times(1)).listar();
    }

    @Test
    @DisplayName("Probar múltiples inserciones válidas")
    void insertarMultiplesAlumnosValidos() {
        // Given
        when(alumnoDao.guardar(any(Alumno.class)))
            .thenReturn(new Alumno("Juan", "Pérez", 20))
            .thenReturn(new Alumno("Ana", "García", 22))
            .thenReturn(new Alumno("Luis", "Martínez", 21));

        // When & Then
        assertAll("Múltiples inserciones",
            () -> {
                Alumno alumno1 = alumnoServicio.insertar("Juan", "Pérez", 20);
                assertEquals("Juan", alumno1.getNombre());
            },
            () -> {
                Alumno alumno2 = alumnoServicio.insertar("Ana", "García", 22);
                assertEquals("Ana", alumno2.getNombre());
            },
            () -> {
                Alumno alumno3 = alumnoServicio.insertar("Luis", "Martínez", 21);
                assertEquals("Luis", alumno3.getNombre());
            }
        );
        verify(alumnoDao, times(3)).guardar(any(Alumno.class));
    }
}