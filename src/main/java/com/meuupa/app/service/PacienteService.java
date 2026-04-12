package com.meuupa.app.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meuupa.app.model.CorTriagem;
import com.meuupa.app.model.Paciente;
import com.meuupa.app.model.StatusPaciente;
import com.meuupa.app.repository.PacienteRepository;

@Service
public class PacienteService {

	@Autowired
	private PacienteRepository pacienteRepository;

	public List<Paciente> listarTodos() {
		try {
			return pacienteRepository.findAll();
		} catch (Exception e) {
			System.err.println("Erro ao listar pacientes: " + e.getMessage());
			return new ArrayList<>();
		}

	}

	public List<Paciente> listarTodos(CorTriagem corTriagem) {
		try {
			return pacienteRepository.findAll().stream().filter(p -> p.getCorTriagem().equals(corTriagem))
					.collect(java.util.stream.Collectors.toList());

		} catch (Exception e) {
			System.err.println("Erro ao listar pacientes por cor: " + e.getMessage());
			return new ArrayList<>();
		}
	}

	public List<Paciente> listarFila() {
		try {
			return pacienteRepository.findFilaOrdenada()
				.stream()
				.sorted(Comparator
						.comparingInt(p -> p.getCorTriagem().getPrioridade()))
				.collect(java.util.stream.Collectors.toList());

		} catch (Exception e) {
			System.err.println("Erro ao listar fila: " + e.getMessage());
			return new ArrayList<>();
		}
	}

	public Paciente salvar(Paciente paciente) {
		try {
			paciente.setData(LocalDateTime.now());
			paciente.setStatus(StatusPaciente.AGUARDANDO);
			return pacienteRepository.save(paciente);
		} catch (Exception e) {
			System.err.println("Erro ao salvar paciente: " + e.getMessage());
			throw new RuntimeException("Erro ao salvar paciente", e);
		}

	}

	public void deletar(Long id) {
		try {
			pacienteRepository.deleteById(id);
		} catch (Exception e) {
			System.err.println("Erro ao deletar paciente: " + e.getMessage());
			throw new RuntimeException("Erro ao deletar paciente", e);
		}

	}

	public Paciente atualizar(Long id, Paciente pacienteAtualizado) {
		try {
			pacienteAtualizado.setId(id.intValue());
			return pacienteRepository.save(pacienteAtualizado);
		} catch (Exception e) {
			System.err.println("Erro ao atualizar paciente: " + e.getMessage());
			throw new RuntimeException("Erro ao atualizar paciente", e);
		}

	}

}
