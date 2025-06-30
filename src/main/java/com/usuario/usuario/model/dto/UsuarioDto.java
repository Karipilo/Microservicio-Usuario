package com.usuario.usuario.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UsuarioDto {

    private Long idUsuario;
    private String nombreUsuario;
    private String appaterno;
    private String emailUsuario;
}
