package com.meuupa.app.model;



import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "pacientes")
public class Paciente implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@Column
	String nome;
	@Column
	String cpf;
	@Column
	LocalDateTime dataNascimento;
	@Column
	String corTriagem;
	@Column
	LocalDateTime data;
	
	public Paciente() {
		
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public String getCorTriagem() {
		return corTriagem;
	}
	public void setCorTriagem(String corTriagem) {
		this.corTriagem = corTriagem;
	}
	public LocalDateTime getData() {
		return data;
	}
	public void setData(LocalDateTime data) {
		this.data = data;
	}


	public LocalDateTime getDataNascimento() {
		return dataNascimento;
	}


	public void setDataNascimento(LocalDateTime dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	

}
