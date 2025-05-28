package com.example.demo.publicacao;

import com.example.demo.usuario.Usuario;
import com.example.demo.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublicacaoService {

    private final UsuarioService usuarioService;

    @Autowired
    public PublicacaoService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public Publicacao criarPublicacao(DTOPublicacao publicacaoDTO) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(publicacaoDTO.id_usuario_criador());
        return new Publicacao(publicacaoDTO.id_publi(), publicacaoDTO.rede_social(), publicacaoDTO.descricao(), publicacaoDTO.data_publi(), usuario);
    }

    public void atualizarPublicacao(Publicacao publicacao, DTOPublicacao publicacaoAtualizar) {
        if (publicacaoAtualizar.rede_social() != null) {
            publicacao.setRede_social(publicacaoAtualizar.rede_social());
        }
        if (publicacaoAtualizar.data_publi() != null) {
            publicacao.setData_publi(publicacaoAtualizar.data_publi());
        }
        if (publicacaoAtualizar.descricao() != null) {
            publicacao.setDescricao(publicacaoAtualizar.descricao());
        }
        if (publicacaoAtualizar.id_usuario_criador() != null) {
            Usuario usuario = usuarioService.buscarUsuarioPorId(publicacaoAtualizar.id_usuario_criador());
            publicacao.setIdUsuarioCriador(usuario);
        }
    }
}
