package stats;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import stats.Player.Classe;

public class GUI extends JFrame {
	final int gameWidth = 1024;
	static int largura;
	static int altura;

	public GUI() {
		super("Wolf Hack");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(10, 1));
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		largura = d.width - gameWidth;
		altura = d.height;
		this.setBounds(gameWidth, 0, largura, altura);

		Player l = new Player("Zidane");
		l.setaClasse(Classe.MEDIC);
		l.setVivo(true);
		panel.add(l);

		this.setContentPane(panel);
		// this.pack();
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new GUI();
	}
}
