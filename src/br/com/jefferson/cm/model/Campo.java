package br.com.jefferson.cm.model;

import java.util.ArrayList;
import java.util.List;

import br.com.jefferson.cm.exception.ExplosaoException;

public class Campo {

	private int linha;

	private int coluna;

	// j� inicializam "FALSE" como padr�o.
	private boolean estaMinado;
	private boolean estaAberto;
	private boolean estaMarcado;

	// Auto-relacionamento= rela��o 1:n consigo mesmo
	private List<Campo> vizinhos = new ArrayList<>();

	Campo(int linha, int coluna) {
		this.coluna = coluna;
		this.linha = linha;
	}

	/*
	 * testa se o campo � vizinho do campo marcado, tanto diagonalmente, quanto na
	 * mesma linha ou coluna do campo marcado.
	 */
	boolean adicionarVizinho(Campo candidatoVizinho) {

		boolean linhaDiferente = this.linha != candidatoVizinho.linha;
		boolean coluniaDiferente = this.coluna != candidatoVizinho.coluna;

		boolean linhaDiagonal = linhaDiferente && coluniaDiferente;

		int deltaLinha = Math.abs(this.linha - candidatoVizinho.linha);
		int deltaColuna = Math.abs(this.coluna - candidatoVizinho.coluna);

		/*
		 * se a soma for 1 � vizinho e est� na mesma linha ou coluna, se der 2 � vizinho
		 * e est� na diagonal.
		 */
		int deltaGeral = deltaColuna + deltaLinha;

		if (deltaGeral == 1 && !linhaDiagonal) {
			vizinhos.add(candidatoVizinho);
			return true;
		} else if (deltaGeral == 2 && linhaDiagonal) {
			vizinhos.add(candidatoVizinho);
			return true;
		} else {
			// n�o � um campo vizinho
			return false;
		}
	}

	/*
	 * marca ou desmarca o campo como campo minado. Uma vez marcado, o campo n�o
	 * pode ser aberto.
	 */
	void alternarMarcacao() {
		if (!estaAberto) {
			estaMarcado = !estaMarcado;
		}
	}

	boolean abrirCampo() {

		// se n�o estiver aberto e n�o estiver marcado, a fun��o abre o campo
		if (!estaAberto && !estaMarcado) {
			estaAberto = true;

			if (estaMinado) {
				throw new ExplosaoException();
			}

			if (expandirVizinhos()) {
				vizinhos.forEach(v -> v.abrirCampo());
			}

			return true;

		} else {
			return false;
		}

	}

	boolean expandirVizinhos() {

		return vizinhos.stream().noneMatch(vizinho -> vizinho.estaMinado);
	}

	// permite minar um campo
	void minarCampo() {

		estaMinado = true;
	}

	/*
	 * ao clicar em um campo qualquer, essa fun�ao testa se o objetivo final foi
	 * alcan�ado. Ou o campo foi aberto com sucesso, ou ele foi protegido (marcado
	 * como perigoso) com sucesso.
	 */
	boolean objetivoAlcancado() {

		boolean campoAberto = !estaMinado && estaAberto;
		boolean campoProtegido = estaMinado && estaMarcado;

		return campoAberto || campoProtegido;

	}

	/*
	 * este m�todo mostra quantas minas tem ativas na vizinhan�a
	 */
	long mostrarMinasNaVizinhanca() {
		return vizinhos.stream().filter(v -> v.estaMinado).count();
	}

	/*
	 * reset das vari�veis para o default inicial do jogo
	 */
	void reiniciarCampos() {
		estaAberto = false;
		estaMarcado = false;
		estaMinado = false;
	}

	public String toString() {

		if (estaMarcado) {
			return "x";
		} else if (estaAberto && estaMinado) {
			return "*";
		} else if (estaAberto && mostrarMinasNaVizinhanca() > 0) {
			return Long.toString(mostrarMinasNaVizinhanca());
		} else if (estaAberto) {
			return " ";
		} else {
			return "?";
		}
	}

	public boolean isMarcado() {
		return estaMarcado;
	}

	public boolean isMinado() {
		return estaMinado;
	}

	public boolean isAberto() {
		return estaAberto;
	}
	
	
	void setEstaAberto(boolean aberto) {
		this.estaAberto = aberto;
	}

	
	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}

}
