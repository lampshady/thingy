package ivec;


import java.awt.Canvas;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

class Renderer {
	private Camera camera;
	//private TextureList texture;
	//private EntityList entity;
	private int objectlist = 1;
	
	private float lightAmbient[] = { 0.2f, 0.2f, 0.2f, 1.0f };    // Ambient Light Values ( NEW )
	private float lightDiffuse[] = { 1.0f, 1.0f, 1.0f, 1.0f };  // Diffuse Light Values ( NEW )
	private float lightPosition[] = { 0.0f, 5.0f, 0.0f, 1.0f }; // Light Position ( NEW )
	private float lightSpecular[] = { 1.0f, 1.0f, 1.0f, 1.0f };

	public static float lcolor[] = { 1.0f, 1.0f, 1.0f, 1.0f };
	
	//Notifier for adding textures
	boolean textures_changed = false;	
	
	public Renderer(TextureList _texture, EntityList _entity) {
		//texture = _texture;
		//entity = _entity;
	}
	
	public void addReferences(Camera _camera) throws LWJGLException {
		camera=_camera;
	}
	
	public void transparentcube(float alpha, float cube_size) throws FileNotFoundException, IOException {		
		//GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, lcolor);
		
		GL11.glBegin(GL11.GL_QUADS);
	        // Front Face
			GL11.glNormal3f( 0.0f, 0.0f, 1.0f);
	        GL11.glVertex3f(-1.0f, -1.0f,  1.0f);   // Bottom Left Of The Texture and Quad
	        GL11.glVertex3f( 1.0f, -1.0f,  1.0f);   // Bottom Right Of The Texture and Quad
	        GL11.glVertex3f( 1.0f,  1.0f,  1.0f);   // Top Right Of The Texture and Quad
	        GL11.glVertex3f(-1.0f,  1.0f,  1.0f);   // Top Left Of The Texture and Quad
	
	        // Back Face
	        GL11.glNormal3f( 0.0f, 0.0f, -1.0f);
	        GL11.glVertex3f(-1.0f, -1.0f, -1.0f);   // Bottom Right Of The Texture and Quad
	        GL11.glVertex3f(-1.0f,  1.0f, -1.0f);   // Top Right Of The Texture and Quad
	        GL11.glVertex3f( 1.0f,  1.0f, -1.0f);   // Top Left Of The Texture and Quad
	        GL11.glVertex3f( 1.0f, -1.0f, -1.0f);   // Bottom Left Of The Texture and Quad
	
	        // Top Face
	        GL11.glNormal3f( 0.0f, 1.0f, 0.0f);
	        GL11.glVertex3f(-1.0f,  1.0f, -1.0f);   // Top Left Of The Texture and Quad
	        GL11.glVertex3f(-1.0f,  1.0f,  1.0f);   // Bottom Left Of The Texture and Quad
	        GL11.glVertex3f( 1.0f,  1.0f,  1.0f);   // Bottom Right Of The Texture and Quad
	        GL11.glVertex3f( 1.0f,  1.0f, -1.0f);   // Top Right Of The Texture and Quad
	
	        // Bottom Face
	        GL11.glNormal3f( 0.0f, -1.0f, 0.0f);
	        GL11.glVertex3f(-1.0f, -1.0f, -1.0f);   // Top Right Of The Texture and Quad
	        GL11.glVertex3f( 1.0f, -1.0f, -1.0f);   // Top Left Of The Texture and Quad
	        GL11.glVertex3f( 1.0f, -1.0f,  1.0f);   // Bottom Left Of The Texture and Quad
	        GL11.glVertex3f(-1.0f, -1.0f,  1.0f);   // Bottom Right Of The Texture and Quad
	
	        // Right face
	        GL11.glNormal3f( 1.0f, 0.0f, 0.0f);
	        GL11.glVertex3f( 1.0f, -1.0f, -1.0f);   // Bottom Right Of The Texture and Quad
	        GL11.glVertex3f( 1.0f,  1.0f, -1.0f);   // Top Right Of The Texture and Quad
	        GL11.glVertex3f( 1.0f,  1.0f,  1.0f);   // Top Left Of The Texture and Quad
	        GL11.glVertex3f( 1.0f, -1.0f,  1.0f);   // Bottom Left Of The Texture and Quad
	
	        // Left Face
	        GL11.glNormal3f( -1.0f, 0.0f, 0.0f);
	        GL11.glVertex3f(-1.0f, -1.0f, -1.0f);   // Bottom Left Of The Texture and Quad
	        GL11.glVertex3f(-1.0f, -1.0f,  1.0f);   // Bottom Right Of The Texture and Quad
	        GL11.glVertex3f(-1.0f,  1.0f,  1.0f);   // Top Right Of The Texture and Quad
	        GL11.glVertex3f(-1.0f,  1.0f, -1.0f);   // Top Left Of The Texture and Quad
        GL11.glEnd();
	}
	
