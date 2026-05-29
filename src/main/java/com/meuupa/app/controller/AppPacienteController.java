package com.meuupa.app.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.meuupa.app.model.CorTriagem;
import com.meuupa.app.model.Paciente;
import com.meuupa.app.model.StatusPaciente;
import com.meuupa.app.service.PacienteService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/app")
public class AppPacienteController {

	// ENCAPSULAMENTO: a lista de UPAs fica privada dentro do controller.
	// O restante do sistema nao altera essa lista diretamente; ele apenas recebe
	// os dados prontos pelo Model quando a tela de selecao e carregada.
	private static final List<UpaInfo> UPAS = List.of(
			new UpaInfo(1, "UPA 24h Ceilândia"),
			new UpaInfo(2, "UPA 24h Samambaia"),
			new UpaInfo(3, "UPA 24h Taguatinga"),
			new UpaInfo(4, "UPA 24h Sobradinho"),
			new UpaInfo(5, "UPA 24h Planaltina"),
			new UpaInfo(6, "UPA 24h Santa Maria"),
			new UpaInfo(7, "UPA 24h Recanto das Emas"),
			new UpaInfo(8, "UPA 24h Paranoá"),
			new UpaInfo(9, "UPA 24h Valparaíso de Goiás"),
			new UpaInfo(10, "UPA 24h Luziânia"));

	@Autowired
	private PacienteService pacienteService;

	@GetMapping
	public String splash() {
		try {
			return "app/splash";
		} catch (Exception e) {
			System.err.println("Erro ao abrir tela de apresentacao do app do paciente: " + e.getMessage());
			return "redirect:/app";
		}
	}

	@GetMapping("/login")
	public String login(Model model) {
		try {
			// HERANCA: Paciente herda de Pessoa, uma classe abstrata que concentra
			// dados comuns como nome e cpf. Aqui o controller trabalha com Paciente,
			// mas esses atributos vieram da hierarquia Pessoa -> Paciente.
			// POLIMORFISMO: Pessoa define o metodo abstrato getResumo(), e Paciente
			// implementa esse metodo do seu proprio jeito, mostrando um resumo com
			// informacoes especificas do paciente e da triagem.
			model.addAttribute("paciente", new Paciente());
			return "app/login";
		} catch (Exception e) {
			System.err.println("Erro ao abrir login do app do paciente: " + e.getMessage());
			return "redirect:/app";
		}
	}

	@GetMapping("/aviso-cadastro")
	public String avisoCadastro() {
		try {
			return "app/aviso-cadastro";
		} catch (Exception e) {
			System.err.println("Erro ao abrir aviso de cadastro do app do paciente: " + e.getMessage());
			return "redirect:/app";
		}
	}

	@GetMapping("/confirmar-upa")
	public String confirmarUpa(HttpSession session) {
		try {
			if (getPacienteLogado(session) == null) {
				return "redirect:/app/login";
			}

			return "app/confirmar-upa";
		} catch (Exception e) {
			System.err.println("Erro ao abrir confirmacao de UPA do app do paciente: " + e.getMessage());
			return "redirect:/app";
		}
	}

	@GetMapping("/selecionar-upa")
	public String selecionarUpa(Model model) {
		try {
			model.addAttribute("upas", UPAS);
			return "app/selecionar-upa";
		} catch (Exception e) {
			System.err.println("Erro ao abrir selecao de UPA do app do paciente: " + e.getMessage());
			return "redirect:/app";
		}
	}

