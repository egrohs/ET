package eti.model;

public class Weapon {
	String name;
	String/* float */acrcy;
	String hitsAtts = "";
	int kills;
	int deaths;
	int headshots;

	public Weapon(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAcrcy() {
		return acrcy;
	}

	public void setAcrcy(String acrcy) {
		this.acrcy = acrcy;
	}

	public String getHitsAtts() {
		return hitsAtts;
	}

	public void setHitsAtts(String hitsAtts) {
		this.hitsAtts = hitsAtts;
	}

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public int getHeadshots() {
		return headshots;
	}

	public void setHeadshots(int headshots) {
		this.headshots = headshots;
	}
}