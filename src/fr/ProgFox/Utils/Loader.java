package fr.ProgFox.Utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import fr.ProgFox.Game.Game;

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

	public static void read(String path, Game game) {
		try {
			FileInputStream is = new FileInputStream(path);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			game.posX = Float.parseFloat(br.readLine());
			game.posY = Float.parseFloat(br.readLine());
			game.posZ = Float.parseFloat(br.readLine());
			game.rotX = Float.parseFloat(br.readLine());
			game.rotY = Float.parseFloat(br.readLine());
			
			br.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

}
