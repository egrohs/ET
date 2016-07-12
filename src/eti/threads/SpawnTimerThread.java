package eti.threads;

import eti.CJava;
import eti.model.ConsoleParser;
import eti.model.Variaveis;
import eti.model.Constantes.TEAM;

public class SpawnTimerThread extends Thread {
	private static boolean alreadyFound = false;
	public static int nroKilledHimself;
	private long sleep;

	public SpawnTimerThread(long sleep) {
		this.sleep = sleep;
	}

	@Override
	public void run() {
		while (true) {
			try {
				nroKilledHimself = 0;
				sleep(sleep);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void resetSpawntime(final long delay) {
		// /timerSet to set it (eg from a script)
		// /resetTimer to reset the timer
		// /openTimerInput for a ui popup

		// TODO testar isso, ver se /timerSet "" mostra o time atual?!?!
		if (!alreadyFound) {
			CJava.sendCommand("timerSet "
					+ ((ConsoleParser.you.getTeam() == TEAM.AXIS ? Variaveis.axisTime : Variaveis.alliesTime) + delay));
			CJava.sendCommand("resetTimer");
			CJava.sendCommand("timerSet "
					+ (ConsoleParser.you.getTeam() == TEAM.AXIS ? Variaveis.axisTime : Variaveis.alliesTime));
			SpawnTimerThread.nroKilledHimself = 0;
			SpawnTimerThread.alreadyFound = true;
		}
	}

	public static boolean isAlreadyFound() {
		return alreadyFound;
	}

	public static void setAlreadyFound(boolean alreadyFound) {
		SpawnTimerThread.alreadyFound = alreadyFound;
	}
}
