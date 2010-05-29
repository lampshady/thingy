package importing;

import java.util.ArrayList;
import java.util.HashMap;

public class Patch {
	private ArrayList<Face> faces;
	private HashMap<Integer, Material> material;
	private static int id = 0;
	
	public Patch() {
		id++;
	}
	
	public void addFace(Face face) {
		faces.add(face);
	}
	
	public void addMaterial(int index, Material mat) {
		material.put(index, mat);
	}
	public ArrayList<Face> getFaces()
	{
		return faces;
	}
	
	public static int getNextID() {
		return id+1;
	}
}
