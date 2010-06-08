package importing;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class FileLoader {
	public FileLoader(){}
	
	public static int loadFile(String filePath) throws LWJGLException, SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
		String[] temp;
		BufferedReader objfile = null;
		Parser parser = null;
		
		temp = filePath.split("\\.");
		
		try {
			objfile = new BufferedReader( new InputStreamReader( new FileInputStream( filePath ) ) );
		} catch (FileNotFoundException e) {
			System.out.print("Didn't find the xgl file");
			e.printStackTrace();
		}
		
		
		if( temp[temp.length-1].toLowerCase().equals("xgl"))
		{
			//parser = new XGL_Parser();
			XGLParserNew x = new XGLParserNew();
			x.thing();
		}
		
		//parser.readFile(objfile);	
		 
		return drawToList(parser);
			
		
	}
	
	static int drawToList(Parser parser) throws LWJGLException
	{
		
		Display.makeCurrent();
		
		int listID = GL11.glGenLists(1);
		GL11.glNewList(listID,GL11.GL_COMPILE);
		
		parser.getWorld().draw();
		//drawDebug();

		GL11.glEndList();
		Display.releaseContext();
		
		return listID;
		
	}
	
	public static void drawDebug()
	{
		GL11.glBegin(GL11.GL_TRIANGLES);
		 
		// Front
		GL11.glColor3f(0.0f, 1.0f, 1.0f); 
		GL11.glVertex3f(0.0f, 1.0f, 0.0f);
		GL11.glColor3f(0.0f, 0.0f, 1.0f); 
		GL11.glVertex3f(-1.0f, -1.0f, 1.0f);
		GL11.glColor3f(0.0f, 0.0f, 0.0f); 
		GL11.glVertex3f(1.0f, -1.0f, 1.0f);
	 
		// Right Side Facing Front
		GL11.glColor3f(0.0f, 1.0f, 1.0f); 
		GL11.glVertex3f(0.0f, 1.0f, 0.0f);
		GL11.glColor3f(0.0f, 0.0f, 1.0f); 
		GL11.glVertex3f(1.0f, -1.0f, 1.0f);
		GL11.glColor3f(0.0f, 0.0f, 0.0f); 
		GL11.glVertex3f(0.0f, -1.0f, -1.0f);
	 
		// Left Side Facing Front
		GL11.glColor3f(0.0f, 1.0f, 1.0f); 
		GL11.glVertex3f(0.0f, 1.0f, 0.0f);
		GL11.glColor3f(0.0f, 0.0f, 1.0f); 
		GL11.glVertex3f(0.0f, -1.0f, -1.0f);
		GL11.glColor3f(0.0f, 0.0f, 0.0f); 
		GL11.glVertex3f(-1.0f, -1.0f, 1.0f);
	 
		// Bottom
		GL11.glColor3f(0.0f, 0.0f, 0.0f); 
		GL11.glVertex3f(-1.0f, -1.0f, 1.0f);
		GL11.glColor3f(0.1f, 0.1f, 0.1f); 
		GL11.glVertex3f(1.0f, -1.0f, 1.0f);
		GL11.glColor3f(0.2f, 0.2f, 0.2f); 
		GL11.glVertex3f(0.0f, -1.0f, -1.0f);
	 
		GL11.glEnd();
	}
}