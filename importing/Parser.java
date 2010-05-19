package importing;

import java.io.BufferedReader;
import java.util.ArrayList;

public abstract class Parser {
	public abstract void readFile(BufferedReader f);

	public abstract ArrayList<Face> getFaces();
}
