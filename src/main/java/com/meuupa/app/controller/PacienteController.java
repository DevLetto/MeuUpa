package com.meuupa.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller; // ✅ ADICIONADO
import org.springframework.ui.Model; // ✅ ADICIONADO
import org.springframework.web.bind.annotation.*;

import com.meuupa.app.model.Paciente;
import com.meuupa.app.service.PacienteService;

@Controller
@RequestMapping("/pacientes")
public class PacienteController {

	@Autowired
	private PacienteService pacienteService;

	@GetMapping("/formulario")
	public String formulario(Model model) {
		model.addAttribute("paciente", new Paciente());
		return "cadastro";
	}

	@GetMapping
	@ResponseBody
	public ResponseEntity<List<Paciente>> listarTodos() {
		List<Paciente> lista = pacienteService.listarTodos();

		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("/lista")
	public String lista(Model model) {
		model.addAttribute("pacientes", pacienteService.listarTodos());
		return "lista";
	}
	
	@PostMapping("/salvar")
	public String salvarFormulario (@ModelAttribute Paciente paciente) {
		pacienteService.salvar(paciente);
		return "redirect:/pacientes/lista";
	}

	@PostMapping
	@ResponseBody
	public ResponseEntity<Paciente> salvarApi(@RequestBody Paciente paciente) {
		Paciente salvo = pacienteService.salvar(paciente);
		return ResponseEntity.ok(salvo);
	}

	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		pacienteService.deletar(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	@ResponseBody
	public ResponseEntity<Paciente> atualizar(@PathVariable Long id, @RequestBody Paciente paciente) {
		return ResponseEntity.ok(pacienteService.atualizar(id, paciente));
	}
}