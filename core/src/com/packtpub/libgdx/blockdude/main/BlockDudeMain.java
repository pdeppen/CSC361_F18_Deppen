package com.packtpub.libgdx.blockdude.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.packtpub.libgdx.blockdude.game.WorldController;
import com.packtpub.libgdx.blockdude.game.WorldRenderer;
import com.badlogic.gdx.assets.AssetManager;
import com.packtpub.libgdx.blockdude.game.Assets;

/**
 * Created by Philip Deppen (Milestone 1, 10/29/18)
 * Contains game loop and handles major aspects of game
 */
public class BlockDudeMain implements ApplicationListener {

	private static final String TAG = BlockDudeMain.class.getName();
	
	private WorldController worldController;
    private WorldRenderer worldRenderer;
    private boolean paused;
    
	/**
	 * Created by Philip Deppen (Milestone 1, 10/29/18)
	 */
	@Override
	public void create() {
		// Set Libgdx log level to DEBUG
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		// load assets
		Assets.instance.init(new AssetManager());
		
		// Initialize controller and renderer
		worldController = new WorldController();
		worldRenderer = new WorldRenderer(worldController);	
	}

	/**
	 * Created by Philip Deppen (Milestone 1, 10/29/18)
	 */
	@Override
	public void resize(int width, int height) 
	{
		worldRenderer.resize(width, height);
	}

	/**
	 * Created by Philip Deppen (Milestone 1, 10/29/18)
	 */
	@Override
	public void render() 
	{
		// Do not update game when paused
		if (!paused) 
		{
			// Update game world by the time that has passed
			// since last rendered frame.
			worldController.update(Gdx.graphics.getDeltaTime());
		}
		
		// Sets the clear screen color 
		Gdx.gl.glClearColor(0xff/255.0f, 0xff/255.0f, 0x66/255.0f, 0xff/255.0f);
		
		// Clears the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Render game world to screen
		worldRenderer.render();
		
	}

	/**
	 * Created by Philip Deppen (Milestone 1, 10/29/18)
	 */
	@Override
	public void pause() 
	{
		paused = true;
	}

	/**
	 * Created by Philip Deppen (Milestone 1, 10/29/18)
	 */
	@Override
	public void resume() 
	{
		Assets.instance.init(new AssetManager());
		paused = false;
	}

	/**
	 * Created by Philip Deppen (Milestone 1, 10/29/18)
	 */
	@Override
	public void dispose() 
	{
		worldRenderer.dispose();
		Assets.instance.dispose();
	}

}
