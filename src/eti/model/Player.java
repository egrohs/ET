package eti.model;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JLabel;

import eti.model.Constantes.CLASS;
import eti.model.Constantes.STATE;
import eti.model.Constantes.TEAM;

public class Player extends JLabel {
	private CLASS classe;

	private TEAM team;

	private STATE state;

	private List<Weapon> weapons = new ArrayList<Weapon>();

	private List<Skill> skills = new ArrayList<Skill>();

	// ^7Overall stats for: ^3Zorro ^7(^21^7 Round)
	// Weapon Acrcy Hits/Atts Kills Deaths Headshots
	// ^7TEAM Player Kll Dth Sui TK Eff ^3Gib^7 ^2DG ^1DR ^6TD ^3Score
	// ^3Damage Given: ^71295 ^3Team Damage: ^70
	// ^3Damage Recvd: ^71055 ^3Team Damage Received: ^70
	// ^3 Gibs: ^70
	// ^2Rank: ^7Gefreiter (209 XP)
	// Skills Level/Points Medals
	public Player(String name) {
		// name = name.toLowerCase();
		setName(name);
		setText(name);
		setOpaque(true);
		setFont(new Font("Arial", Font.BOLD, 12));
		// setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
	}

	@Override
	public Icon getIcon() {
		if (classe != null) {
			return classe.getPic();
		}
		return super.getIcon();
	}

	@Override
	public Color getForeground() {
		if (state != null) {
			return state.getColor();
		}
		return super.getForeground();
	}

	@Override
	public Color getBackground() {
		if (team != null) {
			return team.getColor();
		}
		return super.getBackground();
	}
	
	@Override
	public String toString() {
		return getText();
	}

	public void setTeam(TEAM team) {
		this.team = team;
	}

	public Weapon getWeapon(String name) {
		for (Weapon weapon : weapons) {
			if (name.equals(weapon.name)) {
				return weapon;
			}
		}
		Weapon w = new Weapon(name);
		weapons.add(w);
		return w;
	}

	public Skill getSkill(String name) {
		for (Skill skill : skills) {
			if (name.equals(skill.name)) {
				return skill;
			}
		}
		Skill w = new Skill(name);
		skills.add(w);
		return w;
	}

	public STATE getState() {
		return state;
	}

	public void setState(STATE state) {
		this.state = state;
	}

	public TEAM getTeam() {
		return team;
	}

	public List<Weapon> getWeapons() {
		return weapons;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public CLASS getClasse() {
		return classe;
	}

	public void setClasse(CLASS c) {
		classe = c;
	}
}