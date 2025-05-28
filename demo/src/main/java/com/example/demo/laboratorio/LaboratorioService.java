package com.example.demo.laboratorio;

import com.example.demo.usuario.Usuario;
import com.example.demo.usuario.UsuarioRepository;
import com.example.demo.usuario_reserva_lab.ReservaRepository;
import com.example.demo.usuario_reserva_lab.Usuario_reserva_lab;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class LaboratorioService {
    private final LaboratorioRepository laboratorioRepository;
    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public LaboratorioService(LaboratorioRepository laboratorioRepository, ReservaRepository reservaRepository, UsuarioRepository usuarioRepository) {
        this.laboratorioRepository = laboratorioRepository;
        this.reservaRepository = reservaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void atualizarInformacoes(Laboratorio laboratorio, DTOLaboratorio laboratorioAtualizar) {
        if(laboratorioAtualizar.nome_lab() != null){
            laboratorio.setNome_lab(laboratorioAtualizar.nome_lab());
        }
        if(laboratorioAtualizar.capacidade() > 0){
            laboratorio.setCapacidade(laboratorioAtualizar.capacidade());
        }
    }


    public void atualizarStatusReserva(Laboratorio laboratorio, Usuario_reserva_lab reserva){
        if(LocalDate.now().isAfter(reserva.getData_inicio()) && LocalDate.now().isBefore(reserva.getData_fim())){
            laboratorio.setStatus_reserva(Status.RESERVADO);
        } else {
            laboratorio.setStatus_reserva(Status.NAO_RESERVADO);
        }
    }

    @Transactional
    public Usuario_reserva_lab reservarLaboratorio(Long laboratorioId, Long usuarioId, LocalDate dataInico, LocalDate dataFim,
                                                   String motivoReserva, LocalTime horaInicio, LocalTime horaFim, String lab_frequencia) {
        Laboratorio laboratorio = laboratorioRepository.findById(laboratorioId)
                .orElseThrow(() -> new EntityNotFoundException("Laboratório não encontrado"));
        Usuario solicitante = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        // Verifica se existe algum conflito de reservas
        List<Usuario_reserva_lab> reservasConflitantes = reservaRepository.findReservasConflitantes(
                laboratorioId, dataInico, dataFim, horaInicio, horaFim);

        if (!reservasConflitantes.isEmpty()) {
            throw new IllegalStateException("O laboratório já está reservado nesse período.");
        }

        // Criação da nova reserva
        Usuario_reserva_lab reserva = new Usuario_reserva_lab();
        reserva.setData_reserva(LocalDate.now());
        reserva.setData_inicio(dataInico);
        reserva.setData_fim(dataFim);
        reserva.setHora_inicio(horaInicio);
        reserva.setHora_fim(horaFim);
        reserva.setMotivo_reserva(motivoReserva);
        reserva.setLab_frequencia(lab_frequencia);
        reserva.setIdSolicitante(solicitante);
        reserva.setId_lab_reservado(laboratorio);

        // Atualiza o status do laboratório
        atualizarStatusReserva(laboratorio, reserva);

        reservaRepository.save(reserva);
        laboratorioRepository.save(laboratorio);

        return reserva;
    }


}
