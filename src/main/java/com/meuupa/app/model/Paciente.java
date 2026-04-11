package com.meuupa.app.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "pacientes")
public class Paciente extends Pessoa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@Column
	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate dataNascimento;
	@Column
	@Enumerated(EnumType.STRING)
	private CorTriagem corTriagem;
	@Column
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime data;

	public Paciente() {

	}

	public Paciente(String nome, String cpf, CorTriagem corTriagem) {
		this.nome = nome;
		this.cpf = cpf;
		this.corTriagem = corTriagem;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CorTriagem getCorTriagem() {
		return corTriagem;
	}

	public void setCorTriagem(CorTriagem corTriagem) {
		this.corTriagem = corTriagem;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	@Override
	public String getResumo() {
		return "Paciente: " + nome + " | Triagem: " + corTriagem;
	}

}
