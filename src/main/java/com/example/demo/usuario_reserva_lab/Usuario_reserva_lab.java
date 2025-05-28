package com.example.demo.usuario_reserva_lab;


import com.example.demo.laboratorio.Laboratorio;
import com.example.demo.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Table(name = "Usuario_reserva_lab")
@Entity(name = "usuario_reserva_lab")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id_reserva")
public class Usuario_reserva_lab {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_reserva;

    @Column(name = "data_reserva", nullable = false)
    private LocalDate data_reserva;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate data_inicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDate data_fim;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime hora_inicio;

    @Column(name = "hora_fim", nullable = false)
    private LocalTime hora_fim;

    @Column(name = "lab_frequencia", nullable = false)
    private String lab_frequencia;

    @Column(name = "motivo_reserva", nullable = false)
    private String motivo_reserva;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_lab_reservado", nullable = false)
    private Laboratorio id_lab_reservado;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "id_solicitante", nullable = false)
    private Usuario idSolicitante;
}
