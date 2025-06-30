package com.usuario.usuario.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Usuario {

    private Long idUsuario;

    @NotNull
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String nombreUsuario;

    @NotNull
    @Size(max = 30, message = "El apellido paterno no puede tener más de 30 caracteres")
    private String appaterno;

    @NotNull
    @Size(max = 30, message = "El apellido materno no puede tener más de 30 caracteres")
    private String apmaterno;

    @NotNull
    @Email(message = "El formato del email no es válido")
    @Size(max = 50, message = "El email no puede tener más de 50 caracteres y debe tener un formato válido (@)")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "El email debe tener un formato válido")
    private String emailUsuario;

    @NotNull
    @Size(max = 100, message = "La dirección no puede tener más de 100 caracteres")
    private String direccionUsuario;

    @NotNull
    @Positive(message = "El teléfono debe ser un número positivo")
    private Integer telefonoUsuario;

    @NotNull
    @Size(max = 30, message = "El género no puede tener más de 30 caracteres")
    private String generoUsuario;

    @NotNull
    @Size(min = 8, max = 10, message = "La contraseña debe tener entre 8 y 10 caracteres")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", message = "La contraseña debe contener al menos un número, una letra mayúscula, una minúscula y un carácter especial")
    private String contrasenaUsuario;
}
