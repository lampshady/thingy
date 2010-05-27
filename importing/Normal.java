package importing;

import javax.vecmath.Vector3f;

public class Normal {
	private Vector3f position;
	private static int id=0;
	
	public Normal(Vector3f _pos) {
		position = _pos;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public static int getNextID() {
		return id + 1;
	}
}
