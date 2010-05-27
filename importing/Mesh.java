package importing;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;

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
	
	public void draw()
	{
	//// Quad Begin Header ////
		int polytype;
		if (faces.get(0).vertices.size() == 3) {
			polytype = GL11.GL_TRIANGLES;
		} else if (faces.get(0).vertices.size() == 4) {
			polytype = GL11.GL_QUADS;
		} else {
			polytype = GL11.GL_POLYGON;
		}
		
		
		GL11.glBegin(polytype);	
			GL11.glColor3f(0.5f,0.5f,0.6f);	
			for ( Map.Entry<Integer, Patch> patchEntry : patches.entrySet()) {
				for (Map.Entry<Integer, Face> faceEntry : patchEntry.getValue().getFaces().entrySet())
				{
					Face face = faceEntry.getValue();
					GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SHININESS, );
					GL11.glNormal3f(
							face.getNormals.get(j).x,
							face.getNormals.get(j).y,
							face.getormals.get(j).z
							);
					GL11.glVertex3f(
							faces.get(w).vertices.get(j).x,
							faces.get(w).vertices.get(j).y,
							faces.get(w).vertices.get(j).z
							);
				}
			}
		GL11.glEnd();
		GL11.glEndList();
		Display.releaseContext();
	}
}
