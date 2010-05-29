package importing;

import java.util.HashMap;

public class Object_3D {
	private Transform transform;
	//private HashMap<Integer,Mesh> mesh;
	private HashMap<Integer,Object_3D> objects;
	private int meshref;
	private static int id = 0;
	//Data data;
	//Name name;
	
	public Object_3D() {
		id++;
	}
	
	public void addTransform(Transform _transform) {
		transform = _transform;
	}
	
	public Transform getTransform() {
		return transform;
	}
	
	public void setMeshRef(int mr) {
		meshref = mr;
	}
	
	public int getMeshRef() {
		return meshref;
	}
	
	/*
	public void addMesh(Mesh mesh) {
		meshes.put(Mesh.getNextID(),mesh);
	}
	*/
	
	public void addObject(Object_3D obj) {
		objects.put(Object_3D.getNextID(), obj);
	}
	
	public static int getNextID() {
		return id + 1;
	}

	public int getNumberOfObjects() {
		// TODO Auto-generated method stub
		return objects.size();
	}
	
	public Object_3D getObject(int index)
	{
		return objects.get(objects.keySet().toArray()[index]);
	}
}
