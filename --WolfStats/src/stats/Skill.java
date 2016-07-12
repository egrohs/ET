package stats;

import java.util.Observable;

public class Skill extends Observable {
	String name;
	String level;
	int points;
	String medals;

	public Skill(String name) {
		this.name = name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		if (!level.equals(this.level)) {
			this.level = level;
			setChanged();
			notifyObservers(level);
		}
	}
}