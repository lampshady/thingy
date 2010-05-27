package importing;

import java.util.HashMap;

public class Mesh {
	private HashMap<Integer,Material> materials;
	private HashMap<Integer,Point> points;
	private HashMap<Integer,Normal> normals;
	private HashMap<Integer,Patch> patches;
	private static int id = 0;
	
	public Mesh() {
		id++;
	}
	
	public void addPoints(Point point) {
		points.put(Point.getNextID(),point);
	}
	
	public void addNormals(Normal normal) {
		normals.put(Normal.getNextID(),normal);
	}
	
	public void addPatches(Patch patch) {
		patches.put(Patch.getNextID(),patch);
	}
	
	public Point getPoint(int index) {
		return points.get(index);
	}
	
	public Normal getNormal(int index) {
		return normals.get(index);
	}

	public Patch getPatch(int index) {
		return patches.get(index);
	}
	
	public Material getMaterial(int index) {
		return materials.get(index);
	}
	
	public static int getNextID() {
		return id + 1;
	}
}
