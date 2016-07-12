package eti.model;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.regex.Matcher;

import eti.CJava;
import eti.WolfControler;
import eti.model.Constantes.CLASS;
import eti.model.Constantes.STATE;
import eti.model.Constantes.TEAM;
import eti.threads.SpawnTimerThread;

public class ConsoleParser {
	// CG_Obituary
	// CG_Respawn
	// ClientSpawn
	// TODO Dynamite counter, melhorar comunicação ex. incoming,
	// path cleared qndo tah todo mundo morto
	// 3) suas skills?
	// viewpos, players, guids

	// b_floodMaxCommands <n> - flood protection -- approximately n rate-limited
	// commands are allowed per 30 seconds, 0 disables. default: 6
	// b_floodKickRate - continuous commands/sec allowed default 15 valid values
	// >= 10
	// if a client sends more, it is counted against burst below
	// b_floodKickBurst - max commands in a burst
	// Clients get warnings if they come within 4/5ths of the limit default 20
	// valid values >= 10

	// TODO TEAM KILL
	// TODO Allied Command Post constructed. Charge speed increased! mostrar
	// Icone quem possui CP.
	// Allies capture the Old City!
	// Axis reclaim the Old City!
	// Axis have damaged the Oasis Water Pump!
	// Allies have built the Old City Water Pump!
	// peludo connected
	// peludo entered the game
	// peludo has joined the Allied team!

	public static Player you;
	private Player playerStatsAllAtual;

	private boolean getPlayers(String line) {
		// Status : ID : Player Nudge Rate MaxPkts Snaps
		// ----------------------------------------------------------------------
		// NOTREADY : L11 : zidane 0 13000 75 30 REF
		// L 0 : zidane 0 13000 75 30
		// X 0 : zidane 0 13000 75 30 REF

		// if (!line.contains(":")) {
		// return false;
		// }
		Matcher pMatcher = Constantes.patternPlayers.matcher(line);
		if (pMatcher.find()) {
			line = line.replaceFirst(pMatcher.group(), "");
			System.out.println(line);
			String[] parts = line.split(":");
			if (parts.length == 3) {
				parts[0] = parts[1];
				parts[1] = parts[2];
			}
			TEAM team = Constantes.tagsTeams.get("" + parts[0].trim().charAt(0));
			if (team == null) {
				return false;
			}
			String name = parts[1].substring(1, parts[1].length()).replaceAll(Constantes.tagTrim, "");
			Player p = WolfControler.getIntancia().getPlayer(name);
			if (p.getTeam() != team) {
				// System.out.println("NOVO TIME: " + line);
			}
			p.setTeam(team);
			return true;
		}
		System.out.println(line);
		return false;
	}

	private boolean getYou(String line) {
		// "name" is:"Zidane" default:"ETPlayer"
		if (you == null && line.startsWith(Constantes.tagMyName)) {
			line = line.replaceFirst(Constantes.tagMyName, "");
			String name = line.substring(0, line.indexOf("\" default:"));
			you = WolfControler.getIntancia().getPlayer(name);
			return true;
		}
		return false;
	}

	private boolean getStatsAllPlayer(String line) {
		if (line.startsWith(Constantes.tagBeginStatsAll)) {
			// Busca player
			playerStatsAllAtual = WolfControler.getIntancia().getPlayer(
					line.replaceFirst(Constantes.tagBeginStatsAll, "").replaceFirst(Constantes.tagEndStatsAll, ""));
			// System.out.println("playerStatsall = " +
			// playerStatsall.getName());
			return true;
		}
		return false;
	}

	private boolean killsVerify(String line) {
		if (line.contains(Constantes.tagName_Weapon)) {
			for (String kname : Constantes.kills) {
				if (line.contains(kname)) {
					try {
						String[] s1 = line.split(kname);
						String victim = s1[0];
						String[] s2 = s1[1].split(Constantes.tagName_Weapon);
						String killer = s2[0];
						String weapon = s2[1];

						Player player = WolfControler.getIntancia().getPlayer(victim);
						player.setState(STATE.FALL);
						if (Variaveis.doAutoNeedMedic && Variaveis.needMedic && player.equals(you)) {
							CJava.sendCommand("vsay_team Medic");
							Variaveis.needMedic = false;
						}
						player = WolfControler.getIntancia().getPlayer(killer);
						if (!weapon.equals("Landmine") && !weapon.equals("Dynamite")) {
							// TODO !airstrike, !ff, panzer, landmine...
							player.setState(STATE.ALIVE);
						}
						CLASS c = Constantes.weaponsClasses.get(weapon);
						if (c != null) {
							player.setClasse(c);
							// System.out.println(line + " => " +
							// playerKill.getName() +
							// " --- " + c);
						}
						TEAM team = Constantes.weaponsTeams.get(weapon);
						if (team != null) {
							if (player.getTeam() != team) {
								System.out.println("NOVO TIME: " + line);
							}
							player.setTeam(team);
						}
						return true;
					} catch (ArrayIndexOutOfBoundsException e) {
						System.out.println("ERRO:" + line);
						e.printStackTrace();
					}
				}
			}
		}
		return false;
	}

