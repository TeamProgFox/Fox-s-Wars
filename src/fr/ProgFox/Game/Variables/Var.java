package fr.ProgFox.Game.Variables;

import fr.ProgFox.Math.Vec3;
import fr.ProgFox.World.Blocks.Block;

public class Var {
	public static float gravityFactor;
	public static float jumpFactor;
	public static boolean isJumping;
	public static boolean flyMode;
	public static boolean debugMode;
	public static Block selectedBlock;
	public static Vec3 selectedPosition;
	public static float InfoSpeedFactor = 0;
	public static float breakSpeedFactor = 0;
	public static boolean grounded = false;
	public static float light = 0.2f;
	public static float speedTime = 0.0001f;
	public static boolean isInDay = true;
	public static boolean isInFirstPersonne = true;
	public static boolean isInThirdPersonne = !isInFirstPersonne;
	public static boolean isInMenu = false;
	public static boolean isInGame = false;
}
