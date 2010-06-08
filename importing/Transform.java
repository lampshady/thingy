package importing;

import javax.vecmath.Vector3f;

import org.lwjgl.opengl.GL11;

public class Transform {
	private Vector3f forward;
	private Vector3f up;
	private Vector3f position;
	private float scale;
	
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
	
	public Transform(Vector3f _forward, Vector3f _up, Vector3f _position, float _scale) {
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
	
	public float getScale() {
		return scale;
	}

	public void newdraw() {
		double[][] transform_matrix = new double[4][4];
		Vector3f side = new Vector3f(); 
		side.cross(forward, up);
		transform_matrix[0][0] = scale * side.x;
		transform_matrix[0][1] = scale * side.y;
		transform_matrix[0][2] = scale * side.z;
		transform_matrix[0][3] = scale * position.x;
		
		transform_matrix[1][0] = up * side.x;
		transform_matrix[1][1] = up * side.x;
		transform_matrix[1][2] = up * side.x;
		transform_matrix[1][3] = up * position.y;
		
		transform_matrix[2][0] = forward * side.x;
		transform_matrix[2][1] = forward * side.x;
		transform_matrix[2][2] = forward * side.x;
		transform_matrix[2][3] = forward * position.z;
		
		transform_matrix[3][0] = 0;
		transform_matrix[3][1] = 0;
		transform_matrix[3][2] = 0;
		transform_matrix[3][3] = 1;
		
	}
	
	public void draw()
	{
		GL11.glTranslatef(position.x, position.y, position.z);
		
		//Bad as well
		//GL11.glRotatef(forward.angle(new Vector3f(0,0,1)), 0, 1, 0);
		
		Vector3f temp = new Vector3f( 0, up.y, up.z);
		GL11.glRotatef(temp.angle(new Vector3f(0,1,0)), 1, 0, 0);
		
		Vector3f temp2 = new Vector3f( up.x, up.y, 0);
		temp2 = new Vector3f( up.x, up.y, 0);
		//This one
		//GL11.glRotatef(temp2.angle(new Vector3f(0,1,0)), 0, 0, 1);
	}
}
