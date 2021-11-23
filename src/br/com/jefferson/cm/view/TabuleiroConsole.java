package br.com.jefferson.cm.view;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.jefferson.cm.exception.ExplosaoException;
import br.com.jefferson.cm.exception.SairException;
import br.com.jefferson.cm.model.Tabuleiro;

public class TabuleiroConsole {

	private Tabuleiro tabuleiro;

	Scanner entrada = new Scanner(System.in);
	
	
	public TabuleiroConsole(Tabuleiro tabuleiro) {
		
		this.tabuleiro = tabuleiro;

		executarJogo();
		
	}

	private void executarJogo() {
			
		try {
			
				boolean continuar = true;				
				while(continuar) {
					loopDoJogo();
					System.out.println("Deseja jogar outra partida? S/n");
					String resposta = entrada.nextLine();
					
					if ("n".equalsIgnoreCase(resposta)) {
						continuar = false;
						System.out.println("Jogo Encerrado...");
					}else if ("s".equalsIgnoreCase(resposta)) {
						tabuleiro.reiniciarJogo();
						
					}else {
						System.out.println("Op��o inv�lida! Digite (S) ou (N)");
					}
					
				}
				
			}catch (SairException sair) {
				System.out.println("Obrigado por jogar! At� mais ;)");
			}finally {
				entrada.close();
			}
	}

	private void loopDoJogo() {
		try {
			while(!tabuleiro.conseguiuGanharJogo()) {
				System.out.println(tabuleiro);				
				String valorDigitado = capturarValorDigitado("Digite a posi��o"+
				  "(X , Y) ou SAIR para sair do jogo.");
				
				/*
				 * melhorar cobertura... se usu�rio n�o usar virgula, digitar
				 * apenas um n�mero ou digitar valores inv�lidos?
				 */
				//transforma o valor das coordenadas em n� inteiros.
				Iterator<Integer> coordenadasXY = 
						Arrays.stream(valorDigitado.split(","))
							  .map(e -> Integer.parseInt(e.trim()))
						      .iterator();
			
				valorDigitado = capturarValorDigitado("Digite [1] para Abrir ou"+
				 "[2] para [Des]marcar campo:");
				
				if (valorDigitado.equals("1")) {
					tabuleiro.abrirCampoEscolhido(coordenadasXY.next(),coordenadasXY.next());
				} else if (valorDigitado.equals("2")) {
					tabuleiro.alterarMarcacaoCampoEscolhido(coordenadasXY.next(),coordenadasXY.next());
				}else {
					System.out.println("Op��o Inv�lida!");
				}
				
			}
			System.out.println(tabuleiro);
			System.out.println("Parab�ns! Voc� venceu!");
			
		} catch (ExplosaoException explosao) {
			System.out.println(tabuleiro);
			System.out.println("Que Pena! Voc� perdeu...");
		}
		
	}

	private String capturarValorDigitado(String texto) {
		System.out.print(texto);
		String opcaoDigitado = entrada.nextLine();
		
		if (opcaoDigitado.equalsIgnoreCase("sair")) {
			throw new SairException();
		}
		
		return opcaoDigitado;
	}
}
