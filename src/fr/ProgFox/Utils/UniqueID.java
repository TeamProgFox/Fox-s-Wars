package fr.ProgFox.Utils;

import java.security.SecureRandom;

public class UniqueID {
	public static int getUniqueID(){
		int random = new SecureRandom().nextInt();
		return (int) System.nanoTime() + random;
	}
}
