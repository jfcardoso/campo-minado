package br.com.jefferson.cm.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import br.com.jefferson.cm.exception.ExplosaoException;

public class Tabuleiro {

	private int quantidadeLinhas;
	private int quantidadeColunas;
	private int quantidadeMinas;

	private final List<Campo> campos = new ArrayList<Campo>();

	public Tabuleiro(int quantidadeLinhas, int quantidadeColunas, int quantidadeMinas) {

		this.quantidadeLinhas = quantidadeLinhas;
		this.quantidadeColunas = quantidadeColunas;
		this.quantidadeMinas = quantidadeMinas;

		gerarCampos();
		associarVizinhos();
		gerarMinasAleatoriamente();

	}

	public void abrirCampoEscolhido(int linha, int coluna) {

		try {
			Predicate<Campo> predicado = c -> c.getLinha() == linha && 
					c.getColuna() == coluna;

			campos.parallelStream().filter(predicado)
								   .findFirst()
								   .ifPresent(c -> c.abrirCampo());
			
		} catch (ExplosaoException explosao) {
			campos.forEach(c -> c.setEstaAberto(true));
			throw explosao;
		}
		

	}

	public void alterarMarcacaoCampoEscolhido(int linha, int coluna) {

		Predicate<Campo> predicado = c -> c.getLinha() == linha &&
										c.getColuna() == coluna;

		campos.parallelStream().filter(predicado)
								.findFirst()
								.ifPresent(c -> c.alternarMarcacao());

	}

	private void gerarCampos() {
		for (int linha = 0; linha < quantidadeLinhas; linha++) {
			for (int coluna = 0; coluna < quantidadeColunas; coluna++) {
				campos.add(new Campo(linha, coluna));
			}
		}

	}

	private void associarVizinhos() {
		for (Campo c1 : campos) {
			for (Campo c2 : campos) {
				c1.adicionarVizinho(c2);
			}
		}

	}

	private void gerarMinasAleatoriamente() {

		long minasArmadas = 0;
		Predicate<Campo> minado = c -> c.isMinado();

		do {			
			int aleatorio = (int) (Math.random() * campos.size());
			campos.get(aleatorio).minarCampo();
			minasArmadas = campos.stream().filter(minado).count();
		} while (minasArmadas < quantidadeMinas);

	}

	public boolean conseguiuGanharJogo() {
		Predicate<Campo> naoExplodiu = n -> n.objetivoAlcancado();
		boolean vitoria = campos.stream().allMatch(naoExplodiu);
		return vitoria;
	}

	public void reiniciarJogo() {
		campos.forEach(c -> c.reiniciarCampos());
		gerarMinasAleatoriamente();
	}

	public String toString() {

		StringBuilder formatTabuleiro = new StringBuilder();
		
		//criando o índice das colunas
		formatTabuleiro.append("  ");
		for (int coluna = 0; coluna < quantidadeColunas; coluna++) {
			formatTabuleiro.append(" ");
			formatTabuleiro.append(coluna);
			formatTabuleiro.append(" ");			
		}
		
		formatTabuleiro.append("\n");
		
		
		int i = 0;
		for (int linha = 0; linha < quantidadeLinhas; linha++) {
			formatTabuleiro.append(linha);
			formatTabuleiro.append(" ");
			for (int coluna = 0; coluna < quantidadeColunas; coluna++) {
				formatTabuleiro.append(" ");
				formatTabuleiro.append(campos.get(i));
				formatTabuleiro.append(" ");
				i++;
			}

			formatTabuleiro.append("\n");
		}

		return formatTabuleiro.toString();
	}

}
