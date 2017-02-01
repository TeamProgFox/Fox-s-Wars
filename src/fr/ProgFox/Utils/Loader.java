package fr.ProgFox.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Loader {
	public static String loadFile(String path) {
		BufferedReader reader;
		String buffer;
		String result;

		result = "";
		try {
			reader = new BufferedReader(new FileReader(path));
			while ((buffer = reader.readLine()) != null)
				result += buffer + "\n";
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
