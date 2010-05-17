package ivec;

import java.io.*;

import java.util.ArrayList;

import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;

import org.lwjgl.opengl.GL11;

/**
 * Reads a 3DS file (3D Studio Max) into ArrayLists.  Data is read
 * into vertex, normal, texture coodinate and face lists.  Rendering
 * and mesh manipulation are done by calling classes.
 * 
 * @see GL_3DS_Importer.java
 */
public class GL_3DS_Reader
{
    // These three hold the vertex, texture and normal coordinates
    // There is only one set of verts, textures, normals for entire file
    public ArrayList<float[]> vertices = new ArrayList<float[]>();  // Contains float[3] for each Vertex (XYZ)
    public ArrayList<float[]> normals = new ArrayList<float[]>();     // Contains float[3] for each normal
    public ArrayList<float[]> textureCoords = new ArrayList<float[]>();  // Contains float[3] for each texture map coord (UVW)
    public ArrayList<Face> faces = new ArrayList<Face>();       // Contains Face objects
    public static int objectlist;
    public String headers;
    private int currentId;
    private int nextOffset;

    private String currentObjectName = null;
    private boolean endOfStream = false;
    
    class Face { 
    	public int[] vertexIDs;
    	public int[] textureIDs;
    	public int[] normalIDs;

    	Face(int[] vertIDs, int[] txtrIDs, int[] normIDs) {
    		vertexIDs = new int[vertIDs.length];
    		textureIDs = new int[vertIDs.length];
    		normalIDs = new int[vertIDs.length];
    		if (vertIDs != null)
    			System.arraycopy(vertIDs, 0, vertexIDs, 0, vertIDs.length);
    		if (txtrIDs != null)
    			System.arraycopy(txtrIDs, 0, textureIDs, 0, txtrIDs.length);
    		if (normIDs != null)
    			System.arraycopy(normIDs, 0, normalIDs, 0, normIDs.length);
    	}
    }

    public GL_3DS_Reader() {
    }

    // load an object 
    // ASSUMES ONLY ONE object in 3ds file
    public boolean load3DSFromStream(InputStream inStream) {
        System.out.println(">> Importing scene from 3ds stream ...");
        BufferedInputStream in = new BufferedInputStream(inStream);
        try {
            readHeader(in);
            if (currentId != 0x4D4D) {  // 3DS file identifier
                System.out.println("Error: This is not a valid 3ds file.");
                return false;
            }
            while (!endOfStream) {
                readNext(in); // will load into currentObject
            }
            //System.out.print("Finished Parsing\n");
        }
        catch (Throwable e) {
        	//System.out.print("3ds stream constructor: " + e.getMessage() + "\n");
        }
        return true;
    }


    private String readString(InputStream in) throws IOException {
        String result = new String();
        byte inByte;
        while ( (inByte = (byte) in.read()) != 0)
            result += (char) inByte;
        return result;
    }

    private int readInt(InputStream in) throws IOException {
        return in.read() | (in.read() << 8) | (in.read() << 16) | (in.read() << 24);
    }

    private int readShort(InputStream in) throws IOException {
        return in.read() | ( in.read() << 8 );
    }

    private float readFloat(InputStream in) throws IOException {
        return Float.intBitsToFloat(readInt(in));
    }

    private void readHeader(InputStream in) throws IOException {
        currentId = readShort(in);
        nextOffset = readInt(in);
        endOfStream = currentId < 0;
    }

    private void readNext(InputStream in) throws IOException {
       readHeader(in);
       headers = headers + Integer.toHexString(currentId) + " \n";
       if (currentId == 0x3D3D) {  // Mesh block
            return;
        }
        if (currentId == 0x4000) {   // Object block
            currentObjectName = readString(in);
            System.out.println("\nGL_3DS_Reader: " + currentObjectName);
            return;
        }
        if (currentId == 0x4100) { // Triangular polygon object
            System.out.println("\nGL_3DS_Reader: start mesh object");
            //mesh = new GL_Object();
            return;
        }
        if (currentId == 0x4110) { // Vertex list
            System.out.println("\nGL_3DS_Reader: read vertex list");
            readVertexList(in);
            return;
        }
        if (currentId == 0x4120) { // Triangle Point list
            System.out.println("\nGL_3DS_Reader: read triangle list");
            readPointList(in);
            return;
        }
        if (currentId == 0x4140) { // Mapping coordinates
            System.out.println("\nGL_3DS_Reader: read mapping coords");
            readMappingCoordinates(in);
            return;
        }
        skip(in);
    }

    private void skip(InputStream in) throws IOException, OutOfMemoryError {
        for (int i = 0; (i < nextOffset - 6) && (!endOfStream); i++) {
            endOfStream = in.read() < 0;
        }
    }

    /**
     *  3D Studio Max Models have the Z-Axis pointing up.  For compatibility
     *  with OpenGL we need to flip the y values with the z values, and
     *  negate z.  This gives us z coming toward the viewer, x is horizontal
     *  and y is vertical.
     */
    private void readVertexList(InputStream in) throws IOException {
        float x, y, z, tmpy;
        int numVertices = readShort(in);
        for (int i = 0; i < numVertices; i++) {
            x = readFloat(in);
            y = readFloat(in);
            z = readFloat(in);
            //swap the Y and Z values
            tmpy = y;
            y = z;
            z = -tmpy;
            // add vertex to the list
            vertices.add( new float[] {x, y, z} );
                      
            //System.out.print("adding vert[" + i + "]: " + x + "," + y + "," + z + "\n");
        }
    }

