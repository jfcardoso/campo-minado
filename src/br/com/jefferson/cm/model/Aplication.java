package br.com.jefferson.cm.model;

import br.com.jefferson.cm.view.TabuleiroConsole;

public class Aplication {

	public static void main(String[] args) {

		Tabuleiro tabuleiro = new Tabuleiro(6, 6, 6);
		new TabuleiroConsole(tabuleiro);

		
	}

}
