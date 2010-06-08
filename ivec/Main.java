package ivec;
import importing.FileLoader;
import importing.XGLParserNew;

import java.applet.Applet;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;

import java.net.URL;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import renderer.Renderer;


public class Main extends Applet {
	DisassemblyMenu popup;
	Canvas GLView;
	HashMap<String, Integer> model_hash;
	String displayed_model;
	Camera camera;
	Input input;
	Renderer render;
	TextureList texture;
	EntityList entity;
	
	public final static  int width = 640;
	public final static int height = 776;
	
	static int popup_width = 320;
	static int popup_height = 776;
	
	static int button_width = 2;
	static int button_height = 776;
	
	JButton expand_button;
	Boolean tree_expanded = true;
	
	String[] fileList =	{ 
		"0335-CATHODE_ASSEMBLY",
		"0877-CATHODE_PELLET",
		"8133-MoRu_BRAZE",
		"1266-BODY",
		"0335-REMACHINE_1",
		"8102-POTTING",
		"9161-HEATER",
		"8134-MoRuNi_10_BRAZE",
		"1729-SLEEVE",
		"2903-HEAT_SHIELD",
		"2903-1-HEAT_SHIELD",
		"2173-MOUNTING_ADAPTER",
		"8139-NICORO_CuAuNi_BRAZE",
		"8134ground-MoRuNi_10_BRAZE",
		"0335-REMACHINE_2",
		"1730-SLEEVE",
		"2785-INSULATOR"
	};

	private static Main uniqueInstance = new Main();
	
	public Main(){}
	
	public static Main getInstance()
	{
		return uniqueInstance;
		
	}
	
	
	public static void main(String args[])
	{
		Applet applet = new Main();
		Frame frame = new JFrame();
		
		frame.add(applet);
		frame.setSize(width,height);
		frame.setVisible(true);
		applet.init();
	}
	
	
	private static final long serialVersionUID = 1L;
	
	public void init() {
		try{
			boolean isRunning = true;
			model_hash = new HashMap<String, Integer>();
			
			setupComponents();
			
			//Load Files		
			//String folder = "http://192.168.143.17/ivec/lib/Models/";
			//String path;

			//Put in Loading Label
			GLView.setVisible(false);
			popup.setVisible(false);
			
			
			
			//Image img = ImageIO.read();
			Icon asdf = new ImageIcon(new URL("http://192.168.143.17/ivec/images/loading.gif"));
			JLabel loadingImage = new JLabel(asdf);
			loadingImage.setSize(width, height);
			
			this.add(loadingImage);
			loadingImage.setVisible(true);
			
			this.repaint();
			
			/*for( int i = 0; i < fileList.length; i++ )
			{
				path = folder + fileList[i] + ".obj";
				model_hash.put(fileList[i], i);
				
				Display.makeCurrent();
				BufferedReader objfile = new BufferedReader( 
					new InputStreamReader(
							//new FileInputStream( path )
							new URL( path ).openStream()
					)
				);
					
				new ObjParser(objfile,true,i);	
				
				displayed_model=fileList[0];
			
				Display.releaseContext();
			}*/
			
			this.remove(loadingImage);
			GLView.setVisible(true);
			popup.setVisible(true);
			popup.revalidate();
			this.repaint();
			
			String filePath = "./lib/10000111.xgl";
			int drawID = FileLoader.loadFile(filePath);
			
			//showDisassemblyMenu();
			while (isRunning) {				
				//read keyboard and mouse
				input.handleMouse();

				//Draw world
				//render.draw((int)model_hash.get(displayed_model));
				render.draw(drawID);
				//render.draw(1);
				
				//Check if it's time to close
				if (Display.isCloseRequested()) {
					isRunning=false;
				}
			}
		} catch(Exception e) {
			//System.out.print("\nError Occured.  Exiting." + e.toString());
			e.printStackTrace();
			//System.exit(-1);
		}
	}
	
