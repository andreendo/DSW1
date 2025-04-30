package org.example.model;

import java.util.Date;

public record Mensagem(int iid,
                       String enviadoPor,
                       String enviadoPara,
                       String texto,
                       Date timestamp) { }
