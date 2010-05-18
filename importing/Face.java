package importing;

import java.util.ArrayList;

import javax.vecmath.Vector3f;

public class Face {

	ArrayList<Vector3f> vertices;
	ArrayList<Vector3f> normals;
	ArrayList<Float> textures;
	
	Face(ArrayList<Vector3f> verts, ArrayList<Float> txtrs, ArrayList<Vector3f> norms) {
		vertices = verts;
		textures = txtrs;
		normals = norms;
	}
}
