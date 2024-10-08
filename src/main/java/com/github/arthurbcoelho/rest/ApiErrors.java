package com.github.arthurbcoelho.rest;

import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class ApiErrors {
    private List<String> erros;

    public ApiErrors(String errorMsg) {
        this.erros = Collections.singletonList(errorMsg);
    }
}
