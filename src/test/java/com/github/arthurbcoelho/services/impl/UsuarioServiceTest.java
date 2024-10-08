package com.github.arthurbcoelho.services.impl;

import com.github.arthurbcoelho.domain.entity.Usuario;
import com.github.arthurbcoelho.domain.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Test
    public void deveCriarUsuario() {
        Usuario usuario = Usuario.builder().dataCadastro(LocalDateTime.now().minusDays(1)).build();
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario result = usuarioService.createUser(usuario);

        assertNotNull(result);
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    public void deveLancarValidationExceptionAoCriarUsuarioComDataFutura() {
        Usuario usuario = Usuario.builder().dataCadastro(LocalDateTime.now().plusDays(1)).build();

        assertThrows(ValidationException.class, () -> usuarioService.createUser(usuario));
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    public void deveBuscarUsuarioPorId() {
        Usuario usuario = Usuario.builder().build();
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));

        Usuario result = usuarioService.getUsuarioById(1L);

        assertNotNull(result);
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    public void deveLancarEntityNotFoundExceptionAoBuscarUsuario() {
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> usuarioService.getUsuarioById(1L));
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    public void deveAtualizarUsuario() {
        Usuario usuario = Usuario.builder().build();
        Usuario updatedUsuario = Usuario.builder().build();

        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(updatedUsuario);

        Usuario result = usuarioService.updateUsuario(1L, updatedUsuario);

        assertNotNull(result);
        verify(usuarioRepository, times(1)).findById(1L);
        verify(usuarioRepository, times(1)).save(updatedUsuario);
    }

    @Test
    public void deveLancarEntityNotFoundExceptionAoAtualizarUsuario() {
        Usuario updatedUsuario = Usuario.builder().build();
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> usuarioService.updateUsuario(1L, updatedUsuario));
        verify(usuarioRepository, times(1)).findById(1L);
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    public void deveDeletarUsuario() {
        Usuario usuario = Usuario.builder().build();
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));

        usuarioService.deleteUsuario(1L);

        verify(usuarioRepository, times(1)).findById(1L);
        verify(usuarioRepository, times(1)).delete(usuario);
    }

    @Test
    public void deveLancarEntityNotFoundExceptionAoDeletarUsuario() {
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> usuarioService.deleteUsuario(1L));
        verify(usuarioRepository, times(1)).findById(1L);
        verify(usuarioRepository, never()).delete(any(Usuario.class));
    }
}
