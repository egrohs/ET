package stats;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Teste {
	public static void main(String[] args) {
		try {
			RandomAccessFile raf = new RandomAccessFile("C://Arquivos de programas//Wolfenstein - Enemy Territory//etpro//server.log","r");
			System.out.println(raf.readLine());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
