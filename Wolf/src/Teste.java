import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Teste {
	public static void main(String[] args) {
		final JFrame d = new JFrame() {
			@Override
			public void paint(Graphics g) {
				// TODO Auto-generated method stub
				// super.paint(g);
			}
		};
		d.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				System.out.println("asd");
			};
		});
		d.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				System.out.println("zxc");
				// d.setState(Frame.ICONIFIED);
				// d.setState(Frame.NORMAL);
				// d.transferFocus();
				// d.requestFocusInWindow();
				// d.setVisible(false);
				// d.setVisible(true);
				// d.show();
				d.invalidate();
				d.validate();
				// d.repaint();
			};

			public void mouseDragged(MouseEvent e) {
				System.out.println("qwe");
			};

		});
		d.setSize(200, 200);
		JFrame.setDefaultLookAndFeelDecorated(true);
		JPanel j = new JPanel() {
			@Override
			public void paint(Graphics g) {
				// TODO Auto-generated method stub
				// super.paint(g);
			}
		};
		j.setOpaque(false);
		// j.setSize(100,100);
		JButton b = new JButton("Botao");
		b.setOpaque(true);
		j.add(b);
		//j.setBackground(new Color(0, 0, 0, 0));
		d.add(j);
		// d.setContentPane(j);
		d.setVisible(true);
	}
}
