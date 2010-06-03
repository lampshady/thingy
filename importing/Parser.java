package importing;

import java.io.BufferedReader;

public abstract class Parser {
	public abstract void readFile(BufferedReader f);
	private World world;
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	public World getWorld() {
		return world;
	}
	//public abstract ArrayList<Face> getFaces();

}
