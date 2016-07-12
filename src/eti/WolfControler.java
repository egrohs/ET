package eti;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Properties;

import eti.model.ConsoleParser;
import eti.model.MyTreeModel;
import eti.model.Player;
import eti.model.Variaveis;
import eti.threads.SpawnTimerThread;
import eti.threads.StatsallThread;
import eti.visao.Gui;

public class WolfControler {
	private static WolfControler controler;
	private Gui visao;
	private MyTreeModel model;

	public static WolfControler getIntancia() {
		if (controler == null) {
			controler = new WolfControler();
		}
		return controler;
	}

	private WolfControler() {
		// TODO nao pode ter 2 commandos lentos seguidos!
		CJava.sendCommand("players");
		// JavaNative.send("name");
		criaVisao();
		// sr = new ConsoleParser();
		// new Thread(new ConsoleReaderThread(this, 1000)).start();
		new StatsallThread(Variaveis.statsallThreadDelay).start();
		new SpawnTimerThread(Variaveis.spawnTimerThreadDelay).start();
		loadTimes();
	}

	private void criaVisao() {
		visao = new Gui();
		// visao.getReset().addActionListener(new ActionListener() {
		// // @Override
		// public void actionPerformed(ActionEvent e) {
		// visao.getPanel().removeAll();
		// JavaNative.send("players");
		// // JavaNative.send("name");
		// SpawnTimerThread.setAlreadyFound(false);
		// }
		// });
	}

	private void loadTimes() {
		try {
			Properties p = new Properties();
			p.load(new FileInputStream(new File(WolfControler.class.getResource("/config.ini").getFile())));
			Variaveis.axisTime = Integer.parseInt((String) p.get("axis"));
			Variaveis.alliesTime = Integer.parseInt((String) p.get("allies"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private WolfControler(String path) {
		controler = this;
		criaVisao();
		ConsoleParser sr = new ConsoleParser();
		try {
			RandomAccessFile raf = new RandomAccessFile(path, "r");
			while (raf.getFilePointer() != raf.length()) {
				sr.parseLine(raf.readLine());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// WolfControler.getIntancia();
//		new WolfControler("etconsole.log");
		// new WolfControler("server.log");
		
		CJava.sendCommand("say works!");
	}

	public Player getPlayer(String name) {
		return visao.getPlayer(name);
	}
}