package com.github.andreendo.avalexemplo.q4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FuncionarioDAO {

    private static Logger logger = LoggerFactory.getLogger(FuncionarioDAO.class);

    public void salvar(FuncionarioForm funcionarioForm) {
        logger.info("Salvo: {}", funcionarioForm);
    }
}
