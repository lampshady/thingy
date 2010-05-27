package importing;

public class Face {
	private int fv1;
	private int fv2;
	private int fv3;
	private int material;
	private static int id;
	
	Face() {
		id++;
	}

	Face(int _fv1, int _fv2, int _fv3, int _material) {
		fv1 = _fv1;
		fv2 = _fv2;
		fv3 = _fv3;
		material = _material;
	}
	
	public static int getNextID() {
		return id + 1;
	}
	
	public Integer[] getFV() {
		return new Integer[]{fv1, fv2, fv3, material};
	}
	
	public int getVertex1() {
		return fv1;
	}
	public int getVertex2() {
		return fv2;
	}
	public int getVertex3() {
		return fv3;
	}
	public int getMaterial() {
		return material;
	}
}
