package com.example.demo.usuario_reserva_lab;

import com.example.demo.validation.ValidReservaLab;
import jakarta.validation.constraints.FutureOrPresent;

import java.time.LocalDate;
import java.time.LocalTime;

@ValidReservaLab
public record DTOReservaLab(
        Long id,
        LocalDate data_reserva,
        @FutureOrPresent
        LocalDate data_inicio,
        @FutureOrPresent
        LocalDate data_fim,
        LocalTime hora_inicio,
        LocalTime hora_fim,
        String lab_frequencia,
        String motivo_reserva,
        Long id_solicitante,
        Long id_lab_reservado
) {
        


        public DTOReservaLab(Usuario_reserva_lab reserva) {
                this(
                        reserva.getId_reserva(),
                        reserva.getData_reserva(),
                        reserva.getData_inicio(),
                        reserva.getData_fim(),
                        reserva.getHora_inicio(),
                        reserva.getHora_fim(),
                        reserva.getLab_frequencia(),
                        reserva.getMotivo_reserva(),
                        reserva.getIdSolicitante().getIdUsuario(),
                        reserva.getId_lab_reservado().getId_lab()
                );
        }

}
