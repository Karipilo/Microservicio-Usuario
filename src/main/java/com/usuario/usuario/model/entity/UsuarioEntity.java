package com.usuario.usuario.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Usuarios")

public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(name = "nombre_usuario", nullable = false, length = 50)
    @NotNull
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String nombreUsuario;

    @Column(name = "appaterno", nullable = false, length = 30)
    @NotNull
    @Size(max = 30, message = "El apellido paterno no puede tener más de 30 caracteres")
    private String appaterno;

    @Column(name = "apmaterno", nullable = false, length = 30)
    @NotNull
    @Size(max = 30, message = "El apellido materno no puede tener más de 30 caracteres")
    private String apmaterno;

    @Column(name = "email_usuario", nullable = false, length = 50)
    @NotNull
    @Email(message = "El formato del email no es válido")
    @Size(max = 50, message = "El email no puede tener más de 50 caracteres")
    private String emailUsuario;

    @Column(name = "direccion_usuario", nullable = false, length = 100)
    @NotNull
    @Size(max = 100, message = "La dirección no puede tener más de 100 caracteres")
    private String direccionUsuario;

    @Column(name = "telefono_usuario", nullable = false)
    @NotNull
    @Positive(message = "El teléfono debe ser un número positivo")
    private Integer telefonoUsuario;

    @Column(name = "genero_usuario", nullable = false, length = 30)
    @NotNull
    @Size(max = 30, message = "El género no puede tener más de 30 caracteres")
    private String generoUsuario;

    @Column(name = "contrasena_usuario", nullable = false, length = 10)
    @NotNull
    @Size(min = 8, max = 10, message = "La contraseña debe tener entre 8 y 10 caracteres")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", message = "La contraseña debe contener al menos un número, una letra mayúscula, una minúscula y un carácter especial")
    private String contrasenaUsuario;

}
