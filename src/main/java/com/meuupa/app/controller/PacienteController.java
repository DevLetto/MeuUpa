package com.meuupa.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meuupa.app.model.Paciente;
import com.meuupa.app.service.PacienteService;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

	@Autowired
	private PacienteService pacienteService;
	
	@GetMapping
	public ResponseEntity<List<Paciente>> listarTodos(){
		 	List<Paciente> lista = pacienteService.listarTodos();
		 	return ResponseEntity.ok(lista);
		
	}
	
	@PostMapping
	public ResponseEntity<Paciente> salvar(@RequestBody Paciente paciente){
		Paciente salvo =  pacienteService.salvar(paciente);
		return ResponseEntity.ok(salvo);
	}
	
	@DeleteMapping("/id")
	public ResponseEntity<Void> deletar(@PathVariable Long id){
		pacienteService.deletar(id);
		return ResponseEntity.noContent().build();
	}
	
	
	
}
