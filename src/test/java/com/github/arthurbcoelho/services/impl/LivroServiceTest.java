package com.github.arthurbcoelho.services.impl;

import com.github.arthurbcoelho.domain.entity.Livro;
import com.github.arthurbcoelho.domain.repository.LivroRepository;
import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LivroServiceTest {

    @Mock
    private LivroRepository livroRepository;

    @InjectMocks
    private LivroServiceImpl livroService;

    @Test
    public void deveCriarLivro() {
        Livro livro = Livro.builder().build();
        when(livroRepository.save(any(Livro.class))).thenReturn(livro);

        Livro result = livroService.createLivro(livro);

        assertNotNull(result);
        verify(livroRepository, times(1)).save(livro);
    }

    @Test
    public void deveBuscarLivroPorId() {
        Livro livro = Livro.builder().build();
        when(livroRepository.findById(anyLong())).thenReturn(Optional.of(livro));

        Livro result = livroService.getLivroById(1L);

        assertNotNull(result);
        verify(livroRepository, times(1)).findById(1L);
    }

    @Test
    public void deveBuscarRecomendacoesLivroByUsuarioId() {
        List<Livro> recomendacoes = Arrays.asList(Livro.builder().build(), Livro.builder().build());
        when(livroRepository.findRecomendacoesByUsuarioId(anyLong())).thenReturn(recomendacoes);

        List<Livro> result = livroService.getRecomendacoesLivroByUsuarioId(2L);

        assertEquals(2, result.size());
        verify(livroRepository, times(1)).findRecomendacoesByUsuarioId(2L);
    }

    @Test
    public void deveAtualizarLivro() {
        Livro livro = Livro.builder().build();
        Livro updatedLivro = Livro.builder().build();

        when(livroRepository.findById(anyLong())).thenReturn(Optional.of(livro));
        when(livroRepository.save(any(Livro.class))).thenReturn(updatedLivro);

        Livro result = livroService.updateLivro(1L, updatedLivro);

        assertNotNull(result);
        assertEquals(updatedLivro, result);

        verify(livroRepository, times(1)).findById(1L);
        verify(livroRepository, times(1)).save(updatedLivro);
    }

    @Test
    public void deveDeletarLivro() {
        Livro livro = Livro.builder().build();
        when(livroRepository.findById(anyLong())).thenReturn(Optional.of(livro));

        livroService.deleteLivro(1L);

        verify(livroRepository, times(1)).findById(1L);
        verify(livroRepository, times(1)).delete(livro);
    }

    @Test
    public void deveGerarEntityNotFoundNaBusca() {
        when(livroRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> livroService.getLivroById(1L));
    }

    @Test
    public void deveGerarEntityNotFoundNoUpdate() {
        when(livroRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> livroService.updateLivro(1L, Livro.builder().build()));
    }

    @Test
    public void deveGerarEntityNotFoundNoDelete() {
        when(livroRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () ->  livroService.deleteLivro(1L));
    }
}
