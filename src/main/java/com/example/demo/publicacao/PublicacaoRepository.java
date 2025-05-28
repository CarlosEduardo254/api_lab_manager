package com.example.demo.publicacao;

import com.example.demo.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicacaoRepository extends JpaRepository<Publicacao, Long> {

    List<Publicacao> findByIdUsuarioCriador(Usuario usuario);
}
