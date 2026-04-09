package com.meuupa.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.meuupa.app.model.Paciente;

import com.meuupa.app.repository.PacienteRepository;

@Service
public class PacienteService {
	
	@Autowired
	private PacienteRepository pacienteRepository;
	
	public List<Paciente> listarTodos() {
		
		return pacienteRepository.findAll();
	}
	
	public Paciente salvar(Paciente paciente) {
		return pacienteRepository.save(paciente);
	}

}
