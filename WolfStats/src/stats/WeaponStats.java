package stats;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JTable;

public class WeaponStats {
	private final Pattern separador = Pattern.compile("(\\^\\d)?\\s+(\\^\\d)?\\s*");

	private final String[] weaponsNames = new String[] { "MP-40", "Thompson" };

	List<Weapon> weapons = new ArrayList<Weapon>();

	GregorianCalendar data;

	String fileName;

	public WeaponStats() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setSelectedFile(new File("C:/Arquivos de programas/Wolfenstein - Enemy Territory/etpro/stats/"));
		fc.setMultiSelectionEnabled(true);
		fc.showOpenDialog(null);
		File[] files = fc.getSelectedFiles();
		recursivo(files);
		// grava("C:/Downloads/Wolf");
	}

	private void recursivo(File[] files) {
		for (File file : files) {
			if (file.isDirectory()) {
				recursivo(file.listFiles());
			} else if (file.getName().endsWith(".txt")) {
				data = new GregorianCalendar();
				data.setTime(new Date(file.lastModified()));
//				data.set(data.get(GregorianCalendar.YEAR), data.get(GregorianCalendar.MONTH), data.get(GregorianCalendar.DATE),
//						0, 0, 0);
				fileName = file.getName();
				readFile(file);
			}
		}
	}

	private void readFile(File file) {
		try {
			Scanner scanner = new Scanner(file);
			scanner.useDelimiter("\n"/* System.getProperty("line.separator") */);
			while (scanner.hasNext()) {
				try {
					parseLine(scanner.next());
				} catch (NoSuchElementException e) {
					// nao encontrou a linha certa
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void parseLine(String line) {
		Scanner lineScanner = new Scanner(line);
		lineScanner.useDelimiter(separador);
		for (String weaponName : weaponsNames) {
			String w = lineScanner.findInLine(weaponName);
			if (w != null) {
				Weapon weapon = getWeapon(w, data);
				//weapon.vezesNoDia++;
				String s = lineScanner.next();// Pula o ":"
				s = lineScanner.next();
				if (s.contains(".")) {
					weapon.acrcy = s;
					s = lineScanner.next();
				}
				if (s.contains("/")) {
					weapon.hitsAtts = s;
					weapon.kills += lineScanner.nextInt();
				} else {
					weapon.kills += Integer.parseInt(s);
				}
				weapon.deaths += lineScanner.nextInt();
				weapon.headshots += lineScanner.nextInt();
			}
		}
	}

	private Weapon getWeapon(String name, GregorianCalendar ref) {
		for (Weapon weapon : weapons) {
			if (weapon.name.equals(name) && weapon.data.equals(ref)/* && weapon.data.get(Calendar.DATE) == data.get(Calendar.DATE)
					&& weapon.data.get(Calendar.MONTH) == data.get(Calendar.MONTH)
					&& weapon.data.get(Calendar.YEAR) == data.get(Calendar.YEAR)*/) {
				return weapon;
			}
		}
		Weapon w = new Weapon(name, ref, fileName);
		weapons.add(w);
		return w;
	}

	private void grava(String path) {
		try {
			FileWriter outputFile = new FileWriter(path + "/Weapons.html");
			HTMLTable.go(outputFile, geraTable());
			// ...
			outputFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private JTable geraTable() {
		Object[] columnNames = new String[] { "Weapon", "Acc", "Hits/Atts", "Kills", "Deaths", "Headshots" };
		Object[][] linhas = new Object[weapons.size()][];
		int i = 0;
		for (Weapon p : weapons) {
			linhas[i] = new Object[] { p.name, p.acrcy, p.hitsAtts, p.kills, p.deaths, p.headshots };
			i++;
		}
		JTable table = new JTable(linhas, columnNames);
		return table;
	}

	public static void main(String[] args) {
		new WeaponStats();
	}
}