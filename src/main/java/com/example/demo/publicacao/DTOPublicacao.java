package com.example.demo.publicacao;

import com.example.demo.usuario.Usuario;

import java.time.LocalDate;

public record DTOPublicacao(
        Long id_publi,
        RedeSocial rede_social,
        String descricao,
        LocalDate data_publi,
        Long id_usuario_criador
) {

    public DTOPublicacao(Publicacao publicacao) {
        this(
                publicacao.getId_publi(),
                publicacao.getRede_social(),
                publicacao.getDescricao(),
                publicacao.getData_publi(),
                publicacao.getIdUsuarioCriador().getIdUsuario()
        );
    }


    public DTOPublicacao(Long idPubli, RedeSocial redeSocial, String descricao,LocalDate dataPubli, Usuario idUsuarioCriador) {
        this(
                idPubli,
                redeSocial,
                descricao,
                dataPubli,
                idUsuarioCriador.getIdUsuario()
        );
    }
}
