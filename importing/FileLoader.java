package importing;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class FileLoader {
	public FileLoader(){}
	
	public static void loadFile(String filePath)
	{
		String[] temp;
		BufferedReader objfile = null;
		
		
		temp = filePath.split(".");
		
		try {
			
			objfile = new BufferedReader( new InputStreamReader( new FileInputStream( filePath ) ) );
			
			if( temp[1].toLowerCase() == "xgl")
			{
				XGL_Parser parser = new XGL_Parser();
				
				parser.readFile(objfile);
				
				drawToList(parser);
			}
			
			
			
		} catch (FileNotFoundException e) {
			System.out.print("Didn't find the xgl file");
			e.printStackTrace();
		}
	}
	
	static void drawToList(Parser parser)
	{
		FloatBuffer temp = BufferUtils.createFloatBuffer(4);
		
		ArrayList<Face> faces = parser.getFaces();
		GL11.glGenLists(1);
		
		GL11.glNewList(1,GL11.GL_COMPILE);
		for (int i=0;i<faces.size();i++) {
/*			int[] tempfaces = (faces.get(i));
			int[] tempfacesnorms = (facesnorms.get(i));
			int[] tempfacestexs = (facestexs.get(i));
	*/		
			//// Quad Begin Header ////
			int polytype;
			if (faces.get(i).vertices.size() == 3) {
				polytype = GL11.GL_TRIANGLES;
			} else if (faces.get(i).vertices.size() == 4) {
				polytype = GL11.GL_QUADS;
			} else {
				polytype = GL11.GL_POLYGON;
			}
			GL11.glBegin(polytype);	
				GL11.glColor3f(0.5f,0.5f,0.6f);				
				GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, (FloatBuffer)temp.put(new float[]{ 0.8f, 0.8f, 0.8f, 1.0f }).flip());
				
				for (int w=0;w<faces.size();w++) {
					if( faces.get(i).normals.get(w) != 0) {
						float normtempx = (vertexsetsnorms.get(tempfacesnorms[w] - 1))[0];
						float normtempy = (vertexsetsnorms.get(tempfacesnorms[w] - 1))[1];
						float normtempz = (vertexsetsnorms.get(tempfacesnorms[w] - 1))[2];
						GL11.glNormal3f(normtempx, normtempy, normtempz);
					}
					
					if( tempfacestexs[w] != 0) {
						float textempx = (vertexsetstexs.get(tempfacestexs[w] - 1))[0];
						float textempy = (vertexsetstexs.get(tempfacestexs[w] - 1))[1];
						float textempz = (vertexsetstexs.get(tempfacestexs[w] - 1))[2];
						GL11.glTexCoord3f(textempx,1f-textempy,textempz);
					}
					
					float tempx = (vertexsets.get(tempfaces[w] - 1))[0];
					float tempy = (vertexsets.get(tempfaces[w] - 1))[1];
					float tempz = (vertexsets.get(tempfaces[w] - 1))[2];
					
					//GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, lcolor);
					GL11.glVertex3f(tempx,tempy,tempz);
				}
			GL11.glEnd();
		}
		GL11.glEndList();
	}
}
