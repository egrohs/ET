package stats;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Player extends JLabel implements Comparable<Player> {
	public enum Classe {
		MEDIC("medic.jpg"), SOLDIER("soldier.jpg"), FIELD("field_ops.jpg"), ENG("engineer.jpg"), COVER("covert_ops.jpg");

		private String pic;

		private Classe(String pic) {
			this.pic = pic;
		}

		public String getPic() {
			return pic;
		}
	}

	public String team;

	public String name;

	boolean vivo;

	public int prefferedTeam;

	// String suggestedTeam;

	public String prefferedClass;

	public float kll;

	public float dth;

	public float sui;

	public float tk;

	public float eff;

	public float gib;

	public float dg;

	public float dr;

	public float td;

	public float score;

	public int matches;

	public float rank;

	public Date data;

	public List<Weapon> weapons = new ArrayList<Weapon>();

	public List<Skill> skills = new ArrayList<Skill>();

	public Player(String team, String player, int kll, int dth, int sui, int tk, int eff, int gp, int dg, int dr,
			int td, int score/* , Date data */) {
		this.name = player;
		this.team = team;
		calcPrefTeam(team);
		this.kll = kll;
		this.dth = dth;
		this.sui = sui;
		this.tk = tk;
		this.eff = eff;
		this.gib = gp;
		this.dg = dg;
		this.dr = dr;
		this.td = td;
		this.score = score;
		matches = 1;
		// this.data = data;
	}

	public Player(String name) {
		super(name);
		setOpaque(true);
		setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));
	}

	public void setaClasse(Classe classe) {
		ImageIcon im = new ImageIcon(classe.pic);
		setIcon(im);
		setSize(GUI.largura, im.getIconHeight() + 10);
		setPreferredSize(new Dimension(GUI.largura, im.getIconHeight() + 10));
	}

	public void addStats(String team, int kll, int dth, int sui, int tk, int eff, int gp, int dg, int dr, int td,
			int score) {
		calcPrefTeam(team);
		this.kll += kll;
		this.dth += dth;
		this.sui += sui;
		this.tk += tk;
		this.eff += eff;
		this.gib += gp;
		this.dg += dg;
		this.dr += dr;
		this.td += td;
		this.score += score;
		matches++;
	}

	private void calcPrefTeam(String team) {
		if (team.equals("Axis"))
			this.prefferedTeam--;
		else
			this.prefferedTeam++;
		if (prefferedTeam < 0)
			this.team = "Axis(" + prefferedTeam + ")";
		else if (prefferedTeam > 0)
			this.team = "Allied(" + prefferedTeam + ")";
	}

	public void calcRank() {
		/** 15% a mais pra kills. */
		float mEff = 1.15f * kll / (1.15f * kll + dth);
		/**
		 * Razão entre dano aplicado em relação a dano no time. 50 é fator de
		 * escala para os outros indices.
		 */
		float mTd = dg / (50 * td);
		/**
		 * Média de suicidios por partida. 6 é fator de escala para os outros
		 * indices.
		 */
		float mSuis = sui / (6 * matches);
		/**
		 * Razão entre Gibs em relação aos kills.
		 */
		float mGibs = gib / kll;
		// Média com pesos * 100.
		rank = 100 * ((8.0f * mEff) + (1.0f * mTd) + (0.5f * mSuis) + (0.5f * mGibs)) / 10;
		if ("Medic".equals(prefferedClass))
			rank *= 0.9;
	}

	public int compareTo(Player o) {
		if (o != null) {
			if (o.rank == rank)
				return 0;
			if (o.rank > rank)
				return 1;
		}
		return -1;
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

	public boolean isVivo() {
		return vivo;
	}

	public void setVivo(boolean vivo) {
		this.vivo = vivo;
		if (vivo) {
			setBackground(Color.GREEN);
		} else {
			setBackground(Color.RED);
		}
	}
}