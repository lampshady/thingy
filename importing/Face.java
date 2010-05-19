package importing;

import java.util.ArrayList;

import javax.vecmath.Vector3f;

public class Face {

	ArrayList<Float> vertices;
	ArrayList<Float> normals;
	ArrayList<Float> textures;
	
	Face()
	{
		vertices = null;
		normals = null;
		textures = null;
	}
	
	Face(ArrayList<Float> verts, ArrayList<Float> txtrs, ArrayList<Float> norms) {
		vertices = verts;
		textures = txtrs;
		normals = norms;
	}
	
	public Face setWithVectors(ArrayList<Vector3f> verts, ArrayList<Float> txtrs, ArrayList<Vector3f> norms) {
		//reset the face
		vertices.removeAll(vertices);
		normals.removeAll(normals);
		textures.removeAll(textures);
		
		//add new vectors and such
		for( Vector3f v : verts )
		{
			vertices.add(v.x);
			vertices.add(v.y);
			vertices.add(v.z);
		}
		for( Vector3f n : norms )
		{
			vertices.add(n.x);
			vertices.add(n.y);
			vertices.add(n.z);
		}
		textures = txtrs;
		
		return this;
	}
}
