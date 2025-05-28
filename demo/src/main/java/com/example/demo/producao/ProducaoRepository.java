package com.example.demo.producao;

import com.example.demo.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProducaoRepository extends JpaRepository<Producao, Long> {
    List<Producao> findByIdUsuarioCriador(Usuario usuario);
}
