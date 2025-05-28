package com.example.demo.controllers;

import com.example.demo.publicacao.DTOPublicacao;
import com.example.demo.publicacao.Publicacao;
import com.example.demo.publicacao.PublicacaoRepository;
import com.example.demo.publicacao.PublicacaoService;
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
@RequestMapping("/publicacao")
public class PublicacaoController {

    @Autowired
    private PublicacaoRepository repository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PublicacaoService publicacaoService;

    @PostMapping
    @Transactional
    public ResponseEntity<DTOPublicacao> publicacaoCadastro(@RequestBody @Valid DTOPublicacao publicacaoDTO, UriComponentsBuilder uriBuilder) {
        // Checagem da existencia de um usuário
        var usuarioCriador = usuarioRepository.findById(publicacaoDTO.id_usuario_criador()).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        var publi = new Publicacao();
        publi.setRede_social(publicacaoDTO.rede_social());
        publi.setDescricao(publicacaoDTO.descricao());
        publi.setData_publi(publicacaoDTO.data_publi());
        publi.setIdUsuarioCriador(usuarioCriador); // Associa o usuário criador

        usuarioCriador.setPublicacao(publi);

        repository.save(publi);

        var uri = uriBuilder.path("/publicacao/{id_publi}").buildAndExpand(publi.getId_publi()).toUri();

        return ResponseEntity.created(uri).body(new DTOPublicacao(publi));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTOPublicacao> buscarPublicacao(@PathVariable Long id) {
        Publicacao publicacao = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Publicação não encontrada"));


        return ResponseEntity.ok(new DTOPublicacao(publicacao.getId_publi(), publicacao.getRede_social(), publicacao.getDescricao(), publicacao.getData_publi(), publicacao.getIdUsuarioCriador()));
    }

    @GetMapping("/publicacoesUsuario")
    public ResponseEntity<LinkedList<DTOPublicacao>> buscarPublicacoesUsuario(@RequestParam Long id_usuario) {
        var usuario = usuarioRepository.findById(id_usuario).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        var publicacaoList = repository.findByIdUsuarioCriador(usuario).stream()
                .map(DTOPublicacao::new)
                .collect(Collectors.toCollection(LinkedList::new));

        return ResponseEntity.ok(publicacaoList);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DTOPublicacao> atualizarPubli(@RequestBody @Valid DTOPublicacao publicacaoAtualizar) {
        var publi = repository.findById(publicacaoAtualizar.id_publi()).orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada"));
        publicacaoService.atualizarPublicacao(publi, publicacaoAtualizar);

        return ResponseEntity.ok(new DTOPublicacao(publi));
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<DTOPublicacao> deletarPubli(@PathVariable Long id) {
        var publicacao = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Publicação não encontrada"));
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }


}
