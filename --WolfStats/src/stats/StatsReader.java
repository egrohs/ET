package stats;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

import stats.Player.Classe;

public class StatsReader implements Runnable {
	static Thread t;
	// 1) qntos vivos?
	// 2) quais classes?
	// 3) suas skills?
	// OBS: eng skill pode ser ganha em minas colocadas ha muito tempo, o cara
	// pode nao ser mais eng.
	// Se 2 ou mais derem killhimself então spawntime é em 2 segs.
	// path cleared qndo tah todo mundo morto, need medic qndo morre
	// TODO não confundir grenade, com rifle grenade.
	// TODO reset program
	// viewpos, players, guids
	// b_floodMaxCommands <n> - flood protection -- approximately n rate-limited
	// commands are allowed per 30 seconds, 0 disables.
	// default: 6
	// b_floodKickRate - continuous commands/sec allowed
	// default 15 valid values >= 10
	// if a client sends more, it is counted against burst below
	// b_floodKickBurst - max commands in a burst
	// Clients get warnings if they come within 4/5ths of the limit
	// default 20 valid values >= 10
	// TEAM KILL
	// [^\\(]*\\
	// Pattern separador = Pattern.compile("(\\^\\d)?\\s+(\\^\\d)?\\s*");
	// private static final String s = "Allied Command Post constructed. Charge
	// speed increased!";
	// Allies capture the Old City!
	// Axis reclaim the Old City!
	// Axis have damaged the Oasis Water Pump!
	// Allies have built the Old City Water Pump!
	// Manga Larga connected
	// peludo entered the game
	// peludo has joined the Allied team!
	private static final String nBegin = "Overall stats for: ";
	private static final String nEnd = " (\\d Round)";
	private static final Map<String, Classe> weaponsClasses = new HashMap<String, Classe>();
	static {
		weaponsClasses.put("Airstrike", Classe.FIELD);
		weaponsClasses.put("Artillery", Classe.FIELD);
		weaponsClasses.put("Dynamite", Classe.ENG);
		weaponsClasses.put("FG42", Classe.COVER);
		weaponsClasses.put("flamethrower", Classe.SOLDIER);
		weaponsClasses.put("G.Launchr", Classe.ENG);
		weaponsClasses.put("Garand", Classe.ENG);
		weaponsClasses.put("K43", Classe.COVER);
		weaponsClasses.put("K43 Rifle", Classe.COVER);
		weaponsClasses.put("Landmine", Classe.ENG);
		weaponsClasses.put("Mortar", Classe.SOLDIER);
		weaponsClasses.put("Panzerfaust", Classe.SOLDIER);
		weaponsClasses.put("SmokeScrn", Classe.COVER);
		weaponsClasses.put("Sten", Classe.COVER);
		weaponsClasses.put("Syringe", Classe.MEDIC);
		weaponsClasses.put("rifle grenade", Classe.ENG);
	}
	private static final String[] weaponsNames = new String[] { "Airstrike", "Artillery", "Colt", "Dynamite", "FG42",
			"flamethrower", "G.Launchr", "Garand", "grenade", "K43", "K43 Rifle", "Knife", "Landmine", "Luger",
			"MP-40", "MP40", "Mortar", "Panzerfaust", "SmokeScrn", "Sten", "Syringe", "Thompson", "rifle grenade" };

	private static final String[] skills = new String[] { "Heavy Weapons", "First Aid", "Engineering", "Signals",
			"Covert Ops", "Battle Sense", "Light Weapons" };
	List<Player> players = new ArrayList<Player>();
	Player playerDaVez;
	private static final String[] kills = new String[] { " was killed by ", " was exploded by ", " failed to spot ",
			" was blasted by ", " was stabbed by ", " was silenced ", " was shelled by ", " was mown down by " };
	private static final String[] selfKills = new String[] { " killed himself.", " vaporized himself.",
			" failed to spot his own ", " dove on his own ", " ate his own " };
	private static final String[] dinas = new String[] { "Planted at ", "Defused at " };
	private Weapon weaponDaVez;
	private Skill skillDaVez;

	private void parseLine(String line) {
		line = line.replaceAll("\\s+", " ").trim();
		if (line.startsWith(nBegin)) {
			// Busca player
			playerDaVez = getPlayer(line.replaceFirst(nBegin, "").replaceFirst(nEnd, ""));
			return;
		}
		weaponVerify(line);
		skillVerify(line);
	}

	private void weaponVerify(String line) {
		for (String wname : weaponsNames) {
			if (line.startsWith(wname)) {
				// Busca weapon
				weaponDaVez = playerDaVez.getWeapon(wname);
				StringTokenizer st = new StringTokenizer(line);
				try {
					String s = "";
					while (!s.contains("/") && st.hasMoreTokens()) {
						s = st.nextToken();
					}
					if (s.contains("/") && !s.equals(weaponDaVez.hitsAtts)) {
						weaponDaVez.hitsAtts = s;
						weaponDaVez.changed = true;
						// playerDaVez.vivo = true;???
					}
				} catch (NoSuchElementException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}
		}
	}

	private void skillVerify(String line) {
		for (String sname : skills) {
			if (line.startsWith(sname)) {
				// Busca weapon
				skillDaVez = playerDaVez.getSkill(sname);
				StringTokenizer st = new StringTokenizer(line);
				try {
					String s = "";
					while (!s.contains("/") && st.hasMoreTokens()) {
						s = st.nextToken();
					}
					if (s.contains("/") && !s.equals(skillDaVez.level)) {
						skillDaVez.level = s;
						skillDaVez.changed = true;
						// playerDaVez.vivo = true;???
					}
				} catch (NoSuchElementException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}
		}
	}

	private Player getPlayer(String name) {
		for (Player player : players) {
			if (name.equals(player.name)) {
				return player;
			}
		}
		Player w = new Player(name);
		players.add(w);
		return w;
	}

	public static void main(String[] args) {
		// t = new Thread(new StatsReader());
		// t.start();
		StatsReader sr = new StatsReader();
		File file = new File("a.txt");
		try {
			Scanner scanner = new Scanner(file);
			scanner.useDelimiter("\n");
			while (scanner.hasNext()) {
				try {
					sr.parseLine(scanner.next());
				} catch (NoSuchElementException e) {
					// nao encontrou a linha certa
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		// while
		// t.sleep(1000);
	}
}
