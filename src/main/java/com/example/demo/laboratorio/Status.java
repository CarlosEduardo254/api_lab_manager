package com.example.demo.laboratorio;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Status {
    RESERVADO,
    NAO_RESERVADO
}
