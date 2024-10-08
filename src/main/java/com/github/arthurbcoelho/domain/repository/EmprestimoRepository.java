package com.github.arthurbcoelho.domain.repository;

import com.github.arthurbcoelho.domain.entity.Emprestimo;
import com.github.arthurbcoelho.domain.entity.Livro;
import com.github.arthurbcoelho.domain.enums.StatusEmprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    boolean existsByLivroAndStatus(Livro livro, StatusEmprestimo statusEmprestimo);
}
