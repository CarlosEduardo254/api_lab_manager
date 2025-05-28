package com.example.demo.frequencia;

import com.example.demo.usuario.Usuario;
import com.example.demo.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FrequenciaService {

    private final UsuarioService usuarioService;

    @Autowired
    public FrequenciaService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public void AtualizarFrequencia(Frequencia frequecia, DTOFrequencia frequenciaAtualizar) {
        if(frequenciaAtualizar.data() != null){
            frequecia.setData(frequenciaAtualizar.data());
        }
        if(frequenciaAtualizar.hora() != null){
            frequecia.setHora(frequenciaAtualizar.hora());
        }
        if(frequenciaAtualizar.lab_frequencia() != null){
            frequecia.setFreq_alunos(frequenciaAtualizar.lab_frequencia());
        }
        if(frequenciaAtualizar.tipo_de_usua() != null){
            frequecia.setPresenca_alunos(frequenciaAtualizar.tipo_de_usua());
        }
        if (frequenciaAtualizar.id_usuario_prof() != null){
            Usuario professor = usuarioService.buscarUsuarioPorId(frequenciaAtualizar.id_usuario_prof());
            frequecia.setIdUsuarioProf(professor);
        }
    }
}
