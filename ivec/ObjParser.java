package ivec;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;


//Add Object parser
//final JFileChooser fc_model = new JFileChooser("lib/Models/");
//fc_model.showOpenDialog(window);
//BufferedReader objfile = new BufferedReader(new FileReader(fc_model.getSelectedFile()));
//ObjParser model = new ObjParser(objfile, true);


public class ObjParser {
	private ArrayList<float[]> vertexsets = new ArrayList<float[]>(); // Vertex Coordinates
	private ArrayList<float[]> vertexsetsnorms = new ArrayList<float[]>(); // Vertex Coordinates Normals
	private ArrayList<float[]> vertexsetstexs = new ArrayList<float[]>(); // Vertex Coordinates Textures
	private ArrayList<int[]> faces = new ArrayList<int[]>(); // Array of Faces (vertex sets)
	private ArrayList<int[]> facestexs = new ArrayList<int[]>(); // Array of of Faces textures
	private ArrayList<int[]> facesnorms = new ArrayList<int[]>(); // Array of Faces normals
	//private ArrayList<float[]> groups = new ArrayList<float[]>(); // Array of groups
	
	static private int objectlist;
	private int numpolys = 0;
	
	//// Statistics for drawing ////
	public float toppoint = 0;		// y+
	public float bottompoint = 0;	// y-
	public float leftpoint = 0;		// x-
	public float rightpoint = 0;	// x+
	public float farpoint = 0;		// z-
	public float nearpoint = 0;		// z+	
	
	private FloatBuffer temp = BufferUtils.createFloatBuffer(4);
	
	static public int getObjectListSize()
	{
		return objectlist;
	}
	
	public ObjParser(BufferedReader ref, boolean centerit, int i) {
		objectlist = i;
		loadobject(ref);
		if (centerit) {
			centerit();
		}
		opengldrawtolist();
		numpolys = faces.size();
		
		cleanup();
	}
	
	private void cleanup() {
		vertexsets.clear();
		vertexsetsnorms.clear();
		vertexsetstexs.clear();
		faces.clear();
		facestexs.clear();
		facesnorms.clear();
	}
	
	private void loadobject(BufferedReader br) {
		int linecounter = 0;
		try {
			
			String newline;
			boolean firstpass = true;
			
			while (((newline = br.readLine()) != null)) {
				linecounter++;
				newline = newline.trim();
				if (newline.length() > 0) {
					if (newline.charAt(0) == 'v' && newline.charAt(1) == ' ') {
						float[] coords = new float[4];
						String[] coordstext = new String[4];
						coordstext = newline.split("\\s+");
						for (int i = 1;i < coordstext.length;i++) {
							coords[i-1] = Float.valueOf(coordstext[i]).floatValue();
						}
						//// check for far-points ////
						if (firstpass) {
							rightpoint = coords[0];
							leftpoint = coords[0];
							toppoint = coords[1];
							bottompoint = coords[1];
							nearpoint = coords[2];
							farpoint = coords[2];
							firstpass = false;
						}
						if (coords[0] > rightpoint) {
							rightpoint = coords[0];
						}
						if (coords[0] < leftpoint) {
							leftpoint = coords[0];
						}
						if (coords[1] > toppoint) {
							toppoint = coords[1];
						}
						if (coords[1] < bottompoint) {
							bottompoint = coords[1];
						}
						if (coords[2] > nearpoint) {
							nearpoint = coords[2];
						}
						if (coords[2] < farpoint) {
							farpoint = coords[2];
						}
						/////////////////////////////
						vertexsets.add(coords);
					}
					if (newline.charAt(0) == 'v' && newline.charAt(1) == 't') {
						float[] coords = new float[4];
						String[] coordstext = new String[4];
						coordstext = newline.split("\\s+");
						for (int i = 1;i < coordstext.length;i++) {
							coords[i-1] = Float.valueOf(coordstext[i]).floatValue();
						}
						vertexsetstexs.add(coords);
					}
					if (newline.charAt(0) == 'v' && newline.charAt(1) == 'n') {
						float[] coords = new float[4];
						String[] coordstext = new String[4];
						coordstext = newline.split("\\s+");
						for (int i = 1;i < coordstext.length;i++) {
							coords[i-1] = Float.valueOf(coordstext[i]).floatValue();
						}
						vertexsetsnorms.add(coords);
					}
					if (newline.charAt(0) == 'f' && newline.charAt(1) == ' ') {
						String[] coordstext = newline.split("\\s+");
						int[] v = new int[coordstext.length - 1];
						int[] vt = new int[coordstext.length - 1];
						int[] vn = new int[coordstext.length - 1];
						
						for (int i = 1;i < coordstext.length;i++) {
							String fixstring = coordstext[i].replaceAll("//","/0/");
							String[] tempstring = fixstring.split("/");
							v[i-1] = Integer.valueOf(tempstring[0]).intValue();
							if (tempstring.length > 1) {
								vt[i-1] = Integer.valueOf(tempstring[1]).intValue();
							} else {
								vt[i-1] = 0;
							}
							if (tempstring.length > 2) {
								vn[i-1] = Integer.valueOf(tempstring[2]).intValue();
							} else {
								vn[i-1] = 0;
							}
						}
						faces.add(v);
						facestexs.add(vt);
						facesnorms.add(vn);
					}
					if (newline.charAt(0) == 'g') {
						if(newline.charAt(1) == ' '){
							
						}
					}
				}
			}
			
		} catch (IOException e) {
			//System.out.println("Failed to read file: " + br.toString());
			//System.exit(0);			
		} catch (NumberFormatException e) {
			//System.out.println("Malformed OBJ (on line " + linecounter + "): " + br.toString() + "\r \r" + e.getMessage());
			//System.exit(0);
		}
		
	}
	
