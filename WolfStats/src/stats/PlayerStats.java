package stats;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;

public class PlayerStats {
	private final Pattern separador = Pattern.compile("(\\^\\d)?\\s+(\\^\\d)?\\s*");

	private final Pattern teams = Pattern.compile("Axis|Allies");

	List<Player> players = new ArrayList<Player>();

	Date data;

	String fileName;

	private List<String[]> nomes = new ArrayList<String[]>();

	private String[] ocultar;

	public PlayerStats() {
		nomes.add(new String[] { "Zidane", "Qui-Gon Jinn", "Qui-GonJinn", "Saddan", "Mouse de Bolinh", "Babosa", "TEAM MATE" });
		nomes.add(new String[] { "AMDias", "Obi-WanKenobi" });
		nomes.add(new String[] { "Red", "Yellow", "DARTHVADER", "ODARTHVADER" });
		nomes.add(new String[] { "Desmond", "theof", "Desmont", "MangebaVoadora" });
		nomes.add(new String[] { "MURPHY", "Maquina: BOUNTY" });
		nomes.add(new String[] { "Cap.Miller", "Cap. Miller", "donramon" });
		nomes.add(new String[] { "Sr.Incrivel", "Wendt", "Maquina:Yoda", "Juvenal" });
		nomes.add(new String[] { "Lucasa Matador", "ETPlayer" });
		nomes.add(new String[] { "NATO", "Grievous" });
		nomes.add(new String[] { "Hazards", "Chewbacca" });
		nomes.add(new String[] { "Peludo", "peludo","Manga Larga" });
		nomes.add(new String[] { "RM", "Mao Peluda" });
		nomes.add(new String[] { "Lucioso", "BinLaden" });

		ocultar = new String[] { "Oshidinha", "Luciozo", "Artemise", "Mao Peluda", "Cap. Miller", "BinLaden", "Cel. Robgol",
				"RM", "NATO" };

		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setSelectedFile(new File("C:/Arquivos de programas/Wolfenstein - Enemy Territory/etpro/stats/"));
		fc.setMultiSelectionEnabled(true);
		fc.showOpenDialog(null);
		File[] files = fc.getSelectedFiles();
		recursivo(files);
	}

	private void recursivo(File[] files) {
		for (File file : files) {
			if (file.isDirectory()) {
				recursivo(file.listFiles());
			} else if (file.getName().endsWith(".txt")) {
				data = new Date(file.lastModified());
				fileName = file.getName();
				readFile(file);
			}
		}
	}

	private void readFile(File file) {
		try {
			Scanner scanner = new Scanner(file);
			scanner.useDelimiter("\n"/* System.getProperty("line.separator") */);
			//String map = scanner.next();
			//if (map.equals(">>> Map: goldrush")) {
				while (scanner.hasNext()) {
					try {
						parseLine(scanner.next());
					} catch (NoSuchElementException e) {
						// nao encontrou a linha certa
					}
				}
			//}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void parseLine(String line) {
		Scanner lineScanner = new Scanner(line);
		lineScanner.useDelimiter(separador);
		String TEAM = lineScanner.findInLine(teams);
		if (TEAM == null)
			return;
		String name = lineScanner.findInLine("[^\\d\\s]+(\\s[^\\d\\s]+)*");
		if (name == null || name.equals("Totals"))
			return;
		int Kll = lineScanner.nextInt();
		int Dth = lineScanner.nextInt();
		int Sui = lineScanner.nextInt();
		int TK = lineScanner.nextInt();
		int Eff = lineScanner.nextInt();
		int GP = lineScanner.nextInt();
		int DG = lineScanner.nextInt();
		int DR = lineScanner.nextInt();
		int TD = lineScanner.nextInt();
		int Score = lineScanner.nextInt();
		name = getRealName(name);
		//if (Kll > 10) {
			// if (name.equals("Zidane")) {
			// if (!Arrays.asList(ocultar).contains(name)) {
			players.add(new Player(TEAM, name, Kll, Dth, Sui, TK, Eff, GP, DG, DR, TD, Score, data));
			// }
		//}
	}

	private String getRealName(String name) {
		for (String[] n : nomes) {
			for (int i = 0; i < n.length; i++) {
				if (n[i].equals(name))
					return n[0];
			}
		}
		return name;
	}
}