package eti.model;

public class Skill {
	String name;
	String level = "";
	int points;
	String medals;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String getMedals() {
		return medals;
	}

	public void setMedals(String medals) {
		this.medals = medals;
	}

	public Skill(String name) {
		this.name = name;
	}
}