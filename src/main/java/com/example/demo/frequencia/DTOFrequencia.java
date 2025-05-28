package com.example.demo.frequencia;

import com.example.demo.usuario.Usuario;

import java.time.LocalDate;
import java.time.LocalTime;

public record DTOFrequencia(
        Long id,
        LocalDate data,
        LocalTime hora,
        String lab_frequencia,
        String tipo_de_usua,
        Long id_usuario_prof
) {

    public DTOFrequencia(Frequencia frequencia) {
        this(
                frequencia.getId_frequencia(),
                frequencia.getData(),
                frequencia.getHora(),
                frequencia.getFreq_alunos(),
                frequencia.getPresenca_alunos(),
                frequencia.getIdUsuarioProf().getIdUsuario()
        );
    }

    public DTOFrequencia(Long idFrequencia, LocalDate data, LocalTime hora, String labFrequencia, String tipoDeUsua, Usuario idUsuarioProf) {
        this(
                idFrequencia,
                data,
                hora,
                labFrequencia,
                tipoDeUsua,
                idUsuarioProf.getIdUsuario()
        );
    }
}
