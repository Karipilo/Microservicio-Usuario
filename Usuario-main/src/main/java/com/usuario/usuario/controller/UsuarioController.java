package com.usuario.usuario.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usuario.usuario.model.Usuario;
import com.usuario.usuario.model.dto.UsuarioDto;
import com.usuario.usuario.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @Operation(summary = "Este endpoint permite agregar usuarios")
    public ResponseEntity<String> crearUsuario(@Valid @RequestBody Usuario usuario) {
        String resultado = usuarioService.crearUsuario(usuario);

        // Si el resultado contiene "Error", devolver estado de error
        if (resultado.startsWith("Error:")) {
            if (resultado.contains("ya existe") || resultado.contains("ya está registrado")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(resultado);
            }
            return ResponseEntity.badRequest().body(resultado);
        }

        // Si llegamos aquí, el usuario se creó exitosamente
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    @GetMapping("/{nombreUsuario}")
    @Operation(summary = "Este endpoint permite obtener un usuario por el nombre")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable String nombreUsuario) {
        Usuario usuario = usuarioService.obtenerUsuario(nombreUsuario);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    @Operation(summary = "Este endpoint permite obtener todos los usuarios creados")
    public ResponseEntity<List<Usuario>> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{nombreUsuario}")
    @Operation(summary = "Este endpoint permite modificar algun usuario por su nombre")
    public ResponseEntity<?> actualizarUsuario(@PathVariable String nombreUsuario,
            @Valid @RequestBody Usuario usuario) {
        try {
            String resultado = usuarioService.actualizarUsuario(nombreUsuario, usuario);

            if (resultado.equals("Usuario no encontrado")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensaje", "Usuario no encontrado"));

            }
            return ResponseEntity.ok(Map.of("mensaje", "Usuario actualizado exitosamente"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al actualizar el Usuario" + e.getMessage()));

        }
    }

    @DeleteMapping("/{nombreUsuario}")
    @Operation(summary = "Este endpoint permite eliminar un usuario por el nombre")
    public ResponseEntity<String> eliminarUsuario(@PathVariable String nombreUsuario) {
        String mensaje = usuarioService.eliminarUsuario(nombreUsuario);
        if ("Usuario eliminado correctamente".equals(mensaje)) {
            return ResponseEntity.ok(mensaje);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/obtenerUsuarioDto/{IdUsuario}")
    @Operation(summary = "Este endpoint permite obtener un usuario por su ID y devolverlo como UsuarioDto")
    public ResponseEntity<UsuarioDto> obtenerUsuarioDto(@PathVariable Long IdUsuario) {
        UsuarioDto usuarioDto = usuarioService.obtenerUsuarioPorId(IdUsuario);
        if (usuarioDto != null) {
            return ResponseEntity.ok(usuarioDto);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/obtenerUsuarioporProducto/{idUsuario}/{idProducto}")
    @Operation(summary = "Este endpoint permite obtener un usuario por su ID y el ID de un producto asociado")
    public ResponseEntity<String> obtenerUsuarioporProducto(
            @PathVariable Long idUsuario,
            @PathVariable Long idProducto) {
        return ResponseEntity.ok(usuarioService.obtenerUsuarioporProducto(idUsuario, idProducto));
    }
}
