package fr.ProgFox.Game.World;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class SaveChunk {

	private static List<String> list = new ArrayList<>();

	public SaveChunk() {

	}

	public static void save(String name, int x, int y, int z) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("saves/trees/" + name + ".tpf"), true));
			writer.write((x + ";" + y + ";" + z));
			writer.newLine();
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void init(String name) {
		list.clear();
		FileInputStream fin;
		try {
			fin = new FileInputStream("saves/trees/" + name + ".tpf");

			BufferedReader br = new BufferedReader(new InputStreamReader(fin));

			String ligne;

			while ((ligne = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(ligne, ",");

				while (st.hasMoreTokens()) {
					list.add(st.nextToken());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static boolean equals(int x, int y, int z) {
		String[] data;
		int xx, yy, zz;
		for (String a : list) {
			if (a != null) {

				data = a.split(";");
				xx = Integer.parseInt(data[0]);
				yy = Integer.parseInt(data[1]);
				zz = Integer.parseInt(data[2]);
				if (xx == x && yy == y && zz == z) {
					return true;
				}
			}
		}

		return false;

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
