package eti.visao;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import eti.model.Player;
import eti.model.Constantes.STATE;
import eti.model.Constantes.TEAM;

public class Gui extends JFrame implements KeyListener {
	static {
		try {
			System.setProperty("sun.java2d.noddraw", "true");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
	}
	private JPanel north, panel;
	// private JLabel ltimer;
	private JComboBox team;
	private JButton reset;

	public Gui() {
		// Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		// WindowUtils.setWindowTransparent(this, true);
		// WindowUtils.setWindowAlpha(this, 0.7f);
		this.addKeyListener(this);
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(getNorth(), BorderLayout.NORTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// this.setBounds(500, 0, largura, altura);
		// this.setResizable(false);
		// this.setUndecorated(true);
		this.setAlwaysOnTop(true);
		// this.setContentPane(panel);
		this.getContentPane().add(getPanel(), BorderLayout.CENTER);
		this.pack();
		this.setVisible(true);
	}

	public JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			// panel.setLayout(new FlowLayout(FlowLayout.CENTER));
			panel.setLayout(new GridLayout(30, 1));
		}
		return panel;
	}

	private JPanel getNorth() {
		if (north == null) {
			north = new JPanel();
			// north.setLayout(new grid)
			north.add(getTime());
			north.add(getReset());
		}
		return north;
	}

	public JButton getReset() {
		if (reset == null) {
			reset = new JButton("Reset");
		}
		return reset;
	}

	private JComboBox getTime() {
		if (team == null) {
			team = new JComboBox(new Object[] { TEAM.UNKNOWN, TEAM.ALLIES, TEAM.AXIS });
			team.addActionListener(new ActionListener() {
				// @Override
				public void actionPerformed(ActionEvent e) {
					showPlayers((TEAM) ((JComboBox) e.getSource()).getSelectedItem());
				}
			});
		}
		return team;
	}

	public Player getPlayer(String name) {
		//name = name.toLowerCase();
		for (Component c : panel.getComponents()) {
			if (c instanceof Player && name.equals(c.getName())) {
				return (Player) c;
			}
		}
		Player w = new Player(name);
		panel.add(w);
		this.pack();
		panel.revalidate();
		panel.repaint();
		return w;
	}

	public void clearChanged() {
		for (Component c : panel.getComponents()) {
			if (c instanceof Player) {
				Player p = (Player) c;
				p.setState(STATE.ALIVE);
			}
		}
	}

	void showPlayers(TEAM time) {
		for (Component c : panel.getComponents()) {
			if (c instanceof Player) {
				Player p = (Player) c;
				if (time == TEAM.UNKNOWN || time.equals(p.getTeam())) {
					p.setVisible(true);
				} else {
					p.setVisible(false);
				}
			}
		}
	}

	// public void updateClock(int time) {
	// ltimer.setText("" + time);
	// }

	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == 'p') {
			this.setUndecorated(!this.isUndecorated());
		}
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == 'p') {
			this.setUndecorated(!this.isUndecorated());
		}
	}
}