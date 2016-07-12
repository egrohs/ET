package eti;

public class CJava {
	static {
		System.loadLibrary("eti");
	}

	public synchronized static native void sendCommand(String s);

	public synchronized static native void receiveConsoleLine(String s);
}
