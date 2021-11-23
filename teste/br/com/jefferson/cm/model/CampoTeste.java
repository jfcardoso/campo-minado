package br.com.jefferson.cm.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.jefferson.cm.exception.ExplosaoException;

class CampoTeste {

	Campo campo;
	
	//vai inicializar o campo para cada novo teste com o parâmetro (3,3)
	@BeforeEach
	void iniciarCampo () {
		campo = new Campo(3, 3);
	}

	@Test
	void testarSeraVizinhoEsquerda() {
		Campo seraVizinho = new Campo(3, 2);
		boolean resultado = campo.adicionarVizinho(seraVizinho);

		assertTrue(resultado);
	}

	@Test
	void testarSeraVizinhoDireita() {
		Campo seraVizinho = new Campo(3, 4);
		boolean resultado = campo.adicionarVizinho(seraVizinho);

		assertTrue(resultado);
	}
	
	@Test
	void testarSeraVizinhoAcima() {
		Campo seraVizinho = new Campo(2, 3);
		boolean resultado = campo.adicionarVizinho(seraVizinho);

		assertTrue(resultado);
	}

	@Test
	void testarSeraVizinhoAbaixo() {
		Campo seraVizinho = new Campo(2, 3);
		boolean resultado = campo.adicionarVizinho(seraVizinho);

		assertTrue(resultado);
	}
	
	@Test
	void testarVizinhoDiagonalEsquerdaAcima() {
		Campo seraVizinho = new Campo(2, 2);
		boolean resultado = campo.adicionarVizinho(seraVizinho);

		assertTrue(resultado);
	}

	@Test
	void testarVizinhoDiagonalDireitaAcima() {
		Campo seraVizinho = new Campo(2, 4);
		boolean resultado = campo.adicionarVizinho(seraVizinho);

		assertTrue(resultado);
	}

	@Test
	void testarVizinhoDiagonalEsquerdaAbaixo() {
		Campo seraVizinho = new Campo(4, 2);
		boolean resultado = campo.adicionarVizinho(seraVizinho);

		assertTrue(resultado);
	}

	@Test
	void testarVizinhoDiagonalDireitaAbaixo() {
		Campo seraVizinho = new Campo(4, 4);
		boolean resultado = campo.adicionarVizinho(seraVizinho);

		assertTrue(resultado);
	}
	@Test
	void testarNaoVizinho() {

		Campo seraVizinho = new Campo(1, 4);
		boolean resultado = campo.adicionarVizinho(seraVizinho);

		assertFalse(resultado);
	}
	
	//Por padrão, o campo começa desmarcado, ou seja, FALSE.
	@Test	
	void testarAlternarMarcacaoDefault() {
		assertFalse(campo.isMarcado());		
	}
	
	//Ao chamar o método alternarMarcacao(), o campo deve ser marcado e retornar
	// true.
	@Test
	void testarAlternarMarcacao() {
		campo.alternarMarcacao();
		assertTrue(campo.isMarcado());		
	}
	
	//O campo deve ficar desmarcado e o status retornar FALSE.
	@Test
	void testarAlternarMarcacaoDuasVezes() {
		campo.alternarMarcacao();
		campo.alternarMarcacao();
		assertFalse(campo.isMarcado());		
	}
	
	//Por padrão, se o campo não estiver minado e não estiver marcado, ele será
	//aberto normamente.
	@Test
	void testarAbrirCampoNaoMinadoNaoMarcado() {
		assertTrue(campo.abrirCampo());		
	}
	
	// Se estiver marcado, o campo não poderá ser aberto.
	@Test
	void testarAbrirCampoNaoMinadoEmarcado() {
		//alterou o campo para marcado
		campo.alternarMarcacao();
		assertFalse(campo.abrirCampo());		
	}
	
	//Mesmo minado não deve lançar exceção porque o campo está marcado
	@Test
	void testarAbrirCampoMinadoEmarcado() {
		campo.alternarMarcacao();
		campo.minarCampo();
		assertFalse(campo.abrirCampo());		
	}
	
	//Deve abrir o campo e  estourar a mina (lançar uma exceção)
	@Test
	void testarAbrirCampoMinadoNaoMarcado() {
		campo.minarCampo();
		assertThrows(ExplosaoException.class,() ->campo.abrirCampo());		
	}
	
	@Test
	void testarExpandirVizinhosCampoNaoMinado() {
		
		Campo vizinho1 = new Campo (2,2);
		Campo vizinhoDoVizinho1 = new Campo (1,1);
		vizinho1.adicionarVizinho(vizinhoDoVizinho1);
		campo.adicionarVizinho(vizinho1);
		campo.abrirCampo();
		
		/*
		 * embora somente o campo atual foi aberto inicialmente, os outros dois
		 * campos foram abertos também por serem seguros (não estarem minados)
		 */
		
		assertTrue(vizinho1.isAberto() && vizinhoDoVizinho1.isAberto());
	}
	
	@Test
	void testarExpandirVizinhosCampoMinado() {
		
		Campo vizinho1 = new Campo (2,2);
		Campo vizinhoDoVizinho1 = new Campo (1,1);		
		Campo campoMinado = new Campo (1,2);
		
		campoMinado.minarCampo();
		
		vizinho1.adicionarVizinho(vizinhoDoVizinho1);
		vizinho1.adicionarVizinho(campoMinado);
		
		campo.adicionarVizinho(vizinho1);
		campo.abrirCampo();
		
		/*
		 * O vizinho1 poderá se expandir pois é segura, mas o vizinhodovizinho
		 * tem uma mina ao seu lado o que impede que haja a expansão da
		 * vizinhança. Por isso, deve ficar fechado.
		 */
		
		assertTrue(vizinho1.isAberto() && !(vizinhoDoVizinho1.isAberto()));
	}
	
	@Test
	void testarReiniciarCampos() {
		
		campo.alternarMarcacao();
		campo.abrirCampo();
		campo.minarCampo();
		
		campo.reiniciarCampos();
		
		assertFalse(campo.isAberto() && campo.isMarcado() && campo.isMinado());
		
	}
	
	
}


	