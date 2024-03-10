package com.codigo.examen.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignUpRequest {
    private String username;
    private String telefono;
    private String email;
    private String password;
}
