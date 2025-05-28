package com.example.demo.producao;

import com.example.demo.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name = "Producao")
@Entity(name = "producao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id_producao")
public class Producao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_producao;

    @Column(name = "tipo_conteudo", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoCont tipo_conteudo;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "data_publicacao", nullable = false)
    private LocalDate data_publicacao;

    @ManyToOne
    @JoinColumn(name = "id_do_usuario", nullable = false)
    private Usuario idUsuarioCriador;

}
