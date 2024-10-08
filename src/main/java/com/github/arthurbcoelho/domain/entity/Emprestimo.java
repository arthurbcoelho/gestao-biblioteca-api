package com.github.arthurbcoelho.domain.entity;

import com.github.arthurbcoelho.domain.enums.StatusEmprestimo;
import com.github.arthurbcoelho.utils.ApiConstants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "EMPRESTIMOS", schema = ApiConstants.LIBRARY_SCHEMA)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    private Usuario usuario;

    @JoinColumn(name = "livro_id", referencedColumnName = "id", nullable = false)
    @OneToOne
    private Livro livro;

    @Column(name = "data_emprestimo", nullable = false)
    private LocalDateTime dataEmprestimo;

    @Column(name = "data_devolucao", nullable = false)
    private LocalDateTime dataDevolucao;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEmprestimo status;
}
