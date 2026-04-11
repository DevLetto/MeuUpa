package com.meuupa.app.model;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Pessoa {

	protected String nome;
	protected String cpf;

	public abstract String getResumo();

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}
