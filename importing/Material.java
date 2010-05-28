package importing;

import javax.vecmath.Vector3f;

public class Material {
	//For all of these:  x = red, y = green, b = blue
	private Vector3f ambientReflect; 	
	private Vector3f diffuseReflect;	
	private Vector3f specularReflect;	//default 0.0f, 0.0f, 0.0f
	private Vector3f emission; //default 0.0f, 0.0f, 0.0f
	private float alpha; //default 1.0f
	private float shine; //default 0.0f
	private static int id;
	
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
	
	public Vector3f getAmbientReflect() {
		return ambientReflect;
	}
	public Vector3f getDiffuseReflect() {
		return diffuseReflect;
	}	
	public Vector3f getSpecularReflect() {
		return specularReflect;
	}
	public Vector3f getEmission() {
		return emission;
	}
	public float getAlpha() {
		return alpha;
	}
	public float getShine() {
		return shine;
	}
	
	public static int getNextID() {
		return id + 1;
	}
}
