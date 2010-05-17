package importing;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.vecmath.Vector3f;

public class XGL_Parser {
	ArrayList<Material> matList;
	ArrayList<Ambient_Light> ambLightList;
	ArrayList<Directional_Light> dirLightList;
	ArrayList<Texture> textureListAlpha;
	ArrayList<Texture> textureListNoAlpha;
	ArrayList<Vector3f> vertices;
	ArrayList<Vector3f> normals;
	ArrayList<Face> faces;
	
	
	XGL_Parser(){}
	
	void readFile(String filePath)
	{
		byte[] buffer = new byte[(int) new File(filePath).length()];
	    BufferedInputStream f;
	    
		try {
			f = new BufferedInputStream(new FileInputStream(filePath));
			f.read(buffer);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	    String tempData = new String(buffer);
	    
	    tempData.replace("<", "");
	    tempData.replace("\n", "");
	    //tempData.replace(" ", "");
	    
	    String[] fileData = tempData.split(">");
	    
	    if( fileData[0].toUpperCase() == "WORLD"){
		    for( int i = 1; i < fileData.length; i++)
		    {
		    	/*
		    	Possible values for a child of <WORLD>:
		    	<MESH>			- Defines a Mesh of triangles 
		   		<MAT>			- Defines a new Material
		   		
		   		--Unimplemented--
		   		<OBJECT>		- Defines a 3d Object (unimplemented)
				<LINESTYLE>		- Defines a new Line Style (unimplemented)
				<POINTSTYLE>	- Defines a new Point Style (unimplemented)
				
					From my understanding, TEXTURERGB and RGBA should always be a child of TEXTURE, so not sure
					why they can be a child of the WORLD.  Will have to investigate
				<TEXTURE>		- Defines a new Texture
				<TEXTURERGB>	- 
				<TEXTURERGBA>
				<TC>			- This tag represents a position on the 2D space of a texture.
									The tag should contain a 2D vector in the form u,v. 
									The texture coordinate 0,0 corresponds to the upper left had corner of the texture.
									The coordinate 1,1 corresponds to the lower right hand corner.
									To represent a repeated or clamped texture, values for u and v greater than 1 or less than 0 may be used.
		    	*/
		    	if(fileData[i].toUpperCase() == "MESH")
		    	{
		    		i++; //move to the next piece of data
		    		readMesh(i, fileData);
		    	}
		    	
		    }
	    }else{
	    	System.out.print("File does not start with <WORLD> tag");
	    }
	}
	
	void readMesh(int i, String[] fileData)
	{
		while(fileData[i] != "/MESH")
		{
			/*
			Read in Mesh data:
			Optional Tags
				F - Faces (any number)
				P - Points (any number)
				PATCH - Patches (any number) - Not really sure of the purpose of these just yet
				
				--Unimplemented--
				L - Lines (any number)
				SURFACE - If this tag is present, faces will be visible from both sides.
 							If this flag is absent, faces will be visible from only one side as described in the F tag.
 							
			Available Defines
			<MAT>
			<P>
			<N>
			
			--Unimplemented--
			<LINESTYLE>
			<POINTSTYLE>
			<TEXTURE>
			<TEXTURERGB>
			<TEXTURERGBA>
			<TC>
			*/
			i++;
		}
	}
}
