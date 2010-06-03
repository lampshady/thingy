package importing;

import javax.vecmath.Vector3f;

import org.lwjgl.opengl.GL11;

public class Transform {
	private Vector3f forward;
	private Vector3f up;
	private Vector3f position;
	private Vector3f scale;
	
	public Transform()
	{
		forward = null;
		up = null;
		position = null;
	}
	
	public Transform(Vector3f _forward, Vector3f _up, Vector3f _position) {
		forward = _forward;
		up = _up;
		position = _position;
	}
	
	public Transform(Vector3f _forward, Vector3f _up, Vector3f _position, Vector3f _scale) {
		forward = _forward;
		up = _up;
		position = _position;
		scale = _scale;
	}
	
	public Vector3f getForward() {
		return forward;
	}
	
	public Vector3f getUp() {
		return up;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public Vector3f getScale() {
		return scale;
	}
	
	public void draw()
	{
		GL11.glPushMatrix();

		GL11.glTranslatef(position.x, position.y, position.z);
		
		GL11.glPopMatrix();
		
		GL11.glRotatef(forward.angle(new Vector3f(0,0,1)), 0, 1, 0);
		
		Vector3f temp = new Vector3f( 0, up.y, up.z);
		GL11.glRotatef(temp.angle(new Vector3f(0,1,0)), 1, 0, 0);
		
		Vector3f temp2 = new Vector3f( up.x, up.y, 0);
		temp2 = new Vector3f( up.x, up.y, 0);
		GL11.glRotatef(temp2.angle(new Vector3f(0,1,0)), 0, 0, 1);
		
		System.out.print( 
			"forward:" + forward.angle(new Vector3f(0,0,1)) + 
			"|| up(xy):" + temp.angle(new Vector3f(0,1,0)) + 
			"|| up(yz):" + temp2.angle(new Vector3f(0,0,1)) + "\n\n\n"
		);
		
		if(temp2.toString() == "NaN")
			System.out.print( 
				"======\nforward:" + forward.toString() + 
				"|| up(xy):" + up.toString() + 
				"======\n"
			);
	}
}
