package com.github.arthurbcoelho.services.impl;

import com.github.arthurbcoelho.domain.entity.Emprestimo;
import com.github.arthurbcoelho.domain.entity.Livro;
import com.github.arthurbcoelho.domain.entity.Usuario;
import com.github.arthurbcoelho.domain.enums.StatusEmprestimo;
import com.github.arthurbcoelho.domain.repository.EmprestimoRepository;
import com.github.arthurbcoelho.exceptions.LivroEmprestadoException;
import com.github.arthurbcoelho.rest.dto.EmprestimoDTO;
import com.github.arthurbcoelho.services.EmprestimoService;
import com.github.arthurbcoelho.services.LivroService;
import com.github.arthurbcoelho.services.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmprestimoServiceImpl implements EmprestimoService {
    private final EmprestimoRepository emprestimoRepository;
    private final UsuarioService usuarioService;
    private final LivroService livroService;

    @Override
    public Page<Emprestimo> getAllEmprestimos(PageRequest pageRequest) {
        return this.emprestimoRepository.findAll(pageRequest);
    }

    @Override
    public Emprestimo emprestarLivro(EmprestimoDTO emprestimoDTO) {
        Objects.requireNonNull(emprestimoDTO.livroId());
        Objects.requireNonNull(emprestimoDTO.usuarioId());
        Objects.requireNonNull(emprestimoDTO.dataDevolucao());

        Livro livro = this.livroService.getLivroById(emprestimoDTO.livroId());
        Usuario usuario = this.usuarioService.getUsuarioById(emprestimoDTO.usuarioId());

        if (this.emprestimoRepository.existsByLivroAndStatus(livro, StatusEmprestimo.EMPRESTADO)) {
            throw new LivroEmprestadoException("Livro: " + livro.getTitulo() + " já está emprestado");
        }

        Emprestimo emprestimo = Emprestimo.builder()
                .usuario(usuario)
                .livro(livro)
                .dataEmprestimo(emprestimoDTO.dataEmprestimo() != null ? emprestimoDTO.dataEmprestimo() : LocalDateTime.now())
                .dataDevolucao(emprestimoDTO.dataDevolucao())
                .status(StatusEmprestimo.EMPRESTADO)
            .build();

        return this.emprestimoRepository.save(emprestimo);
    }

    @Override
    public Emprestimo atualizarEmprestimo(Long id, EmprestimoDTO emprestimoDTO) {
        Objects.requireNonNull(emprestimoDTO.statusEmprestimo());
        Objects.requireNonNull(emprestimoDTO.dataDevolucao());

        Emprestimo emprestimo = this.emprestimoRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empréstimo " + id + " não existe."));

        emprestimo.setStatus(emprestimoDTO.statusEmprestimo());
        emprestimo.setDataDevolucao(emprestimoDTO.dataDevolucao());

        return this.emprestimoRepository.save(emprestimo);
    }
}
