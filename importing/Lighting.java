package importing;

public class Lighting {
	private Ambient_Light ambient;
	private Directional_Light dlight;
	
	public Lighting(Ambient_Light amb, Directional_Light dl) {
		ambient = amb;
		dlight = dl;
	}
	
	public Ambient_Light getAmbientLight() {
		return ambient;
	}
	
	public Directional_Light getDirectionLight() {
		return dlight;
	}
}
