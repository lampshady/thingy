package importing;

import java.util.HashMap;

public class Patch {
	private HashMap<Integer, Face> faces;
	private HashMap<Integer, Material> material;
	private static int id = 0;
	
	public Patch() {
		id++;
	}
	
	public void addFace(int index, Face face) {
		faces.put(index, face);
	}
	
	public void addMaterial(int index, Material mat) {
		material.put(index, mat);
	}
	public HashMap<Integer, Face> getFaces()
	{
		return faces;
	}
	
	public static int getNextID() {
		return id+1;
	}
}
