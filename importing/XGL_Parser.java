package importing;

import java.io.BufferedReader;
import java.io.IOException;

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
	
	int position = 0;
	public void readFile(BufferedReader f)
	{
		
		String[] temp = new String[1];
		int endTag = 0;
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
	    	this.setWorld(new World());
	    	for( position = 2; position < fileData.length; position++)
		    {
	    		tagParams = fileData[position].split(" ");
	    		for( int j = 0; j < possibleSubTags.length; j++)
	    		{
	    			if(tagParams[0].equals(possibleSubTags[j]))
			    	{
			    		//tagID = findID(tagParams);
			    		
			    		endTag = findTag("/"+possibleSubTags[j], fileData, position);
			    		temp = new String[fileData.length-position-2];
			    		System.arraycopy(fileData, position+1, temp, 0, (fileData.length-3)-position+1);
			    		
			    		break;
			    	}
	    		}
	    		
		    	if(tagParams[0].equals("MESH")) {
		    		this.getWorld().addMesh(readMesh(temp, tagParams));
		    		position=endTag;
		    	}else if (tagParams[0].equals("OBJECT")) {
		    		position++;
		    		this.getWorld().addObject(readObject(fileData, tagParams));
		    	}/*else if (tagParams[0].equals("MAT")) {
		    		world.addMaterial(readMaterial(temp, tagParams));*/
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
	private Mesh readMesh(String[] fileData, String[] tagParams)
	{	
		Mesh newMesh = new Mesh();
		String[] possibleSubTags = {"F", "P","N","MAT","PATCH","TC"};
		String[] subTagParams;
		int endTag;
		//int subTagID = 0;
		String[] tagContents = new String[1];
		newMesh.setReference(findID(tagParams));
		for( int i = 0; i < fileData.length; i++)
	    {
			if(fileData[i].equals("/MESH"))
			{
				return newMesh;
			}
			
    		subTagParams = fileData[i].split(" ");

    		//get next tag, its sub parameters, and its contents
    		for( int j = 0; j < possibleSubTags.length; j++)
    		{
    			if(subTagParams[0].equals(possibleSubTags[j]))
		    	{
		    		endTag = findTag("/"+possibleSubTags[j], fileData, i);
		    		tagContents = new String[endTag-i-1];
		    		System.arraycopy(fileData, i+1, tagContents, 0, endTag-i-1);
		    		i=endTag;
		    	}
    		}
    		
    		//Do things depending on what tag was read above
    		if(subTagParams[0].equals("F"))
			{
    			//Do nothing because this is stupid
    			System.out.print("FUcking face in a mesh");
	    		//newMesh.addFace(readFace(tagContents, subTagParams));
			}else if( subTagParams[0].equals("P"))
			{
	    		newMesh.addPoint(readPoint(tagContents, subTagParams));
			}else if (subTagParams[0].equals("N"))
			{
	    		newMesh.addNormal(readNormal(tagContents, subTagParams));
			}else if (subTagParams[0].equals("MAT"))
			{
	    		newMesh.addMaterial(readMaterial(tagContents, subTagParams));
			}else if (subTagParams[0].equals("PATCH"))
			{
				newMesh.addPatch(readPatch(tagContents, subTagParams));
			} else {System.out.print("unhandled tag in mesh\n");}
	    }
		System.out.print("Never found closing mesh tag");
		return newMesh;
	}
	
	private Patch readPatch(String[] fileData, String[] tagParams)
	{	
		Patch newPatch = new Patch();
		String[] possibleSubTags = {"F","MAT"};
		String[] subTagParams;
		int endTag;
		String[] tagContents = new String[1];
		
		newPatch.setReference(findID(tagParams));
		for( int i = 0; i < fileData.length; i++)
	    {
    		subTagParams = fileData[i].split(" ");

    		for( int j = 0; j < possibleSubTags.length; j++)
    		{
    			if(subTagParams[0].equals(possibleSubTags[j]))
		    	{
		    		endTag = findTag("/"+possibleSubTags[j], fileData, i);
		    		tagContents = new String[endTag-i-1];
		    		System.arraycopy(fileData, i+1, tagContents, 0, endTag-i-1);
		    		i=endTag;
		    	}
    		}
    		if(subTagParams[0].equals("F"))	{
	    		newPatch.addFace(readFace(tagContents, subTagParams));
			}else if (subTagParams[0].equals("MAT")){
				Material temp = readMaterial(tagContents, subTagParams);
	    		newPatch.addMaterial(temp.getReference(), temp);
			}
	    }
		
		return newPatch;
	}
	
	private Object_3D readObject(String[] fileData, String[] tagParams)
	{	
		Object_3D newObj = new Object_3D();
		String[] possibleSubTags = {"TRANSFORM","MESHREF","OBJECT"};
		String[] subTagParams;
		int endTag = 0;
		//int subTagID = 0;
		String[] temp = new String[1];
		
		newObj.setReference(findID(tagParams));
		
		for( ; position < fileData.length; position++)
	    {
			if(fileData[position].equals("/OBJECT"))
			{
				//position++; //Move past the /OBJECT
				return newObj;
			}
			
    		subTagParams = fileData[position].split(" ");

    		for( int j = 0; j < possibleSubTags.length; j++)
    		{
    			if(subTagParams[0].equals(possibleSubTags[j]))
		    	{
		    		//subTagID = findID(tagParams);
		    		
		    		endTag = findTag("/"+possibleSubTags[j], fileData, position);
		    		temp = new String[endTag-position-1];
		    		System.arraycopy(fileData, position+1, temp, 0, endTag-position-1);
		    	}
    		}
    		
    		if(subTagParams[0].equals("TRANSFORM"))
			{
	    		newObj.addTransform(readTransform(temp, subTagParams));
	    		position=endTag;
			}else if( subTagParams[0].equals("MESHREF"))
			{
	    		newObj.setMeshRef(Integer.parseInt(temp[0]));
	    		position=endTag;
			}else if( subTagParams[0].equals("OBJECT"))
			{
				position++;
				newObj.addObject(readObject(fileData, subTagParams));
			}
	    }
		
		return newObj;
	}
	
	private Transform readTransform(String[] fileData, String[] tagParams) {
		//Face newFace;
		int endTag;
		int startTag;
		Vector3f up = new Vector3f();
		Vector3f forward = new Vector3f();
		Vector3f trans_position = new Vector3f();
		float scale = 1.0f;
		boolean has_scale = false;

		//Load the Forward
		startTag = findTag("FORWARD", fileData, 0);
		endTag = findTag("/FORWARD", fileData, startTag);	
		if( startTag != -1&& startTag+1==endTag-1) {
			String[] temp = fileData[startTag+1].split(",");
			forward=new Vector3f(
					Float.parseFloat(temp[0]),
					Float.parseFloat(temp[1]),
					Float.parseFloat(temp[2])
			);
		} else { System.out.print("Transform without 'forward' found, ILLEGAL!!!"); }

		//Load the Up
		startTag = findTag("UP", fileData, 0);
		endTag = findTag("/UP", fileData, startTag);	
		if( startTag != -1 && startTag+1==endTag-1) {
			String[] temp = fileData[startTag+1].split(",");
			up=new Vector3f(
					Float.parseFloat(temp[0]),
					Float.parseFloat(temp[1]),
					Float.parseFloat(temp[2])
			);
		} else { System.out.print("Transform without 'up' found, ILLEGAL!!!"); }

		//Load the Position
		startTag = findTag("POSITION", fileData, 0);
		endTag = findTag("/POSITION", fileData, startTag);	
		if( startTag != -1&& startTag+1==endTag-1) {
			String[] temp = fileData[startTag+1].split(",");
			trans_position=new Vector3f(
					Float.parseFloat(temp[0]),
					Float.parseFloat(temp[1]),
					Float.parseFloat(temp[2])
			);
		} else { System.out.print("Transform without 'position' found, ILLEGAL!!!"); }

		//Load the Scale
		startTag = findTag("SCALE", fileData, 0);
		endTag = findTag("/SCALE", fileData, startTag);	
		if( startTag != -1&& startTag+1==endTag-1) {
			String[] temp = fileData[startTag+1].split(",");
			trans_position=new Vector3f(
					Float.parseFloat(temp[0]),
					Float.parseFloat(temp[1]),
					Float.parseFloat(temp[2])
			);
			has_scale = true;
		}
		
		if(has_scale) {
			return new Transform(forward,up,trans_position,scale);
		}else {
			return new Transform(forward,up,trans_position);
		}
	}
	
	private Face readFace(String[] fileData, String[] tagParams)
	{
		
		Face newFace = new Face();
		int endTag;
		int startTag;
		int position;
		
		/*
		//Load the Material
		startTag = findTag("MAT", fileData, 0);
		endTag = findTag("\\MAT", fileData, startTag);
		if( startTag != -1)
		{
			subTagParams = fileData[startTag].split(" ");
			//subTagID = findID(subTagParams);
			
			temp = new String[endTag-startTag-1];
			System.arraycopy(fileData, startTag+1, temp, 0, endTag-startTag-1);
			readMaterial(temp, subTagParams);
		}
		*/
		
		//Load the Material
		startTag = findTag("MATREF", fileData, 0);
		endTag = findTag("/MATREF", fileData, startTag);
		if( startTag != -1 && startTag+1 == endTag-1) {
			newFace.setMaterial(Integer.parseInt(fileData[startTag+1]));
		}else
		{
			System.out.print("No Matref in face.  Goddamn");
		}
		
		for(int i = 1; i <= 3; i++)
		{
			startTag = findTag("FV" + i, fileData, 0);
			endTag = findTag("/FV" + i, fileData, startTag);
			
			/*position = findTag("P", fileData, startTag) + 1;
			if( position < endTag && position != 1 )
			{
				fileData[position].replace(" ", "");
				temp = fileData[position].split(",");
				points.add( new Vector3f(Float.parseFloat(temp[0]), Float.parseFloat(temp[1]),Float.parseFloat(temp[2])) );
			}else
			{*/
				position = findTag("PREF", fileData, startTag) + 1;
				if(position < endTag && position != 0)
				{
					fileData[position].replace(" ", "");
					newFace.addPoint(Integer.parseInt(fileData[position]));
				}else
				{
					System.out.print("Fessed up FV Tag. Point");
				}
			//}
			
			/*position = findTag("N", fileData, startTag) + 1;
			if( position < endTag && position != 1 )
			{
				fileData[position].replace(" ", "");
				temp = fileData[position].split(",");
				norms.add( new Vector3f(Float.parseFloat(temp[0]), Float.parseFloat(temp[1]),Float.parseFloat(temp[2])) );
			}else
			{*/
				position = findTag("NREF", fileData, startTag) + 1;
				if(position < endTag && position != 0)
				{
					fileData[position].replace(" ", "");
					newFace.addNormal(Integer.parseInt(fileData[position]));
				}else
				{
					System.out.print("Fessed up FV Tag. Normal");
				}
			//}
		}
		
		//Stuff for Textures goes here
		
		//Stuff for Shader Groups goes here
		
		//Create new Face
		return newFace;
	}
	
	private Point readPoint(String[] fileData, String[] tagParams) 
	{
		Point newPoint = new Point();
		String[] temp = new String[3];
		
		if( fileData.length == 1)
		{
			fileData[0].replace(" ", "");
			
			temp = fileData[0].split(",");
			
			newPoint.setReference(findID(tagParams));
			newPoint.setPosition(
					new Vector3f(
						Float.parseFloat(temp[0]),
						Float.parseFloat(temp[1]),
						Float.parseFloat(temp[2])
					)
			);
		}else
		{
			System.out.print("Tag within a P tag.");
		}
		return newPoint;
	}
	
	private Material readMaterial(String[] fileData, String[] tagParams) 
	{
		String[] possibleSubTags = {"AMB", "DIFF", "SPEC", "EMISS", "ALPHA", "SHINE"};
		Material newMat = new Material();
		int endTag;
		String[] tagContents = null;
		
		String[] subTagParams;
		
		
		newMat.setReference(findID(tagParams));
		
		for( int i = 0; i < fileData.length; i++)
	    {
    		subTagParams = fileData[i].split(" ");

    		for( int j = 0; j < possibleSubTags.length; j++)
    		{
    			if(subTagParams[0].equals(possibleSubTags[j]))
		    	{
		    		//subTagID = findID(tagParams);
		    		
		    		endTag = findTag("/"+possibleSubTags[j], fileData, i);
		    		tagContents = new String[endTag-i-1];
		    		System.arraycopy(fileData, i+1, tagContents, 0, endTag-i-1);
		    		i=endTag;
		    	}
    		}
    		
    		if(subTagParams[0].equals("AMB"))
			{
	    		newMat.setAmbient(readVector(tagContents[0]));
			}else if(subTagParams[0].equals("DIFF"))
			{
	    		newMat.setDiffuse(readVector(tagContents[0]));
			}else if(subTagParams[0].equals("SPEC"))
			{
	    		newMat.setSpecular(readVector(tagContents[0]));
			}else if(subTagParams[0].equals("EMISS"))
			{
	    		newMat.setEmission(readVector(tagContents[0]));
			}else if(subTagParams[0].equals("ALPHA"))
			{
	    		newMat.setAlpha(Float.parseFloat(tagContents[0]));
			}else if(subTagParams[0].equals("SHINE"))
			{
	    		newMat.setShine(Float.parseFloat(tagContents[0]));
			}
	    }
		
		
		return newMat;
	}

	private Normal readNormal(String[] fileData, String[] tagParams) 
	{
		Normal newNormal = new Normal();
		String[] temp = new String[3];
		
		if( fileData.length == 1)
		{
			fileData[0].replace(" ", "");
			
			temp = fileData[0].split(",");
			
			newNormal.setReference(findID(tagParams));
			newNormal.setDirection(
					new Vector3f(
						Float.parseFloat(temp[0]),
						Float.parseFloat(temp[1]),
						Float.parseFloat(temp[2])
					)
			);
		}else
		{
			System.out.print("Tag within a P tag.");
		}
		return newNormal;
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
			if(temp[0].equals("ID") || temp[0].equals("PATHID") || temp[0].equals("PATCHID"))
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
	
	private Vector3f readVector(String tagContents)
	{
		String[] temp = tagContents.split(",");
		
		return new Vector3f(
			Float.parseFloat(temp[0]), 
			Float.parseFloat(temp[1]), 
			Float.parseFloat(temp[2])
		);
	}
}
