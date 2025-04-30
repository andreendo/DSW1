package org.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class Mural {
    private static Logger logger = Logger.getLogger(Mural.class.getName());
    private List<Mensagem> mensagens;

    // construtor
    public Mural() {
        logger.info("Mural Construtor");
        mensagens = new ArrayList<>();
        // simula 2 mensagens existentes
        mensagens.add(new Mensagem(1, "andre", "turma", "Oi pesssoal!", new Date()));
        mensagens.add(new Mensagem(2, "Terminator", "John Connor", "I'll be back!", new Date()));
    }

    public List<Mensagem> getMensagens() {
        return mensagens;
    }

    public void addMensagem(String de, String para, String texto) {
        int iid = mensagens.size() + 1;
        var novaMsg = new Mensagem(iid, de, para, texto, new Date());
        mensagens.add(novaMsg);
    }
}