	public void draw(int list_to_display) throws LWJGLException, FileNotFoundException, IOException {
		//Make sure that the screen is active
		//(doing this every frame slows stuff down)
		Display.makeCurrent();

		// render using OpenGL 
	    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // Clear The Screen And The Depth Buffer
		GL11.glLoadIdentity();
		
		//Place the camera
		GLU.gluLookAt(
				camera.getPositionX()	, camera.getPositionY()	,	camera.getPositionZ(),
				camera.getFocusX()		, camera.getFocusY()	,	camera.getFocusZ(),
				camera.getUpVectorX()	, camera.getUpVectorY()	,	camera.getUpVectorZ()
		);
		//GL11.glEnable(GL11.GL_BLEND);		// Turn Blending On
		//GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE);
		
		//GL11.glTranslatef(1, 0, 0);
		//for( int i = 0; i < ObjParser.getObjectListSize(); i++ )
		//{
			GL11.glCallList(list_to_display);
		//}
		
		//transparentcube(0,1);
		
        GL11.glFlush();
		Display.update();	// now tell the screen to update
		
		Display.releaseContext();
	}

	@SuppressWarnings("unused")
	private static int getObjectListSize() {
		return 0;
	}

	public void initGL() throws LWJGLException {
		Display.makeCurrent();
		
		//initialize the view
		setPerspective();
		GL11.glEnable(GL11.GL_TEXTURE_2D);     
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		GL11.glClearDepth(1.0f);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
		//GL11.glEnable(GL11.GL_BLEND);		// Turn Blending On
		//GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE);

		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glColorMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE);
		GL11.glMateriali(GL11.GL_FRONT, GL11.GL_SHININESS, 128);

		GL11.glEnable(GL11.GL_LIGHTING);
		ByteBuffer temp = ByteBuffer.allocateDirect(16);
        temp.order(ByteOrder.nativeOrder());

        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_AMBIENT, (FloatBuffer)temp.asFloatBuffer().put(lightAmbient).flip());      // Setup The Ambient Light
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, (FloatBuffer)temp.asFloatBuffer().put(lightDiffuse).flip());      // Setup The Diffuse Light
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION,(FloatBuffer)temp.asFloatBuffer().put(lightPosition).flip());     // Position The Light
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_SPECULAR,(FloatBuffer)temp.asFloatBuffer().put(lightSpecular).flip());

        GL11.glEnable(GL11.GL_LIGHT1);                          // Enable Light One
        
        GL11.glEnable(GL11.GL_NORMALIZE);
        GL11.glEnable(GL11.GL_POLYGON_SMOOTH_HINT);

		Display.releaseContext();
	}
	
	public void setPerspective() {
		//Calculate the shape of the screen and notify OpenGL
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(45.0f, (float) Main.width / (float) Main.height, 0.01f, 100.0f);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
	}
	
	public void setPerspective(Canvas GLView) {
		//Calculate the shape of the screen and notify OpenGL
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(45.0f, (float) GLView.getWidth() / (float) GLView.getHeight(), 0.01f, 100.0f);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	public void setObjectlist(int objectlist) {
		this.objectlist = objectlist;
	}

	public int getObjectlist() {
		return objectlist;
	}
}