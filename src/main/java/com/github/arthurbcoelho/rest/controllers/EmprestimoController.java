package com.github.arthurbcoelho.rest.controllers;

import com.github.arthurbcoelho.domain.entity.Emprestimo;
import com.github.arthurbcoelho.rest.dto.EmprestimoDTO;
import com.github.arthurbcoelho.services.EmprestimoService;
import com.github.arthurbcoelho.utils.ApiConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emprestimos")
@RequiredArgsConstructor
public class EmprestimoController {
    private final EmprestimoService emprestimoService;

    @GetMapping
    public Page<Emprestimo> getAllEmprestimos(@PathVariable(required = false) Integer page,
                                              @RequestParam(required = false) Integer limit) {
        if (page == null) {
            page = ApiConstants.PAGE_OFFSET;
        }

        if (limit == null) {
            limit = ApiConstants.PAGE_LIMIT;
        }

        PageRequest pageRequest = PageRequest.of(page, limit);
        return this.emprestimoService.getAllEmprestimos(pageRequest);
    }

    @PostMapping
    public Emprestimo emprestarLivro(@RequestBody EmprestimoDTO emprestimoDTO) {
        return this.emprestimoService.emprestarLivro(emprestimoDTO);
    }

    @PutMapping("{id}")
    public Emprestimo atualizarEmprestimo(@PathVariable Long id, @RequestBody EmprestimoDTO emprestimoDTO) {
        return this.emprestimoService.atualizarEmprestimo(id, emprestimoDTO);
    }
}
