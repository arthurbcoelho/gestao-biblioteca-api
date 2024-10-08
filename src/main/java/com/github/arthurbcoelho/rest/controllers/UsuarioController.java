package com.github.arthurbcoelho.rest.controllers;

import com.github.arthurbcoelho.domain.entity.Usuario;
import com.github.arthurbcoelho.services.UsuarioService;
import com.github.arthurbcoelho.utils.ApiConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Usuario> getAllUsuarios(@PathVariable(required = false) Integer page, @RequestParam(required = false) Integer limit) {
        if (page == null) {
            page = ApiConstants.PAGE_OFFSET;
        }

        if (limit == null) {
            limit = ApiConstants.PAGE_LIMIT;
        }

        PageRequest pageRequest = PageRequest.of(page, limit);
        return this.usuarioService.getAllUsuarios(pageRequest);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        return this.usuarioService.createUser(usuario);
    }

    @GetMapping("{id}")
    public Usuario getUsuarioById(@PathVariable Long id) {
        return this.usuarioService.getUsuarioById(id);
    }

    @PutMapping("{id}")
    public Usuario updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        return this.usuarioService.updateUsuario(id, usuario);
    }

    @DeleteMapping("{id}")
    public void deleteUsuario(@PathVariable Long id) {
        this.usuarioService.deleteUsuario(id);
    }
}