	/*
	public void showDisassemblyMenu() throws LWJGLException
	{
		//GLView.setBounds(-1 * (popup_width+expand_button.getWidth()), 0, width-expand_button.getWidth(), height);
		GLView.setBounds(0, 0, width-popup.getWidth()-expand_button.getWidth(), height);
		popup.setBounds(width-popup_width, 0, popup_width, height);
		expand_button.setBounds((width-popup_width)-expand_button.getWidth(), 0, button_width, button_height);
		
		Display.makeCurrent();
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(45.0f, (float) Main.width / (float) Main.height, 0.01f, 100.0f);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		Display.releaseContext();
		this.repaint();
		popup.revalidate();
		
		//Stupid resize hack to get it to display as an Applet
		this.resize(width, 481);
		this.resize(width, height);
	}
	
	
	public void hideDisassemblyMenu( String clicked )
	{
		GLView.setBounds(0, 0, width-expand_button.getWidth(), height);
		popup.setBounds(GLView.getWidth()+expand_button.getWidth(), 0, width, height);
		expand_button.setBounds(GLView.getWidth(), 0, button_width, button_height);
		
		this.repaint();
		popup.revalidate();
		this.resize(width, height + 1);
		this.resize(width, height);
		//If model was selected
		if(!clicked.equals("")) {
			//change model
			this.displayed_model = clicked;
		} else {
			//If no model has been selected pick the root
			//Else leave it alone
			if(this.displayed_model.equals("")) {
				this.displayed_model = fileList[0];
			}
		}
	}*/
	
	
	/*
	public void showDisassemblyMenu() throws LWJGLException
	{
		GLView.setBounds(
				0
				, 0
				, GLView.getWidth()
				, GLView.getHeight()
		);
		expand_button.setBounds(
				16000, 
				0, 
				expand_button.getWidth(), 
				expand_button.getHeight()
		);
		popup.setBounds(
				800, 
				0, 
				popup.getWidth(), 
				popup.getHeight()
		);
		
		this.repaint();
		popup.revalidate();
		
		//Stupid resize hack to get it to display as an Applet
		this.resize(width, 481);
		this.resize(width, height);
	}
	
	
	public void hideDisassemblyMenu( String clicked )
	{
		GLView.setBounds(
				0,
				0,
				GLView.getWidth(),
				GLView.getHeight()
		);
		expand_button.setBounds(
				GLView.getWidth(), 
				0, 
				expand_button.getWidth(), 
				expand_button.getHeight()
		);
		popup.setBounds(
				width+expand_button.getWidth(),
				0, 
				popup.getWidth(), 
				popup.getHeight()
		);
		
		
		this.repaint();
		popup.revalidate();
		this.resize(width, height + 1);
		this.resize(width, height);
		//If model was selected
		if(!clicked.equals("")) {
			//change model
			this.displayed_model = clicked;
		} else {
			//If no model has been selected pick the root
			//Else leave it alone
			if(this.displayed_model.equals("")) {
				this.displayed_model = fileList[0];
			}
		}
	}
	*/
	
	public void showDisassemblyMenu() throws LWJGLException
	{
		GLView.setBounds(
				800,
				0,
				width, 
				height
		);

		popup.setBounds(
				0, 
				0, 
				width, 
				height
		);
		
		this.repaint();
		popup.revalidate();
		
		//Stupid resize hack to get it to display as an Applet
		this.resize(width, 481);
		this.resize(width, height);
	}
	
	
	public void hideDisassemblyMenu( String clicked )
	{
		GLView.setBounds(
				0,
				0,
				GLView.getWidth(),
				GLView.getHeight()
		);
		popup.setBounds(
				800,
				0, 
				popup.getWidth(), 
				popup.getHeight()
		);
		
		
		this.repaint();
		popup.revalidate();
		this.resize(width, height + 1);
		this.resize(width, height);
		//If model was selected
		if(!clicked.equals("")) {
			//change model
			this.displayed_model = clicked;
		} else {
			//If no model has been selected pick the root
			//Else leave it alone
			if(this.displayed_model.equals("")) {
				this.displayed_model = fileList[0];
			}
		}
	}
	
	
	public void setupComponents() throws LWJGLException
	{
		//Create buttons and listeners for expanding tree
		/*
		expand_button = new JButton(">");
		expand_button.setPreferredSize(new Dimension(25,this.getHeight()));
		expand_button.setVisible(true);
		this.add(expand_button);
		expand_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!tree_expanded) {
					try {
						showDisassemblyMenu();
					} catch (LWJGLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					tree_expanded = true;
					expand_button.setText(">");
				
				} else {
					hideDisassemblyMenu("");
					tree_expanded = false;
					expand_button.setText("<");
				}
				
			}
		});
		*/
		
		//Maybe resize here
		setBackground(new Color(0));
		
		this.setLayout(null);
		//Canvas
		GLView = new Canvas();
		add(GLView);
		GLView.setFocusable(true);
		
		GLView.setSize(width, height);
		
		popup = new DisassemblyMenu(getWidth()/2, getHeight(), this);
		add(popup);
		
		Display.setParent(GLView);
		Display.create();
		
		showDisassemblyMenu();
		//hideDisassemblyMenu("9161-HEATER");

		//Create a texture holder
		texture = new TextureList();
		
		//A list for storing all the entities
		entity = new EntityList();
		
		//Renderer for drawing stuff
		render = new Renderer(texture, entity);
		
		//setup the initial perspective
		render.initGL();
	
		//Camera
		camera = new Camera(width,height);
		
		//Create inputs
		input = new Input(camera, entity);

		//Renderer also needs references to the editor and camera
		render.addReferences(camera);
	}
}