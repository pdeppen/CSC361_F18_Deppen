package com.packtpub.libgdx.blockdude.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.packtpub.libgdx.blockdude.util.Constants;

/**
 * Created by Philip Deppen (Milestone 1, 10/29/18)
 * renders game screen
 */
public class WorldRenderer implements Disposable
{
	private OrthographicCamera camera;
    private SpriteBatch batch;
    private WorldController worldController;
    
    /**
     * Created by Philip Deppen (Milestone 1, 10/29/18)
     * constructor
     * @param worldController
     */
    public WorldRenderer (WorldController worldController) 
    {
    		this.worldController = worldController;
    		init();
    }
    
    /**
     * Created by Philip Deppen (Milestone 1, 10/29/18)
     */
    private void init () 
    {
    		batch = new SpriteBatch();
    		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
    		camera.position.set(0, 0, 0);
    		camera.update();
    }
    
    /**
     * Created by Philip Deppen (Milestone 1, 10/29/18)
     * Defines in which order the game objects are drawn over others
     */
    public void render () 
    {
    		
    }
    
    /**
     * Created by Philip Deppen (Milestone 1, 10/29/18)
     * this method is called when the screen size is changed
     * @param width
     * @param height
     */
    public void resize (int width, int height) 
    { 
    		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
    		camera.update();
    }
    
    /**
     * Created by Philip Deppen (Milestone 1, 10/29/18)
     * frees allocated memory
     */
    @Override 
    public void dispose () 
    { 
    		batch.dispose();
    }
}
