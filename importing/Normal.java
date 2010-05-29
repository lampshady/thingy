package importing;

import javax.vecmath.Vector3f;

public class Normal {
	private Vector3f direction;
	private int reference;
	
	public Normal(Vector3f _dir) {
		direction = _dir;
	}
	
	public Normal() {
		direction = new Vector3f(0,0,0);
		reference = 0;
	}

	public Vector3f getDirection() {
		return direction;
	}
	
	public void setDirection( Vector3f _dir)
	{
		direction = _dir;
	}
	
	
	public int getRef()
	{
		return reference;
	}
	
	public void setRef( int ref )
	{
		reference = ref;
	}
}
