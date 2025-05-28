package com.example.demo.publicacao;

import com.example.demo.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name = "Publicacao")
@Entity(name = "publicacoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id_publi")
public class Publicacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_publi;

    @Column(name = "rede_social", nullable = false)
    @Enumerated(EnumType.STRING)
    private RedeSocial rede_social;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "data_publi", nullable = false)
    private LocalDate data_publi;

    @ManyToOne
    @JoinColumn(name = "id_usuario_criador", nullable = false)
    private Usuario idUsuarioCriador;

}
