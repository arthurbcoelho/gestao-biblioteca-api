package com.github.arthurbcoelho.services;

import com.github.arthurbcoelho.domain.entity.Livro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface LivroService {
    Page<Livro> getLivros(PageRequest pageRequest);
    Livro createLivro(Livro livro);
    Livro getLivroById(Long id);
    Livro updateLivro(Long id, Livro updatedLivro);
    void deleteLivro(Long id);

    List<Livro> getRecomendacoesLivroByUsuarioId(Long usuarioId);
}
