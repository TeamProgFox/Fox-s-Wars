package fr.ProgFox.Utils;

import java.io.*;

import fr.ProgFox.Game.*;
import fr.ProgFox.Game.Variables.*;

public class Loader {
	public static String loadFile(String path) {
		if (new File(path).exists()) {

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
		return null;
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
			Var.flyMode = Boolean.parseBoolean(br.readLine());
			Var.debugMode = Boolean.parseBoolean(br.readLine());
			br.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

}
