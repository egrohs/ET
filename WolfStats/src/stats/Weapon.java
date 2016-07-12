package stats;

import java.util.GregorianCalendar;

public class Weapon {
	boolean changed;
	String name;
	GregorianCalendar data;
	String/* float */acrcy;
	String hitsAtts = "";
	int kills;
	int deaths;
	int headshots;
	// int vezesNoDia;
	String map;

	public Weapon(String name, GregorianCalendar data, String map) {
		this.name = name;
		this.data = data;
		this.map = map;
	}

	public Weapon(String name) {
		// TODO Auto-generated constructor stub
	}
}