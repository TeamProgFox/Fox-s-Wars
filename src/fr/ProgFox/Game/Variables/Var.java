package fr.ProgFox.Game.Variables;

import fr.ProgFox.Math.Vec3;
import fr.ProgFox.World.Blocks.Block;

public class Var {
	public static float gravityFactor;
	public static float jumpFactor;
	public static boolean isJumping;
	public static boolean flyMode;
	public static Block selectedBlock;
	public static Vec3 selectedPosition;
	public static float InfoSpeedFactor = 0;
	public static float breakSpeedFactor = 0;
	public static boolean grounded = false;
}
