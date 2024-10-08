package com.github.arthurbcoelho.services.impl;

import com.github.arthurbcoelho.domain.entity.Emprestimo;
import com.github.arthurbcoelho.domain.entity.Livro;
import com.github.arthurbcoelho.domain.entity.Usuario;
import com.github.arthurbcoelho.domain.enums.StatusEmprestimo;
import com.github.arthurbcoelho.domain.repository.EmprestimoRepository;
import com.github.arthurbcoelho.exceptions.LivroEmprestadoException;
import com.github.arthurbcoelho.rest.dto.EmprestimoDTO;
import com.github.arthurbcoelho.services.LivroService;
import com.github.arthurbcoelho.services.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
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
public class EmprestimoServiceTest {

    @Mock
    private EmprestimoRepository emprestimoRepository;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private LivroService livroService;

    @InjectMocks
    private EmprestimoServiceImpl emprestimoService;

    @Test
    public void deveEmprestarLivro() {
        Livro livro = Livro.builder().build();
        Usuario usuario = Usuario.builder().build();
        EmprestimoDTO emprestimoDTO = new EmprestimoDTO(1L, 1L, 1L, LocalDateTime.now(), LocalDateTime.now().plusDays(7), StatusEmprestimo.EMPRESTADO);

        when(livroService.getLivroById(anyLong())).thenReturn(livro);
        when(usuarioService.getUsuarioById(anyLong())).thenReturn(usuario);
        when(emprestimoRepository.existsByLivroAndStatus(any(Livro.class), any(StatusEmprestimo.class))).thenReturn(false);
        when(emprestimoRepository.save(any(Emprestimo.class))).thenReturn(Emprestimo.builder().build());

        Emprestimo result = emprestimoService.emprestarLivro(emprestimoDTO);

        assertNotNull(result);
        verify(livroService, times(1)).getLivroById(1L);
        verify(usuarioService, times(1)).getUsuarioById(1L);
        verify(emprestimoRepository, times(1)).existsByLivroAndStatus(livro, StatusEmprestimo.EMPRESTADO);
        verify(emprestimoRepository, times(1)).save(any(Emprestimo.class));
    }

    @Test
    public void deveGerarLivroEmprestadoException() {
        Livro livro = Livro.builder().build();
        EmprestimoDTO emprestimoDTO = new EmprestimoDTO(1L, 1L, 1L, LocalDateTime.now(), LocalDateTime.now().plusDays(7), StatusEmprestimo.EMPRESTADO);

        when(livroService.getLivroById(anyLong())).thenReturn(livro);
        when(emprestimoRepository.existsByLivroAndStatus(any(Livro.class), any(StatusEmprestimo.class))).thenReturn(true);

        assertThrows(LivroEmprestadoException.class, () -> emprestimoService.emprestarLivro(emprestimoDTO));
    }

    @Test
    public void deveAtualizarEmprestimo() {
        Emprestimo emprestimo = Emprestimo.builder().build();
        EmprestimoDTO emprestimoDTO = new EmprestimoDTO(null, null, null, null, LocalDateTime.now().plusDays(7), StatusEmprestimo.DEVOLVIDO);

        when(emprestimoRepository.findById(anyLong())).thenReturn(Optional.of(emprestimo));
        when(emprestimoRepository.save(any(Emprestimo.class))).thenReturn(emprestimo);

        Emprestimo result = emprestimoService.atualizarEmprestimo(1L, emprestimoDTO);

        assertNotNull(result);
        assertEquals(StatusEmprestimo.DEVOLVIDO, result.getStatus());

        verify(emprestimoRepository, times(1)).findById(1L);
        verify(emprestimoRepository, times(1)).save(emprestimo);
    }

    @Test
    public void deveGerarEntityNotFoundExceptionNoUpdate() {
        EmprestimoDTO emprestimoDTO = new EmprestimoDTO(null, null, null, null, LocalDateTime.now().plusDays(7), StatusEmprestimo.DEVOLVIDO);

        when(emprestimoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> emprestimoService.atualizarEmprestimo(1L, emprestimoDTO));
    }
}
