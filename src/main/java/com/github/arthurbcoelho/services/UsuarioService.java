package com.github.arthurbcoelho.services;

import com.github.arthurbcoelho.domain.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface UsuarioService {
    Page<Usuario> getAllUsuarios(PageRequest pageRequest);
    Usuario createUser(Usuario usuario);
    Usuario getUsuarioById(Long id);
    Usuario updateUsuario(Long id, Usuario updateUsuario);
    void deleteUsuario(Long id);
}
