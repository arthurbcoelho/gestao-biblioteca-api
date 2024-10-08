package com.github.arthurbcoelho.services.impl;

import com.github.arthurbcoelho.domain.entity.Livro;
import com.github.arthurbcoelho.domain.repository.LivroRepository;
import com.github.arthurbcoelho.services.LivroService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LivroServiceImpl implements LivroService {
    private final LivroRepository livroRepository;

    @Override
    public Page<Livro> getLivros(PageRequest pageRequest) {
        return this.livroRepository.findAll(pageRequest);
    }

    @Override
    public Livro createLivro(Livro livro) {
        if (livro != null) {
            return this.livroRepository.save(livro);
        }
        return null;
    }

    @Override
    public Livro getLivroById(Long id) {
        return this.livroRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livro " + id + " não foi encontrado"));
    }

    @Override
    public Livro updateLivro(Long id, Livro updatedLivro) {
        Optional<Livro> livro = this.livroRepository.findById(id);

        if (livro.isPresent()) {
            updatedLivro.setId(id);
            return this.livroRepository.save(updatedLivro);
        }
        throw new EntityNotFoundException("Não foi possível atualizar o livro " + id + ". Livro não existe");
    }

    @Override
    public void deleteLivro(Long id) {
        this.livroRepository
                .findById(id)
                .ifPresentOrElse(this.livroRepository::delete,
                    () -> { throw new EntityNotFoundException("Não foi possível excluir o livro " + id +
                            ". Livro não existe"); });
    }

    @Override
    public List<Livro> getRecomendacoesLivroByUsuarioId(Long usuarioId) {
        return this.livroRepository.findRecomendacoesByUsuarioId(usuarioId);
    }


}
