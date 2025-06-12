package com.usuario.usuario;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
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
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario(1, "testUser", "User", "Test", "test@gmail.com", "123 Limonares", 1234567890,
                "Masculino", "Test%12345");

        usuarioEntity = new UsuarioEntity();
        usuarioEntity.setIdUsuario(1);
        usuarioEntity.setNombreUsuario("testUser");
        usuarioEntity.setAppaterno("User");
        usuarioEntity.setApmaterno("Test");
        usuarioEntity.setEmailUsuario("test@gmail.com");
        usuarioEntity.setDireccionUsuario("123 Limonares");
        usuarioEntity.setTelefonoUsuario(1234567890);
        usuarioEntity.setGeneroUsuario("Masculino");
        usuarioEntity.setContrasenaUsuario("Test%12345");
    }

    @Test
    public void testCrearUsuario() {
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuarioEntity);

        Usuario resultado = usuarioService.crearUsuario(usuario);

        assertNotNull(resultado);
        assertEquals(usuario.getNombreUsuario(), resultado.getNombreUsuario());
        assertEquals(usuario.getAppaterno(), resultado.getAppaterno());
        assertEquals(usuario.getApmaterno(), resultado.getApmaterno());
        assertEquals(usuario.getEmailUsuario(), resultado.getEmailUsuario());
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
    }

    @Test
    public void testObtenerUsuarioPorId() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioEntity));

        Usuario resultado = usuarioService.obtenerUsuarioPorId(1L);

        assertNotNull(resultado);
        assertEquals(usuario.getIdUsuario(), resultado.getIdUsuario());
        assertEquals(usuario.getNombreUsuario(), resultado.getNombreUsuario());
    }

    @Test
    public void testObtenerTodosLosUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuarioEntity));

        List<Usuario> resultado = usuarioService.obtenerTodosLosUsuarios();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals(usuario.getNombreUsuario(), resultado.get(0).getNombreUsuario());
    }

    @Test
    public void testActualizarUsuario() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioEntity));
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuarioEntity);

        Usuario usuarioActualizado = usuarioService.actualizarUsuario(1L, usuario);

        assertNotNull(usuarioActualizado);
        assertEquals(usuario.getNombreUsuario(), usuarioActualizado.getNombreUsuario());
        assertEquals(usuario.getEmailUsuario(), usuarioActualizado.getEmailUsuario());
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
    }

    @Test
    public void testEliminarUsuario() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioEntity));
        doNothing().when(usuarioRepository).deleteById(1L);

        usuarioService.eliminarUsuario(1L);

        verify(usuarioRepository, times(1)).findById(1L);
        verify(usuarioRepository, times(1)).deleteById(1L);
    }
}
