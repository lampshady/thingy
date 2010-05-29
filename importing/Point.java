package importing;

import javax.vecmath.Vector3f;

public class Point {
	private Vector3f position;
	private static int id=0;
	private int reference;
	
	public Point(Vector3f _pos) {
		position = _pos;
	}
	
	public Point()
	{
		position = new Vector3f(0,0,0);
		reference = 0;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public int getRef()
	{
		return reference;
	}
	
	public void setRef( int ref )
	{
		reference = ref;
	}
	
	public void setPosition(Vector3f _point)
	{
		position = _point;
	}
	
	public static int getNextID() {
		return id + 1;
	}
}
