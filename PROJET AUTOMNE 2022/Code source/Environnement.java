import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Color;

public class Environnement extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton[][] lstNodes;
	private JButton btn_executer;
	private JButton btn_reset;
	private JButton btn_origine_coleur;
	private JButton btn_destination_coleur;
	private JButton btn_bloc_coleur;
	private JLabel lblInstruction;


	public static final int TAILLE_ENVIRONNEMENT = 10;

	public boolean isSetOrigine() {
		for (int i = 0; i < lstNodes.length; i++) {
			for (int j = 0; j < lstNodes[i].length; j++) {
				if (lstNodes[i][j].getBackground() == Color.green) {
					return true;
				}
			}
		}
		return false;
	}

	
	public boolean isSetDestination() {
		for (int i = 0; i < lstNodes.length; i++) {
			for (int j = 0; j < lstNodes[i].length; j++) {
				if (lstNodes[i][j].getBackground() == Color.blue) {
					return true;
				}
			}
		}
		return false;
	}

	public int getNoeud_X(Color couleur) {
		int x = 0;
		for (int i = 0; i < lstNodes.length; i++) {
			for (int j = 0; j < lstNodes[i].length; j++) {
				if (lstNodes[i][j].getBackground() == couleur) {
					x = i;
					break;
				}
			}
		}
		return x;
	}

	public int getNoeud_Y(Color couleur) {
		int y = 0;
		for (int i = 0; i < lstNodes.length; i++) {
			for (int j = 0; j < lstNodes[i].length; j++) {
				if (lstNodes[i][j].getBackground() == couleur) {
					y = j;
					break;
				}
			}
		}
		return y;
	}

	public int[][] getNoeud_blocs(Color couleur) {
		int[][] result;
		int nbreBloc = 0;

		for (int i = 0; i < lstNodes.length; i++) {
			for (int j = 0; j < lstNodes[i].length; j++) {
				if (lstNodes[i][j].getBackground() == couleur) {
					nbreBloc++;
				}
			}
		}

		result = new int[nbreBloc][2];
		int k = -1;

		for (int i = 0; i < lstNodes.length; i++) {
			for (int j = 0; j < lstNodes[i].length; j++) {
				if (lstNodes[i][j].getBackground() == couleur) {
					k++;
					result[k][0] = i;
					result[k][1] = j;
				}
			}
		}
		return result;
	}

	public Color getNextColor(Color couleur) {
		Color uneCouleur = couleur;
		if (uneCouleur == Color.green) {
			if (isSetDestination()) {
				return Color.black;
			} else {
				return Color.white;
			}
		}
		if (uneCouleur == Color.blue) {
			if (isSetOrigine()) {
				return Color.black;
			} else {
				return Color.green;
			}
		}
		if (uneCouleur == Color.black) {
			return Color.white;
		}
		if (uneCouleur == Color.white) {
			if (isSetOrigine()) {
				if (isSetDestination()) {
					return Color.black;
				} else {
					return Color.blue;
				}
			} else {
				return Color.green;
			}
		}
		return uneCouleur;
	}

	public void print_path(List<Node> path, Node initialNode, Node finalNode) {
		for (Node node : path) {
			if (!node.equals(initialNode) && !node.equals(finalNode)) {
				lstNodes[node.getRow()][node.getCol()].setBackground(Color.lightGray);
			}
			System.out.println(node);
		}
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Environnement frame = new Environnement();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Environnement() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 516, 700);
		setResizable(false);
		setTitle("Robot assistant");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblInstruction = new JLabel("Cliquez sur une case pour changer sa couleur.");
		lblInstruction.setForeground(Color.RED);
		lblInstruction.setBackground(Color.RED);
		lblInstruction.setBounds(235, 505, 325, 50);
		contentPane.add(lblInstruction);

		btn_executer = new JButton();
		btn_executer.setBounds(380, 575, 75, 50);
		btn_executer.setText("RUN");
		contentPane.add(btn_executer);

		btn_reset = new JButton();
		btn_reset.setBounds(240, 575, 75, 50);
		btn_reset.setText("RESET");
		contentPane.add(btn_reset);

		btn_origine_coleur = new JButton();
		btn_origine_coleur.setBounds(0, 560, 170, 25);
		btn_origine_coleur.setText("COULEUR ORIGINE");
		btn_origine_coleur.setBackground(Color.green);
		btn_origine_coleur.setForeground(Color.black);
		contentPane.add(btn_origine_coleur);

		btn_destination_coleur = new JButton();
		btn_destination_coleur.setBounds(0, 590, 170, 25);
		btn_destination_coleur.setText("COULEUR DESTINATION");
		btn_destination_coleur.setBackground(Color.blue);
		btn_destination_coleur.setForeground(Color.white);
		contentPane.add(btn_destination_coleur);

		btn_bloc_coleur = new JButton();
		btn_bloc_coleur.setBounds(0, 620, 170, 25);
		btn_bloc_coleur.setText("COULEUR BLOC");
		btn_bloc_coleur.setBackground(Color.black);
		btn_bloc_coleur.setForeground(Color.white);
		contentPane.add(btn_bloc_coleur);

		

		btn_executer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Node initialNode = new Node(getNoeud_X(Color.green), getNoeud_Y(Color.green));
				Node finalNode = new Node(getNoeud_X(Color.blue), getNoeud_Y(Color.blue));
				AStar aStar = new AStar(TAILLE_ENVIRONNEMENT, TAILLE_ENVIRONNEMENT, initialNode, finalNode);
				int[][] blocksArray = getNoeud_blocs(Color.black);
				aStar.setBlocks(blocksArray);
				List<Node> path = aStar.findPath();
				for (Node node : path) {
					if (!node.equals(initialNode) && !node.equals(finalNode)) {
						lstNodes[node.getRow()][node.getCol()].setBackground(Color.lightGray);
					}
					System.out.println(node);
				}

			}
		});
		
		btn_reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (int i = 0; i < lstNodes.length; i++) {
					for (int j = 0; j < lstNodes[i].length; j++) {
						lstNodes[i][j].setBackground(Color.white);
					}
				}
			}
		});

		// Ajout des cases de l'environnement.
		lstNodes = new JButton[TAILLE_ENVIRONNEMENT][TAILLE_ENVIRONNEMENT];
		for (int i = 0; i < lstNodes.length; i++) {
			for (int j = 0; j < lstNodes[i].length; j++) {
				lstNodes[i][j] = new JButton();
				lstNodes[i][j].setBounds(i * 50, j * 50, 50, 50);
				lstNodes[i][j].setBackground(Color.white);
				contentPane.add(lstNodes[i][j]);
				lstNodes[i][j].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Color coleur = ((javax.swing.JButton) arg0.getSource()).getBackground();
						((javax.swing.JButton) arg0.getSource()).setBackground(getNextColor(coleur));
					}
				});
			}
		}

	}

}
