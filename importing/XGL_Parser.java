package importing;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.vecmath.Vector3f;

public class XGL_Parser {
	/*
	ArrayList<Material> matList;
	ArrayList<Ambient_Light> ambLightList;
	ArrayList<Directional_Light> dirLightList;
	ArrayList<Texture> textureListAlpha;
	ArrayList<Texture> textureListNoAlpha;
	*/
	HashMap<Integer, Vector3f> vertices;
	HashMap<Integer, Vector3f> normals;
	ArrayList<Face> faces;
	
	//Do nothing in the contructor  BLAH, BLAH I SAY
	XGL_Parser(){}
	
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
	public void readFile(String filePath)
	{
		String[] temp = null;
		int endTag;
		String[] tagParams;
		//int tagID = 0;
		String tempData;
		String[] fileData;
		String[] possibleSubTags = {"MESH", "MAT", "OBJECT", "LINESTYLE", 
				"POINTSTYLE", "TEXTURE", "TEXTURERGB", "TEXTURERGBA", "TC"};
		
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
		
		
	    tempData = new String(buffer);
	    
	    tempData = tempData.toUpperCase();
	    tempData.replace("\n", "");
	    //tempData.replace(" ", "");
	    
	    fileData = tempData.split("<|>");
	    
	    if( fileData[0] == "WORLD"){
	    	for( int i = 1; i < fileData.length; i++)
		    {
	    		tagParams = fileData[i].split(" ");
	    		for( int j = 0; i < possibleSubTags.length; i++)
	    		{
	    			if(tagParams[0] == possibleSubTags[j])
			    	{
			    		//tagID = findID(tagParams);
			    		
			    		endTag = findTag("/"+possibleSubTags[j], fileData, i);
			    		temp = new String[endTag-i-1];
			    		System.arraycopy(fileData, i+1, temp, 0, endTag-i-1);
			    		i=endTag;
			    	}
	    		}
	    		
		    	if(tagParams[0] == "MESH")
		    	{
		    		readMesh(temp, tagParams);
		    	}else if (tagParams[0] == "MAT")
		    	{
		    		readMaterial(temp, tagParams);
		    	}
		    }
	    }else{
	    	System.out.print("File does not start with <WORLD> tag");
	    }
	}

	public ArrayList<Face> getFaces()
	{
		return faces;
	}
	
	
	/*
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
	private void readMesh(String[] fileData, String[] tagParams)
	{	
		String[] possibleSubTags = {"F","P","N","MAT"};
		String[] subTagParams;
		int endTag;
		//int subTagID = 0;
		String[] temp = null;
		
		for( int i = 1; i < fileData.length; i++)
	    {
    		subTagParams = fileData[i].split(" ");
    		for( int j = 0; i < possibleSubTags.length; i++)
    		{
    			if(tagParams[0] == possibleSubTags[j])
		    	{
		    		//subTagID = findID(tagParams);
		    		
		    		endTag = findTag("/"+possibleSubTags[j], fileData, i);
		    		temp = new String[endTag-i-1];
		    		System.arraycopy(fileData, i+1, temp, 0, endTag-i-1);
		    		i=endTag;
		    	}
    		}
    		
    		if(subTagParams[0] == "F")
			{
	    		readFace(temp, subTagParams);
			}else if( subTagParams[0] == "P")
			{
	    		readPoint(temp, subTagParams);
			}else if (subTagParams[0] == "N")
			{
	    		readNormal(temp, subTagParams);
			}else if (subTagParams[0] == "MAT")
			{
	    		readMaterial(temp, subTagParams);
			}
	    }
	}
	
	private void readFace(String[] fileData, String[] tagParams)
	{
		//Face newFace;
		int endTag;
		int startTag;
		int position;
		//int subTagID;
		String[] temp;
		ArrayList<Vector3f> points = new ArrayList<Vector3f>();
		ArrayList<Vector3f> norms = new ArrayList<Vector3f>();
		String[] subTagParams;
		
		//Load the Material
		
		startTag = findTag("MAT", fileData, 0);
		endTag = findTag("\\MAT", fileData, startTag);
		
		subTagParams = fileData[startTag].split(" ");
		//subTagID = findID(subTagParams);
		
		temp = new String[endTag-startTag-1];
		System.arraycopy(fileData, startTag+1, temp, 0, endTag-startTag-1);
		readMaterial(temp, subTagParams);
		
		for(int i = 0; i < 3; i++)
		{
			startTag = findTag("FV" + i, fileData, 0);
			endTag = findTag("\\FV" + i, fileData, startTag);
			
			position = findTag("P", fileData, startTag) + 1;
			if( position < endTag && position != 0 )
			{
				fileData[position].replace(" ", "");
				temp = fileData[position].split(",");
				points.add( new Vector3f(Float.parseFloat(temp[0]), Float.parseFloat(temp[1]),Float.parseFloat(temp[2])) );
			}else
			{
				position = findTag("PREF", fileData, startTag) + 1;
				if(position < endTag && position != 0)
				{
					fileData[position].replace(" ", "");
					points.add(vertices.get(Integer.parseInt(fileData[position])));
				}else
				{
					System.out.print("Fessed up FV Tag. Point");
				}
			}
			
			position = findTag("N", fileData, startTag) + 1;
			if( position < endTag && position != 0 )
			{
				fileData[position].replace(" ", "");
				temp = fileData[position].split(",");
				norms.add( new Vector3f(Float.parseFloat(temp[0]), Float.parseFloat(temp[1]),Float.parseFloat(temp[2])) );
			}else
			{
				position = findTag("NREF", fileData, startTag) + 1;
				if(position < endTag && position != 0)
				{
					fileData[position].replace(" ", "");
					norms.add(vertices.get(Integer.parseInt(fileData[position])));
				}else
				{
					System.out.print("Fessed up FV Tag. Normal");
				}
			}
		}
		
		//Stuff for Textures goes here
		
		//Stuff for Shader Groups goes here
		
		//Create new Face
		faces.add(new Face(points, null, norms));
	}
	
	private void readPoint(String[] fileData, String[] tagParams) 
	{
		String[] temp = new String[3];
		
		if( fileData.length == 1)
		{
			fileData[0].replace(" ", "");
			
			temp = fileData[0].split(",");
			
			vertices.put(findID(tagParams), new Vector3f(
					Float.parseFloat(temp[0]),
					Float.parseFloat(temp[1]),
					Float.parseFloat(temp[2])
			));
		}else
		{
			System.out.print("Tag within a P tag.");
		}
	}
	
	private void readMaterial(String[] temp, String[] tagParams) 
	{
		
	}

	private void readNormal(String[] fileData, String[] tagParams) 
	{
		String[] temp = new String[3];
		
		if( fileData.length == 1)
		{
			fileData[0].replace(" ", "");
			
			temp = fileData[0].split(",");
			
			normals.put(findID(tagParams), new Vector3f(
					Float.parseFloat(temp[0]),
					Float.parseFloat(temp[1]),
					Float.parseFloat(temp[2])
			));
		}else
		{
			System.out.print("Tag within a P tag.");
		}
	}
	
	private int findTag(String tag, String[] data, int startAt)
	{
		int temp = 0;
		for(int i = startAt; i < data.length; i++)
		{
			if(data[i].split(" ")[0] == tag)
			{
				temp = i;
				break;
			}
		}
		return temp;
	}
	
	private int findID(String[] data)
	{
		String[] temp;
		for( String s : data)
		{
			temp = s.split("=");
			if(temp[0] == "ID")
			{
				return Integer.parseInt(temp[1]);
			}
		}
		return -1;
	}
}
