# Meu UPA

Aplicação web para **acompanhamento de filas em Unidades de Pronto Atendimento (UPAs)**. O paciente consegue ver sua posição na fila, a classificação de risco e uma estimativa de tempo até o atendimento, reduzindo a incerteza e a aglomeração na sala de espera.

> Projeto acadêmico desenvolvido em equipe no curso de Engenharia de Software da UNICEPLAC.

## Funcionalidades

- Acompanhamento da **posição do paciente** na fila
- **Classificação de risco** pelo Protocolo de Manchester
- **Estimativa de tempo de atendimento**
- Interface web renderizada no servidor com Thymeleaf

## Protocolo de Manchester

A fila é priorizada pela gravidade do caso, e não apenas pela ordem de chegada. Referência do protocolo:

| Cor | Prioridade | Tempo-alvo de atendimento |
|---|---|---|
| Vermelho | Emergência | Imediato |
| Laranja | Muito urgente | até 10 min |
| Amarelo | Urgente | até 60 min |
| Verde | Pouco urgente | até 120 min |
| Azul | Não urgente | até 240 min |

## Tecnologias

- **Java** e **Spring Boot**
- **Thymeleaf** (camada de visualização)
- **H2** (banco de dados)
- **Maven** (gerenciamento de dependências e build, com Maven Wrapper incluso)

## Como executar

Pré-requisitos: **JDK** instalado (use a versão definida no `pom.xml`).

```bash
# Clonar o repositório
git clone https://github.com/DevLetto/MeuUpa.git
cd MeuUpa

# Executar com o Maven Wrapper
./mvnw spring-boot:run        # Linux / macOS
mvnw.cmd spring-boot:run      # Windows
```

Depois, acesse `http://localhost:8080` no navegador (porta padrão do Spring Boot, caso não tenha sido alterada em `application.properties`).

## Estrutura do projeto

```
MeuUpa/
├── src/        # Código-fonte da aplicação
├── data/       # Dados da aplicação
├── .mvn/       # Maven Wrapper
├── pom.xml     # Dependências e configuração do build
└── README.md
```

## Equipe

Projeto desenvolvido em equipe:

- **Gabriel Letto Sousa Santos** — análise de requisitos, documentação, design e desenvolvimento full stack
- **Arthur Duarte De Jesus** — Ui/Ux
- **Adler Moraes Guimarães— Desenvolvedor**
- **Brenda Estefany Alves de Azevedo** — Analista de Requisitos, UI/Ux
- **Carlos Daniel Felgueiras Andrade** — Desenvolvedor
- **Kelvin Có da Vitória** — Desenvolvedor


## Licença

Projeto acadêmico, sem fins comerciais.
