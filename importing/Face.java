package importing;

public class Face {

	float[] vertices;
	float[] normals;
	float[] textures;
	
	Face(float[] verts, float[] txtrs, float[] norms) {
		vertices = new float[verts.length];
		textures = new float[txtrs.length];
		normals = new float[norms.length];
		if (verts != null)
			System.arraycopy(verts, 0, vertices, 0, verts.length);
		if (txtrs != null)
			System.arraycopy(txtrs, 0, textures, 0, txtrs.length);
		if (norms != null)
			System.arraycopy(norms, 0, normals, 0, norms.length);
	}
}
