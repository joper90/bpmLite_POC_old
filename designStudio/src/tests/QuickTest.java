package tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.renderer.Renderer;

public class QuickTest extends BasicGame{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Running");
		 try { 
	            Renderer.setRenderer(Renderer.VERTEX_ARRAY_RENDERER); 
	             
	            AppGameContainer container = new AppGameContainer(new QuickTest()); 
	            container.setDisplayMode(800,600,false); 
	            container.start(); 
	        } catch (SlickException e) { 
	            e.printStackTrace(); 
	        } 
	}
	
	public QuickTest()
	{
		super("QuickTest");
	}

	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		// 
		
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		// 
		
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		// 
		
	}

}
