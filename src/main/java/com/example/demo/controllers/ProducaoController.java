package com.example.demo.controllers;

import com.example.demo.producao.DTOProducao;
import com.example.demo.producao.Producao;
import com.example.demo.producao.ProducaoRepository;
import com.example.demo.producao.ProducaoService;
import com.example.demo.usuario.UsuarioRepository;
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
@RequestMapping("/producao")
public class ProducaoController {

    @Autowired
    private ProducaoRepository repository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ProducaoService producaoService;

    @PostMapping
    @Transactional
    public ResponseEntity<DTOProducao> producaoCadastro(@RequestBody @Valid DTOProducao producaoDTO, UriComponentsBuilder uriBuilder) {
        var usuarioCriador = usuarioRepository.findById(producaoDTO.id_usuario_criador()).orElseThrow(() -> new EntityNotFoundException("Usuário criador da produção não encontrada"));

        var producao = new Producao();
        producao.setTitulo(producaoDTO.titulo());
        producao.setDescricao(producaoDTO.descricao());
        producao.setData_publicacao(producaoDTO.data_publicacao());
        producao.setTipo_conteudo(producaoDTO.tipo_conteudo());
        producao.setIdUsuarioCriador(usuarioCriador);

        usuarioCriador.setProducao(producao);
        repository.save(producao);

        var uri = uriBuilder.path("/producao/{id_producao}").buildAndExpand(producao.getId_producao()).toUri();

        return ResponseEntity.created(uri).body(new DTOProducao(producao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTOProducao> buscarProducao(@PathVariable Long id) {
        var producao = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Produção não encontrada"));

        return ResponseEntity.ok(new DTOProducao(producao));
    }

    @GetMapping("/producoesUsuario")
    public ResponseEntity<LinkedList<DTOProducao>> buscarProdutosUsuario(@RequestParam Long id_usuario) {
        var usuario = usuarioRepository.findById(id_usuario).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrada"));

        var producaoList = repository.findByIdUsuarioCriador(usuario).stream()
                .map(DTOProducao::new)
                .collect(Collectors.toCollection(LinkedList::new));

        return ResponseEntity.ok(producaoList);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DTOProducao> atualizarProducao(@RequestBody @Valid DTOProducao producaoAtualizar) {
        var producao = repository.findById(producaoAtualizar.id_producao()).orElseThrow(() -> new EntityNotFoundException("Produção não encontrada"));
        producaoService.atualizarProducao(producao, producaoAtualizar);

        return ResponseEntity.ok(new DTOProducao(producao));

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<DTOProducao> deletarProducao(@PathVariable Long id) {
        var producao = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Produção não encontrada"));
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}



