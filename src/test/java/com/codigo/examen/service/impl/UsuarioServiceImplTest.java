package com.codigo.examen.service.impl;

import com.codigo.examen.entity.Rol;
import com.codigo.examen.entity.Usuario;
import com.codigo.examen.repository.RolRepository;
import com.codigo.examen.repository.UsuarioRepository;
import com.codigo.examen.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UsuarioServiceImplTest {
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        usuarioService= new UsuarioServiceImpl(usuarioRepository,rolRepository);
    }


    @Test
    void createUsuarioOutSuccess() {
        Usuario usuarioRequest= createUsuario();
        when(rolRepository.findByNombreRol("USER")).thenReturn(getRol());
        when(usuarioRepository.findByUsername("kordero")).thenReturn(Optional.empty());
        assertEquals(HttpStatus.CREATED, usuarioService.createUsuario(usuarioRequest).getStatusCode());
    }

    @Test
    void  createUsuarioOutError(){
        Usuario usuarioRequest = new Usuario();
        usuarioRequest.setUsername("kordero");
        usuarioRequest.setIdUsuario(123L);
        usuarioRequest.setTelefono("921984319");
        usuarioRequest.setPassword("123456");
        usuarioRequest.setEmail("elpadredelcordero@gmail.com");

        when(usuarioRepository.save(new Usuario())).thenThrow(RuntimeException.class);
        assertThrows(Exception.class,()->usuarioService.createUsuario(usuarioRequest));
    }

    @Test
    void getUsuarioByIdSuccess() {
        Long id=1L;
        Optional<Usuario> usuarioFinded = getFindByUserName();
        when(usuarioRepository.findById(id)).thenReturn(usuarioFinded);
        ResponseEntity<Usuario> usuarioResponse=usuarioService.getUsuarioById(id);
        assertTrue(usuarioFinded.isPresent());
        assertEquals(usuarioFinded.get(),usuarioResponse.getBody());
    }

    @Test
    void getUsuarioByIdThrow(){
        Long id=2L;
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,usuarioService.getUsuarioById(id).getStatusCode());

    }

    @Test
    void updateUsuarioSuccess() {
        Long id=1L;
        Optional<Usuario> usuarioFinded = getFindByUserName();
        Usuario user = createUsuario();
        when(usuarioRepository.findById(id)).thenReturn(usuarioFinded);
        ResponseEntity<Usuario>usuarioResponse = usuarioService.updateUsuario(id,user);
        assertTrue(usuarioFinded.isPresent());

    }

    @Test
    void updateUsuarioSuccessWhenIsNotPresent(){
        Long id=2L;
        Usuario user = createUsuario();
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());
        assertEquals(HttpStatus.NOT_FOUND, usuarioService.updateUsuario(id,user).getStatusCode());

    }

   @Test
    void updateUsuarioSuccessWhenIsDifferentUsername(){
        Long id=1L;
        Usuario user= createUsuario();
        Optional<Usuario> usuarioFinded = getFindByUserName();
        user.setUsername("kordero2");
        when(usuarioRepository.findById(id)).thenReturn(usuarioFinded);
    }
    @Test
    void deleteUsuario() {
        Long id=1L;
        Optional<Usuario> usuario=getFindByUserName();
        when(usuarioRepository.findById(id)).thenReturn(usuario);
        assertEquals(HttpStatus.NO_CONTENT, usuarioService.deleteUsuario(id).getStatusCode());

    }

    @Test
    void deleteUsuarioNotFound() {
        Long id=1L;
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());
        assertEquals(HttpStatus.NOT_FOUND, usuarioService.deleteUsuario(id).getStatusCode());
    }


        private Usuario createUsuario(){
        Rol role= new Rol(123L,"USER");
        Set<Rol> rol = new HashSet<>();
        rol.add(role);
        Usuario usuario = new Usuario();
        usuario.setUsername("kordero");
        usuario.setIdUsuario(1L);
        usuario.setTelefono("921984319");
        usuario.setPassword("123456");
        usuario.setRoles(rol);
        return  usuario;
    }




    private Optional<Usuario>getFindByUserName(){
        Rol role= new Rol(123L,"USER");
        Set<Rol> rol = new HashSet<>();
        rol.add(role);
        Usuario usuario = new Usuario();
        usuario.setUsername("kordero");
        usuario.setIdUsuario(1L);
        usuario.setTelefono("921984319");
        usuario.setPassword("123456");
        usuario.setRoles(rol);
        return Optional.of(usuario);
    }

    private Optional<Rol> getRol(){
        Rol role= new Rol(123L,"USER");
        return  Optional.of(role);
    }
}