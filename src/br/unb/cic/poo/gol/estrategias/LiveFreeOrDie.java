package br.unb.cic.poo.gol.estrategias;

import br.unb.cic.poo.gol.EstrategiaDeDerivacao;
import br.unb.cic.poo.gol.GameEngine;

/**
 * Implementacao de uma estrategia de derivacao baseda nas 
 * regras LiveFreeOrDie. 
 * 
 * @author rbonifacio
 */

public class LiveFreeOrDie implements EstrategiaDeDerivacao {

	@Override
	public String getName() {
		return "LiveFreeOrDie";
	}

	@Override
	public boolean shouldKeepAlive(int i, int j, GameEngine engine) {
		return engine.numberOfNeighborhoodAliveCells(i, j) == 0;
	}

	@Override
	public boolean shouldRevive(int i, int j, GameEngine engine) {
		return engine.numberOfNeighborhoodAliveCells(i, j) == 2;
	}

}