	private void centerit() {
		float xshift = (rightpoint-leftpoint) /2f;
		float yshift = (toppoint - bottompoint) /2f;
		float zshift = (nearpoint - farpoint) /2f;
		
		for (int i=0; i < vertexsets.size(); i++) {
			float[] coords = new float[4];
			
			coords[0] = ((vertexsets.get(i)))[0] - leftpoint - xshift;
			coords[1] = ((vertexsets.get(i)))[1] - bottompoint - yshift;
			coords[2] = ((vertexsets.get(i)))[2] - farpoint - zshift;
			
			vertexsets.set(i,coords); // = coords;
		}
		
	}
	
	public float getXWidth() {
		float returnval = 0;
		returnval = rightpoint - leftpoint;
		return returnval;
	}
	
	public float getYHeight() {
		float returnval = 0;
		returnval = toppoint - bottompoint;
		return returnval;
	}
	
	public float getZDepth() {
		float returnval = 0;
		returnval = nearpoint - farpoint;
		return returnval;
	}
	
	public int numpolygons() {
		return numpolys;
	}
	
	public void opengldrawtolist() {
		
		GL11.glGenLists(1);
		
		GL11.glNewList(objectlist,GL11.GL_COMPILE);
		for (int i=0;i<faces.size();i++) {
			int[] tempfaces = (faces.get(i));
			int[] tempfacesnorms = (facesnorms.get(i));
			int[] tempfacestexs = (facestexs.get(i));
			
			//// Quad Begin Header ////
			int polytype;
			if (tempfaces.length == 3) {
				polytype = GL11.GL_TRIANGLES;
			} else if (tempfaces.length == 4) {
				polytype = GL11.GL_QUADS;
			} else {
				polytype = GL11.GL_POLYGON;
			}
			GL11.glBegin(polytype);	
				GL11.glColor3f(0.5f,0.5f,0.6f);				
				GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, (FloatBuffer)temp.put(new float[]{ 0.8f, 0.8f, 0.8f, 1.0f }).flip());
				
				for (int w=0;w<tempfaces.length;w++) {
					if (tempfacesnorms[w] != 0) {
						float normtempx = (vertexsetsnorms.get(tempfacesnorms[w] - 1))[0];
						float normtempy = (vertexsetsnorms.get(tempfacesnorms[w] - 1))[1];
						float normtempz = (vertexsetsnorms.get(tempfacesnorms[w] - 1))[2];
						GL11.glNormal3f(normtempx, normtempy, normtempz);
					}
					
					if (tempfacestexs[w] != 0) {
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
	
	public void opengldraw() {
		GL11.glCallList(objectlist);
	}
	
}