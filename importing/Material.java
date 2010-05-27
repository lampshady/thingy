package importing;

import javax.vecmath.Vector3f;

public class Material {
	//For all of these:  x = red, y = green, b = blue
	Vector3f ambientReflect; 	
	Vector3f diffuseReflect;	
	Vector3f specularReflect;	//default 0.0f, 0.0f, 0.0f
	Vector3f emission; //default 0.0f, 0.0f, 0.0f
	float alpha; //default 1.0f
	float shine; //default 0.0f
	static int id;
	
	public Material( Vector3f ambient, Vector3f diffuse)
	{
		ambientReflect = ambient;
		diffuseReflect = diffuse;
		
		specularReflect = new Vector3f(0,0,0);
		
		emission = new Vector3f(0,0,0);
		
		alpha = 1;
		
		shine = 0;
		
		id++;
	}
	
	public Material( Vector3f ambient, Vector3f diffuse, Vector3f specular)
	{
		ambientReflect = ambient;
		diffuseReflect = diffuse;
		
		if( specular != null)
			specularReflect = specular;
		else
			specularReflect = new Vector3f(0,0,0);
		
		emission = new Vector3f(0,0,0);
		
		alpha = 1;
		
		shine = 0;
		
		id++;
	}
	
	public Material( Vector3f ambient, Vector3f diffuse, Vector3f specular, Vector3f emis)
	{
		ambientReflect = ambient;
		diffuseReflect = diffuse;
		
		if( specular != null)
			specularReflect = specular;
		else
			specularReflect = new Vector3f(0,0,0);
		
		if( emis != null)
			emission = emis;
		else
			emission = new Vector3f(0,0,0);
		
		alpha = 1;
		
		shine = 0;
		
		id++;
	}
	
	public Material( Vector3f ambient, Vector3f diffuse, Vector3f specular, Vector3f emis, float a)
	{
		ambientReflect = ambient;
		diffuseReflect = diffuse;
		
		if( specular != null)
			specularReflect = specular;
		else
			specularReflect = new Vector3f(0,0,0);
		
		if( emis != null)
			emission = emis;
		else
			emission = new Vector3f(0,0,0);
		
		if( !Float.isNaN(a) )
			alpha = a;
		else
			alpha = 1;
		
		shine = 0;
		
		id++;
	}
	
	public Material( Vector3f ambient, Vector3f diffuse, Vector3f specular, Vector3f emis, float a, float s)
	{
		ambientReflect = ambient;
		diffuseReflect = diffuse;
		
		if( specular != null)
			specularReflect = specular;
		else
			specularReflect = new Vector3f(0,0,0);
		
		if( emis != null)
			emission = emis;
		else
			emission = new Vector3f(0,0,0);
		
		if( !Float.isNaN(a) )
			alpha = a;
		else
			alpha = 1;
		
		if( !Float.isNaN(s) )
			shine = s;
		else
			shine = 0;
			
		id++;
	}
	
	public Vector3f ambientReflect() {
		return ambientReflect;
	}
	public Vector3f diffuseReflect() {
		return diffuseReflect;
	}	
	public Vector3f specularReflect() {
		return specularReflect;
	}
	public Vector3f emission() {
		return emission;
	}
	public float alpha() {
		return alpha;
	}
	public float shine() {
		return shine;
	}
	
	public static int getNextID() {
		return id + 1;
	}
}
