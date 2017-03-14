package fr.ProgFox.Game.Items;

import java.util.*;

public class Item {
	public static List<Item> items = new ArrayList<Item>();
	
	boolean isBlock = false;
	String name;
	public Item(String name, boolean isBlock) {
		this.name = name;
		this.isBlock = isBlock;
	}
	
	public String getName() {
		return name;
	}

	public static void addItem(Item e){
		items.add(e);
	}
	
}
