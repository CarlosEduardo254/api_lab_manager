package com.example.demo.controllers;

import com.example.demo.laboratorio.LaboratorioRepository;
import com.example.demo.laboratorio.LaboratorioService;
import com.example.demo.usuario.UsuarioRepository;
import com.example.demo.usuario_reserva_lab.DTOReservaLab;
import com.example.demo.usuario_reserva_lab.ReservaRepository;
import com.example.demo.usuario_reserva_lab.ReservaService;
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
@RequestMapping("/reserva-lab")
public class ReservaController {

    @Autowired
    private LaboratorioService laboratorioService;
    @Autowired
    private ReservaRepository repository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    LaboratorioRepository laboratorioRepository;
    @Autowired
    private ReservaService reservaService;

    @PostMapping
    @Transactional
    public ResponseEntity<?> criarReserva(@RequestBody @Valid DTOReservaLab dtoReservaLab, UriComponentsBuilder uriBuilder) {
        try {
            var reserva = laboratorioService.reservarLaboratorio(
                    dtoReservaLab.id_lab_reservado(),
                    dtoReservaLab.id_solicitante(),
                    dtoReservaLab.data_inicio(),
                    dtoReservaLab.data_fim(),
                    dtoReservaLab.motivo_reserva(),
                    dtoReservaLab.hora_inicio(),
                    dtoReservaLab.hora_fim(),
                    dtoReservaLab.lab_frequencia()
            );

            var uri = uriBuilder.path("/reserva-lab/{id_reserva}").buildAndExpand(reserva.getId_reserva()).toUri();

            return ResponseEntity.created(uri).body(new DTOReservaLab(reserva));

        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<DTOReservaLab> buscarReservaID(@PathVariable Long id){
        var reserva = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada"));

        return ResponseEntity.ok(new DTOReservaLab(reserva));
    }

    @GetMapping
    public ResponseEntity<LinkedList<DTOReservaLab>> buscarTodasReserva(){
        var reserva = repository.findAll().stream()
                .map(DTOReservaLab::new)
                .collect(Collectors.toCollection(LinkedList::new));


        return ResponseEntity.ok(reserva);
    }

    @GetMapping("/reservas-laboratorio")
    public ResponseEntity<LinkedList<DTOReservaLab>> buscarReservaLaboratorio(@RequestParam Long id_lab){
        var laboratorio = laboratorioRepository.findById(id_lab).orElseThrow(() -> new EntityNotFoundException("Laboratório não encontrado"));

        LinkedList<DTOReservaLab> reservasList = repository.findReservasAtivasByLaboratorio(laboratorio.getId_lab()).stream()
                .map(DTOReservaLab::new)
                .collect(Collectors.toCollection(LinkedList::new)).reversed();

        if(reservasList.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(reservasList);
    }

    @GetMapping("/reservas-usuario")
    public ResponseEntity<LinkedList<DTOReservaLab>> buscarReservasUsuario(@RequestParam Long id_usuario){
        var usuario = usuarioRepository.findById(id_usuario).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        var reservasList = repository.findByIdSolicitante_Id(usuario.getIdUsuario()).stream()
                .map(DTOReservaLab::new)
                .collect(Collectors.toCollection(LinkedList::new));

        if(reservasList.isEmpty()){ return ResponseEntity.notFound().build(); }

        return ResponseEntity.ok(reservasList);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DTOReservaLab> atualizarReserva (@RequestBody @Valid DTOReservaLab dtoReservaLab){
        var reserva = repository.findById(dtoReservaLab.id()).orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada"));
        reservaService.atualizarInformacoes(reserva, dtoReservaLab);

        return ResponseEntity.ok(new DTOReservaLab(reserva));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DTOReservaLab> excluirReserva(@PathVariable Long id){
        var reserva = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada"));
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
