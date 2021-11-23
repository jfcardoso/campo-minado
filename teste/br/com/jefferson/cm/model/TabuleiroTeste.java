package br.com.jefferson.cm.model;

import org.junit.jupiter.api.Test;

class TabuleiroTeste {

	@Test
	void testarTabuleiro() {
		
		Tabuleiro tabuleiro = new Tabuleiro(6,6,6);
		
		tabuleiro.alterarMarcacaoCampoEscolhido(4,3);
		tabuleiro.alterarMarcacaoCampoEscolhido(2,4);
		tabuleiro.abrirCampoEscolhido(3,3);
						
	}

}
