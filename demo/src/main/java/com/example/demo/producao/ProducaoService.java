package com.example.demo.producao;

import com.example.demo.usuario.Usuario;
import com.example.demo.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProducaoService {

    private final UsuarioService usuarioService;

    @Autowired
    public ProducaoService(UsuarioService usuarioService) {this.usuarioService = usuarioService;}


    public void atualizarProducao(Producao producao, DTOProducao producaoAtualizar) {
        if(producaoAtualizar.tipo_conteudo() != null){
            producao.setTipo_conteudo(producaoAtualizar.tipo_conteudo());
        }
        if(producaoAtualizar.titulo() != null){
            producao.setTitulo(producaoAtualizar.titulo());
        }
        if(producaoAtualizar.descricao() != null){
            producao.setDescricao(producaoAtualizar.descricao());
        }
        if(producaoAtualizar.data_publicacao() != null){
            producao.setData_publicacao(producaoAtualizar.data_publicacao());
        }
        if(producaoAtualizar.id_usuario_criador() != null){
            Usuario usuario = usuarioService.buscarUsuarioPorId(producaoAtualizar.id_usuario_criador());
            producao.setIdUsuarioCriador(usuario);
        }
    }
}
