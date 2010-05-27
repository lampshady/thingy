package ivec;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.vecmath.Vector3f;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;

public class Camera {
	private Vector3f position;					//x, y, z
	private float declination;					//Angle up and down
	private float rotation;						//Angle left and right
	private Vector3f focus;						//x, y, z of target
	private float distance;						//distance from focus
	private Vector3f up_vector;					//vector pointing up
	
	//Don't flip over, its confusing.
	private float maximum_declination = (float) (Math.PI/2.0f) - 0.01f;
	private float minimum_declination = (float) ((float) -1.0f*((Math.PI/2.0f) - 0.01f));
	private float minimum_distance = 0.001f;
	private float maximum_distance = 100.0f;
	
	public Camera(float height, float width){
		//initial setup (float about 0,0,0 I guess?
		
		position = new Vector3f(0,0,0);
		//focus = new Vector3f(-0.5f,0,0);
		focus = new Vector3f(0.0f,0,0);
		declination = 0;
		rotation = 0;
		distance = 3.0f;
		setUpVector( 0, 1, 0 );
		updatePosition();
	}
	
	public Camera(float x, float y, float z, float height, float width)
	{
		position = new Vector3f(0,0,0);
		focus = new Vector3f(x, y, z);
		declination = 0;
		rotation = 0;
		distance = 1.0f;
		setUpVector( 0, 1, 0 );
		updatePosition();
	}
	
	public float getPositionX()
	{
		return position.x + focus.x;
	}
	
	public float getPositionY()
	{
		return position.y + focus.y;
	}
	
	public float getPositionZ()
	{
		return position.z + focus.z;
	}
	
	Vector3f getUp()
	{
		return up_vector;
	}
	
	public float getUpVectorX()
	{
		return up_vector.x;
	}
	
	public float getUpVectorY()
	{
		return up_vector.y;
	}
	
	public float getUpVectorZ()
	{
		return up_vector.z;
	}
	
	public float getFocusX()
	{
		return focus.x;
	}
	
	public float getFocusY()
	{
		return focus.y;
	}
	
	public float getFocusZ()
	{
		return focus.z;
	}
	
	public void changeFocus(float x, float y, float z)
	{
		focus.x = x;
		focus.y = y;
		focus.z = z;
		updatePosition();
	}
	
	public void incrementDistance( float change )
	{
		float temp = distance + change;
		if( temp > maximum_distance)
		{
			distance = maximum_distance;
		}else
		{
			if(temp < minimum_distance)
			{
				distance = minimum_distance;
			}else
			{
				distance = temp;
			}
		}
		updatePosition();
	}
	
	public void incrementDeclination(float angle)
	{
		declination += angle;
		if( declination > maximum_declination ){
			declination = maximum_declination;
		}
		if( declination < minimum_declination ){
			declination = minimum_declination;
		}
		updatePosition();
	}
	
	public void incrementRotation(float angle)
	{
		rotation += angle;
		updatePosition();
	}
	
	public void moveFocus( Vector3f vector3f )
	{
		//focus.set( vector3f.x, vector3f.y, vector3f.z );
		focus.add(vector3f);
		updatePosition();
	}
	
	private void updatePosition()
	{
		 float a = 0;
		 
		 //calculate positions from angles as if focus were (0,0,0)
		 position.y = (float) ((distance * Math.sin(declination)));
		 a = (float) ((distance * Math.cos(declination)));
		 position.x = (float) (a*Math.sin(rotation));
		 position.z = (float) (a*Math.cos(rotation));
	}
	
	private void setUpVector(float x, float y, float z)
	{
		setUpVector( new Vector3f( x, y, z ) );
	}
	
	private void setUpVector( Vector3f newUp )
	{
		up_vector = newUp;
	}

