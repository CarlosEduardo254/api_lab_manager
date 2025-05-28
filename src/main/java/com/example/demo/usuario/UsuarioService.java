package com.example.demo.usuario;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    @Autowired
    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario buscarUsuarioPorId(Long id) {
        if (repository.existsById(id)) {
            return repository.getReferenceById(id);
        }
        return null;
    }

    public void atualizarInformacoes(Usuario usuario, DTODadosUsuario dados) {
        if (dados.nome() != null){
            usuario.setNomeUsuario(dados.nome());
        }
        if (dados.email() != null){
            usuario.setEmail(dados.email());
        }
        if (dados.tipo_usuario() != null){
            usuario.setTipo_usuario(dados.tipo_usuario());
        }
    }

    public Usuario login(String nome, String email) {
        // Busca o usuário pelo nome e e-mail
        Optional<Usuario> usuarioOptional = repository.findByNomeUsuarioAndEmail(nome, email);

        if (usuarioOptional.isPresent()) {
            return usuarioOptional.get(); // Login bem-sucedido, retorna o usuário
        } else {
            throw new EntityNotFoundException("Usuário não encontrado ou dados incorretos"); // Nome ou e-mail inválido
        }
    }
}
