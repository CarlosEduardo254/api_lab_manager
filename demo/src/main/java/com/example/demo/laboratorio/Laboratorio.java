package com.example.demo.laboratorio;


import com.example.demo.usuario_reserva_lab.Usuario_reserva_lab;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "Laboratorio")
@Entity(name = "Laboratorios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id_lab")
public class Laboratorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_lab;

    @Column(name = "nome_lab", nullable = false)
    private String nome_lab;

    @Column(name = "capacidade", nullable = false)
    private int capacidade;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_reserva", nullable = false)
    private Status status_reserva = Status.NAO_RESERVADO;

    @JsonIgnore
    @OneToMany(mappedBy = "id_lab_reservado", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Usuario_reserva_lab> resevaLab;

    public Laboratorio(DTOLaboratorio laboratorioDTO) {
        this.id_lab = laboratorioDTO.id();
        this.nome_lab = laboratorioDTO.nome_lab();
        this.capacidade = laboratorioDTO.capacidade();
        this.status_reserva = laboratorioDTO.status_reserva() != null ? laboratorioDTO.status_reserva(): Status.NAO_RESERVADO;
    }



}
