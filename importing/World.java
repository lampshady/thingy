package importing;

import java.util.ArrayList;
import java.util.HashMap;

public class World {
	ArrayList<String> data_tag;
	float[] background = new float[3];
	//Texture goes here
	private HashMap<Integer,Mesh> meshes;
	private HashMap<Integer,Object_3D> objects;
	
	public World() {
		
	}
	
	public void addMesh(Mesh _mesh) {
		meshes.put(Mesh.getNextID(), _mesh);
	}
	
	public void addObject(Object_3D obj) {
		objects.put(Object_3D.getNextID(), obj);
	}
	
	public void addData(String data) {
		data_tag.add(data);
	}
	
	public void addBackground(float r, float g, float b) {
		background[0]=r;
		background[1]=g;
		background[2]=b;
	}
	
	public Mesh getMesh(int index) {
		return meshes.get(index);
	}
	
	public Object getObject(int index) {
		return objects.get(index);
	}
}
