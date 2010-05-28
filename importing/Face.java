package importing;

import java.util.ArrayList;

public class Face {
	private ArrayList<Integer> pref;
	private ArrayList<Integer> nref;
	private int material;
	private static int id;
	
	Face() {
		id++;
	}

	Face(ArrayList<Integer> _pref, ArrayList<Integer> _nref, int _material) {
		pref = _pref;
		nref = _nref;
		material = _material;
	}
	
	public static int getNextID() {
		return id + 1;
	}
	
	public ArrayList<Integer> getPointRefs() {
		return pref;
	}
	
	public ArrayList<Integer> getNormalRefs() {
		return nref;
	}

	public int getMaterial() {
		return material;
	}
}
