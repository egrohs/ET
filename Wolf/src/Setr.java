
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JTable;

import stats.Player;

public class Setr {
	Map<String, Player> players = new LinkedHashMap<String, Player>();

	Map<String, Player> players2 = new LinkedHashMap<String, Player>();

	Pattern teams = Pattern.compile("Axis|Allies");

	Pattern separador = Pattern.compile("(\\^\\d)?\\s+(\\^\\d)?\\s*");

	private List<String[]> nomes = new ArrayList<String[]>();

	List<Player> teamA;

	List<Player> teamB;

	private String[] inativos;

	boolean primeiraPassada = true;

	public Setr() {
		nomes.add(new String[] { "Zidane", "Qui-Gon Jinn", "Qui-GonJinn", "Saddan", "Mouse de Bolinha" });
		nomes.add(new String[] { "AMDias", "Obi-WanKenobi" });
		nomes.add(new String[] { "Red", "Yellow", "DARTHVADER", "ODARTHVADER" });
		nomes.add(new String[] { "Desmond", "theof", "Desmont" });
		nomes.add(new String[] { "MURPHY", "Maquina: BOUNTY" });
		nomes.add(new String[] { "Cap.Miller", "donramon" });
		nomes.add(new String[] { "Sr.Incrivel", "Wendt", "Maquina:Yoda" });
		nomes.add(new String[] { "Lucasa Matador", "ETPlayer" });
		nomes.add(new String[] { "NATO", "Grievous" });
		nomes.add(new String[] { "Hazards", "Chewbacca" });
		inativos = new String[] { "Oshidinha", "Luciozo", "Artemise" };

		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setSelectedFile(new File("C:/Arquivos de programas/Wolfenstein - Enemy Territory/etpro/stats/"));
		fc.setMultiSelectionEnabled(true);
		fc.showOpenDialog(null);
		String path = fc.getCurrentDirectory().getPath();
		File[] files = fc.getSelectedFiles();
		recursivo(files);
		primeiraPassada = false;
		recursivo(files);
		grava("C:/Downloads/Wolf");
	}

	private void grava(String path) {
		try {
			FileWriter outputFile = new FileWriter(path + "/Stats.html");
			HTMLTable.go(outputFile, geraTable());
			balanceTeams();
			outputFile.write("TIME A: ");
			for (Player p : teamA) {
				outputFile.write(p.name + " ");
			}
			outputFile.write(getTeamRank(teamA) + "<BR>");
			outputFile.write("TIME B: ");
			for (Player p : teamB) {
				outputFile.write(p.name + " ");
			}
			outputFile.write(getTeamRank(teamB) + "<BR>");
			outputFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void recursivo(File[] files) {
		for (File file : files) {
			if (file.isDirectory()) {
				recursivo(file.listFiles());
			} else if (file.getName().endsWith(".txt")) {
				readFile(file);
			}
		}
	}

	private float getTeamRank(List<Player> team) {
		float rank = 0;
		for (Player player : team) {
			rank += player.rank;
		}
		return rank;
	}

	// trade(int i, int j)
	private void balanceTeams() {
		float desnivel = Float.MAX_VALUE;
		List<Player> bestA = new ArrayList<Player>();
		for (Player player : players2.values()) {
			if (inativos == null || !Arrays.asList(inativos).contains(player.name))
				bestA.add(player);
		}
		// Collections.sort(bestA);
		List<Player> bestB = new ArrayList<Player>();
		while (bestA.size() > bestB.size()) {
			bestB.add(bestA.remove(0));
		}

		backTeams(bestA, bestB);
		for (int i = 0; i < bestA.size(); i++) {
			for (int j = 0; j < bestB.size(); j++) {
				if (desnivel > 0) {
					sswitch(i, j);
				}
				float rankA = getTeamRank(teamA);
				float rankB = getTeamRank(teamB);
				if (desnivel > Math.abs(rankA - rankB)) {
					bestA = new ArrayList<Player>();
					bestB = new ArrayList<Player>();
					bestA.addAll(teamA);
					bestB.addAll(teamB);
					desnivel = Math.abs(rankA - rankB);
					i = j = 0;
				} else {
					backTeams(bestA, bestB);
				}
			}
		}
	}

	private void backTeams(List<Player> bestA, List<Player> bestB) {
		teamA = new ArrayList<Player>();
		teamA.addAll(bestA);
		teamB = new ArrayList<Player>();
		teamB.addAll(bestB);
	}

	private void sswitch(int i, int j) {
		Player plA = teamA.remove(i);
		Player plB = teamB.remove(j);
		teamB.add(j, plA);
		teamA.add(i, plB);
	}

	private void readFile(File file) {
		// if(file.getName())
		try {
			Scanner scanner = new Scanner(file);
			scanner.useDelimiter("\n"/* System.getProperty("line.separator") */);
			while (scanner.hasNext()) {
				try {
					parseLine(scanner.next());
				} catch (NoSuchElementException e) {
					// nao encontrou a linha certa
				}
			}
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
		name = contem(name);
		Player p;

		if (primeiraPassada)
			p = players.get(name);
		else
			p = players2.get(name);

		if (Score != 0) {
			if (p != null) {
				if (primeiraPassada || ((players.get(name).score / players.get(name).matches) / 2) < Score) {
					p.addStats(TEAM, Kll, Dth, Sui, TK, Eff, GP, DG, DR, TD, Score);
					p.calcRank();
				}
			} else if (primeiraPassada || ((players.get(name).score / players.get(name).matches) / 2) < Score) {
				p = new Player(TEAM, name, Kll, Dth, Sui, TK, Eff, GP, DG, DR, TD, Score);
				/*
				 * if (name.startsWith("Lucasa") || name.startsWith("Zidane") || name.startsWith("Sr.Incr")) p.prefferedClass =
				 * "Medic";
				 */
				if (primeiraPassada)
					players.put(name, p);
				else
					players2.put(name, p);
				p.calcRank();
			}
		}
	}

	private String contem(String player) {
		Player p = players2.get(player);
		if (p != null)
			return p.name;
		for (String[] n : nomes) {
			for (int i = 0; i < n.length; i++) {
				if (n[i].equals(player))
					return n[0];
			}
		}
		return player;
	}

	private JTable geraTable() {
		List<Player> pls = new ArrayList<Player>();
		Object[] names = players2.keySet().toArray();
		for (int i = 0; i < names.length; i++) {
			Player p = players2.get(names[i]);
			if (p.matches < 10) {
				players2.remove(p.name);
			}
		}
		pls.addAll(players2.values());
		Collections.sort(pls);
		Object[] columnNames = new String[] { "Jogador", "Número de Partidas", "Preffered Team", "Kills", "Deaths", "Suicídios",
				"TKs", "Eficiência", "Gib", "Dano Aplicado", "Dano Recebido", "Dano no Time", "XP", "Rank" };
		Object[][] linhas = new Object[pls.size()][];
		int i = 0;
		for (Player p : pls) {
			linhas[i] = new Object[] { p.name, p.matches, p.team, p.kll / p.matches, p.dth / p.matches, p.sui / p.matches,
					p.tk / p.matches, p.eff / p.matches, p.gib / p.matches, p.dg / p.matches, p.dr / p.matches, p.td / p.matches,
					p.score / p.matches, p.rank };
			i++;
		}
		JTable table = new JTable(linhas, columnNames);
		return table;
	}

	public static void main(String[] args) {
		new Setr();
	}
}