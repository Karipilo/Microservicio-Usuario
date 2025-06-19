package com.usuario.usuario;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.usuario.usuario.model.Usuario;
import com.usuario.usuario.model.entity.UsuarioEntity;
import com.usuario.usuario.repository.UsuarioRepository;
import com.usuario.usuario.service.UsuarioService;

@SpringBootTest
public class TestUsuario {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;
    private UsuarioEntity usuarioEntity;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        usuario = new Usuario();
        usuario.setIdUsuario(1L);
        usuario.setNombreUsuario("Karla");
        usuario.setAppaterno("Fernandez");
        usuario.setApmaterno("Ortega");
        usuario.setEmailUsuario("karla@gmail.com");
        usuario.setDireccionUsuario("Violeta Parra 876");
        usuario.setTelefonoUsuario(987654567);
        usuario.setGeneroUsuario("Femenino");
        usuario.setContrasenaUsuario("Karla%2023");

        usuarioEntity = new UsuarioEntity();
        usuarioEntity.setIdUsuario(1L);
        usuarioEntity.setNombreUsuario("Karla");
        usuarioEntity.setAppaterno("Fernandez");
        usuarioEntity.setApmaterno("Ortega");
        usuarioEntity.setEmailUsuario("karla@gmail.com");
        usuarioEntity.setDireccionUsuario("Violeta Parra 876");
        usuarioEntity.setTelefonoUsuario(987654567);
        usuarioEntity.setGeneroUsuario("Femenino");
        usuarioEntity.setContrasenaUsuario("Karla%2023");
    }

    @Test
    public void testCrearUsuario_nuevo() {
        when(usuarioRepository.existsByNombreUsuario("Karla")).thenReturn(false);
        when(usuarioRepository.existsByEmailUsuario("karla@gmail.com")).thenReturn(false);
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuarioEntity);

        String resultado = usuarioService.crearUsuario(usuario);

        assertEquals("Usuario creado exitosamente", resultado);
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
    }

    @Test
    public void testCrearUsuario_yaExiste() {
        when(usuarioRepository.existsByNombreUsuario("Karla")).thenReturn(true);

        String resultado = usuarioService.crearUsuario(usuario);

        assertEquals("Error: El nombre de usuario ya existe", resultado);
        verify(usuarioRepository, never()).save(any(UsuarioEntity.class));
    }

    @Test
    public void testActualizarUsuario_existoso() {
        when(usuarioRepository.findByNombreUsuario("Karla")).thenReturn(Optional.of(usuarioEntity));
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuarioEntity);

        String resultado = usuarioService.actualizarUsuario("Karla", usuario);

        assertEquals("Usuario actualizado correctamente", resultado);
        verify(usuarioRepository, never()).save(any()); // el método no guarda explícitamente, solo actualiza el objeto
                                                        // gestionado
    }

    @Test
    public void testActualizarUsuario_noExiste() {
        when(usuarioRepository.findByNombreUsuario("Karla")).thenReturn(Optional.empty());

        String resultado = usuarioService.actualizarUsuario("Karla", usuario);

        assertEquals("Usuario no encontrado", resultado);
    }

    @Test
    public void testEliminarUsuario_existente() {
        when(usuarioRepository.findByNombreUsuario("Karla")).thenReturn(Optional.of(usuarioEntity));
        doNothing().when(usuarioRepository).delete(any(UsuarioEntity.class));

        String resultado = usuarioService.eliminarUsuario("Karla");

        assertEquals("Usuario eliminado correctamente", resultado);
        verify(usuarioRepository, times(1)).delete(usuarioEntity);
    }

    @Test
    public void testEliminarUsuario_noExiste() {
        when(usuarioRepository.findByNombreUsuario("Karla")).thenReturn(Optional.empty());

        String resultado = usuarioService.eliminarUsuario("Karla");

        assertEquals("Usuario no encontrado", resultado);
        verify(usuarioRepository, never()).delete(any(UsuarioEntity.class));
    }
}
