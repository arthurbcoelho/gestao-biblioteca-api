package com.github.arthurbcoelho.domain.entity;

import com.github.arthurbcoelho.domain.enums.CategoriaLivro;
import com.github.arthurbcoelho.utils.ApiConstants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "LIVROS", schema = ApiConstants.LIBRARY_SCHEMA)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "autor", nullable = false)
    private String autor;

    @Column(name = "isbn", nullable = false)
    private String isbn;

    @Column(name = "data_publicacao", nullable = false)
    private LocalDate dataPublicacao;

    // Categoria principal do livro
    @Column(name = "categoria", nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoriaLivro categoria;
}
