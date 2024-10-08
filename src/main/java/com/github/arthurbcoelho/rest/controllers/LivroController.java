package com.github.arthurbcoelho.rest.controllers;

import com.github.arthurbcoelho.domain.entity.Livro;
import com.github.arthurbcoelho.integracoes.GoogleBooksService;
import com.github.arthurbcoelho.services.LivroService;
import com.github.arthurbcoelho.utils.ApiConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController {
    private final LivroService livroService;
    private final GoogleBooksService googleBooksService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Livro> findLivros(@PathVariable(required = false) Integer page, @RequestParam(required = false) Integer limit) {
        if (page == null) {
            page = ApiConstants.PAGE_OFFSET;
        }

        if (limit == null) {
            limit = ApiConstants.PAGE_LIMIT;
        }

        PageRequest pageRequest = PageRequest.of(page, limit);
        return this.livroService.getLivros(pageRequest);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Livro createLivro(@RequestBody Livro livro) {
        return this.livroService.createLivro(livro);
    }

    @GetMapping("{id}")
    public Livro getLivroById(@PathVariable Long id) {
        return this.livroService.getLivroById(id);
    }

    @PutMapping("{id}")
    public Livro updateLivro(@PathVariable Long id, @RequestBody Livro livro) {
        return this.livroService.updateLivro(id, livro);
    }

    @DeleteMapping("{id}")
    public void deleteLivro(@PathVariable Long id) {
        this.livroService.deleteLivro(id);
    }

    @GetMapping("/recomendacoes-usuario/{usuarioId}")
    public List<Livro> findRecomendacoesByUsuarioId(@PathVariable Long usuarioId) {
        return this.livroService.getRecomendacoesLivroByUsuarioId(usuarioId);
    }

    @GetMapping("/integracao/busca-titulo")
    public String getLivrosByTituloFromGoogleBooksApi(@RequestParam String titulo) throws IOException, InterruptedException {
        return this.googleBooksService.getLivrosByTitulo(titulo);
    }
}
