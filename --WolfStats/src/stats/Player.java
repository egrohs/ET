package stats;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Player extends JLabel implements Comparable<Player>, Observer {
	public enum Classe {
		SOLDIER("soldier.jpg"), MEDIC("medic.jpg"), ENGINEER("engineer.jpg"), FIELD("field_ops.jpg"), COVER(
				"covert_ops.jpg");

		private String pic;

		private Classe(String pic) {
			this.pic = pic;
		}

		public String getPic() {
			return pic;
		}
	}

	int changesCount;
	public String team;

	String skill;

	// public String name;

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

	public List<Weapon> weapons;

	public List<Skill> skills;

	public Player(String team, String player, int kll, int dth, int sui, int tk, int eff, int gp, int dg, int dr,
			int td, int score, Date data) {
		this(player);
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
		this.data = data;
	}

	public Player(String name) {
		super(name);
		setText(name);
		// setSize(50, 50);
		// setOpaque(true);
		// setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
		setBackground(new Color(0, 0, 0, 0));
		setFont(Font.getFont("BOLD"));
		// setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
		weapons = new ArrayList<Weapon>();
		skills = new ArrayList<Skill>();
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
		rank = (2 * ((score / 10)) + 1.5f * (eff) + 1.3f * (kll)) / matches;
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
			if (weapon.name.equals(name)) {
				return weapon;
			}
		}
		Weapon w = new Weapon(name);
		w.addObserver(this);
		weapons.add(w);
		return w;
	}

	public Skill getSkill(String name) {
		for (Skill skill : skills) {
			if (skill.name.equals(name)) {
				return skill;
			}
		}
		Skill w = new Skill(name);
		w.addObserver(this);
		skills.add(w);
		return w;
	}

	public boolean isVivo() {
		return vivo;
	}

	public void setVivo(boolean vivo) {
		System.out.println(getText() + " -> " + vivo);
		this.vivo = vivo;
		if (vivo) {
			changesCount = 0;
			setForeground(Color.GREEN);
		} else {
			setForeground(Color.RED);
		}
	}

	public void setClasse(Classe classe) {
		if (classe != null) {
			System.out.println(getText() + " -> " + classe);
			ImageIcon im = new ImageIcon(classe.pic);
			setIcon(im);
			setSize(GUI.largura, im.getIconHeight() + 10);
			setPreferredSize(new Dimension(GUI.largura, im.getIconHeight() + 10));
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (!isVivo()) {
			changesCount++;
		}
		if (changesCount > 1) {
			System.out.println("RESSURRECTING -> " + getText());
			setVivo(true);
		}
	}

	protected void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();

		g.setColor(getBackground());
		g.fillRoundRect(0, 0, width, height, height, height);

		super.paintComponent(g);
	}
}