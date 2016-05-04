package br.unb.cic.poo.gol;

import com.google.inject.Inject;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um ambiente (environment) do jogo GameOfLife.
 * 
 * Essa implementacao segue o padrao de projeto Strategy, e a 
 * classe GameEngine possui uma referencia para uma estrategia de 
 * derivacao que pode ser alterada durante a execucao do jogo. 
 * @author rbonifacio
 */
public class GameEngine {
	private int height;
	private int width;
        @SuppressWarnings("FieldMayBeFinal")
	private Cell[][] cells;
	private Statistics statistics;
	private EstrategiaDeDerivacao estrategia;

	/**
	 * Construtor da classe Environment.
	 * 2
         * 
         * 
         * 
	 * @param height
	 *            dimensao vertical do ambiente
	 * @param width
	 *            dimentsao horizontal do ambiente
         * @param statistics
	 */
	public GameEngine(int height, int width, Statistics statistics) {
		this.height = height;
		this.width = width;

		cells = new Cell[height][width];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				cells[i][j] = new Cell();
			}
		}
		
		this.statistics = statistics;
	}
	
        @Inject
	public void setEstrategia(EstrategiaDeDerivacao estrategia) {
		this.estrategia = estrategia;
	}

	public EstrategiaDeDerivacao getEstrategia() {
		return estrategia;
	}
	
	/**
	 * Calcula uma nova geracao do ambiente. Essa implementacao delega para 
	 * a estrategia de derivacao a logica necessaria para identificar 
	 * se uma celula deve se tornar viva ou ser mantida viva na proxima 
	 * geracao. 
	 */
	public void nextGeneration() {
		List<Cell> mustRevive = new ArrayList<>();
		List<Cell> mustKill = new ArrayList<>();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (estrategia.shouldRevive(i, j, this)) {
					mustRevive.add(cells[i][j]);
				} 
				else if ((!estrategia.shouldKeepAlive(i, j, this)) && cells[i][j].isAlive()) {
					mustKill.add(cells[i][j]);
				}
			}
		}
		
		updateStatistics(mustRevive, mustKill);
	}

	/*
	 * Metodo auxiliar que atualiza as estatisticas das celulas 
	 * que foram mortas ou se tornaram vivas entre duas geracoes. 
	 */
	private void updateStatistics(List<Cell> mustRevive, List<Cell> mustKill) {
		for (Cell cell : mustRevive) {
			cell.revive();
			statistics.recordRevive();
		}
		
		for (Cell cell : mustKill) {
			cell.kill();
			statistics.recordKill();
		}
	}
	
	/**
	 * Torna a celula de posicao (i, j) viva
	 * 
	 * @param i posicao vertical da celula
	 * @param j posicao horizontal da celula
	 * 
	 * @throws InvalidParameterException caso a posicao (i, j) nao seja valida.
	 */
	public void makeCellAlive(int i, int j) throws InvalidParameterException {
		if(validPosition(i, j)) {
			cells[i][j].revive();
			statistics.recordRevive();
		}
		else {
                    throw new InvalidParameterException("Invalid position (" + i + ", " + j + ")" );
		}
	}
	
	/**
	 * Verifica se uma celula na posicao (i, j) estah viva.
	 * 
	 * @param i Posicao vertical da celula
	 * @param j Posicao horizontal da celula
	 * @return Verdadeiro caso a celula de posicao (i,j) esteja viva.
	 * 
	 * @throws InvalidParameterException caso a posicao (i,j) nao seja valida. 
	 */
	public boolean isCellAlive(int i, int j) throws InvalidParameterException {
		if(validPosition(i, j)) {
			return cells[i][j].isAlive();
		}
		else {
			throw new InvalidParameterException("Invalid position (" + i + ", " + j + ")" );
		}
	}

	/**
	 * Retorna o numero de celulas vivas no ambiente. 
	 * Esse metodo eh particularmente util para o calculo de 
	 * estatisticas e para melhorar a testabilidade.
	 * 
	 * @return  numero de celulas vivas.
	 */
	public int numberOfAliveCells() {
		int aliveCells = 0;
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				if(isCellAlive(i,j)) {
					aliveCells++;
				}
			}
		}
		return aliveCells;
	}

	/*
	 * Computa o numero de celulas vizinhas vivas, dada uma posicao no ambiente
	 * de referencia identificada pelos argumentos (i,j).
	 */
	public int numberOfNeighborhoodAliveCells(int i, int j) {
		int alive = 0;
                int aux1,aux2;
                
		for (int a = i - 1; a <= i + 1; a++) {
			for (int b = j - 1; b <= j + 1; b++) {
				if (validPosition(a, b)  && (!(a==i && b == j)) && cells[a][b].isAlive()) {
					alive++;
				}
                                else{
                                    aux1 = a;
                                    aux2 = b;
                                    if(a == -1)
                                        aux1 = height -1;
                                    if(b == -1)
                                        aux2 = width-1;
                                    if(a == height)
                                        aux1 = 0;
                                    if(b == width)
                                        aux2 = 0;
                                
                                    if (validPosition(aux1,aux2) && (!(aux1==i && aux2 == j)) && cells[aux1][aux2].isAlive()) {
					alive++;
                                    }
                                    }
			}
		}
		return alive;
	}

	/*
	 * Verifica se uma posicao (a, b) referencia uma celula valida no tabuleiro.
	 */
	private boolean validPosition(int a, int b) {
		return a >= 0 && a < height && b >= 0 && b < width;
	}

	/* Metodos de acesso as propriedades height e width */
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}
