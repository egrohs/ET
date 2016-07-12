package eti.threads;

import java.util.Observable;

public class DinaTimerThread extends Observable implements Runnable {
	private int clock = 30;

	// Class timer?
	public void run() {
		while (clock <= 0) {
			setChanged();
			notifyObservers(clock);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			clock--;
		}
	}
}