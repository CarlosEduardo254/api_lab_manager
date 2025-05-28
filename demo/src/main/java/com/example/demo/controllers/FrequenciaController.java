package com.example.demo.controllers;

import com.example.demo.frequencia.DTOFrequencia;
import com.example.demo.frequencia.Frequencia;
import com.example.demo.frequencia.FrequenciaRepository;
import com.example.demo.frequencia.FrequenciaService;
import com.example.demo.usuario.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.LinkedList;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/frequencia")
public class FrequenciaController {

    @Autowired
    private FrequenciaRepository repository;
    @Autowired
    private FrequenciaService frequenciaService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DTOFrequencia> criarFrequencia(@RequestBody DTOFrequencia frequenciaDTO, UriComponentsBuilder uriBuilder) {
        // Checagem da existencia de um usuário
        var professor = usuarioRepository.findById(frequenciaDTO.id_usuario_prof()).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        var frequencia = new Frequencia();
        frequencia.setData(frequenciaDTO.data());
        frequencia.setHora(frequenciaDTO.hora());
        frequencia.setFreq_alunos(frequenciaDTO.lab_frequencia());
        frequencia.setPresenca_alunos(frequenciaDTO.tipo_de_usua());
        frequencia.setIdUsuarioProf(professor);

        professor.setFrequencia(frequencia);
        repository.save(frequencia);

        var uri = uriBuilder.path("/frequencia/{id_frequencia}").buildAndExpand(frequencia.getId_frequencia()).toUri();

        return ResponseEntity.created(uri).body(new DTOFrequencia(frequencia));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTOFrequencia> listarFrequencia(@PathVariable Long id) {
        var frequencia = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Frequência não encontrada"));

        return ResponseEntity.ok(new DTOFrequencia(frequencia.getId_frequencia(), frequencia.getData(), frequencia.getHora(), frequencia.getFreq_alunos(), frequencia.getPresenca_alunos(), frequencia.getIdUsuarioProf()));
    }

    @GetMapping("/frequenciasProfessor")
    public ResponseEntity<LinkedList<DTOFrequencia>> listarFrequenciaProfessor(@RequestParam Long idProfessor) {
        var professor = usuarioRepository.findById(idProfessor).orElseThrow(() -> new EntityNotFoundException("Professor não encontrada"));

        var frequenciaList = repository.findByIdUsuarioProf(professor).stream()
                .map(DTOFrequencia::new)
                .collect(Collectors.toCollection(LinkedList::new));

        return ResponseEntity.ok(frequenciaList);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DTOFrequencia> atualizarFreq(@RequestBody DTOFrequencia frequenciaDTO) {
        var freq = repository.findById(frequenciaDTO.id()).orElseThrow(() -> new EntityNotFoundException("Frequência não encontrada"));
        frequenciaService.AtualizarFrequencia(freq, frequenciaDTO);

        return ResponseEntity.ok(new DTOFrequencia(freq));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<DTOFrequencia> deletarFrequencia(@PathVariable Long id) {
        var frequecia = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Frequência não encontrada"));
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
