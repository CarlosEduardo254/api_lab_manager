package com.example.demo.producao;

import java.time.LocalDate;

public record DTOProducao(
        Long id_producao,
        TipoCont tipo_conteudo,
        String titulo,
        String descricao,
        LocalDate data_publicacao,
        Long id_usuario_criador // Adicionando validação de não nulo
) {
    public DTOProducao(Producao producao) {
        this(
                producao.getId_producao(),
                producao.getTipo_conteudo(),
                producao.getTitulo(),
                producao.getDescricao(),
                producao.getData_publicacao(),
                producao.getIdUsuarioCriador().getIdUsuario()
        );
    }
}
