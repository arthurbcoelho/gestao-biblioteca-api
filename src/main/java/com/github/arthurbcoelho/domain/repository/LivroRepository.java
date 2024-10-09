package com.github.arthurbcoelho.domain.repository;

import com.github.arthurbcoelho.domain.entity.Livro;
import com.github.arthurbcoelho.domain.enums.CategoriaLivro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    @Query(value = "SELECT DISTINCT l.categoria FROM biblioteca_owner.livros l " +
            "INNER JOIN biblioteca_owner.emprestimos e ON l.id = e.livro_id " +
            "WHERE e.usuario_id = :usuarioId", nativeQuery = true)
    List<CategoriaLivro> findCategoriasLidasPeloUsuario(Long usuarioId);

    @Query(value = "SELECT * " +
            "FROM biblioteca_owner.livros l " +
            "WHERE l.categoria IN (" +
            "    SELECT l.categoria " +
            "    FROM biblioteca_owner.livros l" +
            "    JOIN biblioteca_owner.emprestimos e ON e.livro_id = l.id " +
            "    WHERE e.usuario_id = :usuarioId " +
            ") " +
            "AND l.id NOT IN (" +
            "    SELECT e.livro_id " +
            "    FROM biblioteca_owner.emprestimos e " +
            "    WHERE e.usuario_id = :usuarioId)", nativeQuery = true)
    List<Livro> findRecomendacoesByUsuarioId(Long usuarioId);
}
