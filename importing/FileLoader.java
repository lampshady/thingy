package importing;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class FileLoader {
	public FileLoader(){}
	
	public static int loadFile(String filePath) throws LWJGLException
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
			parser = new XGL_Parser();	
		}
		
		parser.readFile(objfile);	
		return drawToList(parser);
			
		
	}
	
	static int drawToList(Parser parser) throws LWJGLException
	{
		
		Display.makeCurrent();
		
		World world = parser.getWorld();
		
		
		int listID = GL11.glGenLists(1);
		GL11.glNewList(1,GL11.GL_COMPILE);
		
		world.draw();
		
		
		return listID;
	}
}

/*			
	int[] tempfaces = (faces.get(i));
int[] tempfacesnorms = (facesnorms.get(i));
int[] tempfacestexs = (facestexs.get(i));
*/		
/*		
//// Quad Begin Header ////
int polytype;
if (faces.get(0).vertices.size() == 3) {
polytype = GL11.GL_TRIANGLES;
} else if (faces.get(0).vertices.size() == 4) {
polytype = GL11.GL_QUADS;
} else {
polytype = GL11.GL_POLYGON;
}

GL11.glBegin(polytype);	
GL11.glColor3f(0.5f,0.5f,0.6f);	
for (int w=0;w<faces.size();w++) {
	for (int j=0; j<faces.get(w).vertices.size(); j++)
	{
		GL11.glNormal3f(
				faces.get(w).normals.get(j).x,
				faces.get(w).normals.get(j).y,
				faces.get(w).normals.get(j).z
				);
		GL11.glVertex3f(
				faces.get(w).vertices.get(j).x,
				faces.get(w).vertices.get(j).y,
				faces.get(w).vertices.get(j).z
				);
	}
}
GL11.glEnd();
GL11.glEndList();
Display.releaseContext();
*/
