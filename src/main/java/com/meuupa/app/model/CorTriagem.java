package com.meuupa.app.model;

public enum CorTriagem {
	VERMELHO(1),
	LARANJA(2),
	AMARELO(3),
	VERDE(4),
	AZUL(5);
	
	private int prioridade;
	
	CorTriagem(int prioridade) {
		this.prioridade = prioridade;
	}
	
	public int getPrioridade() {
		return prioridade;
	}

}
