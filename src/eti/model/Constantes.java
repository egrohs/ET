package eti.model;

import java.awt.Color;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;

public class Constantes {
	static int xpRevive = 4;

	private static ClassLoader cl = Thread.currentThread().getContextClassLoader();
	static final Pattern patternPlayers = Pattern.compile("\\s(\\d)+\\s(\\d)+\\s(\\d)+\\s(\\d)+(\\sREF)?");
	private static String term = ".gif";
	static final String tagDiscard = "\\[skipnotify\\]";
	static final String tagTK = "TEAM KILL: ";
	static final String tagName_Weapon = "'s ";
	static final String tagTrim = "\\^.";
	static final String tagMyName = "\"name\" is:\"";
	static final String tagBeginStatsAll = "Overall stats for: ";
	static final String tagEndStatsAll = " \\(\\d Rounds?\\)";
	static final String[] weaponsNames = new String[] { ".45ACP 1911", "Airstrike", "Artillery", "Colt", "Dynamite",
			"FG-42", "FG42", "flamethrower", "G.Launchr", "Garand", "grenade", "K43", "K43 Rifle", "Knife", "Landmine",
			"Luger", "MG-42 Gun", "MP-40", "MP40", "Mortar", "Panzerfaust", "SmokeScrn", "Sten", "Syringe", "Thompson",
			"rifle grenade", "support fire" };
	static final String[] skills = new String[] { "Heavy Weapons", "First Aid", "Engineering", "Signals", "Covert Ops",
	/* "Battle Sense", */"Light Weapons" };
	static final String[] kills = new String[] { " WAS KILLED BY TEAMMATE ", " was killed by ", " was exploded by ",
			" failed to spot ", " was blasted by ", " was stabbed by ", " was silenced by ", " was shelled by ",
			" was mown down by ", " was performed by " };
	static final String[] selfKills = new String[] { " killed himself.", " obliterate himself.", " vaporized himself.",
			" failed to spot his own ", " dove on his own ", " ate his own ", " fell to his death" };
	static final String[] dinas = new String[] { "Planted at ", "Defused at " };
	static final Map<String, TEAM> tagsTeams = new HashMap<String, TEAM>();
	static final Map<String, CLASS> weaponsClasses = new HashMap<String, CLASS>();
	static final Map<String, TEAM> weaponsTeams = new HashMap<String, TEAM>();
	static final Map<String, CLASS> selfKillsClasses = new HashMap<String, CLASS>();
	static final Map<String, CLASS> skillsClasses = new HashMap<String, CLASS>();
	static {
		tagsTeams.put("L", TEAM.ALLIES);
		tagsTeams.put("X", TEAM.AXIS);

		weaponsTeams.put("Colt", TEAM.ALLIES);
		weaponsTeams.put("G.Launchr", TEAM.ALLIES);
		weaponsTeams.put("Garand", TEAM.ALLIES);
		weaponsTeams.put("Thompson", TEAM.ALLIES);
		weaponsTeams.put("K43", TEAM.AXIS);
		weaponsTeams.put("K43 Rifle", TEAM.AXIS);
		weaponsTeams.put("Luger", TEAM.AXIS);
		weaponsTeams.put("MP-40", TEAM.AXIS);
		weaponsTeams.put("MP40", TEAM.AXIS);

		weaponsClasses.put("support fire", CLASS.FIELD);
		weaponsClasses.put("Airstrike", CLASS.FIELD);
		weaponsClasses.put("Artillery", CLASS.FIELD);
		weaponsClasses.put("Dynamite", CLASS.ENG);
		weaponsClasses.put("FG42", CLASS.COVER);
		weaponsClasses.put("flamethrower", CLASS.SOLDIER);
		weaponsClasses.put("G.Launchr", CLASS.ENG);
		weaponsClasses.put("Garand", CLASS.ENG);
		weaponsClasses.put("K43", CLASS.ENG);
		weaponsClasses.put("K43 Rifle", CLASS.ENG);
		weaponsClasses.put("Landmine", CLASS.ENG);
		weaponsClasses.put("Mortar", CLASS.SOLDIER);
		weaponsClasses.put("Panzerfaust", CLASS.SOLDIER);
		weaponsClasses.put("SmokeScrn", CLASS.COVER);
		weaponsClasses.put("Sten", CLASS.COVER);
		weaponsClasses.put("Syringe", CLASS.MEDIC);
		weaponsClasses.put("rifle grenade", CLASS.ENG);

		skillsClasses.put("First Aid", CLASS.MEDIC);
		skillsClasses.put("Heavy Weapons", CLASS.SOLDIER);
		skillsClasses.put("Engineering", CLASS.ENG);
		skillsClasses.put("Signals", CLASS.FIELD);
		skillsClasses.put("Covert Ops", CLASS.COVER);

		selfKillsClasses.put(" air-striked himself.", CLASS.FIELD);
		selfKillsClasses.put(" obliterate himself.", CLASS.FIELD);
		selfKillsClasses.put(" vaporized himself.", CLASS.SOLDIER);
		selfKillsClasses.put(" failed to spot his own ", CLASS.ENG);
	}

	public enum CLASS {
		MEDIC("medic"), SOLDIER("soldier"), FIELD("field_ops"), ENG("engineer"), COVER("covert_ops");
		private String name;
		private ImageIcon icon;

		private CLASS(String name) {
			this.name = name;
		}

		public ImageIcon getPic() {
			if (icon == null) {
				icon = new ImageIcon(cl.getResource(name + term));
				icon.setImage(icon.getImage().getScaledInstance(16, 16, Image.SCALE_FAST));
			}
			return icon;
		}
	}

	public enum TEAM {
		ALLIES("Allies", Color.ORANGE), AXIS("Axis", Color.darkGray), UNKNOWN("Both", Color.white);

		private String name;
		private Color color;

		private TEAM(String team, Color color) {
			this.name = team;
			this.color = color;
		}

		public Color getColor() {
			return color;
		}

		public String getName() {
			return name;
		}
	}

	public enum STATE {
		ALIVE(new Color(0, 150, 0)), FALL(new Color(150, 150, 0)), GIBBED(new Color(255, 0, 0, 80));
		private Color color;

		private STATE(Color color) {
			this.color = color;
		}

		public Color getColor() {
			return color;
		}
	}
}