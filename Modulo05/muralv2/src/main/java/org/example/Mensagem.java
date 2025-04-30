package org.example;

import java.util.Date;

public record Mensagem(int iid,
                       String enviadoPor,
                       String enviadoPara,
                       String texto,
                       Date timestamp) { }