	@GetMapping("/fila")
	public String fila(Model model, HttpSession session) {
		try {
			Paciente pacienteLogado = getPacienteLogado(session);
			if (pacienteLogado == null) {
				return "redirect:/app/login";
			}

			// ABSTRACAO: o controller pede a fila ao PacienteService e nao precisa
			// saber se os dados vem do H2, de um repository JPA ou de outra fonte.
			// A regra de busca e ordenacao fica escondida na camada de service.
			List<Paciente> pacientes = pacienteService.listarFila();
			int posicao = 0;

			for (int i = 0; i < pacientes.size(); i++) {
				if (pacientes.get(i).getId() == pacienteLogado.getId()) {
					posicao = i + 1;
					break;
				}
			}

			model.addAttribute("posicao", posicao);
			model.addAttribute("totalFila", pacientes.size());
			model.addAttribute("pacienteLogado", pacienteLogado);
			model.addAttribute("pacientes", pacientes);
			model.addAttribute("upaNome", session.getAttribute("upaNome") != null
					? session.getAttribute("upaNome")
					: "UPA nao selecionada");
			model.addAttribute("qtdVermelho", contarAguardandoPorCor(pacientes, CorTriagem.VERMELHO));
			model.addAttribute("qtdLaranja", contarAguardandoPorCor(pacientes, CorTriagem.LARANJA));
			model.addAttribute("qtdAmarelo", contarAguardandoPorCor(pacientes, CorTriagem.AMARELO));
			model.addAttribute("qtdVerde", contarAguardandoPorCor(pacientes, CorTriagem.VERDE));
			model.addAttribute("qtdAzul", contarAguardandoPorCor(pacientes, CorTriagem.AZUL));

			return "app/fila";
		} catch (Exception e) {
			System.err.println("Erro ao abrir fila do app do paciente: " + e.getMessage());
			return "redirect:/app";
		}
	}

	@PostMapping("/login")
	public String autenticar(@RequestParam String cpf, @RequestParam LocalDate dataNascimento, HttpSession session) {
		try {
			// ABSTRACAO: listarTodos() esconde os detalhes de acesso ao banco.
			// O controller usa uma operacao de alto nivel do service e foca apenas
			// na regra de login do paciente pelo cpf e data de nascimento.
			Paciente pacienteEncontrado = pacienteService.listarTodos()
					.stream()
					.filter(paciente -> cpf.equals(paciente.getCpf())
							&& dataNascimento.equals(paciente.getDataNascimento()))
					.findFirst()
					.orElse(null);

			if (pacienteEncontrado == null) {
				return "redirect:/app/login?erro=true";
			}

			session.setAttribute("pacienteLogado", pacienteEncontrado);
			return "redirect:/app/confirmar-upa";
		} catch (Exception e) {
			System.err.println("Erro ao autenticar paciente no app: " + e.getMessage());
			return "redirect:/app";
		}
	}

	@PostMapping("/selecionar-upa")
	public String salvarUpa(@RequestParam int upaId, @RequestParam String upaNome, HttpSession session) {
		try {
			session.setAttribute("upaId", upaId);
			session.setAttribute("upaNome", upaNome);
			return "redirect:/app/fila";
		} catch (Exception e) {
			System.err.println("Erro ao salvar UPA selecionada no app do paciente: " + e.getMessage());
			return "redirect:/app";
		}
	}

	@PostMapping("/logout")
	public String logout(HttpSession session) {
		try {
			session.invalidate();
			return "redirect:/app";
		} catch (Exception e) {
			System.err.println("Erro ao sair do app do paciente: " + e.getMessage());
			return "redirect:/app";
		}
	}

	private Paciente getPacienteLogado(HttpSession session) {
		Object pacienteLogado = session.getAttribute("pacienteLogado");
		if (pacienteLogado instanceof Paciente paciente) {
			return paciente;
		}

		return null;
	}

	private long contarAguardandoPorCor(List<Paciente> pacientes, CorTriagem corTriagem) {
		return pacientes.stream()
				.filter(paciente -> paciente.getStatus() == StatusPaciente.AGUARDANDO)
				.filter(paciente -> paciente.getCorTriagem() == corTriagem)
				.count();
	}

	public static class UpaInfo {

		// ENCAPSULAMENTO: os atributos ficam privados para impedir acesso direto.
		// Eles sao lidos e alterados pelos metodos getId(), setId(), getNome()
		// e setNome(), mantendo controle sobre como os dados da UPA sao expostos.
		private int id;
		private String nome;

		public UpaInfo(int id, String nome) {
			this.id = id;
			this.nome = nome;
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
	}
}