    /**
     * read triangles from file.  A triangle is defined as three indices
     * into the vertex list.  For consistency, I use the same face class 
     * that the OBJ file uses, which also stores normal and texture coordinates
     * for each point of the face.  In 3DS format, there is one texture
     * coordinate for each vertex, so the indices into the texture coordinate
     * list will be same as indices into the vertex list.  We're not
     * loading normals so null that out.
     */
    private void readPointList(InputStream in) throws IOException { 
        String hex = "";
        
        int triangles = readShort(in);
        hex = hex + Integer.toHexString(triangles) + " ";
        for (int i = 0; i < triangles; i++) {
            int[] vertexIDs = new int[3];
        	vertexIDs[0] = readShort(in);
        	vertexIDs[1] = readShort(in);
        	vertexIDs[2] = readShort(in);
        	hex = hex + Integer.toHexString(vertexIDs[0]) + " ";
        	hex = hex + Integer.toHexString(vertexIDs[1]) + " ";
        	hex = hex + Integer.toHexString(vertexIDs[2]) + " ";
        	
        	Vector3f normal = new Vector3f(0,0,0);
        	
        	Vector3f zero = new Vector3f(
        		vertices.get(vertexIDs[0])[0], 
        		vertices.get(vertexIDs[0])[1],
        		vertices.get(vertexIDs[0])[2]
        	);
        	
        	Vector3f one = new Vector3f(
        		vertices.get(vertexIDs[1])[0], 
        		vertices.get(vertexIDs[1])[1],
        		vertices.get(vertexIDs[1])[2]
        	);
        	
        	Vector3f two = new Vector3f(
        		vertices.get(vertexIDs[2])[0], 
        		vertices.get(vertexIDs[2])[1],
        		vertices.get(vertexIDs[2])[2]
        	);
        	
        	Vector3f a = one;
        	a.sub((Tuple3f)zero);
        	Vector3f b = two;
        	b.sub((Tuple3f)one);
        	
        	readShort(in);
        	//hex = hex + Integer.toHexString(temp) + " \n";
        	//System.out.print(hex);

    		normal.cross(a, b);
        	
        	float[] n = new float[3];
 
        	n[0] = normal.x;
        	n[1] = normal.y;
        	n[2] = normal.z;
        	
        	normals.add(n);
        	normals.add(n);
        	normals.add(n);
        	
        	int[] normalID= new int[3];
        	normalID[0] = normals.size()-3;
        	normalID[1] = normals.size()-2;
        	normalID[2] = normals.size()-1;
            
            faces.add( new Face(vertexIDs,vertexIDs,normalID) );
            /*
            System.out.print("adding face[" + i + "]: " +
        		vertexIDs[0] + "," +
        		vertexIDs[1] + "," +
        		vertexIDs[2] + "\n"
        		
            );
            */
        }
    }

    public int getObjectList() {
    	return objectlist;
    }
    
    private void readMappingCoordinates(InputStream in) throws IOException {
        int numVertices = readShort(in);
        for (int i = 0; i < numVertices; i++) {
            float[] uvw = new float[3];
            uvw[0] = readFloat(in);
            uvw[1] = readFloat(in);
            uvw[2] = 0f;
            textureCoords.add( uvw );
        }
    }
    
    public void opengldrawtolist() {
    	objectlist = GL11.glGenLists(1);
		GL11.glNewList(objectlist,GL11.GL_COMPILE);
		
		System.out.print("\nGenerating OpenGL list[size=" + faces.size() + "]:\n");
		for (int i=0;i<faces.size();i++) {
			int[] tempfaces = (faces.get(i).vertexIDs);
			int[] tempfacesnorms = (faces.get(i).normalIDs);
			//int[] tempfacestexs = (faces.get(i).textureIDs);
			
			//// Quad Begin Header ////
			int polytype;
			if (tempfaces.length == 3) {
				polytype = GL11.GL_TRIANGLES;
				//System.out.print("\nTriangle=\n");
			} else if (tempfaces.length == 4) {
				polytype = GL11.GL_QUADS;
				//System.out.print("\nQuad=\n");
			} else {
				polytype = GL11.GL_POLYGON;
				//System.out.print("\nPolygon=\n");
			}
			GL11.glBegin(polytype);	
				//GL11.glColor3f(1.0f,1.0f,1.0f);
				for (int w=0;w<tempfaces.length;w++) {
					if (tempfacesnorms[w] != 0) {
						float normtempx = (normals.get(tempfacesnorms[w]))[0];
						float normtempy = (normals.get(tempfacesnorms[w]))[1];
						float normtempz = (normals.get(tempfacesnorms[w]))[2];
						GL11.glNormal3f(normtempx, normtempy, normtempz);
					}
					/*
					if (tempfacestexs[w] != 0) {
						float textempx = (textureCoords.get(tempfacestexs[w]))[0];
						float textempy = (textureCoords.get(tempfacestexs[w]))[1];
						float textempz = (textureCoords.get(tempfacestexs[w]))[2];
						GL11.glTexCoord3f(textempx,1f-textempy,textempz);
					}
					*/
					float tempx = (vertices.get(tempfaces[w]))[0];
					float tempy = (vertices.get(tempfaces[w]))[1];
					float tempz = (vertices.get(tempfaces[w]))[2];
					//System.out.print(tempx + "," + tempy + "," + tempz + "\n");
					GL11.glVertex3f(tempx,tempy,tempz);
				}
			GL11.glEnd();
		}
		GL11.glEndList();
		
		System.out.println( headers );
	}
}