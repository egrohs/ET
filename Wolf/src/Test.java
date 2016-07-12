import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

public class Test {
	static class ScreenUpdate extends TimerTask {
		public void run() {
			// Don't want to capture this window's contents
			Rectangle rect = getCurrentRect(true);
			BufferedImage hidden = image.getSubimage(rect.x, rect.y, rect.width, rect.height);
			synchronized (screen) {
				// frame.hide();
				image = robot.createScreenCapture(screen);
				WritableRaster raster = image.getRaster();
				raster.setRect(rect.x, rect.y, hidden.getRaster());
				image.setData(raster);
				// frame.show();
			}
			synchronized (timer) {
				pendingUpdate = new ScreenUpdate();
				timer.schedule(pendingUpdate, 2000);
			}
		}
	}

	static Robot robot;
	static Timer timer = new Timer();
	static TimerTask pendingUpdate = null;
	static JFrame root = new JFrame("Invisible frame");
	// static Window frame = new Window(root);
	static JFrame frame = new JFrame("Transparent frame test");
	static int tbh = 0;
	static Rectangle screen = new Rectangle(0, 0, 1024, 768);
	static BufferedImage contents;
	static BufferedImage image;

	static Rectangle getCurrentRect(boolean border) {
		Point where = frame.getLocationOnScreen();
		int width = 400;
		int height = 400 - tbh;
		if (border) {
			width += 50;
			height += 50;
			where.x -= 25;
			where.y -= 25;
		}
		if (where.x + width >= screen.width)
			width = screen.width - where.x - 1;
		else if (where.x < 0) {
			width += where.x;
			where.x = 0;
		}
		if (where.y + tbh + height >= screen.height)
			height = screen.height - where.y - tbh - 1;
		else if (where.y + tbh < 0) {
			height += where.y + tbh;
			where.y = -tbh;
		}
		return new Rectangle(where.x, where.y + tbh, width, height);
	}

	public static void main(String[] args) {
		try {
			System.out.println("Creating robot");
			robot = new Robot();
			final JLabel label = new JLabel();
			label.setBorder(new EmptyBorder(0, 0, 0, 0));
			if (frame instanceof JFrame)
				((JFrame) frame).getContentPane().add(label);
			else
				frame.add(label);
			frame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
			frame.pack();
			frame.setSize(400, 400);
			frame.setLocation(50, 50);
			if (frame instanceof JFrame)
				tbh = frame.getSize().height - ((JFrame) frame).getContentPane().getSize().height;

			frame.addComponentListener(new ComponentAdapter() {
				public void componentMoved(ComponentEvent e) {
					Rectangle rect = getCurrentRect(false);
					synchronized (screen) {
						contents = image.getSubimage(rect.x, rect.y, rect.width, rect.height);
					}
					label.setIcon(new ImageIcon(contents));
					synchronized (timer) {
						if (pendingUpdate != null) {
							pendingUpdate.cancel();
						}
						pendingUpdate = new ScreenUpdate();
						timer.schedule(pendingUpdate, 2000);
					}
				}
			});

			System.out.println("Capturing image");
			image = robot.createScreenCapture(screen);
			contents = image.getSubimage(50, 50 + tbh, 400, 400 - tbh);
			label.setIcon(new ImageIcon(contents));
			frame.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}