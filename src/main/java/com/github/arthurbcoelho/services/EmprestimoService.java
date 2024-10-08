package com.github.arthurbcoelho.services;

import com.github.arthurbcoelho.domain.entity.Emprestimo;
import com.github.arthurbcoelho.rest.dto.EmprestimoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface EmprestimoService {
    Page<Emprestimo> getAllEmprestimos(PageRequest pageRequest);
    Emprestimo emprestarLivro(EmprestimoDTO emprestimoDTO);
    Emprestimo atualizarEmprestimo(Long id, EmprestimoDTO emprestimoDTO);
}
