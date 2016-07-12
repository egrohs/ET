package stats;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.sun.jna.examples.WindowUtils;

public class GUI extends JFrame implements WindowListener, FocusListener {
	final int gameWidth = 1024;
	static int largura;
	final static int altura = 600;
	JPanel panel;
	JLabel ltimer;
	int timer = 30;
	static Thread ttimer;

	public GUI() {
		super("Wolf Hack");
		// addWindowListener(this);
		// addFocusListener(this);
		try {
			System.setProperty("sun.java2d.noddraw", "true");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		panel = new JPanel();
		createSpawnTimer();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		largura = d.width - gameWidth;
		// altura = d.height;
		this.setBounds(gameWidth, 0, largura, altura);

		//panel.setBackground(new Color(0, 0, 0, 0));
		
		
		// panel.setOpaque(false);
		// this.setBackground(new Color(0, 0, 255, 0));
		// WindowUtils.setWindowAlpha(this, 0.7f);
		WindowUtils.setWindowTransparent(this, true);
		// WindowUtils.setWindowTransparent(this, false);
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		// this.setUndecorated(true);
		// this.setState(JFrame.NORMAL);
		// this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setContentPane(panel);
		this.setVisible(true);
	}

	public Player getPlayer(String name) {
		for (Component player : panel.getComponents()) {
			if (((JLabel) player).getText().equals(name)) {
				return (Player) player;
			}
		}
		Player w = new Player(name);
		panel.setLayout(new GridLayout(panel.getComponents().length + 1, 2));
		panel.add(w);
		panel.revalidate();
		panel.repaint();
		return w;
	}

	private void createSpawnTimer() {
		ltimer = new JLabel();
		ltimer.setForeground(Color.BLACK);
		ltimer.setVisible(false);
		panel.add(ltimer);

		ttimer = new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						ltimer.setText(timer + "");
						timer--;
						if (timer == 0) {
							timer = 30;
						}
						panel.validate();
						panel.repaint();
						ttimer.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		ttimer.start();
	}

	public void resetSpawnTimer() {
		timer = 30;
		ltimer.setVisible(true);
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		toFront();
		this.setAlwaysOnTop(true);
		System.out.println("to front");
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void focusLost(FocusEvent e) {
		toFront();
		this.setAlwaysOnTop(true);
		System.out.println("to front");
	}
}