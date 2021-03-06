package fr.ProgFox.Game.Variables;

import fr.ProgFox.Game.World.Blocks.Block;
import fr.ProgFox.Math.Vec3;

public class Var {
	public static float gravityFactor;
	public static float jumpFactor;
	public static float InfoSpeedFactor = 0;
	public static float breakSpeedFactor = 0;
	public static float light = 0.2f;
	public static float speedTime = 0.0001f;

	public static boolean isJumping;
	public static boolean flyMode;
	public static boolean debugMode;
	public static boolean grounded = false;
	public static boolean isInDay = true;
	public static boolean isInMenu = true;
	public static boolean isInGame = false;

	public static Block selectedBlock;
	public static Vec3 selectedPosition;

}
