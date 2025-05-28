package com.example.demo.laboratorio;

import jakarta.persistence.Enumerated;

public record DTOLaboratorio(
        Long id,
        String nome_lab,
        int capacidade,
        @Enumerated
        Status status_reserva
) {
    public DTOLaboratorio(Laboratorio laboratorio) {
        this(
                laboratorio.getId_lab(),
                laboratorio.getNome_lab(),
                laboratorio.getCapacidade(),
                laboratorio.getStatus_reserva()
        );
    }

    // Adicionar valor padrão para status_reserva se não for fornecido
    public DTOLaboratorio(Long id, String nome_lab, int capacidade) {
        this(id, nome_lab, capacidade, Status.NAO_RESERVADO);  // Padrão: NAO_RESERVADO
    }

}
