package stats;

import java.util.GregorianCalendar;
import java.util.Observable;

public class Weapon extends Observable {

	String name;
	GregorianCalendar data;
	String/* float */acrcy;
	String hitsAtts;
	int kills;
	int deaths;
	int headshots;
	// int vezesNoDia;
	String map;

	public Weapon(String name, GregorianCalendar data, String map) {
		this(name);
		this.data = data;
		this.map = map;
	}

	public Weapon(String name) {
		this.name = name;
	}

	public String getHitsAtts() {
		return hitsAtts;
	}

	public void setHitsAtts(String hitsAtts) {
		if (!hitsAtts.equals(this.hitsAtts)) {
			this.hitsAtts = hitsAtts;
			setChanged();
			notifyObservers(hitsAtts);
		}
	}
}