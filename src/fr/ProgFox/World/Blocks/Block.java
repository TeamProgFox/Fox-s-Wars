package fr.ProgFox.World.Blocks;

import fr.ProgFox.Math.Vec4;

public abstract class Block {
	public static final Block GRASS = new GrassBlock();
	public static final Block WOOD = new Wood();
	public static final Block DARKGRASS = new DarkGrassBlock();
	public static final Block LEAF = new LeafBlock();
	public static final Block SAPINLEAF = new SapinLeafBlock();
	public static final Block INFO = new InfoBlock();
	public static final Block STONE = new StoneBlock();

	public Vec4 color;
	public static float sizeX = 1, sizeY = 1, sizeZ = 1;

	public Block(Vec4 color) {

		this.color = color;
	}

	public void setColor(Vec4 newColor) {
		this.color = newColor;
	}

	public Vec4 getColor() {
		return this.color;
	}

	public float[] BlockDataFront(float x, float y, float z, Vec4 color2) {

		float r, g, b, a;
		r = color.x;
		g = color.y;
		b = color.z;
		a = color.w;
		return new float[] {

				x, y, z, 					r * 0.8f * color2.x, g * 0.8f * color2.x, b * 0.8f * color2.x, a * 0.8f * color2.x,//////
				x + 1, y, z, 				r * 0.8f * color2.y, g * 0.8f * color2.y, b * 0.8f * color2.y, a * 0.8f * color2.y, ///
				x + 1, y + 1, z, 			r * 0.8f * color2.z, g * 0.8f * color2.z, b * 0.8f * color2.z, a * 0.8f * color2.z, // 5
				x, y + 1, z, 				r * 0.8f * color2.w, g * 0.8f * color2.w, b * 0.8f * color2.w, a * 0.8f * color2.w,/////

		};

	}

	public float[] BlockDataBack(float x, float y, float z, Vec4 color2) {
		float r, g, b, a;
		r = color.x;
		g = color.y;
		b = color.z;
		a = color.w;

		return new float[] {
                                                                                                                                   
				x, y, z + 1,			r * 0.8f * color2.x, g * 0.8f * color2.x, b * 0.8f * color2.x, a * 0.8f * color2.x,/////
				x + 1, y, z + 1,		r * 0.8f * color2.y, g * 0.8f * color2.y, b * 0.8f * color2.y, a * 0.8f * color2.y, /// 
				x + 1, y + 1, z + 1,	r * 0.8f * color2.z, g * 0.8f * color2.z, b * 0.8f * color2.z, a * 0.8f * color2.z, // 5
				x, y + 1, z + 1, 		r * 0.8f * color2.w, g * 0.8f * color2.w, b * 0.8f * color2.w, a * 0.8f * color2.w,/////
                                                                                                                                   
		};

	}

	public float[] BlockDataRight(float x, float y, float z, Vec4 color2) {

		float r, g, b, a;
		r = color.x;
		g = color.y;
		b = color.z;
		a = color.w;


		return new float[] {
                                                                                                                                            
				x + 1, y, z, 					 r * 0.75f * color2.x, g * 0.75f * color2.x, b * 0.75f * color2.x, a * 0.75f * color2.x,///////
				x + 1, y + 1, z,				 r * 0.75f * color2.y, g * 0.75f * color2.y, b * 0.75f * color2.y, a * 0.75f * color2.y, /// 
				x + 1, y + 1, z + 1,			 r * 0.75f * color2.z, g * 0.75f * color2.z, b * 0.75f * color2.z, a * 0.75f * color2.z, // 5
				x + 1, y, z + 1, 				 r * 0.75f * color2.w, g * 0.75f * color2.w, b * 0.75f * color2.w, a * 0.75f * color2.w,/////
                                                                                                                                            
		};

	}

	public float[] BlockDataLeft(float x, float y, float z, Vec4 color2) {

		float r, g, b, a;
		r = color.x;
		g = color.y;
		b = color.z;
		a = color.w;


		return new float[] {
                                                                                                                                    
				x, y, z,				r * 0.75f * color2.x, g * 0.75f * color2.x, b * 0.75f * color2.x, a * 0.75f * color2.x,/////
				x, y + 1, z, 			r * 0.75f * color2.y, g * 0.75f * color2.y, b * 0.75f * color2.y, a * 0.75f * color2.y, /// 
				x, y + 1, z + 1, 		r * 0.75f * color2.z, g * 0.75f * color2.z, b * 0.75f * color2.z, a * 0.75f * color2.z, // 5
				x, y, z + 1, 			r * 0.75f * color2.w, g * 0.75f * color2.w, b * 0.75f * color2.w, a * 0.75f * color2.w,/////
                                                                                                                                    
		};

	}

	public float[] BlockDataUp(float x, float y, float z, Vec4 color2) {

		float r, g, b, a;
		r = color.x;
		g = color.y;
		b = color.z;
		a = color.w;


		return new float[] {
                                                                                                                                        
				x, y + 1, z,				r * 0.7f * color2.x, g * 0.7f * color2.x, b * 0.7f * color2.x, a * 0.7f * color2.x,/////
				x + 1, y + 1, z,			r * 0.7f * color2.y, g * 0.7f * color2.y, b * 0.7f * color2.y, a * 0.7f * color2.y, /// 
				x + 1, y + 1, z + 1,		r * 0.7f * color2.z, g * 0.7f * color2.z, b * 0.7f * color2.z, a * 0.7f * color2.z, // 5
				x, y + 1, z + 1,			r * 0.7f * color2.w, g * 0.7f * color2.w, b * 0.7f * color2.w, a * 0.7f * color2.w,/////
                                                                                                                                        
		};

	}

	public float[] BlockDataDown(float x, float y, float z, Vec4 color2) {

		float r, g, b, a;
		r = color.x;
		g = color.y;
		b = color.z;
		a = color.w;


		return new float[] {
                                                                                                                                       
				x, y, z, 					r * 0.7f * color2.x, g * 0.7f * color2.x, b * 0.7f * color2.x, a * 0.7f * color2.x,/////
				x + 1, y, z,				r * 0.7f * color2.y, g * 0.7f * color2.y, b * 0.7f * color2.y, a * 0.7f * color2.y, /// 
				x + 1, y, z + 1,			r * 0.7f * color2.z, g * 0.7f * color2.z, b * 0.7f * color2.z, a * 0.7f * color2.z, // 5
				x, y, z + 1,				r * 0.7f * color2.w, g * 0.7f * color2.w, b * 0.7f * color2.w, a * 0.7f * color2.w,/////
                                                                                                                                       
		};

	}

}