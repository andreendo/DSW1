package com.github.andreendo.avalexemplo.q4;

import java.util.List;

public class FuncionarioForm {

    private String nome;
    private String cargo;
    private List<String> tecnologias;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public List<String> getTecnologias() {
        return tecnologias;
    }

    public void setTecnologias(List<String> tecnologias) {
        this.tecnologias = tecnologias;
    }

    @Override
    public String toString() {
        return "FuncionarioForm [nome=" + nome + ", cargo=" + cargo + ",  tecnologias=" + tecnologias + "]";
    }
}
