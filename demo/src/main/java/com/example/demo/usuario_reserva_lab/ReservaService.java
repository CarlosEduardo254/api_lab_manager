package com.example.demo.usuario_reserva_lab;

import com.example.demo.laboratorio.LaboratorioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservaService {

    final LaboratorioRepository laboratorioRepository;

    @Autowired
    public ReservaService(LaboratorioRepository laboratorioRepository) {
        this.laboratorioRepository = laboratorioRepository;
    }

    public void atualizarInformacoes(Usuario_reserva_lab reservaLab, @Valid DTOReservaLab dtoReservaLab) {
        //reservaLab.setData_reserva(LocalDate.now()); QUANDO ATUALIZAR AS INFORMAÇÕES A DATA DA RESERVA DEVE MUDAR?
        if(dtoReservaLab.data_inicio() != null){
            reservaLab.setData_inicio(dtoReservaLab.data_inicio());
        }
        if(dtoReservaLab.data_fim() != null){
            reservaLab.setData_fim(dtoReservaLab.data_fim());
        }
        if(dtoReservaLab.hora_inicio() != null){
            reservaLab.setHora_inicio(dtoReservaLab.hora_inicio());
        }
        if(dtoReservaLab.hora_fim() != null){
            reservaLab.setHora_fim(dtoReservaLab.hora_fim());
        }
        if(dtoReservaLab.lab_frequencia() != null){
            reservaLab.setLab_frequencia(dtoReservaLab.lab_frequencia());
        }
        if(dtoReservaLab.motivo_reserva() != null){
            reservaLab.setMotivo_reserva(dtoReservaLab.motivo_reserva());
        }
        if(dtoReservaLab.id_lab_reservado() != null){
            var labNovo = laboratorioRepository.findById(dtoReservaLab.id_lab_reservado()).orElseThrow(() -> new EntityNotFoundException("Laboratório não encontrado para atualizar"));
            reservaLab.setId_lab_reservado(labNovo);
        }

    }
}
