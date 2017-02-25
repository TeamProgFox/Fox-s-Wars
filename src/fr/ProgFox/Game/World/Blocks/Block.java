package fr.ProgFox.Game.World.Blocks;

import fr.ProgFox.Math.Color4f;
import fr.ProgFox.Math.Vec4;
 
public class Block {
	
	public static final Block TESTE = new Block(new Color4f(1f, 1f, 1f, 0.5f));
	
	public Color4f color;
	public static float sizeX = 1, sizeY = 1, sizeZ = 1;

	public Block(Color4f color) {

		this.color = color;
	}

	public void setColor(Color4f newColor) {
		this.color = newColor;
	}

	public Color4f getColor() {
		return this.color;
	}

	public float[] BlockDataFront(float x, float y, float z, Color4f color2) {

		float r, g, b, a;
		r = color.r;
		g = color.g;
		b = color.b;
		a = color.a;
		return new float[] {

				x + 1, y, z, 				r * 0.8f * color2.r, g * 0.8f * color2.r, b * 0.8f * color2.r, a * 0.8f * color2.r, ///
				x, y, z, 					r * 0.8f * color2.g, g * 0.8f * color2.g, b * 0.8f * color2.g, a * 0.8f * color2.g,//////
				x, y + 1, z, 				r * 0.8f * color2.b, g * 0.8f * color2.b, b * 0.8f * color2.b, a * 0.8f * color2.b,/////
				x + 1, y + 1, z, 			r * 0.8f * color2.a, g * 0.8f * color2.a, b * 0.8f * color2.a, a * 0.8f * color2.a, // 5
				
		};

	}

	public float[] BlockDataBack(float x, float y, float z, Color4f color2) {
		float r, g, b, a;
		r = color.r;
		g = color.g;
		b = color.b;
		a = color.a;

		return new float[] {
                                                                                                                                   
				x, y, z + 1,			r * 0.8f * color2.r, g * 0.8f * color2.r, b * 0.8f * color2.r, a * 0.8f * color2.r, ///   
				x + 1, y, z + 1,		r * 0.8f * color2.g, g * 0.8f * color2.g, b * 0.8f * color2.g, a * 0.8f * color2.g,////// 
				x + 1, y + 1, z + 1,	r * 0.8f * color2.b, g * 0.8f * color2.b, b * 0.8f * color2.b, a * 0.8f * color2.b,/////  
				x, y + 1, z + 1, 		r * 0.8f * color2.a, g * 0.8f * color2.a, b * 0.8f * color2.a, a * 0.8f * color2.a, // 5  
                                                                                                                                   
		};

	}

	public float[] BlockDataRight(float x, float y, float z, Color4f color2) {

		float r, g, b, a;
		r = color.r;
		g = color.g;
		b = color.b;
		a = color.a;


		return new float[] {
                                                                                                                                            
				x + 1, y, z, 					 r * 0.75f * color2.r, g * 0.75f * color2.r, b * 0.75f * color2.r, a * 0.75f * color2.r, ///   
				x + 1, y + 1, z,				 r * 0.75f * color2.g, g * 0.75f * color2.g, b * 0.75f * color2.g, a * 0.75f * color2.g,////// 
				x + 1, y + 1, z + 1,			 r * 0.75f * color2.b, g * 0.75f * color2.b, b * 0.75f * color2.b, a * 0.75f * color2.b,/////  
				x + 1, y, z + 1, 				 r * 0.75f * color2.a, g * 0.75f * color2.a, b * 0.75f * color2.a, a * 0.75f * color2.a, // 5  
                                                                                                                                            
		};

	}

	public float[] BlockDataLeft(float x, float y, float z, Color4f color2) {

		float r, g, b, a;
		r = color.r;
		g = color.g;
		b = color.b;
		a = color.a;


		return new float[] {
                                                                                                                                    
				x, y + 1, z, 			r * 0.75f * color2.r, g * 0.75f * color2.r, b * 0.75f * color2.r, a * 0.75f * color2.r, ///   
				x, y, z,				r * 0.75f * color2.g, g * 0.75f * color2.g, b * 0.75f * color2.g, a * 0.75f * color2.g,////// 
				x, y, z + 1, 			r * 0.75f * color2.b, g * 0.75f * color2.b, b * 0.75f * color2.b, a * 0.75f * color2.b,/////  
				x, y + 1, z + 1, 		r * 0.75f * color2.a, g * 0.75f * color2.a, b * 0.75f * color2.a, a * 0.75f * color2.a, // 5  
                                                                                                                                    
		};

	}

	public float[] BlockDataUp(float x, float y, float z, Color4f color2) {

		float r, g, b, a;
		r = color.r;
		g = color.g;
		b = color.b;
		a = color.a;


		return new float[] {
                                                                                                                                        
				x + 1, y + 1, z,			r * 0.7f * color2.r, g * 0.7f * color2.r, b * 0.7f * color2.r, a * 0.7f * color2.r, ///   
				x, y + 1, z,				r * 0.7f * color2.g, g * 0.7f * color2.g, b * 0.7f * color2.g, a * 0.7f * color2.g,////// 
				x, y + 1, z + 1,			r * 0.7f * color2.b, g * 0.7f * color2.b, b * 0.7f * color2.b, a * 0.7f * color2.b,/////  
				x + 1, y + 1, z + 1,		r * 0.7f * color2.a, g * 0.7f * color2.a, b * 0.7f * color2.a, a * 0.7f * color2.a, // 5  

		};

	}

	public float[] BlockDataDown(float x, float y, float z, Color4f color2) {

		float r, g, b, a;
		r = color.r;
		g = color.g;
		b = color.b;
		a = color.a;

		return new float[] {

				x, y, z, r * 0.7f * color2.r, g * 0.7f * color2.r, b * 0.7f * color2.r, a * 0.7f * color2.r, ///
				x + 1, y, z, r * 0.7f * color2.g, g * 0.7f * color2.g, b * 0.7f * color2.g, a * 0.7f * color2.g, //////
				x + 1, y, z + 1, r * 0.7f * color2.b, g * 0.7f * color2.b, b * 0.7f * color2.b, a * 0.7f * color2.b, /////
				x, y, z + 1, r * 0.7f * color2.a, g * 0.7f * color2.a, b * 0.7f * color2.a, a * 0.7f * color2.a, // 5

		};

	}

}