	public Vector3f getRayTo(int mouseX, int mouseY) throws LWJGLException {
		//We need exclusive access to the window
		Display.makeCurrent();
		
		//Create stupid floatbuffers for LWJGL
		IntBuffer viewport = BufferUtils.createIntBuffer(16);
		FloatBuffer modelview = BufferUtils.createFloatBuffer(16);
		FloatBuffer projection = BufferUtils.createFloatBuffer(16);
		FloatBuffer winZ = BufferUtils.createFloatBuffer(1);
		FloatBuffer position = BufferUtils.createFloatBuffer(3);
		Vector3f pos = new Vector3f();
		
		//Get some information about the viewport, modelview, and projection matrix
		GL11.glGetFloat( GL11.GL_MODELVIEW_MATRIX, modelview );
		GL11.glGetFloat( GL11.GL_PROJECTION_MATRIX, projection );
		GL11.glGetInteger( GL11.GL_VIEWPORT, viewport );

		//Find the depth to 
		GL11.glReadPixels(mouseX, mouseY, 1, 1, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, winZ);

		//get the position in 3d space by casting a ray from the mouse
		//coords to the first contacted point in space
		//GLU.gluUnProject(mouseX, mouseY, winZ.get(), modelview, projection, viewport, position);
		GLU.gluUnProject(mouseX, mouseY, winZ.get(), modelview, projection, viewport, position);

		//Make a vector out of the silly float buffer LWJGL forces us to use
		pos.set(position.get(0), position.get(1), position.get(2));
		
		//Don't want to hold on to the context as the renderer will need it
		Display.releaseContext();

		return pos;
	}
	
	public Vector3f getRayToPlane(int mouseX, int mouseY, Vector3f normal, Vector3f planePoint) throws LWJGLException {
		//We need exclusive access to the window
		Display.makeCurrent();
		
		//Create stupid floatbuffers for LWJGL
		IntBuffer viewport = BufferUtils.createIntBuffer(16);
		FloatBuffer modelview = BufferUtils.createFloatBuffer(16);
		FloatBuffer projection = BufferUtils.createFloatBuffer(16);
		FloatBuffer mousePosition = BufferUtils.createFloatBuffer(3);
		Vector3f ray = new Vector3f();
		
		//Get some information about the viewport, modelview, and projection matrix
		GL11.glGetFloat( GL11.GL_MODELVIEW_MATRIX, modelview );
		GL11.glGetFloat( GL11.GL_PROJECTION_MATRIX, projection );
		GL11.glGetInteger( GL11.GL_VIEWPORT, viewport );

		//get the position in 3d space by casting a ray from the mouse  
		//coords to the first contacted point in space
		GLU.gluUnProject(mouseX, mouseY, 1, modelview, projection, viewport, mousePosition);
		
		//Make a vector out of the silly float buffer LWJGL forces us to use
		float d = -1.0f * (float)(normal.dot(planePoint));
		
		//Make ray a vector from origin to point
		ray.set(mousePosition.get(0), 
				mousePosition.get(1), 
				mousePosition.get(2));
		
		ray.set(ray.x - position.x, ray.y - position.y, ray.z-position.z);
		
		//Don't want to hold on to the context as the renderer will need it
		Display.releaseContext();
		float t = ( (-1*d) - position.dot(normal) )/( ray.dot(normal) );
		 
		ray = new Vector3f(this.getPositionX() + t * ray.x,
							this.getPositionY() + t * ray.y,
							this.getPositionZ() + t * ray.z); 
		
		return ray;
	}
	
	public void debug() {
		//Debug the camera
		//System.out.print("Height:		" + height 	+ "	Width:	" + width + "\n");
		//System.out.print("Camera = X:	" + position.x + "	Y:	" + position.y + "	Z:	" + position.z + "\n");
		//System.out.print("Focus  = X:	" + focus.x 	+ "	Y:	" + focus.y 	+ "	Z:	" + focus.z 	+ "\n");
		//System.out.print("Up     = X:	" + up_vector.x + "	Y:	" + up_vector.y + "	Z:	" + up_vector.z + "\n\n");	
	}
	
	
}
