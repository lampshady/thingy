package importing;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.vecmath.Vector3f;

public class XGL_Parser extends Parser{
	/*
	ArrayList<Material> matList;
	ArrayList<Ambient_Light> ambLightList;
	ArrayList<Directional_Light> dirLightList;
	ArrayList<Texture> textureListAlpha;
	ArrayList<Texture> textureListNoAlpha;
	*/
	//HashMap<Integer, Vector3f> vertices = new HashMap<Integer, Vector3f>();
	//HashMap<Integer, Vector3f> normals = new HashMap<Integer, Vector3f>();
	//ArrayList<Face> faces = new ArrayList<Face>();
	World world;
	
	
	//Do nothing in the contructor  BLAH, BLAH I SAY
	XGL_Parser(){
		
	}
	
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
	public void readFile(BufferedReader f)
	{
		
		String[] temp = new String[1];
		int endTag;
		String[] tagParams;
		//int tagID = 0;
		String tempData;
		String[] fileData;
		String[] possibleSubTags = {"MESH", "MAT", "OBJECT", "LINESTYLE", 
				"POINTSTYLE", "TEXTURE", "TEXTURERGB", "TEXTURERGBA", "TC"};
		
	    tempData = convertBufferToString(f);
	    
	    tempData = tempData.toUpperCase();
	    tempData.replace("\n", "");
	    //tempData.replace(" ", "");
	    
	    fileData = tempData.split("(\\s*>\\s*<\\s*)|(\\s*>\\s*)|(\\s*<\\s*)");
	    
	    if( fileData[1].equals("WORLD")){
	    	world = new World();
	    	for( int i = 2; i < fileData.length; i++)
		    {
	    		tagParams = fileData[i].split(" ");
	    		for( int j = 0; j < possibleSubTags.length; j++)
	    		{
	    			if(tagParams[0].equals(possibleSubTags[j]))
			    	{
			    		//tagID = findID(tagParams);
			    		
			    		endTag = findTag("/"+possibleSubTags[j], fileData, i);
			    		temp = new String[endTag-i-1];
			    		System.arraycopy(fileData, i+1, temp, 0, endTag-i-1);
			    		i=endTag;
			    		break;
			    	}
	    		}
	    		
		    	if(tagParams[0].equals("MESH")) {
		    		readMesh(temp, tagParams);
		    	}else if (tagParams[0].equals("MAT")) {
		    		readMaterial(temp, tagParams);
		    	}else if (tagParams[0].equals("OBJECT")) {
		    		readObject(temp, tagParams);
		    	} else if (tagParams[0].equals("PATCH")) {
		    		readPatch(temp, tagParams);
		    	}
		    	
		    }
	    }else{
	    	System.out.print("File does not start with <WORLD> tag");
	    }
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
		String[] temp = new String[1];
		
		for( int i = 0; i < fileData.length; i++)
	    {
    		subTagParams = fileData[i].split(" ");

    		for( int j = 0; j < possibleSubTags.length; j++)
    		{
    			if(subTagParams[0].equals(possibleSubTags[j]))
		    	{
		    		//subTagID = findID(tagParams);
		    		
		    		endTag = findTag("/"+possibleSubTags[j], fileData, i);
		    		temp = new String[endTag-i-1];
		    		System.arraycopy(fileData, i+1, temp, 0, endTag-i-1);
		    		i=endTag;
		    	}
    		}
    		
    		if(subTagParams[0].equals("F"))
			{
	    		readFace(temp, subTagParams);
			}else if( subTagParams[0].equals("P"))
			{
	    		readPoint(temp, subTagParams);
			}else if (subTagParams[0].equals("N"))
			{
	    		readNormal(temp, subTagParams);
			}else if (subTagParams[0].equals("MAT"))
			{
	    		readMaterial(temp, subTagParams);
			}
	    }
	}
	
	private void readPatch(String[] fileData, String[] tagParams)
	{	
		String[] possibleSubTags = {"F","MAT"};
		String[] subTagParams;
		int endTag;
		//int subTagID = 0;
		String[] temp = new String[1];
		
		for( int i = 0; i < fileData.length; i++)
	    {
    		subTagParams = fileData[i].split(" ");

    		for( int j = 0; j < possibleSubTags.length; j++)
    		{
    			if(subTagParams[0].equals(possibleSubTags[j]))
		    	{
		    		//subTagID = findID(tagParams);
		    		
		    		endTag = findTag("/"+possibleSubTags[j], fileData, i);
		    		temp = new String[endTag-i-1];
		    		System.arraycopy(fileData, i+1, temp, 0, endTag-i-1);
		    		i=endTag;
		    	}
    		}
    		
    		if(subTagParams[0].equals("F"))	{
	    		readFace(temp, subTagParams);
			}else if (subTagParams[0].equals("MAT")){
	    		readMaterial(temp, subTagParams);
			}
	    }
	}
	
