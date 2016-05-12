package br.unb.cic.poo.gol;

import java.util.Scanner;

import br.unb.cic.poo.gol.estrategias.Conway;
import br.unb.cic.poo.gol.estrategias.HighLife;
import br.unb.cic.poo.gol.estrategias.LiveFreeOrDie;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Atua como um componente de apresentacao (view), exibindo o estado atual do
 * game com uma implementacao baseada em caracteres ASCII.
 * 
 * @author rbonifacio
 */
public class GameView extends JFrame {
	private static final String LINE = "+-----+";
	private static final String DEAD_CELL = "|     |";
	private static final String ALIVE_CELL = "|  o  |";
	
	private static final int INVALID_OPTION = 0;
	private static final int MAKE_CELL_ALIVE = 1;
	private static final int NEXT_GENERATION = 2;
	private static final int CONWAY = 3;
	private static final int HIGH_LIFE = 4; 
	private static final int LIVEFREEORDIE = 5;
        private static final int HALT = 6; 

	private GameEngine engine;
	private GameController controller;
        
        private JOptionPane haltInformations;
        private JButton halt;
        private JPanel  cellArray;
        private JButton[][] cells = new JButton[10][10];
        private JComboBox implementations = new JComboBox();
        private JButton nextGeneration;
        
	/**
	 * Construtor da classe GameBoard
         * @param controller
         * @param engine
	 */
	public GameView(GameController controller, GameEngine engine) {
                super("Game of Life"); 
                
		this.controller = controller;
		this.engine = engine;
                
                this.setVisible(true);
                
                initComponents();
     
	}
        
        private void criaCelulas(){
            for (JButton[] celula : cells) {
                for (int j = 0; j < celula.length; j++) {
                    celula[j] = new JButton("");
                    cellArray.add(celula[j]);
                    ActionListener actionListener = new ActionListener() {
                        @Override
                        public void actionPerformed( ActionEvent e ) {
                            JButton novo;

                            novo = (JButton) e.getSource();
                            
                            novo.setBackground(new java.awt.Color(1, 1, 1));
                            
                            for (int k = 0; k < engine.getHeight(); k++) {
                                for (int l = 0; l < engine.getWidth(); l++) {
                                    if(novo == cells[k][l])
                                    {
                                        controller.makeCellAlive(k, l);
                                    }
                                }
                            }

                        }

                    };
                    celula[j].addActionListener(actionListener);
                }
            }
        }
        
