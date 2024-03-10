package com.codigo.examen.service.impl;

import com.codigo.examen.entity.Rol;
import com.codigo.examen.entity.Usuario;
import com.codigo.examen.repository.RolRepository;
import com.codigo.examen.repository.UsuarioRepository;
import com.codigo.examen.request.SignUpRequest;
import com.codigo.examen.service.AuthenticationService;
import com.codigo.examen.service.JWTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AuthenticationServiceImplTest {
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTService jwtService;

    @Mock
    private RolRepository rolRepository;


    @InjectMocks
    private AuthenticationServiceImpl authentication;


    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
         authentication = new AuthenticationServiceImpl(usuarioRepository,authenticationManager,jwtService,rolRepository);
    }
    @Test
    void signUpUser() {
        SignUpRequest request = usuarioRequest();
        Usuario usuario = createUsuario();
        when(rolRepository.findByNombreRol("USER")).thenReturn(getRol());
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        System.out.println("datos");
        System.out.println(authentication.signUpUser(request));
        assertEquals(usuario.getUsername(),authentication.signUpUser(request).getUsername());
    }

    @Test
    void signin() {

    }


    private SignUpRequest usuarioRequest(){
        SignUpRequest user=new SignUpRequest("kordero","921984319","elpadredetest@gmail.com","123456");
        return user;
    }
    private Usuario createUsuario(){
        Rol role= new Rol(123L,"USER");
        Set<Rol> rol = new HashSet<>();
        rol.add(role);
        Usuario usuario = new Usuario();
        usuario.setUsername("kordero");
        usuario.setIdUsuario(1L);
        usuario.setEmail("elpadredelcordero@gmail.com");
        usuario.setTelefono("921984319");
        usuario.setPassword("123456");
        usuario.setRoles(rol);
        return  usuario;
    }
    private Optional<Rol> getRol(){
        Rol role= new Rol(123L,"USER");
        return  Optional.of(role);
    }
}