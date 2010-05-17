package ivec;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.vecmath.Vector3f;


import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

class Input {
	private int deltaX, deltaY;	
	private Camera camera;
	//private Window window;
	//private EntityList entity;
	//private Vector3f player_velocity;
	
	public Input(Camera _camera, EntityList _entity) throws LWJGLException {
		camera=_camera;
		//physics=_physics;
		//editor=_editor;
		//entity=_entity;
		//player=_player;
		
		//player_velocity = new Vector3f();
		
		Mouse.create();
		Mouse.setNativeCursor(null);
		Keyboard.create();
		Keyboard.enableRepeatEvents(true);
	}

	public void handleMouse() throws LWJGLException, FileNotFoundException, IOException {
		//Handle Mouse Events here
		while(Mouse.next())	{
			Mouse.poll();
			
			//update the changes in position
			deltaX = Mouse.getEventDX();
			deltaY = Mouse.getEventDY();

			switch(Mouse.getEventButton()) {
				case -1://Mouse Movement
					if(Mouse.isInsideWindow()) {
						if(Mouse.isButtonDown(0)) {
							//Pan camera Z
							if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) ) {
								camera.incrementDistance(-1.0f*deltaY);	
							} else {
								camera.incrementDeclination(-deltaY*.01f);
								camera.incrementRotation(-deltaX*.01f);
							}
						}
						
						if(Mouse.isButtonDown(1)) {
							//Change angle of camera
	
						}
						
						if(Mouse.isButtonDown(2)) {
							//Change Perspective

							camera.moveFocus( new Vector3f(-0.01f*deltaX, -0.01f*deltaY, 0.0f) );
						}
					}
					break;
				case 0://Left Button
					if( Mouse.isButtonDown(0) )	{
						
					} else {
						
					}
					break;
				case 1://Right Button
					break;
				case 2://Middle Button
					break;
			}

			switch(Mouse.getDWheel()) {
				case -120: 
					//move focus
					
					//update current layer
					camera.incrementDistance(0.1f);
					break;
				case  120: 
					//update current layer
					camera.incrementDistance(-0.1f);
					break;
			}
		}
	}
	/*public void handleMouse() throws LWJGLException, FileNotFoundException, IOException {
		//Handle Mouse Events here
		while(Mouse.next())	{
			Mouse.poll();
			
			//update the changes in position
			deltaX = Mouse.getEventDX();
			deltaY = Mouse.getEventDY();
			

			switch(Mouse.getEventButton()) {
				case -1://Mouse Movement
					if(Mouse.isInsideWindow()) {
						//editor.setCurrentBlock(Mouse.getX(), Mouse.getY(), window.getEditorView().getLayer(), camera);
						if(Mouse.isButtonDown(0)) {
							//Pan camera Z
							if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) ) {
								camera.incrementDistance(-10f*deltaY);	
							} else {
								//Change Perspective
								camera.updateRotation(-0.5f*deltaY,0.5f*deltaX,0);
								//System.out.print(deltaX + " " + deltaY + "\n");
							}
						}
						
						if(Mouse.isButtonDown(1)) {
							//Change angle of camera
	
						}
						
						if(Mouse.isButtonDown(2)) {
							camera.moveFocus( new Vector3f(-0.01f*deltaX, -0.01f*deltaY, 0.0f) );
						}
					}
					break;
				case 0://Left Button
					if( Mouse.isButtonDown(0) )	{
						
					} else {
						
					}
					break;
				case 1://Right Button
					if( !(Mouse.isButtonDown(1)) ) {
						//entity.addEntity(editor.getCurrentBlock());
					}
					break;
				case 2://Middle Button
					if( !(Mouse.isButtonDown(1)) ) {

					}
					break;
			}

			switch(Mouse.getDWheel()) {
				case -120: 
					camera.incrementDistance(0.1f);
					break;
				case  120:
					camera.incrementDistance(-0.1f);
					break;
			}
		}
	}*/
}