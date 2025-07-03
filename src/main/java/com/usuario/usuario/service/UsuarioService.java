package com.usuario.usuario.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.usuario.usuario.model.Usuario;
import com.usuario.usuario.model.dto.UsuarioDto;
import com.usuario.usuario.model.entity.UsuarioEntity;
import com.usuario.usuario.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RestTemplate restTemplate;

    public String obtenerUsuarioporProducto(Long idUsuario, Long idProducto) {
        try {
            // Llamada al microservicio de productos para obtener la info del producto
            String productoUrl = "http://3.221.38.5:8080/api/v1/productos/obtenerProductoDto/" + idProducto;
            String productoData = restTemplate.getForObject(productoUrl, String.class);
            if (productoData == null) {
                return "No se encontraron los datos del producto";
            }

            // Obtener usuario desde la BD
            UsuarioDto usuarioDto = obtenerUsuarioPorId(idUsuario);
            if (usuarioDto == null) {
                return "Usuario no encontrado";
            }

            // Combinar la info de usuario y producto para devolverla (ejemplo básico)
            return "Usuario: " + usuarioDto.getNombreUsuario() + ", Producto: " + productoData;
        } catch (Exception e) {
            return "Error al obtener usuario por producto: " + e.getMessage();
        }
    }

    @Transactional
    public String crearUsuario(Usuario usuario) {
        try {
            // Validación de objeto nulo
            if (usuario == null) {
                return "Error: El usuario no puede ser nulo";
            }

            // Validaciones de campos requeridos
            if (usuario.getNombreUsuario() == null || usuario.getNombreUsuario().trim().isEmpty()) {
                return "Error: El nombre de usuario es requerido";
            }
            if (usuario.getEmailUsuario() == null || usuario.getEmailUsuario().trim().isEmpty()) {
                return "Error: El email es requerido";
            }
            if (usuario.getContrasenaUsuario() == null || usuario.getContrasenaUsuario().trim().isEmpty()) {
                return "Error: La contraseña es requerida";
            }

            // Validación de usuario existente
            if (usuarioRepository.existsByNombreUsuario(usuario.getNombreUsuario())) {
                return "Error: El nombre de usuario ya existe";
            }

            // Validación de email existente (asumiendo que tienes este método en el
            // repository)
            if (usuarioRepository.existsByEmailUsuario(usuario.getEmailUsuario())) {
                return "Error: El email ya está registrado";
            }

            UsuarioEntity usuarioNuevo = mapToEntity(usuario);
            usuarioRepository.save(usuarioNuevo);
            return "Usuario creado exitosamente";

        } catch (IllegalArgumentException e) {
            return "Error de validación: " + e.getMessage();
        } catch (Exception e) {
            return "Error interno al crear el usuario: " + e.getMessage();
        }
    }

    public boolean existeUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario) != null;
    }

    public Usuario obtenerUsuario(String nombreUsuario) {
        try {
            UsuarioEntity usuario = usuarioRepository.findByNombreUsuario(nombreUsuario)
                    .orElse(null);
            return usuario != null ? mapToModel(usuario) : null;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener usuario: " + e.getMessage());
        }
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::mapToModel)
                .collect(Collectors.toList());
    }

    @Transactional
    public String actualizarUsuario(String nombreUsuario, Usuario usuario) {
        UsuarioEntity existente = usuarioRepository.findByNombreUsuario(nombreUsuario).orElse(null);
        if (existente != null) {
            existente.setNombreUsuario(usuario.getNombreUsuario());
            existente.setAppaterno(usuario.getAppaterno());
            existente.setApmaterno(usuario.getApmaterno());
            existente.setEmailUsuario(usuario.getEmailUsuario());
            existente.setDireccionUsuario(usuario.getDireccionUsuario());
            existente.setTelefonoUsuario(usuario.getTelefonoUsuario());
            existente.setGeneroUsuario(usuario.getGeneroUsuario());
            existente.setContrasenaUsuario(usuario.getContrasenaUsuario());
            return "Usuario actualizado correctamente";
        }
        return "Usuario no encontrado";
    }

    @Transactional
    public String eliminarUsuario(String nombreUsuario) {
        UsuarioEntity existente = usuarioRepository.findByNombreUsuario(nombreUsuario).orElse(null);
        if (existente != null) {
            usuarioRepository.delete(existente);
            return "Usuario eliminado correctamente";
        }
        return "Usuario no encontrado";
    }

    // Métodos auxiliares para mapear entre Producto y ProductoEntity
    private UsuarioEntity mapToEntity(Usuario usuario) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setNombreUsuario(usuario.getNombreUsuario());
        entity.setAppaterno(usuario.getAppaterno());
        entity.setApmaterno(usuario.getApmaterno());
        entity.setEmailUsuario(usuario.getEmailUsuario());
        entity.setDireccionUsuario(usuario.getDireccionUsuario());
        entity.setTelefonoUsuario(usuario.getTelefonoUsuario());
        entity.setGeneroUsuario(usuario.getGeneroUsuario());
        entity.setContrasenaUsuario(usuario.getContrasenaUsuario());
        return entity;
    }

    private Usuario mapToModel(UsuarioEntity entity) {
        try {
            if (entity == null) {
                return null;
            }

            return Usuario.builder()
                    .idUsuario(entity.getIdUsuario())
                    .nombreUsuario(entity.getNombreUsuario())
                    .appaterno(entity.getAppaterno())
                    .apmaterno(entity.getApmaterno())
                    .emailUsuario(entity.getEmailUsuario())
                    .direccionUsuario(entity.getDireccionUsuario())
                    .telefonoUsuario(entity.getTelefonoUsuario())
                    .generoUsuario(entity.getGeneroUsuario())
                    .contrasenaUsuario(entity.getContrasenaUsuario())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Error al mapear UsuarioEntity a Usuario: " + e.getMessage());
        }
    }

    public UsuarioDto obtenerUsuarioPorId(Long idUsuario) {
        try {
            UsuarioEntity usuario = usuarioRepository.findById(idUsuario).orElse(null);
            if (usuario == null) {
                return null;
            }

            UsuarioDto nuevoUsuario = new UsuarioDto();
            nuevoUsuario.setNombreUsuario(usuario.getNombreUsuario());
            nuevoUsuario.setAppaterno(usuario.getAppaterno());
            nuevoUsuario.setEmailUsuario(usuario.getEmailUsuario());

            return nuevoUsuario;
        } catch (Exception e) {
            return null;
        }
    }
}
