import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TransparentBackground extends JComponent implements ComponentListener, WindowFocusListener, Runnable {
	private JFrame frame;
	private Image background;
	private long lastupdate = 0;
	public boolean refreshRequested = true;

	public TransparentBackground(JFrame frame) {
		this.frame = frame;
		updateBackground();
		frame.addComponentListener(this);
		frame.addWindowFocusListener(this);
		new Thread(this).start();
	}

	public void componentShown(ComponentEvent evt) {
		repaint();
	}

	public void componentResized(ComponentEvent evt) {
		repaint();
	}

	public void componentMoved(ComponentEvent evt) {
		repaint();
	}

	public void componentHidden(ComponentEvent evt) {
	}

	public void windowGainedFocus(WindowEvent evt) {
		refresh();
	}

	public void windowLostFocus(WindowEvent evt) {
		refresh();
	}

	public void refresh() {
		if (frame.isVisible()) {
			repaint();
			refreshRequested = true;
			lastupdate = new Date().getTime();
		}
	}

	public void run() {
		try {
			while (true) {
				Thread.sleep(250);
				long now = new Date().getTime();
				if (refreshRequested && ((now - lastupdate) > 1000)) {
					if (frame.isVisible()) {
						Point location = frame.getLocation();
						frame.hide();
						updateBackground();
						frame.show();
						frame.setLocation(location);
						refresh();
					}
					lastupdate = now;
					refreshRequested = false;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void updateBackground() {
		try {
			Robot rbt = new Robot();
			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension dim = tk.getScreenSize();
			background = rbt.createScreenCapture(new Rectangle(0, 0, (int) dim.getWidth(), (int) dim.getHeight()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Transparent Window");
		frame.setUndecorated(true);

		TransparentBackground bg = new TransparentBackground(frame);
		// bg.setBackground( );
		bg.setLayout(new BorderLayout());

		JPanel panel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.setColor(Color.blue);
				Image img = new ImageIcon("mp3.png").getImage();
				g.drawImage(img, 0, 0, null);
			}
		};
		panel.setOpaque(false);

		bg.add("Center", panel);

		frame.getContentPane().add("Center", bg);
		frame.pack();
		frame.setSize(200, 200);
		frame.setLocation(500, 500);
		frame.show();
	}
}