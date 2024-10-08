package com.github.arthurbcoelho.domain.repository;

import com.github.arthurbcoelho.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
