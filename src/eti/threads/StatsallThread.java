package eti.threads;

import eti.CJava;

public class StatsallThread extends Thread {
	int frequency;

	public StatsallThread(int f) {
		this.frequency = f;
	}

	@Override
	public void run() {
		while (true) {
			try {
				sleep(frequency);
				CJava.sendCommand("statsall");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
