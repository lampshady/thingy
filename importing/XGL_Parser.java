package importing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
	
	void readFile(String filename)
	{
		try {
			BufferedReader file = new FileReader(filename);
			
			String data = "";
			String temp;
			while(temp = file.read())
			{
				trim( temp );
				data += temp;
			}
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
