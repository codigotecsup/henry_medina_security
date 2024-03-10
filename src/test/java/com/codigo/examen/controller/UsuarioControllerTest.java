package com.codigo.examen.controller;

import com.codigo.examen.entity.Rol;
import com.codigo.examen.entity.Usuario;
import com.codigo.examen.service.UsuarioService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

public class UsuarioControllerTest {
    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        usuarioController = new UsuarioController(usuarioService);
    }

    @Test
    public void crearUsuarioSuccess() {
        Rol role= new Rol(123L,"USER");
        Set<Rol> rol = new HashSet<>();
        rol.add(role);
        Usuario usuario = new Usuario();
        usuario.setUsername("Henry");
        usuario.setIdUsuario(123L);
        usuario.setTelefono("921984319");
        usuario.setPassword("123456");
        usuario.setRoles(rol);
    }
}