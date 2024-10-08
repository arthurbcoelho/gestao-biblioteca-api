package com.github.arthurbcoelho.services.impl;

import com.github.arthurbcoelho.domain.entity.Usuario;
import com.github.arthurbcoelho.domain.repository.UsuarioRepository;
import com.github.arthurbcoelho.services.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;

    @Override
    public Page<Usuario> getAllUsuarios(PageRequest pageRequest) {
        return this.usuarioRepository.findAll(pageRequest);
    }

    @Override
    public Usuario createUser(Usuario usuario) {
        if (usuario != null) {
            if (LocalDateTime.now().isAfter(usuario.getDataCadastro())) {
                return this.usuarioRepository.save(usuario);
            }
            throw new ValidationException("Data de cadastro não pode ser maior que a data atual.");
        }
        return null;
    }

    @Override
    public Usuario getUsuarioById(Long id) {
        return this.usuarioRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário " + id + " não foi encontrado"));
    }

    @Override
    public Usuario updateUsuario(Long id, Usuario updateUsuario) {
        Optional<Usuario> usuario = this.usuarioRepository.findById(id);

        if (usuario.isPresent()) {
            updateUsuario.setId(id);
            return this.usuarioRepository.save(updateUsuario);
        }
        throw new EntityNotFoundException("Não foi possível atualizar o usuário " + id + ". Usuário não existe");
    }

    @Override
    public void deleteUsuario(Long id) {
        this.usuarioRepository
                .findById(id)
                .ifPresentOrElse(this.usuarioRepository::delete,
                    () -> { throw new EntityNotFoundException("Não foi possível excluir o usuário "
                                    + id + ". Usuário não existe"); }
        );
    }
}
