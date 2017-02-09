package fr.ProgFox.Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Writter {
	public static void write(String path, float value) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(path));
			bw.write(Float.toString(value));
			bw.newLine();
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void reset(String path){
		new File(path).delete();
	}
}