	private boolean weaponStatsAllVerify(String line) {
		if (playerStatsAllAtual != null) {
			for (String wname : Constantes.weaponsNames) {
				if (line.startsWith(wname)) {
					// Busca weapon
					Weapon weaponStatsAllAtual = playerStatsAllAtual.getWeapon(wname);
					StringTokenizer st = new StringTokenizer(line);
					try {
						String s = "";
						while (!s.contains("/") && st.hasMoreTokens()) {
							s = st.nextToken();
						}
						if (s.contains("/") && !s.equals(weaponStatsAllAtual.getHitsAtts())) {
							weaponStatsAllAtual.setHitsAtts(s);
							playerStatsAllAtual.setState(STATE.ALIVE);// ??? nem
							// sempre
							TEAM team = Constantes.weaponsTeams.get(weaponStatsAllAtual.name);
							if (team != null) {
								playerStatsAllAtual.setTeam(team);
								return true;
							}
						}
					} catch (NoSuchElementException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return false;
	}

	private boolean skillStatsAllVerify(String line) {
		if (playerStatsAllAtual != null) {
			for (String sname : Constantes.skills) {
				if (line.startsWith(sname)) {
					// Busca skill
					Skill skillStatsAllAtual = playerStatsAllAtual.getSkill(sname);
					StringTokenizer st = new StringTokenizer(line);
					try {
						String s = "";
						while (!s.contains("/") && st.hasMoreTokens()) {
							s = st.nextToken();
						}
						if (s.contains("/") && !s.equals(skillStatsAllAtual.getLevel())) {
							skillStatsAllAtual.setLevel(s);
							playerStatsAllAtual.setState(STATE.ALIVE);// ??? nem
							// sempre
							/*
							 * TODO nem sempre skill diz a classe nem vivo=true,
							 * player pode pegar um pacote do chão depois ou
							 * landmine kill.
							 */
							CLASS c = Constantes.skillsClasses.get(skillStatsAllAtual.name);
							if (c != null) {
								// if (playerStatsall.getClasse() != c) {
								// System.out.println(console);
								// System.out.println("****************************");
								// System.out.println(line + " => " +
								// playerStatsall.getName() + " --- " + c);
								// }
								playerStatsAllAtual.setClasse(c);
								return true;
							}
						}
					} catch (NoSuchElementException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return false;
	}

	private boolean selfKillsVerify(String line) {
		for (String kname : Constantes.selfKills) {
			// TODO melhorar esse if
			if (line.contains(kname)) {
				String[] s1 = line.split(kname);
				String victim = s1[0];
				Player player = WolfControler.getIntancia().getPlayer(victim);
				player.setState(STATE.FALL);
				if (Variaveis.doAutoNeedMedic && Variaveis.needMedic && player.equals(you)) {
					CJava.sendCommand("vsay_team Medic");
					Variaveis.needMedic = false;
				} else if (you != null && player.getTeam() != you.getTeam()) {
					// incrementa counter killedhimself.
					SpawnTimerThread.nroKilledHimself++;
					if (SpawnTimerThread.nroKilledHimself >= Variaveis.minKilledHimself) {
						// TODO nao chamar repetidas x, e nao chamar qndo setado
						// manualmente???
						SpawnTimerThread.resetSpawntime(2000);
					}
				}
				if (s1.length == 2) {
					String weapon = s1[1];
					CLASS c = Constantes.weaponsClasses.get(weapon);
					if (c != null) {
						player.setClasse(c);
					}
				}
				CLASS c = Constantes.selfKillsClasses.get(kname);
				if (c != null) {
					player.setClasse(c);
					// System.out.println(line + " => " + playerKill.getName() +
					// " --- " + c);
				}
				return true;
			}
		}
		return false;
	}

	public void parseLine(String line) {
		// TODO tratar team kill assim?
		line = line.replaceAll(Constantes.tagTrim, "").trim().replaceFirst(Constantes.tagDiscard, "").replaceFirst(
				Constantes.tagTK, "");
		if (line.endsWith("\\.")) {
			line = line.substring(0, line.length() - 1);
		}
		if (getYou(line)) {
			return;
		} else if (getPlayers(line)) {
			return;
		} else if (getStatsAllPlayer(line)) {
			return;
		} else if (selfKillsVerify(line)) {
			return;
		} else if (weaponStatsAllVerify(line)) {
			return;
		} else if (skillStatsAllVerify(line)) {
			return;
		} else if (killsVerify(line)) {
			return;
		}
		// System.out.println("NOT PARSED: " + line);
	}

	public void parse(String console) {
		// this.console = console;
		// long t = System.currentTimeMillis();
		StringTokenizer st = new StringTokenizer(console, "\r\n");
		while (st.hasMoreTokens()) {
			String line = st.nextToken();
			parseLine(line);
		}
		// TODO pode ter???
		// playerStatsall = null;
		// System.out.println((System.currentTimeMillis() - t));
	}
}