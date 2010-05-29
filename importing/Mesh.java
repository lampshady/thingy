package importing;

import java.util.HashMap;
import java.util.Map;

import javax.vecmath.Vector3f;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class Mesh {
	private HashMap<Integer,Material> materials;
	private HashMap<Integer,Point> points;
	private HashMap<Integer,Normal> normals;
	private HashMap<Integer,Patch> patches;
	private int face_size=3;
	private boolean per_vertex_normals = false;
		
	public Mesh() {
	}
	
	public void setVertexNormals(boolean vertnorms){
		per_vertex_normals = vertnorms;
	}
	public boolean getVertexNormals(){
		return per_vertex_normals;
	}
	
	public void addPoint(Point point) {
		points.put(point.getRef(),point);
	}
	
	public void addNormal(Normal normal) {
		normals.put(normal.getRef(),normal);
	}
	
	public void addPatch(Patch patch) {
		patches.put(Patch.getNextID(),patch);
	}
	
	public void addMaterial(Material material) {
		materials.put(Material.getNextID(),material);
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
	
	
	public void draw() throws LWJGLException
	{
		int polytype;
		Vector3f point = new Vector3f();
		Vector3f[] normal = new Vector3f[3];
		Vector3f face_normal = new Vector3f();
		
		//Determine shape
		if (face_size == 3) {
			polytype = GL11.GL_TRIANGLES;
		} else if (face_size == 4) {
			polytype = GL11.GL_QUADS;
		} else {
			polytype = GL11.GL_POLYGON;
		}
		
		GL11.glBegin(polytype);	
			GL11.glColor3f(0.5f,0.5f,0.6f);	
			for ( Map.Entry<Integer, Patch> patchEntry : patches.entrySet()) {
				for (Face face : patchEntry.getValue().getFaces())
				{
					GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, materials.get(face.getMaterial()).getShine());

					if(per_vertex_normals) {
						for(int i=0; i<3;i++) {
							face_normal = points.get(face.getPointRefs().get(i)).getPosition();
							GL11.glNormal3f(
									face_normal.x,
									face_normal.y,
									face_normal.z
							);
							
							point = points.get(face.getPointRefs().get(i)).getPosition();
							GL11.glVertex3f(
								point.x,
								point.y,
								point.z
							);
						}

						
					} else {
						for(int i=0; i<3;i++) {
							normal[i] = points.get(face.getPointRefs().get(i)).getPosition();
						}
						normal[1].sub(normal[0]);
						normal[2].sub(normal[0]);
						face_normal.cross( normal[1], normal[2] );
						
						GL11.glNormal3f(
								face_normal.x,
								face_normal.y,
								face_normal.z
						);
						
						for(int i=0; i<face.getPointRefs().size();i++) {
							point = points.get(face.getPointRefs().get(i)).getPosition();
							GL11.glVertex3f(
								point.x,
								point.y,
								point.z
							);
						}
					}
					
					
					
				}
			}
		GL11.glEnd();
		GL11.glEndList();
		Display.releaseContext();
	}
}
