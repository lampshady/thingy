package importing;

import javax.vecmath.Vector3f;

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
}