	private void readObject(String[] fileData, String[] tagParams)
	{	
		String[] possibleSubTags = {"TRANSFORM","MESHREF","OBJECT"};
		String[] subTagParams;
		int endTag;
		//int subTagID = 0;
		String[] temp = new String[1];
		
		for( int i = 0; i < fileData.length; i++)
	    {
    		subTagParams = fileData[i].split(" ");

    		for( int j = 0; j < possibleSubTags.length; j++)
    		{
    			if(subTagParams[0].equals(possibleSubTags[j]))
		    	{
		    		//subTagID = findID(tagParams);
		    		
		    		endTag = findTag("/"+possibleSubTags[j], fileData, i);
		    		temp = new String[endTag-i-1];
		    		System.arraycopy(fileData, i+1, temp, 0, endTag-i-1);
		    		i=endTag;
		    	}
    		}
    		
    		if(subTagParams[0].equals("TRANSFORM"))
			{
	    		readTransform(temp, subTagParams);
			}else if( subTagParams[0].equals("MESHREF"))
			{
	    		readMeshRef(temp, subTagParams);
			}else if( subTagParams[0].equals("OBJECT"))
			{
				readObject(temp, subTagParams);
			}
	    }
	}
	
	private Transform readTransform(String[] fileData, String[] tagParams) {
		//Face newFace;
		int endTag;
		int startTag;
		String[] temp;
		Vector3f up = new Vector3f();
		Vector3f forward = new Vector3f();
		Vector3f trans_position = new Vector3f();
		Vector3f scale = new Vector3f();
		boolean has_scale = false;

		//Load the Forward
		startTag = findTag("FORWARD", fileData, 0);
		endTag = findTag("/FORWARD", fileData, startTag);	
		if( startTag != 0) {
			temp = new String[endTag-startTag-1];
			System.arraycopy(fileData, startTag+1, temp, 0, endTag-startTag-1);
			forward=new Vector3f(Float.parseFloat(temp[0]),Float.parseFloat(temp[1]),Float.parseFloat(temp[2]));
		} else { System.out.print("Transform without 'forward' found, ILLEGAL!!!"); }

		//Load the Up
		startTag = findTag("UP", fileData, 0);
		endTag = findTag("/UP", fileData, startTag);	
		if( startTag != 0) {
			temp = new String[endTag-startTag-1];
			System.arraycopy(fileData, startTag+1, temp, 0, endTag-startTag-1);
			up=new Vector3f(Float.parseFloat(temp[0]),Float.parseFloat(temp[1]),Float.parseFloat(temp[2]));
		} else { System.out.print("Transform without 'up' found, ILLEGAL!!!"); }

		//Load the Position
		startTag = findTag("POSITION", fileData, 0);
		endTag = findTag("/POSITION", fileData, startTag);	
		if( startTag != 0) {
			temp = new String[endTag-startTag-1];
			System.arraycopy(fileData, startTag+1, temp, 0, endTag-startTag-1);
			trans_position=new Vector3f(Float.parseFloat(temp[0]),Float.parseFloat(temp[1]),Float.parseFloat(temp[2]));
		} else { System.out.print("Transform without 'position' found, ILLEGAL!!!"); }

		//Load the Scale
		startTag = findTag("SCALE", fileData, 0);
		endTag = findTag("/SCALE", fileData, startTag);	
		if( startTag != 0) {
			temp = new String[endTag-startTag-1];
			System.arraycopy(fileData, startTag+1, temp, 0, endTag-startTag-1);
			scale=new Vector3f(Float.parseFloat(temp[0]),Float.parseFloat(temp[1]),Float.parseFloat(temp[2]));
			has_scale = true;
		}
		if(has_scale) {
			return new Transform(forward,up,trans_position,scale);
		}else {
			return new Transform(forward,up,trans_position);
		}
	}
	
	private int readMeshRef(String[] fileData, String[] tagParams) {
			//Face newFace;
			int endTag;
			int startTag;
			String[] temp;
			int meshref=0;

			//Load the Forward
			startTag = findTag("MESHREF", fileData, 0);
			endTag = findTag("/MESHREF", fileData, startTag);	
			if( startTag != 0) {
				temp = new String[endTag-startTag-1];
				System.arraycopy(fileData, startTag+1, temp, 0, endTag-startTag-1);
				meshref=Integer.parseInt(temp[0]);
			} else { System.out.print("Meshref without parent found, ILLEGAL!!!"); }

			//Create new transform
			return meshref;	
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
			
		if( startTag != 0)
		{
			subTagParams = fileData[startTag].split(" ");
			//subTagID = findID(subTagParams);
			
			temp = new String[endTag-startTag-1];
			System.arraycopy(fileData, startTag+1, temp, 0, endTag-startTag-1);
			readMaterial(temp, subTagParams);
		}
		
		for(int i = 1; i <= 3; i++)
		{
			startTag = findTag("FV" + i, fileData, 0);
			endTag = findTag("/FV" + i, fileData, startTag);
			
			position = findTag("P", fileData, startTag) + 1;
			if( position < endTag && position != 1 )
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
			if( position < endTag && position != 1 )
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
		faces.add(new Face());
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
			if(data[i].split(" ")[0].equals(tag))
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
			if(temp[0].equals("ID"))
			{
				return Integer.parseInt(temp[1].replace("\"", ""));
			}
		}
		return -1;
	}
	
	private String convertBufferToString(BufferedReader buffer)
	{
		//Apparently using string buffers is significantly faster than just concatenating strings.  So says the Internet. Whatever.
		String temp;
		StringBuffer data = new StringBuffer();
		
		try {
			while( (temp = buffer.readLine()) != null)
			{
				data.append(temp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return data.toString();
		
	}
}
