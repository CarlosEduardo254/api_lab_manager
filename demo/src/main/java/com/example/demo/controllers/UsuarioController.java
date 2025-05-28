package com.example.demo.controllers;

import com.example.demo.usuario.DTODadosUsuario;
import com.example.demo.usuario.Usuario;
import com.example.demo.usuario.UsuarioRepository;
import com.example.demo.usuario.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.LinkedList;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private UsuarioService service;


    @PostMapping
    @Transactional
    public ResponseEntity<DTODadosUsuario> usuarioCadastro(@RequestBody @Valid DTODadosUsuario usuarioDTO, UriComponentsBuilder uriBuilder){
        var usuario = new Usuario(usuarioDTO);
        repository.save(usuario);

        var uri = uriBuilder.path("/usuario/{idUsuario}").buildAndExpand(usuario.getIdUsuario()).toUri();

        return ResponseEntity.created(uri).body(new DTODadosUsuario(usuario));
    }

    @GetMapping
    public ResponseEntity<LinkedList<DTODadosUsuario>> listarUsuario(){
        var lista = repository.findAll().stream()
                .map(DTODadosUsuario::new)
                .collect(Collectors.toCollection(LinkedList::new));

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTODadosUsuario> buscarUsuarioPorId(@PathVariable Long id){
        var usuario = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        return ResponseEntity.ok(new DTODadosUsuario(usuario));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DTODadosUsuario> atualizar(@RequestBody @Valid DTODadosUsuario usuarioAtualizar){
        var usuario = repository.findById(usuarioAtualizar.id()).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        service.atualizarInformacoes(usuario, usuarioAtualizar);

        return ResponseEntity.ok(new DTODadosUsuario(usuario));
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<DTODadosUsuario> deletarUsuario(@PathVariable Long id){
        var usuario = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
