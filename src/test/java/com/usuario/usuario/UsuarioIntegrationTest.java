package com.usuario.usuario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.usuario.usuario.model.Usuario;
import com.usuario.usuario.model.entity.UsuarioEntity;
import com.usuario.usuario.repository.UsuarioRepository;
import com.usuario.usuario.service.UsuarioService;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UsuarioIntegrationTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void testCrearUsuarioIntegracion() {
        // Crear un usuario de prueba
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("testuser");
        usuario.setAppaterno("Test");
        usuario.setApmaterno("User");
        usuario.setEmailUsuario("test@example.com");
        usuario.setDireccionUsuario("Test Address 123");
        usuario.setTelefonoUsuario(123456789);
        usuario.setGeneroUsuario("Masculino");
        usuario.setContrasenaUsuario("Test123@"); // Contraseña válida: 8 caracteres, mayúscula, minúscula, número y
        // carácter especial

        // Ejecutar el servicio
        String resultado = usuarioService.crearUsuario(usuario);

        // Verificar el resultado
        assertEquals("Usuario creado exitosamente", resultado);

        // Verificar que se guardó en la base de datos
        assertTrue(usuarioRepository.existsByNombreUsuario("testuser"));
        assertTrue(usuarioRepository.existsByEmailUsuario("test@example.com"));
    }

    @Test
    public void testBuscarUsuarioIntegracion() {
        // Crear y guardar un usuario directamente en el repositorio
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setNombreUsuario("searchuser");
        usuarioEntity.setAppaterno("Search");
        usuarioEntity.setApmaterno("User");
        usuarioEntity.setEmailUsuario("search@example.com");
        usuarioEntity.setDireccionUsuario("Search Address 456");
        usuarioEntity.setTelefonoUsuario(987654321);
        usuarioEntity.setGeneroUsuario("Femenino");
        usuarioEntity.setContrasenaUsuario("Search4@"); // Contraseña válida: 8 caracteres, mayúscula, minúscula, número
        // y carácter especial

        usuarioRepository.save(usuarioEntity);

        // Buscar el usuario usando el servicio
        Usuario usuarioEncontrado = usuarioService.obtenerUsuario("searchuser");

        // Verificar que se encontró correctamente
        assertNotNull(usuarioEncontrado);
        assertEquals("searchuser", usuarioEncontrado.getNombreUsuario());
        assertEquals("Search", usuarioEncontrado.getAppaterno());
        assertEquals("search@example.com", usuarioEncontrado.getEmailUsuario());
    }

    @Test
    public void testActualizarUsuarioIntegracion() {
        // Crear y guardar un usuario
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setNombreUsuario("updateuser");
        usuarioEntity.setAppaterno("Update");
        usuarioEntity.setApmaterno("User");
        usuarioEntity.setEmailUsuario("update@example.com");
        usuarioEntity.setDireccionUsuario("Update Address 789");
        usuarioEntity.setTelefonoUsuario(111222333);
        usuarioEntity.setGeneroUsuario("Masculino");
        usuarioEntity.setContrasenaUsuario("Update1@"); // Contraseña válida: 8 caracteres, mayúscula, minúscula, número
        // y carácter especial

        usuarioRepository.save(usuarioEntity);

        // Crear usuario con datos actualizados
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setNombreUsuario("updateuser");
        usuarioActualizado.setAppaterno("UpdatedName");
        usuarioActualizado.setApmaterno("UpdatedLastName");
        usuarioActualizado.setEmailUsuario("updated@example.com");
        usuarioActualizado.setDireccionUsuario("Updated Address 999");
        usuarioActualizado.setTelefonoUsuario(444555666);
        usuarioActualizado.setGeneroUsuario("Femenino");
        usuarioActualizado.setContrasenaUsuario("Update2#"); // Contraseña válida actualizada

        // Actualizar usando el servicio
        String resultado = usuarioService.actualizarUsuario("updateuser", usuarioActualizado);

        // Verificar el resultado
        assertEquals("Usuario actualizado correctamente", resultado);

        // Verificar que los datos se actualizaron en la base de datos
        Usuario usuarioVerificado = usuarioService.obtenerUsuario("updateuser");
        assertEquals("UpdatedName", usuarioVerificado.getAppaterno());
        assertEquals("updated@example.com", usuarioVerificado.getEmailUsuario());
    }
}
