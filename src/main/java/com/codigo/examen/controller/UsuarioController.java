package com.codigo.examen.controller;

import com.codigo.examen.entity.Usuario;
import com.codigo.examen.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ms-examen/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @PostMapping("/crear")
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        return usuarioService.createUsuario(usuario);
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        return usuarioService.getUsuarioById(id);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioService.updateUsuario(id,usuario);
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
        return usuarioService.deleteUsuario(id);
    }
}
