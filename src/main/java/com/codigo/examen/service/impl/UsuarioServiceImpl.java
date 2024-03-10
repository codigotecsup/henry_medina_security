package com.codigo.examen.service.impl;

import com.codigo.examen.entity.Rol;
import com.codigo.examen.entity.Usuario;
import com.codigo.examen.repository.RolRepository;
import com.codigo.examen.repository.UsuarioRepository;
import com.codigo.examen.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;


    @Override
    public ResponseEntity<Usuario> createUsuario(Usuario usuario) {
        Optional<Usuario> existingUser = usuarioRepository.findByUsername(usuario.getUsername());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        //return getUsuarioResponseEntity(usuario);
        Optional<Rol> rolResponse = rolRepository.findByNombreRol("USER");
        Set<Rol> roles= new HashSet<>();
        roles.add(rolResponse.get());

        Usuario usuarioToSave = new Usuario();
        usuarioToSave.setUsername(usuario.getUsername());
        usuarioToSave.setEmail(usuario.getEmail());
        usuarioToSave.setTelefono(usuario.getTelefono());
        usuarioToSave.setRoles(roles);
        usuarioToSave.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
         Usuario userSaved=usuarioRepository.save(usuarioToSave);
         return  ResponseEntity.status(HttpStatus.CREATED).body(userSaved);
    }

    @Override
    public ResponseEntity<Usuario> getUsuarioById(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if(usuario.isEmpty()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Usuario> updateUsuario(Long id, Usuario usuario) {
        Optional<Usuario> existingUsuario = usuarioRepository.findById(id);
        if (existingUsuario.isPresent()) {
            usuario.setIdUsuario(id);
            if (!usuario.getUsername().equals(existingUsuario.get().getUsername())) {
                Optional<Usuario> userWithNewUsername = usuarioRepository.findByUsername(usuario.getUsername());
                if (userWithNewUsername.isPresent()) {
                    return ResponseEntity.badRequest().body(null);
                }
            }
            return getUsuarioResponseEntity(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private ResponseEntity<Usuario> getUsuarioResponseEntity(Usuario usuario) {
        Set<Rol> assignedRoles = new HashSet<>();
        for (Rol roles : usuario.getRoles()) {
            Optional<Rol> rol = rolRepository.findById(roles.getIdRol());
            if (!rol.isPresent()) {
                return ResponseEntity.badRequest().body(null);
            }
            assignedRoles.add(rol.get());
        }
        usuario.setRoles(assignedRoles);
        Usuario updatedUsuario = usuarioRepository.save(usuario);
        return ResponseEntity.ok(updatedUsuario);
    }

    @Override
    public ResponseEntity<Usuario> deleteUsuario(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            usuarioRepository.delete(usuario.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public UserDetailsService userDetailsService() {
        // * Se instancia un userDetailsService y se sobrescribe
        //* inmediatamente el método
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return usuarioRepository.findByUsername(username).orElseThrow( ()->
                        new UsernameNotFoundException("Usuario no encontrado"));
            }
        };
    }
}
