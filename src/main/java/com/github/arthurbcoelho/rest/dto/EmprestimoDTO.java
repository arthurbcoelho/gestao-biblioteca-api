package com.github.arthurbcoelho.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.arthurbcoelho.domain.enums.StatusEmprestimo;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record EmprestimoDTO(
        Long id,
        Long usuarioId,
        Long livroId,
        LocalDateTime dataEmprestimo,
        LocalDateTime dataDevolucao,
        @JsonProperty("status") StatusEmprestimo statusEmprestimo
) {
}
