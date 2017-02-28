package fr.ProgFox.Game.World;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SaveChunk {
	public SaveChunk() {

	}

	public void save(Chunk c, int x, int y, int z, String block, boolean add) {
		try {
			// new File("save/chunks/" + c.toString() + ".tpf").createNewFile();
			BufferedWriter writer = new BufferedWriter(
					new FileWriter(new File("saves/chunks/" + c.getName() + ".tpf"), true));
			writer.write((x + ";" + y + ";" + z + ";" + block + ";" + add));
			writer.newLine();
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void replace(Chunk chunk) {
		if (new File("saves/chunks/" + chunk.getName() + ".tpf").exists()) {

			String[] tab = new String[6];
			tab[0] = "";
			tab[1] = "";
			tab[2] = "";
			tab[3] = "";
			tab[4] = "";
			tab[5] = "";
			try {
				FileInputStream fin = new FileInputStream("saves/chunks/" + chunk.getName() + ".tpf");
				BufferedReader br = new BufferedReader(new InputStreamReader(fin));

				String ligne;
				// lecture des données
				while ((ligne = br.readLine()) != null) {
					StringTokenizer st = new StringTokenizer(ligne, ",");
					String ligne2;
					int index = 0;

					while (st.hasMoreTokens()) {
						ligne2 = st.nextToken();
						for (int i = 0; i < ligne2.length(); i++) {
							if (ligne2.charAt(i) != ';') {
								tab[index] += ligne2.charAt(i);
							} else {
								index++;
							}
						}
					}
				}
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