        private void initComponents() {
            	Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension tamanhoTela = kit.getScreenSize();
		int width = tamanhoTela.width;
		int height = tamanhoTela.height;
		setLocation( width/4, height/8 );
                //setVisible(false);
            
            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

            cellArray = new JPanel(new GridLayout(10,10));

            criaCelulas();

            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(cellArray);
            jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 0, Short.MAX_VALUE)
            );
            jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 500, Short.MAX_VALUE)
            );

            halt = new JButton( "Halt" );
            halt.setPreferredSize( new Dimension( 30, 30 ) );
            halt.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        HaltMouseClicked(evt);
                    }
                }
            );
            
            nextGeneration = new JButton("Next Generation");
            nextGeneration.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        NextGenerationMouseClicked(evt);
                    }
                }
            );

            implementations.setName("Implementacoes"); // NOI18N
            implementations.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Conway", "High Life", "Live Free or Die" }));
            implementations.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                implementationsActionPerformed(evt);
            }
        });

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(nextGeneration, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(103, 103, 103)
                            .addComponent(implementations, 0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(85, 85, 85)
                            .addComponent(halt, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(cellArray, 0, 0, Short.MAX_VALUE))
                    .addContainerGap())
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(cellArray, 500, 500, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(halt)
                        .addComponent(nextGeneration)
                        .addComponent(implementations, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap())
            );

            pack();
        }
        
        private void HaltMouseClicked(java.awt.event.MouseEvent evt) {                                  
            controller.halt();
                       
            
        }
        
        private void NextGenerationMouseClicked(java.awt.event.MouseEvent evt) {                                  
            JButton novo;

            novo = (JButton) evt.getSource();
            
            if(novo == nextGeneration)
            {
//                JOptionPane.showMessageDialog(cellArray, "Informações sobre o game", "Halt", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("icons/halt"));
                nextGeneration();
                for (int k = 0; k < engine.getHeight(); k++) {
                    for (int l = 0; l < engine.getWidth(); l++) {
                        if(engine.isCellAlive(k, l))
                        {
                            cells[k][l].setBackground(new java.awt.Color(1, 1, 1));
                        }
                        else
                        {   
                            cells[k][l].setBackground(null);
                        }
                    }   
                }
            }
            
        }

        private void implementationsActionPerformed(java.awt.event.ActionEvent evt) {                                                
            JComboBox cb = (JComboBox)evt.getSource();
            String implementacao = (String)cb.getSelectedItem();

            if(implementacao.equals("High Life")) {
//                 JOptionPane.showMessageDialog(cellArray, "High Life", "Halt", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("icons/halt"));      
                engine.setEstrategia(new HighLife()); 
//                update();
            }
            else if (implementacao.equals("Live Free or Die")) {
//                 JOptionPane.showMessageDialog(cellArray, "Live Free or Die", "Halt", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("icons/halt"));   
                engine.setEstrategia(new LiveFreeOrDie()); 
//                update();
            }
            else 
            {
//                JOptionPane.showMessageDialog(cellArray, "Conway", "Halt", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("icons/halt"));
                engine.setEstrategia(new Conway()); 
//                update();
            }
//            JOptionPane.showMessageDialog(cellArray, engine.getEstrategia().getName(), "Halt", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("icons/halt"));

        }
	/**
	 * Atualiza o componente view (representado pela classe GameBoard),
	 * possivelmente como uma resposta a uma atualizacao do jogo.
	 */
	public void update() {
		printFirstRow();
		printLine();
		for (int i = 0; i < engine.getHeight(); i++) {
			for (int j = 0; j < engine.getWidth(); j++) {
				System.out.print(engine.isCellAlive(i, j) ? ALIVE_CELL : DEAD_CELL);
			}
			System.out.println("   " + i);
			printLine();
		}
//		printOptions();
	}

	private void printOptions() {
		Scanner s = new Scanner(System.in);
		int option;
		System.out.println("\n \n");
		
		do {
			System.out.println("Select one of the options: (" + engine.getEstrategia().getName() + ")\n \n"); 
			System.out.println("[1] Make a cell alive");
			System.out.println("[2] Next generation");
			System.out.println("[3] Conway");
			System.out.println("[4] High Life");
			System.out.println("[5] Live Free or Die");
                        System.out.println("[6] Halt");
		
			System.out.print("\n \n Option: ");
			
			option = parseOption(s.nextLine());
		}while(option == 0);
		
		switch(option) {
			case MAKE_CELL_ALIVE : makeCellAlive(); break;
			case NEXT_GENERATION : nextGeneration(); break;
			case CONWAY : engine.setEstrategia(new Conway()); update(); break;
			case HIGH_LIFE : engine.setEstrategia(new HighLife()); update();break;
                        case LIVEFREEORDIE : engine.setEstrategia(new LiveFreeOrDie()); update();break;
			case HALT : halt();
		}
	}
	
	private void makeCellAlive() {
		int i, j = 0;
		Scanner s = new Scanner(System.in);
		
		do {
			System.out.print("\n Inform the row number (0 - " + engine.getHeight() + "): " );
			
			i = s.nextInt();
			
			System.out.print("\n Inform the column number (0 - " + engine.getWidth() + "): " );
			
			j = s.nextInt();
		}while(!validPosition(i,j));
		
		controller.makeCellAlive(i, j);
	}
	
	private void nextGeneration() {
		controller.nextGeneration();
	}
	
	private void halt() {
		controller.halt();
	}
	
	private boolean validPosition(int i, int j) {
		System.out.println(i);
		System.out.println(j);
		return i >= 0 && i < engine.getHeight() && j >= 0 && j < engine.getWidth();
	}

	private int parseOption(String option) {
		if(option.equals("1")) {
			return MAKE_CELL_ALIVE;
		}
		else if (option.equals("2")) {
			return NEXT_GENERATION;
		}
		else if (option.equals("3")) {
			return CONWAY;
		}
		else if (option.equals("4")) {
			return HIGH_LIFE;
		}
		else if(option.equals("5")) {
			return LIVEFREEORDIE;
                }
                else if(option.equals("6")) {
                        return HALT;
		}
		else return INVALID_OPTION;
                
	}

	/* Imprime uma linha usada como separador das linhas do tabuleiro */
	private void printLine() {
		for (int j = 0; j < engine.getWidth(); j++) {
			System.out.print(LINE);
		}
		System.out.print("\n");
	}

	/*
	 * Imprime os identificadores das colunas na primeira linha do tabuleiro
	 */
	private void printFirstRow() {
		System.out.println("\n \n");
		for (int j = 0; j < engine.getWidth(); j++) {
			System.out.print("   " + j + "   ");
		}
		System.out.print("\n");
	}
}
