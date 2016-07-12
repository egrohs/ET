import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JWindow;

public class AlphaWindow extends JWindow {
	public AlphaWindow() {
		getRootPane().setOpaque(false);
		getLayeredPane().setOpaque(false);
		((JComponent) getContentPane()).setOpaque(false);

		try {
			// if you don't do this 'off the screen', you will get the window
			// dropshadow
			// now you get most of the the the drop shadow of the component
			// would be nice if someone can find out, how to do this 100%
			// perfect
			// there is actually a hack to turn it off.
			setLocation(-1000, -1000);
			// replace with your own url, nice irregular shape
			URL url = new URL("http://members.ping.at/stefan/LatinByrd.12.png");
			System.out.println(url);
			Image img = Toolkit.getDefaultToolkit().createImage(url);
			ImageIcon icon = new ImageIcon(img);
			JLabel label = new JLabel(icon);
			Container cont = getContentPane();
			cont.add(label);
			setVisible(true);
			setLocation(100, 100);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void paint(Graphics g) {
		if (g instanceof Graphics) {
			Dimension dim = getSize();
			Rectangle rect = g.getClipBounds();
			// this one will make it transparent !!!!
			((Graphics) g).alphaClearRect(rect.x, rect.y, rect.width, rect.height);
			super.paint(g);
		}
	}

	public static void main(String args[]) {
		new AlphaWindow();
	}
}