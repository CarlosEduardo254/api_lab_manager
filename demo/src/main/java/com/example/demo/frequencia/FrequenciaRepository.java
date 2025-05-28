package com.example.demo.frequencia;

import com.example.demo.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FrequenciaRepository extends JpaRepository<Frequencia, Long> {
    List<Frequencia> findByIdUsuarioProf(Usuario idUsuarioProf);
}
