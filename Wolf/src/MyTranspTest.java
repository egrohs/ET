import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

import com.sun.jna.examples.WindowUtils;

public class MyTranspTest {

	public static void main(String args[]) {
		try {
			System.setProperty("sun.java2d.noddraw", "true");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		final JFrame jframe = new JFrame("test frame");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setSize(300, 300);
		//WindowUtils.setWindowAlpha(jframe, 0.7f);
		WindowUtils.setWindowTransparent(jframe, true);
		jframe.setResizable(false);
		
		jframe.setLayout(new GridLayout(3,1));
		JLabel l = new JLabel("asd");
		l.setBackground(new Color(0,0,0,0));
		l.setForeground(Color.RED);
		jframe.add(l);
		jframe.setVisible(true);
	}

}