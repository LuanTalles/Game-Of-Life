package br.unb.cic.poo.gol;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Essa tambem eh uma classe com baixa coesao, 
 * pois mustura caracteristicas de Model (as propriedades) 
 * com caracteristicas de view (metodo display())
 * 
 * Nao eh uma boa implementacao.
 * 
 * @author rodrigobonifacio
 */
public class Statistics {
	private int revivedCells;
	private int killedCells;
	
	public Statistics() {
		revivedCells = 0;
		killedCells = 0;
	}

	public int getRevivedCells() {
		return revivedCells;
	}

	public void recordRevive() {
		this.revivedCells++;
	}

	public int getKilledCells() {
		return killedCells;
	}

	public void recordKill() {
		this.killedCells++;
	}
	
	public void display() {
	
            // Nao é utilizado na parte grafica
                System.out.println("=================================");
		System.out.println("           Statistics            ");
		System.out.println("=================================");
		System.out.println("Revived cells: " + revivedCells);
		System.out.println("Killed cells: " + killedCells);
		System.out.println("=================================");
         
           //É utilizado na parte grafica     
        String st = "\t\t=================================\n"
		+ "\t\t                 Statistics            \n"
                + "\t\t=================================\n"
		+ "\t\tRevived cells: " + revivedCells + "\n"
		+ "\t\tKilled cells: " + killedCells + "\n"
		+ "\t\t=================================";
                JOptionPane.showMessageDialog(null, st, "Statistics", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("icons/statistics.jpg"));
	}

}
